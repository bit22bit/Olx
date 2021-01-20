package com.bits.olx.api_interfaces;


import com.bits.olx.models.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    String URL = "http://10.0.2.2:8080/";

    @GET("/posts")
    Call<List<Posts>> getitems();

    @POST("/posts")
    Call<Posts> createPost(@Body Posts posts);
}
