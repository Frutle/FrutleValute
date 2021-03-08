package com.example.myvalute4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    JsonPlaceHolderApi mJsonPlaceHolderApi;
    private AdapterValute mAdapterValute;
    private  Value mValues;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.cbr-xml-daily.ru/")
                .addConverterFactory(GsonConverterFactory.create())//Use Gson
                .build();

        //Use the retrofit instance to create the method body of JsonPlaceHolderApi Interface
        mJsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        getValute();
    }

    public void getValute(){
        Call<Value> call = mJsonPlaceHolderApi.getValue();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if(response.isSuccessful() && response.body() != null){
                    mValues = response.body();
//                    Toast.makeText(getApplicationContext(),mValues.Valute.values().toString(),Toast.LENGTH_LONG).show();
                    ArrayList<Valute> valuteList = new ArrayList<>(mValues.getMapValute().values());
                    mAdapterValute = new AdapterValute(valuteList,MainActivity.this);
                    mRecyclerView.setAdapter(mAdapterValute);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Ошибка",Toast.LENGTH_LONG).show();
            }
        });
    }
}