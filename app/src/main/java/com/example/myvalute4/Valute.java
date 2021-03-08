package com.example.myvalute4;

import com.google.gson.annotations.SerializedName;

public class Valute {

//    @SerializedName("ID")
//    public String iD;
//    @SerializedName("NumCode")
//    public String numCode;
//    @SerializedName("CharCode")
//    public String charCode;
//    @SerializedName("Nominal")
//    public int nominal;
    @SerializedName("Name")
    public String name;
    @SerializedName("Value")
    public String value;
//    @SerializedName("Previous")
//    public double previous;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }


    @Override
    public String toString() {
        return "Valute{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
