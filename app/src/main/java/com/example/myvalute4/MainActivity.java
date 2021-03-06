package com.example.myvalute4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myvalute4.convertValute.ConvertValuteActivity;
import com.example.myvalute4.model.Value;
import com.example.myvalute4.model.Valute;
import com.example.myvalute4.service.Api;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AdapterValute mAdapterValute;
    private Value mValues;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private String json;
    private SharedPreferences.Editor prefsEditor;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initValutes();
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
                            if (response.isSuccessful() && response.body() != null) {
                                mValues = response.body();

                                ArrayList<Valute> valuteList = new ArrayList<>(mValues.getMapValute().values());
                                mAdapterValute = new AdapterValute(valuteList, MainActivity.this);
                                mRecyclerView.setAdapter(mAdapterValute);
                                Log.d("request", "???????????????? ????????????");

                                json = new Gson().toJson(mValues,Value.class);
                                prefsEditor.putString("MyObject", json);
                                prefsEditor.commit();

                            }
                        }

                        @Override
                        public void onFailure(Call<Value> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "????????????", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            };
            timer.schedule(timerTask, 0, 60000);

    }

    private void init(){
        mPrefs = getPreferences(MODE_PRIVATE);
        prefsEditor = mPrefs.edit();

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
        switch (item.getItemId()){
            case R.id.action_convert:
                Intent intent = new Intent(MainActivity.this, ConvertValuteActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_up:
                getValute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void initValuteNoInternet(){

        String json = mPrefs.getString("MyObject", "");
        Value jsonObject = new Gson().fromJson(json, Value.class);

        ArrayList<Valute> valuteList = new ArrayList<>(jsonObject.getMapValute().values());

        mAdapterValute = new AdapterValute(valuteList,MainActivity.this);
        mRecyclerView.setAdapter(mAdapterValute);

    }

    //?????????????????? ???????? ???? ?????????????????????? ?? ??????????????????
    public static boolean hasConnection(final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        return false;
    }

    //???????? ???????? ?????????????????????? ?? ???????? ?????????????????? ???????????? ,???????? ???????? ???????????????? ????????????
    private void initValutes(){
        if (hasConnection(getApplicationContext())){
            getValute();
            Log.d("Internet", "?????????????????????? ????????");
        } else {
            initValuteNoInternet();
            Log.d("Internet", "?????????????????????? ????????");
        }
    }

}