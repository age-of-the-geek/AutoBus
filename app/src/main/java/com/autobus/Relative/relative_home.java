package com.autobus.Relative;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.autobus.Admin.Config_Admin;
import com.autobus.Driver.User;
import com.autobus.R;
import com.autobus.RelativeTrackActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class relative_home extends AppCompatActivity {

    TextView textName;
    FirebaseAuth mAuth;
    Button track;
    EditText code;
    public static final String MATCH_URL = "http://192.168.43.197/AutoBus/match_qr.php";
    public static final int LOGIN_PERMISSION = 1000;
    public static final String NODE_USERS = "users";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_home_activity);

        mAuth = FirebaseAuth.getInstance();
        FirebaseMessaging.getInstance().subscribeToTopic("updates");


        textName = findViewById(R.id.textViewName);
        code = findViewById(R.id.ticket_code);
        track = findViewById(R.id.track);

        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Tcode = code.getText().toString().trim();
                if (!Tcode.isEmpty())
                    matchQR(Tcode);
                else
                    Toast.makeText(relative_home.this, "Enter Code", Toast.LENGTH_SHORT).show();
            }
        });


        Toolbar toolbar_default = findViewById(R.id.toolbar_relative);
        setSupportActionBar(toolbar_default);
        getSupportActionBar().setTitle("Relative Monitoring");

        FirebaseUser user = mAuth.getCurrentUser();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(relative_home.this, "Error"
                                    + task.getException(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();


                        saveToken(token);
                    }
                });
    }

    private void matchQR(String code) {

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MATCH_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if (response.equalsIgnoreCase(Config_Admin.LOGIN_SUCCESS)) {


                            //Starting profile activity
                            Toast.makeText(relative_home.this, "Match Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(relative_home.this, RelativeTrackActivity.class));
                        } else {
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(relative_home.this,
                                    "Invalid Code", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(relative_home.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                        //You can handle error here if you want
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("ticket_code", code);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void saveToken(String token) {

        String email = mAuth.getCurrentUser().getEmail();
        User user = new User(email, token);
        DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference(NODE_USERS);

        dbUsers.child(mAuth.getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(relative_home.this, "Token Saved", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
//
        //if the user is not logged in
        //opening the login activity
        /*if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(relative_home.this, relative_signup.class));
        }*/
    }
}