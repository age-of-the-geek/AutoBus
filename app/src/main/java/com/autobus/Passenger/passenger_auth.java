package com.autobus.Passenger;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.autobus.Activities.CountryData;
import com.autobus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;


public class passenger_auth extends AppCompatActivity {

    private EditText editText;
    private CountryCodePicker codePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_auth_activity);

        editText = findViewById(R.id.editTextPhone);
        codePicker = findViewById(R.id.ccp);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.continuebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = editText.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }

                String phoneNumber = '+' + codePicker.getSelectedCountryCode() + number;
                Toast.makeText(passenger_auth.this, phoneNumber, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(passenger_auth.this, verify_phone.class);
                intent.putExtra("phonenumber", phoneNumber);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, Bus_Details.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }


    }
}
