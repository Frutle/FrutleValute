package com.example.myvalute4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.health.TimerStat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myvalute4.convertValute.ConvertValuteActivity;
import com.example.myvalute4.model.Value;
import com.example.myvalute4.model.Valute;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AdapterValute mAdapterValute;
    private Value mValues;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private MainActivity service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getValute();


    }

    private void getValute(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Call<Value> call = Api.getPlaceHolderApi().getValue();
                call.enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        if(response.isSuccessful() && response.body() != null){
                            mValues = response.body();
                            ArrayList<Valute> valuteList = new ArrayList<>(mValues.getMapValute().values());
                            mAdapterValute = new AdapterValute(valuteList,MainActivity.this);
                            mRecyclerView.setAdapter(mAdapterValute);
                            Log.d("request","Обновили данные");
                        }
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Ошибка",Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
        timer.schedule(timerTask,0,60000);

    }

    private void init(){
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(MainActivity.this, ConvertValuteActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
    
}