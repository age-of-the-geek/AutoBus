package com.autobus.Passenger;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.autobus.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;


public class passenger_auth extends AppCompatActivity {

    EditText editText, editTextName, name;
    private CountryCodePicker codePicker;
    Dialog dialog;
    Button login;
    String user_name, matchName;
    DatabaseReference rootRef, demoRef;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_auth_activity);

        editText = findViewById(R.id.editTextPhone);
        editTextName = findViewById(R.id.editTextName);
        codePicker = findViewById(R.id.ccp);
        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("userName");
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.enter_name_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        name = dialog.findViewById(R.id.dialog_userName);
        login = dialog.findViewById(R.id.loginNext);

        findViewById(R.id.continuebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = editText.getText().toString().trim();
                user_name = editTextName.getText().toString().trim();
                UserModel tmpUser = new UserModel(user_name);

                demoRef.setValue(tmpUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("","Saved");
                        }
                    }
                });

                if (number.isEmpty() || number.length() < 11) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }

                String phoneNumber = '+' + codePicker.getSelectedCountryCode() + number;
                Intent intent = new Intent(passenger_auth.this, verify_phone.class);
                intent.putExtra("phonenumber", phoneNumber);
                intent.putExtra("Name", user_name);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            reEnterUserName();
        }

    }

    private void reEnterUserName() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_again_name = name.getText().toString().trim();
                if (user_again_name.isEmpty()) {
                    Toast.makeText(passenger_auth.this, "Please Enter User Name", Toast.LENGTH_SHORT).show();
                    name.setError("Enter Username");
                } else {

                    ref.child("userName").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                matchName = (dataSnapshot.child("tmpName").getValue()).toString();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                if (user_again_name.equals(matchName)) {
                    Intent intent = new Intent(passenger_auth.this, ChooseAction.class);
                    Bundle data = new Bundle();
                    data.putString("Name",matchName);
                    intent.putExtras(data);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }

        });
        dialog.show();
    }
}
