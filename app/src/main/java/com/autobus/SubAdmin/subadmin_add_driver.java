package com.autobus.SubAdmin;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class subadmin_add_driver extends AppCompatActivity {

    private static String URL_SEND = "http://192.168.10.4/AutoBus/add_driver.php";
    EditText driver_uname, driver_password, driver_id, driver_phone, bus_number;
    Button add_driver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subadmin_add_driver_activity);

        Toolbar toolbar_default = findViewById(R.id.toolbar_subadmin2);
        setSupportActionBar(toolbar_default);
        getSupportActionBar().setTitle("Add Driver");
        if (getSupportActionBar()!= null){

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        driver_uname = findViewById(R.id.driver_uname);
        driver_password = findViewById(R.id.driver_password);
        driver_id = findViewById(R.id.driver_id);
        driver_phone = findViewById(R.id.driver_phone);
        bus_number = findViewById(R.id.driver_bus_no);
        add_driver = findViewById(R.id.save_driver);

        add_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_new_driver();
            }
        });
    }

    private void add_new_driver() {

        final String driver_unameS = this.driver_uname.getText().toString().trim();
        final String driver_passwordS = this.driver_password.getText().toString().trim();
        final String driver_idS = this.driver_id.getText().toString().trim();
        final String driver_phoneS = this.driver_phone.getText().toString().trim();
        final String bus_numberS = this.bus_number.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SEND,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {

                                Toast.makeText(subadmin_add_driver.this, "Data Entered Successfully", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(subadmin_add_driver.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(subadmin_add_driver.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("driver_uname", driver_unameS);
                params.put("driver_password", driver_passwordS);
                params.put("driver_id", driver_idS);
                params.put("driver_phone", driver_phoneS);
                params.put("bus_number", bus_numberS);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
