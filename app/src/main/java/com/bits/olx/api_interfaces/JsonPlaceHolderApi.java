package com.bits.olx.api_interfaces;


import com.bits.olx.models.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    String URL = "http://10.0.2.2/";

    @GET("posts/")
    Call<List<Posts>> getitems();
}
