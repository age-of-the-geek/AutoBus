package com.autobus.Admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.autobus.Driver.Config_Driver;
import com.autobus.R;
import com.autobus.SubAdmin.Config_subadmin;

import java.util.HashMap;
import java.util.Map;

public class admin_login extends AppCompatActivity implements View.OnClickListener {

    //Defining views
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;

    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login_activity);

        //Initializing views
        editTextEmail = (EditText) findViewById(R.id.UEmail);
        editTextPassword = (EditText) findViewById(R.id.UPassword);

        buttonLogin = (Button) findViewById(R.id.loginbtn);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/Raleway-Regular.ttf");
        editTextEmail.setTypeface(typeface);
        editTextPassword.setTypeface(typeface);

        //Adding click listener
        buttonLogin.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config_Admin.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config_Admin.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if (loggedIn) {
            //We will start the Profile Activity
            Intent intent = new Intent(com.autobus.Admin.admin_login.this,
                    com.autobus.Admin.admin_home.class);
            startActivity(intent);
        }
    }

    private void login() {
        //Getting values from edit texts
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config_Admin.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if (response.equalsIgnoreCase(Config_Admin.LOGIN_SUCCESS)) {
                            //Creating a shared preference
                            SharedPreferences sharedPreferences = com.autobus.Admin.admin_login.this.getSharedPreferences(Config_Admin.SHARED_PREF_NAME,
                                    Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to editor
                            editor.putBoolean(Config_Admin.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config_Admin.EMAIL_SHARED_PREF, email);

                            //Saving values to editor
                            editor.commit();

                            //Starting profile activity
                            Toast.makeText(com.autobus.Admin.admin_login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(com.autobus.Admin.admin_login.this,
                                    com.autobus.Admin.admin_home.class);
                            startActivity(intent);
                        } else {
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(com.autobus.Admin.admin_login.this,
                                    "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(admin_login.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();
                        //You can handle error here if you want
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config_Admin.KEY_EMAIL, email);
                params.put(Config_Admin.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        //Calling the tk_checker_login function
        login();
    }
}