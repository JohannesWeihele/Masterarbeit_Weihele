package com.example.masterarbeit_weihele;

import android.app.Activity;
import android.os.Bundle;

import com.example.masterarbeit_weihele.databinding.ActivityVitalsBinding;

public class VitalsActivity extends Activity {

    private ActivityVitalsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVitalsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}