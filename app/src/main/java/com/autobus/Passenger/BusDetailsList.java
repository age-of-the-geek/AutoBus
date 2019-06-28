package com.autobus.Passenger;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hsalf.smilerating.SmileRating;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BusDetailsList extends AppCompatActivity {

    TextView bus_number, total_seats, available_seats, route_from, route_to, bus_leaving_time, bus_reaching_time,
            bus_driver_name, bus_ticketchecker_name, bus_break_time, bus_company, bus_day, bus_ticket_price;
    ImageView imageView;
    Button cart;
    SmileRating smileRating;
    Dialog mDialog;
    String Sbus_company;
    RatingBar bus_rating;
    int rate;
    private static String URL_SEND = "http://192.168.43.197/AutoBus/save_rating.php";
    private static String URL_GET = "http://192.168.43.197/AutoBus/get_rating.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_detail_list);


        bus_ticket_price = findViewById(R.id.bus_ticket_price);
        bus_company = findViewById(R.id.bus_company);
        bus_number = findViewById(R.id.bus_number);
        total_seats = findViewById(R.id.total_seats);
        available_seats = findViewById(R.id.available_seats);
        route_from = findViewById(R.id.route_from);
        route_to = findViewById(R.id.route_to);
        bus_leaving_time = findViewById(R.id.leaving_time);
        bus_reaching_time = findViewById(R.id.reaching_time);
        bus_driver_name = findViewById(R.id.driver_name);
        bus_ticketchecker_name = findViewById(R.id.tk_checker_name);
        bus_break_time = findViewById(R.id.break_time);
        bus_day = findViewById(R.id.day);
        imageView = findViewById(R.id.imageView);
        cart = findViewById(R.id.cart);
        bus_rating = findViewById(R.id.bus_rating);

        Toolbar toolbar_default = findViewById(R.id.toolbar_passenger);
        setSupportActionBar(toolbar_default);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Recieve data
        Intent intent = getIntent();
        Sbus_company = intent.getExtras().getString("Company");
        String Sbus_number = intent.getExtras().getString("Number");
        String Stotal_seats = intent.getExtras().getString("TotalSeats");
        String Savailable_seats = intent.getExtras().getString("AvailableSeats");
        String Sbus_route_from = intent.getExtras().getString("RouteFrom");
        String Sbus_route_to = intent.getExtras().getString("RouteTo");
        String Sbus_leaving_time = intent.getExtras().getString("LeavingTime");
        String Sbus_reaching_time = intent.getExtras().getString("ReachingTime");
        String Sbus_driver_name = intent.getExtras().getString("DriverName");
        String Sbus_ticketchecker_name = intent.getExtras().getString("TicketCheckerName");
        String Sbus_break_time = intent.getExtras().getString("BreakTime");
        String Sbus_day = intent.getExtras().getString("Day");
        String Sbus_ticket_price = intent.getExtras().getString("TicketPrice");
        String bus_imageView = intent.getExtras().getString("Image");

        getRating(Sbus_company);
        // Loading Image with Glide
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo);

        Glide.with(this)
                .load(bus_imageView)
                .apply(options)
                .into(imageView);

        bus_company.setText(Sbus_company);
        bus_number.setText(Sbus_number);
        total_seats.setText(Stotal_seats);
        available_seats.setText(Savailable_seats);
        bus_leaving_time.setText(Sbus_leaving_time);
        bus_reaching_time.setText(Sbus_company);
        bus_reaching_time.setText(Sbus_reaching_time);
        bus_driver_name.setText(Sbus_driver_name);
        bus_ticketchecker_name.setText(Sbus_ticketchecker_name);
        bus_break_time.setText(Sbus_break_time);
        bus_day.setText(Sbus_day);
        route_from.setText(Sbus_route_from);
        route_to.setText(Sbus_route_to);
        bus_ticket_price.setText(Sbus_ticket_price);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BusDetailsList.this, BuyTicket.class);
                Bundle bundle = new Bundle();
                bundle.putString("Price", Sbus_ticket_price);
                i.putExtras(bundle);
                startActivity(i);
                finish();
            }
        });

    }

    private void getRating(String company) {
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_GET,
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

                                String rating = data.getString("rating");
                                rate = Integer.parseInt(rating);
                                bus_rating.setRating(Float.parseFloat(rating));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(BusDetailsList.this, "Error"
                                    + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BusDetailsList.this, "Error"
                                + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("company_name", company);
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

    @Override
    public void onBackPressed() {
        if (!isTaskRoot()) {
            mDialog = new Dialog(this);
            mDialog.setContentView(R.layout.rating_dialog);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            smileRating = mDialog.findViewById(R.id.smile_rating);
            mDialog.show();
            smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
                @Override
                public void onRatingSelected(int level, boolean reselected) {
                    String rating = Integer.toString(level);
                    saveRating(Sbus_company, rating);
                    mDialog.dismiss();
                }
            });
        } else super.onBackPressed();
    }

    private void saveRating(String sbus_company, String rating) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SEND,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {

                                Toast.makeText(BusDetailsList.this, "Data Entered Successfully", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(BusDetailsList.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BusDetailsList.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("company_name", sbus_company);
                params.put("rating", rating);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}