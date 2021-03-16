package com.example.myvalute4.convertValute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myvalute4.service.Api;
import com.example.myvalute4.R;
import com.example.myvalute4.model.Value;
import com.example.myvalute4.model.Valute;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myvalute4.MainActivity.hasConnection;
import static com.example.myvalute4.convertValute.Сalculator.moneyConvert;

public class ConvertValuteActivity extends AppCompatActivity {

    private Value mValues ;
    private Spinner mSpinner;
    private ArrayAdapter<String> mAdapter;
    private EditText ruble;
    private TextView valuteText;
    private List<Valute> valuteConvert;
    private Button mButton;
    private String json;
    private SharedPreferences.Editor prefsEditor;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_valute);

        init();
        initConvertValute();

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Double valute =  valuteConvert.get(position).getValue();

                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String temp = ruble.getText().toString();
                        double convertValute = moneyConvert( valute, Double.parseDouble(temp));
                        valuteText.setText(convertValute+"");
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getValute(){
        Call<Value> call = Api.getPlaceHolderApi().getValue();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if(response.isSuccessful() && response.body() != null){
                    mValues = response.body();
                    Set<String> key = mValues.getMapValute().keySet();
                    List<String> keyMain = new ArrayList<>();
                    valuteConvert = new ArrayList(mValues.getMapValute().values());
                    keyMain.addAll(key);

                    mAdapter = new ArrayAdapter<String>(ConvertValuteActivity.this, android.R.layout.simple_spinner_item,keyMain);
                    mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinner.setAdapter(mAdapter);

                    json = new Gson().toJson(mValues,Value.class);
                    prefsEditor.putString("MySpinner", json);
                    prefsEditor.commit();

                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Ошибка",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void init(){
        mPrefs = getPreferences(MODE_PRIVATE);
        prefsEditor = mPrefs.edit();

        ruble = findViewById(R.id.Ruble);
        valuteText = findViewById(R.id.ConverValute);
        mSpinner = findViewById(R.id.spinner);
        mButton = findViewById(R.id.bt1);
    }
    //подругажем данные если нету интернета
    private void initSpinnerNoInternet(){

        String json = mPrefs.getString("MySpinner", "");
        Value jsonObject = new Gson().fromJson(json, Value.class);

        Set<String> key = jsonObject.getMapValute().keySet();
        List<String> keyMain = new ArrayList<>();
        valuteConvert = new ArrayList(jsonObject.getMapValute().values());
        keyMain.addAll(key);

        mAdapter = new ArrayAdapter<String>(ConvertValuteActivity.this, android.R.layout.simple_spinner_item,keyMain);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);
    }

   //Если есть подключение обновляем данные,иначе подргужаем старые
   private void initConvertValute(){
        if(hasConnection(ConvertValuteActivity.this)){
            getValute();
        } else {
            initSpinnerNoInternet();
        }
   }

}