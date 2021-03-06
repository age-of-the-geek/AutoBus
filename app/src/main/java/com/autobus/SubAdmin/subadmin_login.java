package com.autobus.SubAdmin;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class subadmin_login extends AppCompatActivity implements View.OnClickListener {

    //Defining views
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    String email, password, bus_company, logo;


    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subadmin_login_activity);

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

    /*@Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config_Driver.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config_Driver.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if (loggedIn) {
            //We will start the Profile Activity
            Intent intent = new Intent(com.autobus.SubAdmin.subadmin_login.this,
                    subadmin_home.class);
            startActivity(intent);
        }
    }*/

    private void login() {


        //Getting values from edit texts
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config_subadmin.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if (response.equalsIgnoreCase(Config_subadmin.LOGIN_SUCCESS)) {
                            /*//Creating a shared preference
                            SharedPreferences sharedPreferences = com.autobus.SubAdmin.subadmin_login.this.getSharedPreferences(Config_subadmin.SHARED_PREF_NAME,
                                    Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to editor
                            editor.putBoolean(Config_subadmin.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config_subadmin.EMAIL_SHARED_PREF, email);

                            //Saving values to editor
                            editor.commit();*/

                            //Starting profile activity
                            Toast.makeText(com.autobus.SubAdmin.subadmin_login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(com.autobus.SubAdmin.subadmin_login.this,
                                    subadmin_home.class);
                            startActivity(intent);
                        } else {
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(com.autobus.SubAdmin.subadmin_login.this,
                                    "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        Toast.makeText(subadmin_login.this, "Network Connection Error. Try again Later/n " +
                                "" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config_subadmin.KEY_EMAIL, email);
                params.put(Config_subadmin.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void load_companyName(String Email, String Password) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config_subadmin.LOAD_LOGO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject data = array.getJSONObject(i);

                                bus_company = data.getString("bus_company");
                                logo = data.getString("bus_logo");

                                Intent intent = new Intent(subadmin_login.this, subadmin_home.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("BusCompany", bus_company);
                                bundle.putString("Logo", logo);
                                intent.putExtras(bundle);
                                startActivity(intent);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(subadmin_login.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(subadmin_login.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();


                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("subadmin_uname", Email);
                params.put("subadmin_password", Password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onClick(View v) {
        //Calling the tk_checker_login function
        login();
        load_companyName(email, password);

    }
}