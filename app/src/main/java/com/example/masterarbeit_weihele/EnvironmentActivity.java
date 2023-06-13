package com.example.masterarbeit_weihele;

import android.app.Activity;
import android.os.Bundle;

import com.example.masterarbeit_weihele.databinding.ActivityEnvironmentBinding;

public class EnvironmentActivity extends Activity {

    private ActivityEnvironmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEnvironmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}