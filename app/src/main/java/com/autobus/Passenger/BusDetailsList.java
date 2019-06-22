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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.autobus.NearbyBusStand;
import com.autobus.R;
import com.autobus.SubAdmin.subadmin_add_bus;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hsalf.smilerating.SmileRating;

public class BusDetailsList extends AppCompatActivity {

    TextView bus_number, total_seats, available_seats, route_from, route_to, bus_leaving_time, bus_reaching_time,
            bus_driver_name, bus_ticketchecker_name, bus_rating, bus_break_time, bus_company, bus_day, bus_ticket_price;
    ImageView imageView;
    ImageButton cart;
    Dialog mDialog;
    SmileRating smileRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_detail_list);

        mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.rating_dialog);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        smileRating = mDialog.findViewById(R.id.smile_rating);
        mDialog.show();
        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                String rating = Integer.toString(level);
                Intent intent = new Intent(BusDetailsList.this, subadmin_add_bus.class);
                intent.putExtra("Rating",rating);
                startActivity(intent);

                Toast.makeText(BusDetailsList.this, rating, Toast.LENGTH_SHORT).show();
            }
        });
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
        bus_rating = findViewById(R.id.rating);
        bus_day = findViewById(R.id.day);
        imageView = findViewById(R.id.imageView);
        cart = findViewById(R.id.cart);

        Toolbar toolbar_default = findViewById(R.id.toolbar_passenger);
        setSupportActionBar(toolbar_default);
        getSupportActionBar().setTitle("AutoBus");
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Recieve data
        Intent intent = getIntent();
        String Sbus_company = intent.getExtras().getString("Company");
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
                Intent i = new Intent(BusDetailsList.this, NearbyBusStand.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}