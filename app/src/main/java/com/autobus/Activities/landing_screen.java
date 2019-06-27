package com.autobus.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.autobus.Admin.admin_login;
import com.autobus.Driver.driver_login;
import com.autobus.Passenger.ChooseAction;
import com.autobus.Passenger.GenerateQRCode;
import com.autobus.Passenger.passenger_auth;
import com.autobus.R;
import com.autobus.Relative.relative_signup;
import com.autobus.SubAdmin.subadmin_login;
import com.autobus.TicketChecker.ticket_checker_home;

public class landing_screen extends AppCompatActivity {

    TextView tv, txtp1, txtp2, txtr1, txtr2, txtd1, txtd2, txttk1, txttk2, txts1, txts2, txta1, txta2;
    CardView passengerfont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_screen_activity);

        tv = (TextView) findViewById(R.id.choose);
        txtp1 = (TextView) findViewById(R.id.passengertxt);
        txtp2 = (TextView) findViewById(R.id.passengertxt2);
        txtr1 = (TextView) findViewById(R.id.relativetxt);
        txtr2 = (TextView) findViewById(R.id.relativetxt2);
        txtd1 = (TextView) findViewById(R.id.drivertxt);
        txtd2 = (TextView) findViewById(R.id.drivertxt2);
        txttk1 = (TextView) findViewById(R.id.tkcheckertxt);
        txttk2 = (TextView) findViewById(R.id.tkcheckertxt2);
        txts1 = (TextView) findViewById(R.id.subadmintxt);
        txts2 = (TextView) findViewById(R.id.subadmintxt2);
        txta1 = (TextView) findViewById(R.id.admintxt);
        txta2 = (TextView) findViewById(R.id.admintxt2);


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/Raleway-Regular.ttf");
        tv.setTypeface(typeface);
        txtp1.setTypeface(typeface);
        txtp2.setTypeface(typeface);
        txtr1.setTypeface(typeface);
        txtr2.setTypeface(typeface);
        txtd1.setTypeface(typeface);
        txtd2.setTypeface(typeface);
        txttk1.setTypeface(typeface);
        txttk2.setTypeface(typeface);
        txts1.setTypeface(typeface);
        txts2.setTypeface(typeface);
        txta1.setTypeface(typeface);
        txta2.setTypeface(typeface);

    }

    public void whoAreYou(View v) {
        switch (v.getId()) {
            case R.id.passenger:
                //do something here when card one is clicked

                Intent passengeri = new Intent(landing_screen.this, passenger_auth.class);
                startActivity(passengeri);

                break;
            case R.id.relative:
                //do something here when card two is clicked.

                Intent relativei = new Intent(getApplicationContext(), relative_signup.class);
                startActivity(relativei);
                break;

            case R.id.driver:
                //do something here when card two is clicked.

                Intent driveri = new Intent(getApplicationContext(), driver_login.class);
                startActivity(driveri);
                break;

            case R.id.tkchecker:
                //do something here when card two is clicked.

                Intent tki = new Intent(getApplicationContext(), ticket_checker_home.class);
                startActivity(tki);
                break;

            case R.id.subadmin:
                //do something here when card two is clicked.

                Intent subadmini = new Intent(getApplicationContext(), subadmin_login.class);
                startActivity(subadmini);
                break;

            case R.id.admin:
                //do something here when card two is clicked.

                Intent admin = new Intent(getApplicationContext(), admin_login.class);
                startActivity(admin);
                break;

            default:

        }


    }
}
