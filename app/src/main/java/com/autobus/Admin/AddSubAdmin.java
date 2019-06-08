package com.autobus.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.autobus.SubAdmin.subadmin_add_ticket_checker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddSubAdmin extends AppCompatActivity {

    private static String URL_SEND = "http://192.168.10.17/AutoBus/add_subadmin.php";
    EditText subadmin_uname, subadmin_password, subadmin_id, subadmin_phone, bus_company;
    Button add_subadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sub_admin_activity);

        Toolbar toolbar_default = findViewById(R.id.toolbar_admin2);
        setSupportActionBar(toolbar_default);
        getSupportActionBar().setTitle("Add Sub Admin");
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        subadmin_uname = findViewById(R.id.subadmin_uname);
        subadmin_password = findViewById(R.id.subadmin_password);
        subadmin_id = findViewById(R.id.subadmin_id);
        subadmin_phone = findViewById(R.id.subadmin_phone);
        bus_company = findViewById(R.id.subadmin_bus_company);
        add_subadmin = findViewById(R.id.save_subadmin);

        add_subadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_new_subadmin();
            }
        });
    }

    private void add_new_subadmin() {

        final String subadmin_unameS = this.subadmin_uname.getText().toString().trim();
        final String subadmin_passwordS = this.subadmin_password.getText().toString().trim();
        final String subadmin_idS = this.subadmin_id.getText().toString().trim();
        final String subadmin_phoneS = this.subadmin_phone.getText().toString().trim();
        final String bus_companyS = this.bus_company.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SEND,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {

                                Toast.makeText(AddSubAdmin.this, "Data Entered Successfully", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AddSubAdmin.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddSubAdmin.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("subadmin_uname", subadmin_unameS);
                params.put("subadmin_password", subadmin_passwordS);
                params.put("subadmin_id", subadmin_idS);
                params.put("subadmin_phone", subadmin_phoneS);
                params.put("bus_company", bus_companyS);
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
