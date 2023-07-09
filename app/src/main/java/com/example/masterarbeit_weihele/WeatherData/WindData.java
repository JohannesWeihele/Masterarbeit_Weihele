package com.example.masterarbeit_weihele.WeatherData;

import com.google.gson.annotations.SerializedName;

public class WindData {

    @SerializedName("speed")
    private float speed;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
