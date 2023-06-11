package com.example.masterarbeit_weihele;

import android.app.Activity;
import android.os.Bundle;

import com.example.masterarbeit_weihele.databinding.ActivityNavigationBinding;

public class NavigationActivity extends Activity {

    private ActivityNavigationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}