package com.example.masterarbeit_weihele;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

    @SerializedName("main")
    private WeatherData weatherData;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("weather")
    private WeatherCondition[] weatherConditions;

    public WeatherData getWeatherData() {
        return weatherData;
    }

    public Wind getWind() {
        return wind;
    }

    public WeatherCondition[] getWeatherConditions() {
        return weatherConditions;
    }

}