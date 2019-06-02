package com.autobus.Relative;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.autobus.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class relative_login extends AppCompatActivity {


    EditText UEmail;
    EditText UPass;
    String EmailID;
    String Pass;
    Button Rlogin,RBsignup;
    private static final String TAG = "Login";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_login_activity);

        UEmail = (EditText) findViewById(R.id.REmail);
        UPass = (EditText) findViewById(R.id.RPassword);
        Rlogin = (Button) findViewById(R.id.Rbtn);
        RBsignup = (Button) findViewById(R.id.RBsingup);

        mAuth = FirebaseAuth.getInstance();


        RBsignup.setOnClickListener(v -> {
            Intent i = new Intent(relative_login.this, relative_signup.class);
            startActivity(i);
            finish();
        });


    }

    public void Rlogin(View v) {
        EmailID = UEmail.getText().toString();
        Pass = UPass.getText().toString();

        if (EmailID != "" && Pass != "") {

            mAuth.signInWithEmailAndPassword(EmailID, Pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(relative_login.this, "LogIn Success.",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(relative_login.this, relative_home.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(relative_login.this, "Your Email or Password is Incorrect.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        } else {
            Toast.makeText(relative_login.this, "Please Enter Valid Email And Password.",
                    Toast.LENGTH_SHORT).show();
        }


    }
}