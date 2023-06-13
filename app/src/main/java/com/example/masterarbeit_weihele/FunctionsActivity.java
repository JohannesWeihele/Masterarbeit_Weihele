package com.example.masterarbeit_weihele;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.masterarbeit_weihele.databinding.ActivityFunctionsBinding;

public class FunctionsActivity extends Activity {

    private ActivityFunctionsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFunctionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void functionClick(View v){
        Button clickedButton = (Button) v;
        Intent intent = null;

        switch (clickedButton.getTag().toString()){
            case "function_btn_vitals":
                intent = new Intent(FunctionsActivity.this, VitalsActivity.class);
                break;
            case "function_btn_communication":
                intent = new Intent(FunctionsActivity.this, CommunicationActivity.class);
                break;
            case "function_btn_emergency":
                intent = new Intent(FunctionsActivity.this, EmergencyActivity.class);
                break;
            case "function_btn_commands":
                intent = new Intent(FunctionsActivity.this, CommandsActivity.class);
                break;
            case "function_btn_navigation":
                intent = new Intent(FunctionsActivity.this, NavigationActivity.class);
                break;
            case "function_btn_more_vitals":
                intent = new Intent(FunctionsActivity.this, MoreVitalsActivity.class);
                break;
            case "function_btn_environment":
                intent = new Intent(FunctionsActivity.this, EnvironmentActivity.class);
                break;
            case "function_btn_options":
                intent = new Intent(FunctionsActivity.this, OptionsActivity.class);
                break;
        }

        startActivity(intent);
    }
}