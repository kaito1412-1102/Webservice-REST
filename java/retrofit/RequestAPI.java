package com.example.webservicerest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RequestAPI {
    @GET("getFoods.php")
    Call<List<Food>> getFoods();

    @FormUrlEncoded
    @POST("insertFood.php")
    Call<String> insertFood(@Field("name") String name, @Field("price") int price,
                                  @Field("encoded_image") String encodedImage, @Field("image_name") String image_name);
}
