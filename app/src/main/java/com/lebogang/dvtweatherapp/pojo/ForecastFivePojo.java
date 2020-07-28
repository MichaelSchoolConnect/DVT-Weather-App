package com.lebogang.dvtweatherapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ForecastFivePojo {

    //@SerializedName annotation is used to specify the name of the field that’s in the JSON Response.
    // And is also a way to get data vie retrofit...
    @SerializedName("name")
    public String name;

    @SerializedName("cod")
    public String cod;

    @SerializedName("message")
    public double message;

    // The current Objects we use get data via JSON.
    public String data;
    public int temp;

    public ForecastFivePojo(){}

    public ForecastFivePojo(String data){
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public int getTemp() {
        return temp;
    }

    // Inner array access via retrofit
    @SerializedName("weather")
    public List<ForecastWeatherData> list = new ArrayList<>();

    @SerializedName("list")
    @Expose
    public List<MainWeatherData> mainWeatherDataList = new ArrayList<>();

    public static class ForecastWeatherData {

        @SerializedName("id")
        public Integer id;

        @SerializedName("main")
        public String main;

        @SerializedName("description")
        public String description;

    }

    public static class MainWeatherData{

        @SerializedName("dt")
        public int dt;

        @SerializedName("main")
        public java.lang.Object main;

        @SerializedName("weather")
        public java.lang.Object weather;

        @SerializedName("temp")
        public int temp;

        @SerializedName("pressure")
        public int pressure;

        @SerializedName("humidity")
        public int humidity;

    }

}
