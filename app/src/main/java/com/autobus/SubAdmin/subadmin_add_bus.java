package com.autobus.SubAdmin;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import com.autobus.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class subadmin_add_bus extends AppCompatActivity {


    Button uploadimage;
    private TextView usrname;
    private EditText bus_number, total_seats, available_seats, bus_route, bus_leaving_time, bus_reaching_time,
            bus_driver_name, bus_ticketchecker_name, bus_rating, bus_break_time, bus_company, day;
    private AppCompatButton savebtn;
    private static String URL_SEND = "http://192.168.10.5/AutoBus/bus_details.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subadmin_add_bus_activity);

        Toolbar toolbar_default = findViewById(R.id.toolbar_subadmin);
        setSupportActionBar(toolbar_default);
        getSupportActionBar().setTitle("Add Bus Details");
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        bus_number = findViewById(R.id.bus_number);
        total_seats = findViewById(R.id.total_seats);
        available_seats = findViewById(R.id.available_seats);
        bus_route = findViewById(R.id.route);
        bus_leaving_time = findViewById(R.id.leaving_time);
        bus_reaching_time = findViewById(R.id.reaching_time);
        bus_driver_name = findViewById(R.id.driver_name);
        bus_ticketchecker_name = findViewById(R.id.tk_checker_name);
        bus_rating = findViewById(R.id.rating);
        bus_break_time = findViewById(R.id.break_time);
        bus_company = findViewById(R.id.bus_company);
        //usrname = findViewById(R.id.usrname);
        day = findViewById(R.id.day);
        savebtn = findViewById(R.id.btn_save);


        //Showing the current logged in email to textview
        // usrname.setText("User: " + email);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });


    }


    public void open_popup(View v) {
        Intent i = new Intent(subadmin_add_bus.this, UploadImage.class);
        startActivity(i);
    }

    private void saveData() {


        final String bus_companyS = this.bus_company.getText().toString().trim();
        final String bus_numberS = this.bus_number.getText().toString().trim();
        final String total_seatsS = this.total_seats.getText().toString().trim();
        final String available_seatsS = this.available_seats.getText().toString().trim();
        final String bus_routeS = this.bus_route.getText().toString().trim();
        final String bus_leaving_timeS = this.bus_leaving_time.getText().toString().trim();
        final String bus_reaching_timeS = this.bus_reaching_time.getText().toString().trim();
        final String bus_driver_nameS = this.bus_driver_name.getText().toString().trim();
        final String bus_ratingS = this.bus_rating.getText().toString().trim();
        final String bus_ticketchecker_nameS = this.bus_ticketchecker_name.getText().toString().trim();
        final String bus_break_timeS = this.bus_break_time.getText().toString().trim();
        final String dayS = this.day.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SEND,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {

                                Toast.makeText(subadmin_add_bus.this, "Data Entered Successfully", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(subadmin_add_bus.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(subadmin_add_bus.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("bus_company", bus_companyS);
                params.put("bus_break_time", bus_break_timeS);
                params.put("bus_rating", bus_ratingS);
                params.put("bus_ticketchecker_name", bus_ticketchecker_nameS);
                params.put("bus_driver_name", bus_driver_nameS);
                params.put("bus_reaching_time", bus_reaching_timeS);
                params.put("bus_leaving_time", bus_leaving_timeS);
                params.put("bus_route", bus_routeS);
                params.put("available_seats", available_seatsS);
                params.put("total_seats", total_seatsS);
                params.put("bus_number", bus_numberS);
                params.put("day", dayS);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}
