package com.example.webservicerest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestAPI {
    @GET("getFoods.php")
    Call<List<Food>> getFoods();
}
