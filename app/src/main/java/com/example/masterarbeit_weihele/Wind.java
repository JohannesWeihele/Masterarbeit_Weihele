package com.example.masterarbeit_weihele;

import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("speed")
    private float speed;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
