package com.example.myvalute4;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("daily_json.js")
    Call<Value> getValue();
}
