package com.autobus.Passenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.autobus.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bus_Details extends AppCompatActivity {

    Toolbar toolbar;
    private static final String URL_PRODUCTS = "http://192.168.43.197/AutoBus/show_bus_details.php";
    private static final String GET_LOGO = "http://192.168.43.197/AutoBus/add_subadmin.php?apicall=getlogo";

    //a list to store all the products
    List<bus_data> productList;

    //the recyclerview
    RecyclerView recyclerView;

    String from, to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_details);


        toolbar = findViewById(R.id.passenger_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            from = bundle.getString("from");
            to = bundle.getString("to");

        } else Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        //initializing the productlist
        productList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts(from, to);
    }


    private void loadProducts(String From, String To) {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
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

                                if (data.getString("bus_from").length() == 0) {
                                    Toast.makeText(Bus_Details.this, "No Bus Found", Toast.LENGTH_SHORT).show();
                                }

                                //adding the product to product list
                                productList.add(new bus_data(

                                        data.getString("bus_number"),
                                        data.getString("bus_total_seats"),
                                        data.getString("bus_available_seats"),
                                        data.getString("bus_from"),
                                        data.getString("bus_to"),
                                        data.getString("bus_leaving_time"),
                                        data.getString("bus_reaching_time"),
                                        data.getString("bus_driver_name"),
                                        data.getString("bus_ticketchecker_name"),
                                        data.getString("bus_break_time"),
                                        data.getString("bus_company"),
                                        data.getString("bus_image"),
                                        data.getString("day"),
                                        data.getString("ticket_price"),
                                        data.getString("bus_logo")

                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            adapter_class adapter = new adapter_class(Bus_Details.this, productList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Bus_Details.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Bus_Details.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();


                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("bus_from", From);
                params.put("bus_to", To);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void logout(View v) {

        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(Bus_Details.this, passenger_auth.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}

