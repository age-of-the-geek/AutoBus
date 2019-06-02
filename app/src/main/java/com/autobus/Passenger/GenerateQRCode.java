package com.autobus.Passenger;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.autobus.SubAdmin.EndPoints;
import com.autobus.SubAdmin.VolleyMultipartRequest;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class GenerateQRCode extends AppCompatActivity {

    Button generate , saveQRImage;
    ImageView qrImage;
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789";
    String ticketCode = "";
    String ticket = "ticket#";
    int ticketCodelength = 5;
    Random random = new Random();
    char[] randomArray = new char[ticketCodelength];
    public String finalTicketCode;
    OutputStream outputStream;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_qr_activity);

        generate = findViewById(R.id.generateQR);
        saveQRImage = findViewById(R.id.saveQRImage);
        qrImage = findViewById(R.id.qrcode);
        ActivityCompat.requestPermissions(GenerateQRCode.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(GenerateQRCode.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);


        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < ticketCodelength; i++) {
                    randomArray[i] = characters.charAt(random.nextInt(characters.length()));
                }

                for (int i = 0; i < randomArray.length; i++) {
                    ticketCode += randomArray[i];
                }

                finalTicketCode = ticket + ticketCode;

                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                BitMatrix bitMatrix = null;
                try {
                    bitMatrix = multiFormatWriter.encode(finalTicketCode, BarcodeFormat.QR_CODE, 500, 500);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                bitmap = barcodeEncoder.createBitmap(bitMatrix);
                qrImage.setImageBitmap(bitmap);

                uploadBitmap(bitmap);

            }
        });

        saveQRImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File path = Environment.getExternalStorageDirectory();
                File dir = new File(path.getAbsoluteFile()+"/AutoBus/");
                dir.mkdir();

                File file = new File(dir,finalTicketCode+".png");

                try {
                    outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
                    Toast.makeText(GenerateQRCode.this, "Image Saved to Folder AutoBus", Toast.LENGTH_SHORT).show();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }

                try {
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }


    /*
     * The method is taking Bitmap as an argument
     * then it will return the byte[] array for the given bitmap
     * and we will send this array to the server
     * here we are using PNG Compression with 80% quality
     * you can give quality between 0 to 100
     * 0 means worse quality
     * 100 means best quality
     * */
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();


    }

    private void uploadBitmap(final Bitmap bitmap) {

        //our custom volley request
        VolleyMultipartRequest2 volleyMultipartRequest = new VolleyMultipartRequest2(Request.Method.POST, URLEndPoints.UPLOAD_URL,
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
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("code", finalTicketCode);
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, VolleyMultipartRequest2.DataPartQR> getByteData() {
                Map<String, VolleyMultipartRequest2.DataPartQR> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("qrpic", new VolleyMultipartRequest2.DataPartQR(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
}
