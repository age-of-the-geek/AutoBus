package com.autobus.Passenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.autobus.R;
import com.bumptech.glide.Glide;

public class BusDetailsList extends AppCompatActivity {

    TextView bus_number, total_seats, available_seats, bus_route, bus_leaving_time, bus_reaching_time,
            bus_driver_name, bus_ticketchecker_name, bus_rating, bus_break_time, bus_company, bus_day;
    ImageView imageView;
    Button cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_detail_list);

        bus_company = findViewById(R.id.bus_company);
        bus_number = findViewById(R.id.bus_number);
        total_seats = findViewById(R.id.total_seats);
        available_seats = findViewById(R.id.available_seats);
        bus_route = findViewById(R.id.route);
        bus_leaving_time = findViewById(R.id.leaving_time);
        bus_reaching_time = findViewById(R.id.reaching_time);
        bus_driver_name = findViewById(R.id.driver_name);
        bus_ticketchecker_name = findViewById(R.id.tk_checker_name);
        bus_break_time = findViewById(R.id.break_time);
        bus_rating = findViewById(R.id.rating);
        bus_day = findViewById(R.id.day);
        imageView = findViewById(R.id.bus_image);
        cart = findViewById(R.id.cart);

        Toolbar toolbar_default = findViewById(R.id.toolbar_passenger);
        setSupportActionBar(toolbar_default);
        getSupportActionBar().setTitle("AutoBus");

        // Recieve data
        Intent intent = getIntent();
        String Sbus_company = intent.getExtras().getString("Company");
        String Sbus_number = intent.getExtras().getString("Number");
        String Stotal_seats = intent.getExtras().getString("TotalSeats");
        String Savailable_seats = intent.getExtras().getString("AvailableSeats");
        String Sbus_route = intent.getExtras().getString("Route");
        String Sbus_leaving_time = intent.getExtras().getString("LeavingTime");
        String Sbus_reaching_time = intent.getExtras().getString("ReachingTime");
        String Sbus_driver_name = intent.getExtras().getString("DriverName");
        String Sbus_ticketchecker_name = intent.getExtras().getString("TicketCheckerName");
        String Sbus_break_time = intent.getExtras().getString("BreakTime");
        String Sbus_rating = intent.getExtras().getString("Rating");
        String Sbus_day = intent.getExtras().getString("Day");

        Bundle bundle=getIntent().getExtras();
        String bus_imageView = bundle.getString("Image");



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
        bus_rating.setText(Sbus_rating);
        bus_day.setText(Sbus_day);
        bus_route.setText(Sbus_route);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BusDetailsList.this, GenerateQRCode.class);
                startActivity(i);
                finish();
            }
        });

    }
}