package com.example.masterarbeit_weihele;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.masterarbeit_weihele.databinding.ActivityOptionsBinding;

public class OptionsActivity extends Activity {

    private ActivityOptionsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void optionClick(View v){
        Button clickedButton = (Button) v;

        switch (clickedButton.getTag().toString()){
            case "options_btn_vitals":
                setContentView(R.layout.activity_options_vitals);
                break;
            case "options_btn_communication":
                setContentView(R.layout.activity_options_communication);
                break;
            case "options_btn_emergency":
                setContentView(R.layout.activity_options_emergency);
                break;
            case "options_btn_commands":
                setContentView(R.layout.activity_options_account);
                break;
        }

    }
}