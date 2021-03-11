package com.example.myvalute4.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

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
    public double value;
//    @SerializedName("Previous")
//    public double previous;


    @Override
    public String toString() {
        return "Valute{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Valute valute = (Valute) o;
        return Double.compare(valute.value, value) == 0 &&
                Objects.equals(name, valute.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}
