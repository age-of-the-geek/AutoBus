package com.autobus.Relative;



import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.autobus.R;
import com.autobus.TicketChecker.tk_checker_login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class relative_signup extends AppCompatActivity  {

    private EditText name, email, password;
    private Button signupbtn ,loginbtn;

    ImageView img;
    static int PReqCode = 1;
    static int REQUESTCODE = 1;
    Uri pickedimguri;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_signup_activity);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        img = (ImageView) findViewById(R.id.usrimg);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signupbtn = (Button) findViewById(R.id.signup);
        loginbtn = (Button)findViewById(R.id.loginbtn);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(getApplicationContext(), relative_login.class);
                startActivity(login);
                finish();
            }
        });


        mAuth = FirebaseAuth.getInstance();

        FirebaseApp.initializeApp(this);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                final String emailadd = email.getText().toString();
                final String pass = password.getText().toString();
                final String usrname = name.getText().toString();

                if (emailadd.isEmpty() || usrname.isEmpty() || pass.isEmpty()|| img.getDrawable()==null) {

                    showError("Please Enter all the required data");



                } else {

                    CreateAccount(emailadd, usrname, pass);
                }
            }
        });


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT > 22) {


                    checkAndRequestForPermission();

                }
                else {
                    openGallery();

                }
            }
        });

    }


    /*@Override
    protected void onStart() {
        super.onStart();

        //if the user is not logged in
        //opening the login activity
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(relative_signup.this, relative_home.class));
        }
    }*/
    private void CreateAccount(String emailadd, final String usrname, String pass) {

        mAuth.createUserWithEmailAndPassword(emailadd,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            showmsg("Account Created Successfully.");

                            updateUserInfo(usrname, pickedimguri,mAuth.getCurrentUser());

                        }
                        else {

                            showerrormsg("Account Creation Failed" + task.getException().getMessage());
                        }

                    }
                });
    }

    private void showerrormsg(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    private void showmsg(String account_created_successfully) {
        Toast.makeText(getApplicationContext(),account_created_successfully,Toast.LENGTH_LONG).show();
    }




    private void updateUserInfo(final String usrname, Uri pickedimguri, final FirebaseUser currentUser) {

        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(pickedimguri.getLastPathSegment());
        imageFilePath.putFile(pickedimguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(usrname)
                                .setPhotoUri(uri)
                                .build();

                        currentUser.updateProfile(profileUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()){

                                            showMassege("Registration Complete. Please Login");
                                            updateUI();
                                        }

                                    }
                                });
                    }
                });

            }
        });

    }



    private void showMassege(String registration_complete) {
        Toast.makeText(getApplicationContext(),registration_complete,Toast.LENGTH_LONG).show();
    }



    private void updateUI() {

        Intent login = new Intent(getApplicationContext(), tk_checker_login.class);
        startActivity(login);
        finish();
    }

    private void showError(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    private void openGallery() {

        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery,REQUESTCODE);

    }

    private void checkAndRequestForPermission() {

        if(ContextCompat.checkSelfPermission(relative_signup.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(relative_signup.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                Toast.makeText(relative_signup.this, "Please Accept to Allow Access",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(relative_signup.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        }
        else
        {
            openGallery();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK && requestCode==REQUESTCODE && data != null){

            pickedimguri = data.getData();
            img.setImageURI(pickedimguri);
        }


    }







}
