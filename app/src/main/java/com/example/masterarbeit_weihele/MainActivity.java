package com.example.masterarbeit_weihele;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.wear.input.WearableButtons;

import com.example.masterarbeit_weihele.databinding.ActivityMainBinding;


public class MainActivity extends WakeLockActivity {

    private ActivityMainBinding binding;

    private final BasicFunctions basicFunctions = new BasicFunctions(this);
    private boolean isBackButtonPressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        basicFunctions.changeActivityOnRotation(null, FunctionsActivity.class);

    }

    public void openCreateAccountView(View v) {
        Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void openFunctionsView(View v){
       Intent intent = new Intent(MainActivity.this, FunctionsActivity.class);
       startActivity(intent);
    }

}