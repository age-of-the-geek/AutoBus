package com.autobus.Passenger;

import com.autobus.Passenger.PlacesModel.Results;

public class URLEndPoints {
    private static final String ROOT_URL = "http://192.168.10.12/AutoBus/upload_qr.php?apicall=";
    public static final String UPLOAD_URL = ROOT_URL + "uploadpic";
    public static final String GET_PICS_URL = ROOT_URL + "getpics";
    private static final String GOOGLE_API_URL = "https://maps.googleapis.com/";
    public static Results currentResults;

    public static GoogleApiService getGoogleApiService() {
        return RetrofitClient.getClient(GOOGLE_API_URL).create(GoogleApiService.class);
    }

    public static GoogleApiService getGoogleApiServiceScalar() {
        return RetrofitScalarClient.getScalarClient(GOOGLE_API_URL).create(GoogleApiService.class);
    }
}
