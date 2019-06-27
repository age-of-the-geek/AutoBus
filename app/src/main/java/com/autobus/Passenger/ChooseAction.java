package com.autobus.Passenger;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.autobus.NearbyBusStand;
import com.autobus.R;

import java.util.Objects;

public class ChooseAction extends AppCompatActivity {

    Button search_bus;
    Dialog mDialog;
    EditText from, to;
    String fromL, toL;
    TextView user_name;
    CardView maps_option, book_option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_action);

        maps_option = findViewById(R.id.maps_option);
        book_option = findViewById(R.id.book_option);
        user_name = findViewById(R.id.user_name);

        Toolbar toolbar_default = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar_default);
        TextView toolbar_title = toolbar_default.findViewById(R.id.toolbar_title);
        toolbar_title.setText("AutoBus");
        Intent i = getIntent();
        String Name = Objects.requireNonNull(i.getExtras()).getString("Name");
        user_name.setText(Name);


        book_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = new Dialog(ChooseAction.this);
                mDialog.setContentView(R.layout.from_to_dialog);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                from = mDialog.findViewById(R.id.dialog_from);
                to = mDialog.findViewById(R.id.dialog_to);
                mDialog.show();
                search_bus = mDialog.findViewById(R.id.search_bus);
                search_bus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (from.getText().toString().isEmpty() || to.getText().toString().isEmpty()) {
                            Toast.makeText(ChooseAction.this, "Please Enter From and To Location", Toast.LENGTH_SHORT).show();

                        } else {
                            fromL = from.getText().toString().trim();
                            toL = to.getText().toString().trim();
                            Intent intent = new Intent(ChooseAction.this, Bus_Details.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("from", fromL);
                            bundle.putString("to", toL);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
        maps_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseAction.this, NearbyBusStand.class));
            }
        });
    }

}
