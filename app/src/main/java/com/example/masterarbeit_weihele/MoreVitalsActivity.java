package com.example.masterarbeit_weihele;

import android.app.Activity;
import android.os.Bundle;

import com.example.masterarbeit_weihele.databinding.ActivityMorevitalsBinding;

public class MoreVitalsActivity extends Activity {

    private ActivityMorevitalsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMorevitalsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}