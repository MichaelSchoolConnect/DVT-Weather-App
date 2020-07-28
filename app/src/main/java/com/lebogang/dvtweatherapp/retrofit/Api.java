package com.lebogang.dvtweatherapp.retrofit;

import com.lebogang.dvtweatherapp.pojo.ForecastFivePojo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "https://samples.openweathermap.org/data/2.5/";

    // Performing HTTP Requests with annotation.
    //weather?q=London,uk&appid=439d4b804bc8187953eb36d2a8c26a02
    @GET("forecast?id=524901&appid=8a547d624aca605f469b238c6718a026")
    Call<ForecastFivePojo> getDaysOfTheWeek();

}
