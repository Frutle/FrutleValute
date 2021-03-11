package com.example.myvalute4.convertValute;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myvalute4.Api;
import com.example.myvalute4.R;
import com.example.myvalute4.model.Value;
import com.example.myvalute4.model.Valute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myvalute4.convertValute.Сalculator.moneyConvert;

public class ConvertValuteActivity extends AppCompatActivity {

    private Value mValues ;
    private Spinner mSpinner;
    private Сalculator mСalculator;
    private ArrayAdapter<String> mAdapter;
    private EditText ruble;
    private TextView valuteText;
    private List<Valute> valuteConver;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_valute);

        ruble = findViewById(R.id.Ruble);
        valuteText = findViewById(R.id.ConverValute);
        mSpinner = findViewById(R.id.spinner);
        mButton = findViewById(R.id.bt1);
        getValute();

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);

                Double valute =  valuteConver.get(position).getValue();
                String temp = ruble.getText().toString();
                double convertValute = moneyConvert( valute, Double.parseDouble(temp));

                //Toast.makeText(ConvertValuteActivity.this,convertValute+"",Toast.LENGTH_LONG).show();

                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        valuteText.setText(convertValute+"");
                    }
                });
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
                    valuteConver = new ArrayList(mValues.getMapValute().values());
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