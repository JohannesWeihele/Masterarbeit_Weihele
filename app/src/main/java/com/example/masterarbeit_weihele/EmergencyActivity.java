package com.example.masterarbeit_weihele;

import android.app.Activity;
import android.os.Bundle;

import com.example.masterarbeit_weihele.databinding.ActivityEmergencyBinding;

public class EmergencyActivity extends Activity {

    private ActivityEmergencyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEmergencyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}