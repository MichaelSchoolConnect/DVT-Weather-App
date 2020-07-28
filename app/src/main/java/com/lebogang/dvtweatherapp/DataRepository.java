package com.lebogang.dvtweatherapp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.lebogang.dvtweatherapp.pojo.ForecastFivePojo;
import com.lebogang.dvtweatherapp.retrofit.APIClient;
import com.lebogang.dvtweatherapp.retrofit.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository handling the work with products and comments.
 */
public class DataRepository {

    private static String TAG = DataRepository.class.getSimpleName();

    private static DataRepository instance;

    private Api apiClient;

    // We use MutableLiveData because our value is going to change.
    @NonNull
    private MutableLiveData<List<ForecastFivePojo>> listMutableLiveData = new MutableLiveData<>();


    public DataRepository(){
        apiClient = APIClient.getClient().create(Api.class);
    }

    public static DataRepository getInstance() {
        Log.i(TAG, "getInstance()");
        if (instance == null) {
            synchronized (DataRepository.class) {
                if (instance == null) {
                    instance = new DataRepository();
                }
            }
        }
        return instance;
    }

    @NonNull
    public LiveData<List<ForecastFivePojo>> getWeatherData(){
        Log.i(TAG, "getWeatherLiveData()");
        return listMutableLiveData;
    }

    //This method is using Retrofit to get the JSON data from URL
    public void getWeatherDataUsingRetro() {
        Log.i(TAG, "getWeatherDataUsingRetro()");

        Call<ForecastFivePojo> call = apiClient.getDaysOfTheWeek();

        call.enqueue(new Callback<ForecastFivePojo>() {
            @Override
            public void onResponse(Call<ForecastFivePojo> call, Response<ForecastFivePojo> response) {
                Log.i(TAG, "onResponse()");

                List<ForecastFivePojo> list = new ArrayList<>();
                ForecastFivePojo forecastFivePojo = new ForecastFivePojo();

                //Demonstration on how to get values using retrofit.
                ForecastFivePojo forecastForecastFivePojo = response.body();
                assert forecastForecastFivePojo != null;
                String cod = forecastForecastFivePojo.cod;
                double message = forecastForecastFivePojo.message;

                // Demonstration on how to get values using retrofit JSON style.
                String gson = new Gson().toJson(forecastForecastFivePojo);

                try {
                // Opted to use JSON because it's a common use amongst developers.
                    JSONObject json = new JSONObject(gson);
                    JSONArray jArray = json.getJSONArray("list");

                    for(int i = 0; i < jArray.length(); i++){
                        JSONObject json_data = jArray.getJSONObject(i);
                        String sss = "main";
                        JSONObject main = json_data.getJSONObject(sss);
                        //int s = main.getInt("temp");
                        //Log.i(TAG, "temp: " + s);
                        //Object main = json_data.getString("main");
                        //Log.i(TAG, "main: " + main);

                        //Object o = json_data.get("temp");

                        forecastFivePojo.temp =  main.getInt("temp");
                        //Log.i(TAG, "temp: " + forecastFivePojo.temp);

                        list.add(forecastFivePojo);

                        // Post the value(s) of the data to the LiveData Object.
                        listMutableLiveData.postValue(list);
                        //Log.i("Repo: ", String.valueOf(listMutableLiveData));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ForecastFivePojo> call, Throwable t) {
                Log.i(TAG, "onFailure() " + t.getMessage());
                call.cancel();
            }
        });
    }

}
