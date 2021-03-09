package com.example.myvalute4;

import com.example.myvalute4.model.Value;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PlaceHolderApi {
    @GET("daily_json.js")
    Call<Value> getValue();
}
