package com.example.masterarbeit_weihele;

import com.google.gson.annotations.SerializedName;

public class WeatherData {

    @SerializedName("temp")
    private float temperature;

    @SerializedName("humidity")
    private float humidity;

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

}
