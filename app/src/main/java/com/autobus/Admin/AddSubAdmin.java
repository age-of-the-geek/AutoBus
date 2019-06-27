package com.autobus.Admin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.autobus.R;
import com.autobus.SubAdmin.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddSubAdmin extends AppCompatActivity {

    EditText subadmin_uname, subadmin_password, subadmin_id, subadmin_phone, bus_company;
    Button add_subadmin;
    ImageView bus_logo, upload_logo;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sub_admin_activity);

        Toolbar toolbar_default = findViewById(R.id.toolbar_admin2);
        setSupportActionBar(toolbar_default);
        getSupportActionBar().setTitle("Add Sub Admin");
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        subadmin_uname = findViewById(R.id.subadmin_uname);
        subadmin_password = findViewById(R.id.subadmin_password);
        subadmin_id = findViewById(R.id.subadmin_id);
        subadmin_phone = findViewById(R.id.subadmin_phone);
        bus_company = findViewById(R.id.subadmin_bus_company);
        add_subadmin = findViewById(R.id.save_subadmin);
        bus_logo = findViewById(R.id.bus_logo);
        upload_logo = findViewById(R.id.upload_logo);

        /* checking the permission
        if the permission is not given we will open setting to add permission
        else app will not open*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }

        upload_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if everything is ok we will open image chooser
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });

        add_subadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_new_subadmin(bitmap);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                //displaying selected image to imageview
                bus_logo.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else Toast.makeText(this, "{lease Select a Logo", Toast.LENGTH_SHORT).show();
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {

        /*
         * The method is taking Bitmap as an argument
         * then it will return the byte[] array for the given bitmap
         * and we will send this array to the server
         * you can give quality between 0 to 100
         * 0 means worse quality
         * 100 means best quality
         * */
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    private void add_new_subadmin(Bitmap bitmap) {

        if (subadmin_uname.getText().toString().isEmpty() || subadmin_password.getText().toString().isEmpty() ||
                subadmin_id.getText().toString().isEmpty() || subadmin_phone.getText().toString().isEmpty() ||
                bus_company.getText().toString().isEmpty()) {

            Toast.makeText(this, "Enter All The Data First!", Toast.LENGTH_SHORT).show();
            subadmin_uname.requestFocus();
            subadmin_uname.setError("Enter Data");
        } else {

            final String subadmin_unameS = this.subadmin_uname.getText().toString().trim();
            final String subadmin_passwordS = this.subadmin_password.getText().toString().trim();
            final String subadmin_idS = this.subadmin_id.getText().toString().trim();
            final String subadmin_phoneS = this.subadmin_phone.getText().toString().trim();
            final String bus_companyS = this.bus_company.getText().toString().trim();


            //our custom volley request
            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Config_Admin.ADD_URL,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            try {
                                JSONObject obj = new JSONObject(new String(response.data));
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {

                /*
                 * If you want to add more parameters with the image
                 * you can do it here
                 * */
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("subadmin_uname", subadmin_unameS);
                    params.put("subadmin_password", subadmin_passwordS);
                    params.put("subadmin_id", subadmin_idS);
                    params.put("bus_company", bus_companyS);
                    params.put("subadmin_phone", subadmin_phoneS);
                    return params;
                }

                /*
                 * Here we are passing image by renaming it with a unique name
                 * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    long imagename = System.currentTimeMillis();
                    params.put("logo", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                    return params;
                }
            };

            //adding the request to volley
            Volley.newRequestQueue(this).add(volleyMultipartRequest);


        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
