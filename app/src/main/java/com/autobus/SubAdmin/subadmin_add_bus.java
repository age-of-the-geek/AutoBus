package com.autobus.SubAdmin;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.autobus.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class subadmin_add_bus extends AppCompatActivity {

    Calendar mCal, mCal1;
    Bitmap bitmap;
    int days, month, year, hour, minute;
    String mCurrentDate, mCurrentTime,
            bus_leaving_stringY, bus_leaving_stringM, bus_leaving_stringD, bus_leaving_stringH, bus_leaving_stringMi, bus_leaving_string,
            bus_reaching_string, bus_reaching_stringY, bus_reaching_stringM, bus_reaching_stringD, bus_reaching_stringH, bus_reaching_stringMi,
            bus_break_string, bus_break_stringY, bus_break_stringM, bus_break_stringD, bus_break_stringH, bus_break_stringMi;
    ImageView bus_image;
    ImageButton upload_image;
    EditText bus_number, total_seats, available_seats, bus_from, bus_to, bus_leaving_time, bus_reaching_time,
            bus_driver_name, bus_ticketchecker_name, bus_rating, bus_break_time, bus_company, day, bus_leaving_date, bus_reaching_date, bus_break_date;
    private Button savebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subadmin_add_bus_activity);

        Toolbar toolbar_default = findViewById(R.id.toolbar_subadmin);
        setSupportActionBar(toolbar_default);
        getSupportActionBar().setTitle("Add Bus Details");
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setCurrentDate();
        setCurrentTime();


        bus_image = findViewById(R.id.bus_image);
        upload_image = findViewById(R.id.upload_image);
        bus_number = findViewById(R.id.bus_number);
        total_seats = findViewById(R.id.total_seats);
        available_seats = findViewById(R.id.available_seats);
        bus_from = findViewById(R.id.route_from);
        bus_to = findViewById(R.id.route_to);
        bus_leaving_date = findViewById(R.id.leaving_date);
        bus_reaching_date = findViewById(R.id.reaching_date);
        bus_leaving_time = findViewById(R.id.leaving_time);
        bus_reaching_time = findViewById(R.id.reaching_time);
        bus_driver_name = findViewById(R.id.driver_name);
        bus_ticketchecker_name = findViewById(R.id.tk_checker_name);
        bus_rating = findViewById(R.id.rating);
        bus_break_time = findViewById(R.id.break_time);
        bus_break_date = findViewById(R.id.break_date);
        bus_company = findViewById(R.id.bus_company);
        day = findViewById(R.id.day);
        savebtn = findViewById(R.id.btn_save);

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

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if everything is ok we will open image chooser
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(bitmap);
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
                bus_image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {

        /*
         * The method is taking Bitmap as an argument
         * then it will return the byte[] array for the given bitmap
         * and we will send this array to the server
         * here we are using PNG Compression with 80% quality
         * you can give quality between 0 to 100
         * 0 means worse quality
         * 100 means best quality
         * */
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    public void selectTime(View v) {

        switch (v.getId()) {
            case R.id.leaving_time:
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        bus_leaving_time.setText("H:" + hourOfDay + "-M:" + minute);
                        bus_leaving_stringH = Integer.toString(hourOfDay);
                        bus_leaving_stringMi = Integer.toString(minute);
                    }
                }, hour, minute, DateFormat.is24HourFormat(this));

                timePickerDialog.show();
                break;
            case R.id.reaching_time:
                TimePickerDialog timePickerDialog1 = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        bus_reaching_time.setText("H:" + hourOfDay + "-M:" + minute);

                        bus_reaching_stringH = Integer.toString(hourOfDay);
                        bus_reaching_stringMi = Integer.toString(minute);
                    }
                }, hour, minute, DateFormat.is24HourFormat(this));

                timePickerDialog1.show();
                break;

            case R.id.break_time:
                TimePickerDialog timePickerDialog2 = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        bus_break_time.setText("H:" + hourOfDay + "-M:" + minute);
                        bus_break_stringH = Integer.toString(hourOfDay);
                        bus_break_stringMi = Integer.toString(minute);
                    }
                }, hour, minute, DateFormat.is24HourFormat(this));

                timePickerDialog2.show();
                break;

                default:
        }
    }

    public void selectDate(View v) {

        switch (v.getId()) {
            case R.id.leaving_date:
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;

                        bus_leaving_date.setText(dayOfMonth + "-" + month + "-" + year);
                        bus_leaving_stringY = Integer.toString(year);
                        bus_leaving_stringM = Integer.toString(month);
                        bus_leaving_stringD = Integer.toString(dayOfMonth);
                    }
                }, year, month, days);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;
            case R.id.reaching_date:
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        bus_reaching_date.setText(dayOfMonth + "-" + month + "-" + year);
                        bus_reaching_stringY = Integer.toString(year);
                        bus_reaching_stringM = Integer.toString(month);
                        bus_reaching_stringD = Integer.toString(dayOfMonth);
                    }
                }, year, month, days);

                datePickerDialog1.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog1.show();
                break;

            case R.id.break_date:
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        bus_break_date.setText(dayOfMonth + "-" + month + "-" + year);
                        bus_break_stringY = Integer.toString(year);
                        bus_break_stringM = Integer.toString(month);
                        bus_break_stringD = Integer.toString(dayOfMonth);
                    }
                }, year, month, days);

                datePickerDialog2.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog2.show();
                break;

            default:
        }


    }

    private void setCurrentTime() {

        mCal1 = Calendar.getInstance();
        hour = mCal.get(Calendar.HOUR_OF_DAY);
        minute = mCal.get(Calendar.MINUTE);

        mCurrentTime = "Hour: " + hour + " Minute: " + minute;

    }

    private void setCurrentDate() {
        mCal = Calendar.getInstance();
        days = mCal.get(Calendar.DAY_OF_MONTH);
        month = mCal.get(Calendar.MONTH);
        year = mCal.get(Calendar.YEAR);

        month = month + 1;

        mCurrentDate = (days + "-" + month + "-" + year);
    }


    private void saveData(final Bitmap bitmap) {

        bus_leaving_string = bus_leaving_stringD + "-" + bus_leaving_stringM + "-" + bus_leaving_stringY+
                            " : "+bus_leaving_stringH+":"+bus_leaving_stringMi;
        bus_reaching_string = bus_reaching_stringD + "-" + bus_reaching_stringM + "-" + bus_reaching_stringY+
                " : "+bus_reaching_stringH+":"+bus_reaching_stringMi;
        bus_break_string = bus_break_stringD + "-" + bus_break_stringM + "-" + bus_break_stringY+
                " : "+bus_break_stringH+":"+bus_break_stringMi;



        final String bus_companyS = this.bus_company.getText().toString().trim();
        final String bus_numberS = this.bus_number.getText().toString().trim();
        final String total_seatsS = this.total_seats.getText().toString().trim();
        final String available_seatsS = this.available_seats.getText().toString().trim();
        final String bus_routeFromS = this.bus_from.getText().toString().trim();
        final String bus_routeToS = this.bus_to.getText().toString().trim();
        final String bus_leaving_timeS = bus_leaving_string;
        final String bus_reaching_timeS = bus_reaching_string;
        final String bus_driver_nameS = this.bus_driver_name.getText().toString().trim();
        final String bus_ratingS = this.bus_rating.getText().toString().trim();
        final String bus_ticketchecker_nameS = this.bus_ticketchecker_name.getText().toString().trim();
        final String bus_break_timeS = bus_break_string;
        final String dayS = this.day.getText().toString().trim();

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, EndPoints.UPLOAD_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("bus_company", bus_companyS);
                params.put("bus_break_time", bus_break_timeS);
                params.put("bus_rating", bus_ratingS);
                params.put("bus_ticketchecker_name", bus_ticketchecker_nameS);
                params.put("bus_driver_name", bus_driver_nameS);
                params.put("bus_reaching_time", bus_reaching_timeS);
                params.put("bus_leaving_time", bus_leaving_timeS);
                params.put("bus_from", bus_routeFromS);
                params.put("bus_to", bus_routeToS);
                params.put("bus_available_seats", available_seatsS);
                params.put("bus_total_seats", total_seatsS);
                params.put("bus_number", bus_numberS);
                params.put("day", dayS);
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


}
