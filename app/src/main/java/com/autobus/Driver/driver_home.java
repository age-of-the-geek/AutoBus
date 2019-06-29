package com.autobus.Driver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.autobus.Activities.landing_screen;
import com.autobus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class driver_home extends AppCompatActivity {

    private TextView usrname, textView;
    public static final String CHANNEL_ID = "autobus";
    private static final String CHANNEL_NAME = "AutoBus";
    private static final String CHANNEL_DESC = "AutoBus Notifications";
    private List<User> userList;
    FirebaseAuth mAuth;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    Button track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_home_activity);

        Toolbar toolbar_default = findViewById(R.id.toolbar_driver);
        setSupportActionBar(toolbar_default);
        getSupportActionBar().setTitle("Driver Dashboard");

        usrname = findViewById(R.id.usrname);
        progressBar = findViewById(R.id.Nprogressbar);
        track = findViewById(R.id.track_btn);

        mAuth = FirebaseAuth.getInstance();

        //Fetching email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config_Driver.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(Config_Driver.EMAIL_SHARED_PREF, "Not Available");

        //Showing the current logged in email to textview
        usrname.setText("User: " + email);
        loadUsers();
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(driver_home.this, TrackerActivity.class));
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


    }


    private void loadUsers() {

        progressBar.setVisibility(View.VISIBLE);
        userList = new ArrayList<>();
        recyclerView = findViewById(R.id.Nrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference("users");
        dbUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                if (dataSnapshot.exists()) {

                    for (DataSnapshot dsUser : dataSnapshot.getChildren()) {

                        User user = dsUser.getValue(User.class);
                        userList.add(user);

                    }

                    UserAdapter adapter = new UserAdapter(driver_home.this, userList);
                    recyclerView.setAdapter(adapter);

                } else {
                    Toast.makeText(driver_home.this, "No user found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //Logout function
    private void logout() {
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config_Driver.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config_Driver.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config_Driver.EMAIL_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting tk_checker_login activity
                        Intent intent = new Intent(driver_home.this, driver_login.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adding our menu to toolbar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuLogout) {
            //calling logout method when the logout button is clicked
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(driver_home.this, landing_screen.class);
        startActivity(intent);
        finish();
        super.onBackPressed();  // optional depending on your needs
    }
}