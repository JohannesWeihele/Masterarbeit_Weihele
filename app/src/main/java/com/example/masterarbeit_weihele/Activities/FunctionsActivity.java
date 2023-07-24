package com.example.masterarbeit_weihele.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    private static final int PERMISSION_REQUEST_BODY_SENSORS_ID = 1;
    private static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.BODY_SENSORS

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

        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID);
        }

    }

    private boolean checkSelfPermission() {

        if(ContextCompat.checkSelfPermission(this, REQUESTED_PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED)
        {
            return ContextCompat.checkSelfPermission(this, REQUESTED_PERMISSIONS[1]) == PackageManager.PERMISSION_GRANTED;
        } else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                Intent communicationServiceIntent = new Intent(this, CommunicationService.class);
                startService(communicationServiceIntent);

                Intent vitalsServiceIntent = new Intent(this, VitalsService.class);
                startService(vitalsServiceIntent);
            } else {
                System.out.println("Berechtigungen wurden nicht erteilt.");
            }
        }
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