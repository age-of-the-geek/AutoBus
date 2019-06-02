package com.autobus.TicketChecker;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.autobus.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ticket_checker_home extends AppCompatActivity {

    private Button scan_btn;
    private TextView usrname;
    String TicketCode;
    private static final String URL_QR = "http://192.168.10.12/AutoBus/read_qr.php";
    String qrCode;
    String[] qrcodeJson=new String[15];
    static int counter=-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_checker_home);

        Toolbar toolbar_default = findViewById(R.id.toolbar_default);
        setSupportActionBar(toolbar_default);
        getSupportActionBar().setTitle("Ticket-Checker Dashboard");


        scan_btn = (Button) findViewById(R.id.scan_btn);
        usrname = (TextView) findViewById(R.id.usrname);
        final Activity activity = this;


        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("Scanning The Ticket");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
                loadQR();

            }
        });


        //Fetching email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config_tk_checker.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(Config_tk_checker.EMAIL_SHARED_PREF, "Not Available");

        //Showing the current logged in email to textview
        usrname.setText("User: " + email);


    }

    private void loadQR() {
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_QR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            counter=-1;
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject data = array.getJSONObject(i);


                                counter++;

                                qrcodeJson[counter] = data.getString("ticket_code").trim();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ticket_checker_home.this, "Error"
                                    + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ticket_checker_home.this, "Error"
                                + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            //The result in QRCODE
            else {
                qrCode = result.getContents();

                for (int j = 0; j <=counter; j++) {

                    if (qrCode.matches(qrcodeJson[j])) {

                        Toast.makeText(ticket_checker_home.this, "Matched", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(ticket_checker_home.this, "not", Toast.LENGTH_SHORT).show();
                    }

                }
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    //Logout function
    private void logout() {
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config_tk_checker.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config_tk_checker.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config_tk_checker.EMAIL_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting tk_checker_login activity
                        Intent intent = new Intent(ticket_checker_home.this, tk_checker_login.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adding our menu to toolbar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuLogout) {
            //calling logout method when the logout button is clicked
            logout();
        }
        return super.onOptionsItemSelected(item);
    }


}