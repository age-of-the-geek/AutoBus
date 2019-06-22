package com.autobus;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.autobus.Passenger.DirectionJSONParser;
import com.autobus.Passenger.GoogleApiService;
import com.autobus.Passenger.PlacesModel.Step;
import com.autobus.Passenger.URLEndPoints;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapDirection extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    LocationRequest locationRequest;
    Location mLastLocation;
    Marker mCurrentMarker;
    Polyline polyline;
    GoogleApiService mService;
    Step Myroutes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_direction);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mService = URLEndPoints.getGoogleApiServiceScalar();

        buildLocationRequest();
        buildLocationCallback();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }


    private void buildLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {

                if (locationResult != null) {
                    mLastLocation = locationResult.getLastLocation();
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                            .title("Current Position")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    mCurrentMarker = mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));


                    LatLng destinationLatLng = new LatLng(Double.parseDouble(URLEndPoints.currentResults.getGeometry().getLocation().getLat()),
                            Double.parseDouble(URLEndPoints.currentResults.getGeometry().getLocation().getLng()));
                    mMap.addMarker(new MarkerOptions()
                            .position(destinationLatLng)
                            .title(URLEndPoints.currentResults.getName())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

                    drawPath(mLastLocation, URLEndPoints.currentResults.getGeometry().getLocation());

                } else {
                    Toast.makeText(MapDirection.this, "Cannot Get Location", Toast.LENGTH_SHORT).show();
                }

            }

        };

    }

    private void buildLocationRequest() {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setSmallestDisplacement(10f);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    @Override
    protected void onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        super.onStop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                mLastLocation = location;
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                        .title("Current Position")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                mCurrentMarker = mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));


                LatLng destinationLatLng = new LatLng(Double.parseDouble(URLEndPoints.currentResults.getGeometry().getLocation().getLat()),
                        Double.parseDouble(URLEndPoints.currentResults.getGeometry().getLocation().getLng()));
                mMap.addMarker(new MarkerOptions()
                        .position(destinationLatLng)
                        .title(URLEndPoints.currentResults.getName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

                drawPath(mLastLocation, URLEndPoints.currentResults.getGeometry().getLocation());
            }
        });
    }

    private void drawPath(Location mLastLocation, com.autobus.Passenger.PlacesModel.Location location) {

        if (polyline != null)
            polyline.remove();
        String origin = new StringBuilder(String.valueOf(mLastLocation.getLatitude())).append(",").append(String.valueOf(mLastLocation.getLongitude()))
                .toString();
        String destination = new StringBuilder(location.getLat()).append(",").append(location.getLng())
                .toString();

        mService.getDirections(getDirectionUrl(origin, destination))
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {


                        new ParserTask().execute(response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
    }

    private String getDirectionUrl(String origin, String destination) {
        // Origin of route
        String str_origin = "origin=" + origin;
        // Destination of route
        String str_dest = "destination=" + destination;
        // Sensor enabled
        String sensor = "sensor=false";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getResources().getString(R.string.google_api_key);
        Log.d("Direction URL", url);
        return url;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {

            JSONObject jsonObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jsonObject = new JSONObject(strings[0]);
                DirectionJSONParser parser = new DirectionJSONParser();
                routes = parser.parse(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {

            ArrayList<LatLng> points = new ArrayList<>();
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.width(10);
            polylineOptions.color(Color.BLUE);


            for (int i = 0; i < lists.size(); i++) {

                List<HashMap<String, String>> path = lists.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));

                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                polylineOptions.addAll(points);

                polylineOptions.geodesic(true);
            }

            if (points.size() != 0)
                mMap.addPolyline(polylineOptions);
            else
                Toast.makeText(MapDirection.this, "Emptyy", Toast.LENGTH_SHORT).show();
            // polyline = mMap.addPolyline(polylineOptions);


            super.onPostExecute(lists);
        }

    }
}
