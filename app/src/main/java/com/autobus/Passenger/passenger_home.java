package com.autobus.Passenger;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.autobus.R;
import com.google.firebase.auth.FirebaseAuth;

public class passenger_home extends AppCompatActivity {

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_passenger_activity);

        txt= (TextView)findViewById(R.id.txt);




    }

    public void logout (View v){

        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(passenger_home.this, passenger_auth.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);


    }
}
