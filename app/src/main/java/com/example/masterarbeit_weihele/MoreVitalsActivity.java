package com.example.masterarbeit_weihele;

import android.app.Activity;
import android.os.Bundle;

import com.example.masterarbeit_weihele.databinding.ActivityMorevitalsBinding;

public class MoreVitalsActivity extends WakeLockActivity {

    private ActivityMorevitalsBinding binding;
    private final BasicFunctions basicFunctions = new BasicFunctions(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMorevitalsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        basicFunctions.hideDownIcon();
        basicFunctions.changeActivityOnRotation(EnvironmentActivity.class, OptionsActivity.class);

    }
}