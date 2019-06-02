package com.autobus.SubAdmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.autobus.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class subadmin_home extends AppCompatActivity {

    FloatingActionMenu floatingActionMenu;
    FloatingActionButton add_ticketChecker, add_driver, add_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subadmin_home_activity);

        floatingActionMenu = findViewById(R.id.fab_menu);
        add_driver = findViewById(R.id.add_driver);
        add_ticketChecker = findViewById(R.id.add_ticketChecker);
        add_details = findViewById(R.id.add_detail);

        add_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(subadmin_home.this, subadmin_add_bus.class);
                startActivity(i);
            }
        });

        add_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(subadmin_home.this, subadmin_add_driver.class);
                startActivity(i);
            }
        });
    }
}
