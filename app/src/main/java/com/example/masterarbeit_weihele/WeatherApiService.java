package com.example.masterarbeit_weihele;

import com.example.masterarbeit_weihele.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("weather")
    Call<WeatherResponse> getWeatherData(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("appid") String apiKey
    );
}
