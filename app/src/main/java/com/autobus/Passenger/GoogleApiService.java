package com.autobus.Passenger;

import com.autobus.Passenger.PlacesModel.Places;
import com.autobus.Passenger.PlacesModel.PlacesDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface GoogleApiService {
    @Headers("Content-Type: application/json")
    @GET
    Call<Places> getNearByPlaces(@Url String url);

    @Headers("Content-Type: application/json")
    @GET
    Call<PlacesDetail> getDetailPlaces(@Url String url);

    @Headers("Content-Type: application/json")
    @GET
    Call<String> getDirections(@Url String url);

    /*@Headers("Content-Type: application/json")
    @GET("maps/api/directions/json")
    Call<String> getDirections(@Query("origin") String origin, @Query("destination") String destination);*/
}
