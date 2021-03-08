package com.example.myvalute4;


import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class Value {

    private String Date;
    private String PreviousDate;
    private String PreviousUrl;
    private String Timestamp;

    @SerializedName("Valute")
    Map<String,Valute> Valute;

    public String getDate() {
        return Date;
    }

    public String getPreviousDate() {
        return PreviousDate;
    }

    public String getPreviousUrl() {
        return PreviousUrl;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public Map<String, Valute> getMapValute() {
        return Valute;
    }
}
