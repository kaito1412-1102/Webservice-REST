package com.example.webservicerest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static final String BASE_URL = "http://192.168.43.93:2019/FoodREST/";

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static RequestAPI requestAPI = retrofit.create(RequestAPI.class);

    public static RequestAPI getRequestAPI() {
        return requestAPI;
    }

}
