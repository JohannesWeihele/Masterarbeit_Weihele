package com.example.masterarbeit_weihele.Classes.WeatherData;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

    @SerializedName("main")
    private WeatherData weatherData;

    @SerializedName("wind")
    private WindData wind;

    @SerializedName("weather")
    private WeatherConditionData[] weatherConditions;

    public WeatherData getWeatherData() {
        return weatherData;
    }

    public WindData getWind() {
        return wind;
    }

    public WeatherConditionData[] getWeatherConditions() {
        return weatherConditions;
    }

}