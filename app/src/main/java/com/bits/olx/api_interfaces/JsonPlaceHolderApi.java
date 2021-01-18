package com.bits.olx.api_interfaces;


import com.bits.olx.models.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    String URL = "http://localhost:3000/";

    @GET("posts/")
    Call<List<Posts>> getitems();
}
