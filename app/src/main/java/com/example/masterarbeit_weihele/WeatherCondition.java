package com.example.masterarbeit_weihele;

import com.google.gson.annotations.SerializedName;

public class WeatherCondition {

    @SerializedName("icon")
    private String icon;

    public String getIcon() {
        return icon;
    }

}