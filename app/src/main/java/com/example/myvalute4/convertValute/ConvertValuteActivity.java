package com.example.myvalute4.convertValute;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myvalute4.AdapterValute;
import com.example.myvalute4.Api;
import com.example.myvalute4.MainActivity;
import com.example.myvalute4.R;
import com.example.myvalute4.model.Value;
import com.example.myvalute4.model.Valute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConvertValuteActivity extends AppCompatActivity {

    private Value mValues ;
    private Spinner mSpinner;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_valute);

        mSpinner = findViewById(R.id.spinner);
        getValute();

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                Toast.makeText(ConvertValuteActivity.this,item,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getValute(){
        Call<Value> call = Api.getPlaceHolderApi().getValue();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if(response.isSuccessful() && response.body() != null){
                    mValues = response.body();
                    Set<String> key = mValues.getMapValute().keySet();
                    List<String> keyMain = new ArrayList<>();
                    keyMain.addAll(key);
                    mAdapter = new ArrayAdapter<String>(ConvertValuteActivity.this, android.R.layout.simple_spinner_item,keyMain);
                    mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinner.setAdapter(mAdapter);

                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Ошибка",Toast.LENGTH_LONG).show();
            }
        });
    }
}