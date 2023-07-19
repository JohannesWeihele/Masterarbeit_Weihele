package com.example.masterarbeit_weihele.Classes.WeatherData;

import com.google.gson.annotations.SerializedName;

public class WeatherConditionData {

    @SerializedName("icon")
    private String icon;

    public String getIcon() {
        return icon;
    }

}