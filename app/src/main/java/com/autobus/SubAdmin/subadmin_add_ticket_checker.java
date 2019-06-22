package com.autobus.SubAdmin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
import com.autobus.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class subadmin_add_ticket_checker extends AppCompatActivity {

    private static String URL_SEND = "http://192.168.10.5/AutoBus/add_ticket_checker.php";
    EditText ticket_checker_uname, ticket_checker_password, ticket_checker_id, ticket_checker_phone, bus_number;
    Button add_ticket_checker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subadmin_add_ticket_checker_activity);

        Toolbar toolbar_default = findViewById(R.id.toolbar_subadmin3);
        setSupportActionBar(toolbar_default);
        getSupportActionBar().setTitle("Add Ticket Checker");
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        ticket_checker_uname = findViewById(R.id.ticket_checker_uname);
        ticket_checker_password = findViewById(R.id.ticket_checker_password);
        ticket_checker_id = findViewById(R.id.ticket_checker_id);
        ticket_checker_phone = findViewById(R.id.ticket_checker_phone);
        bus_number = findViewById(R.id.ticket_checker_bus_no);
        add_ticket_checker = findViewById(R.id.save_ticket_checker);

        add_ticket_checker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_new_ticket_checker();
            }
        });
    }

    private void add_new_ticket_checker() {

        if (ticket_checker_uname.getText().toString().isEmpty() || ticket_checker_password.getText().toString().isEmpty() ||
                ticket_checker_id.getText().toString().isEmpty() || ticket_checker_phone.getText().toString().isEmpty() ||
                bus_number.getText().toString().isEmpty()) {

            Toast.makeText(this, "Enter All The Data First!", Toast.LENGTH_SHORT).show();
            ticket_checker_uname.requestFocus();
            ticket_checker_uname.setError("Enter Data");

        } else {

            final String ticket_checker_unameS = this.ticket_checker_uname.getText().toString().trim();
            final String ticket_checker_passwordS = this.ticket_checker_password.getText().toString().trim();
            final String ticket_checker_idS = this.ticket_checker_id.getText().toString().trim();
            final String ticket_checker_phoneS = this.ticket_checker_phone.getText().toString().trim();
            final String bus_numberS = this.bus_number.getText().toString().trim();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SEND,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");

                                if (success.equals("1")) {

                                    Toast.makeText(subadmin_add_ticket_checker.this, "Data Entered Successfully", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(subadmin_add_ticket_checker.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(subadmin_add_ticket_checker.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    params.put("tk_checker_uname", ticket_checker_unameS);
                    params.put("tk_checker_password", ticket_checker_passwordS);
                    params.put("tk_checker_id", ticket_checker_idS);
                    params.put("tk_checker_phone", ticket_checker_phoneS);
                    params.put("bus_number", bus_numberS);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
