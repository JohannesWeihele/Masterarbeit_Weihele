package com.example.masterarbeit_weihele.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.masterarbeit_weihele.Classes.Basics.BasicFunctions;
import com.example.masterarbeit_weihele.Services.CommunicationService;
import com.example.masterarbeit_weihele.Services.VitalsService;
import com.example.masterarbeit_weihele.databinding.ActivityFunctionsBinding;

public class FunctionsActivity extends WakeLockActivity {

    //Basics
    private ActivityFunctionsBinding binding;
    private final BasicFunctions basicFunctions = new BasicFunctions(this);

    //Prefixes
    private static final int PERMISSION_REQ_ID = 22;
    private static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFunctionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        basicFunctions.hideDownIcon();
        basicFunctions.changeActivityOnRotation(OptionsActivity.class, VitalsActivity.class);
        basicFunctions.getTime();

        initializeServices();
    }

    public void initializeServices(){
        Intent vitalsServiceIntent = new Intent(this, VitalsService.class);
        startService(vitalsServiceIntent);

        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID);
        } else {
            Intent communicationServiceIntent = new Intent(this, CommunicationService.class);
            startService(communicationServiceIntent);
        }
    }

    private boolean checkSelfPermission() {
        return ContextCompat.checkSelfPermission(this, REQUESTED_PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED;
    }

    public void onFunctionButtonClick(View v){
        Button clickedButton = (Button) v;
        Class<? extends Activity> activity = null;

        switch (clickedButton.getTag().toString()){
            case "function_btn_vitals":
                activity = VitalsActivity.class;
                break;
            case "function_btn_communication":
                activity = CommunicationActivity.class;
                break;
            case "function_btn_emergency":
                activity = EmergencyActivity.class;
                break;
            case "function_btn_commands":
                activity = CommandsActivity.class;
                break;
            case "function_btn_navigation":
                activity = NavigationActivity.class;
                break;
            case "function_btn_more_vitals":
                activity = MoreVitalsActivity.class;
                break;
            case "function_btn_environment":
                activity = EnvironmentActivity.class;
                break;
            case "function_btn_options":
                activity = OptionsActivity.class;
                break;
        }
        basicFunctions.loadActivity(activity);
    }

    @Override
    public void onBackPressed() {
        basicFunctions.loadActivity(MainActivity.class);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}