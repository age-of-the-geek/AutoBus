package com.autobus.Passenger;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.autobus.MapDirection;
import com.autobus.Passenger.PlacesModel.PlacesDetail;
import com.autobus.R;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceDetail extends AppCompatActivity {

    ImageView place_image;
    RatingBar ratingBar;
    TextView place_name, place_address, opening_hours;
    Button view_on_Map,view_Direction;
    GoogleApiService mService;
    PlacesDetail mPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        mService = URLEndPoints.getGoogleApiService();
        ratingBar = findViewById(R.id.place_rating);
        place_name = findViewById(R.id.place_name);
        place_address = findViewById(R.id.place_address);
        opening_hours = findViewById(R.id.opening_hours);
        view_on_Map = findViewById(R.id.view_on_Map);
        view_Direction = findViewById(R.id.view_Direction);
        view_Direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlaceDetail.this, MapDirection.class));
            }
        });
        view_on_Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mPlaces.getResult().getUrl()));
                startActivity(mapIntent);
            }
        });

        Toolbar toolbar_default = findViewById(R.id.toolbar_place);
        setSupportActionBar(toolbar_default);
        getSupportActionBar().setTitle("AutoBus");
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        place_image = findViewById(R.id.place_image);
        if (URLEndPoints.currentResults.getPhotos() != null && URLEndPoints.currentResults.getPhotos().length > 0) {
            Glide.with(this)
                    .load(getPhotoOfPlace(URLEndPoints.currentResults.getPhotos()[0].getPhoto_reference(), 1000))
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_error)
                    .into(place_image);
        }

        if (URLEndPoints.currentResults.getRating() != null && !TextUtils.isEmpty(URLEndPoints.currentResults.getRating()))
        {
            ratingBar.setRating(Float.parseFloat(URLEndPoints.currentResults.getRating()));
        }
        else
        {
            ratingBar.setVisibility(View.GONE);
        }
        if (URLEndPoints.currentResults.getOpening_hours() != null)
        {
            opening_hours.setText("Open Now:" + URLEndPoints.currentResults.getOpening_hours().getOpen_now());
        }
        else
        {
            opening_hours.setVisibility(View.GONE);
        }

        mService.getDetailPlaces(getPlaceDetailUrl(URLEndPoints.currentResults.getPlace_id()))
                .enqueue(new Callback<PlacesDetail>() {
                    @Override
                    public void onResponse(Call<PlacesDetail> call, Response<PlacesDetail> response) {

                        mPlaces = response.body();
                        place_address.setText(mPlaces.getResult().getFormatted_address());
                        place_name.setText(mPlaces.getResult().getName());
                    }

                    @Override
                    public void onFailure(Call<PlacesDetail> call, Throwable t) {

                    }
                });


    }

    private String getPlaceDetailUrl(String place_id) {

        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json");
        url.append("?placeid=" + place_id);
        url.append("&key=" + getResources().getString(R.string.google_api_key));
        return url.toString();

    }

    private String getPhotoOfPlace(String photo_refrence, int max_width) {
        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo");
        url.append("?maxwidth=" + max_width);
        url.append("&photoreference=" + photo_refrence);
        url.append("&key=" + getResources().getString(R.string.google_api_key));
        return url.toString();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
