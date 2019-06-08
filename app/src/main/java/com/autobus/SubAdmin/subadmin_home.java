package com.autobus.SubAdmin;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.autobus.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class subadmin_home extends AppCompatActivity {

    FloatingActionMenu floatingActionMenu;
    FloatingActionButton add_ticketChecker, add_driver, add_details;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subadmin_home_activity);

        TabLayout tabLayout = findViewById(R.id.tab_days);
        ViewPager viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new DriverFragment(), "");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.manager);

        floatingActionMenu = findViewById(R.id.fab_menu);
        add_driver = findViewById(R.id.add_driver);
        add_ticketChecker = findViewById(R.id.add_ticketChecker);
        add_details = findViewById(R.id.add_detail);

        Toolbar toolbar_default = findViewById(R.id.toolbar_subadmin2);
        setSupportActionBar(toolbar_default);
        getSupportActionBar().setTitle("SubAdmin Dashboard");
        getSupportActionBar().setElevation(0);

        //Fetching email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config_subadmin.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(Config_subadmin.EMAIL_SHARED_PREF, "Not Available");
        dialog = new Dialog(this);

        add_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(subadmin_home.this, subadmin_add_bus.class);
                startActivity(i);
            }
        });

        add_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(subadmin_home.this, subadmin_add_driver.class);
                startActivity(i);
            }
        });

        add_ticketChecker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(subadmin_home.this, subadmin_add_ticket_checker.class);
                startActivity(i);
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
                        SharedPreferences preferences = getSharedPreferences(Config_subadmin.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config_subadmin.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config_subadmin.EMAIL_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting subadmin activity
                        Intent intent = new Intent(subadmin_home.this, subadmin_login.class);
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
}
