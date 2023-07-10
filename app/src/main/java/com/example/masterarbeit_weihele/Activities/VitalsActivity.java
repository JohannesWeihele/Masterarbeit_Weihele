package com.example.masterarbeit_weihele.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.masterarbeit_weihele.Classes.BasicFunctions;
import com.example.masterarbeit_weihele.R;
import com.example.masterarbeit_weihele.Classes.SharedPreferencesVals;
import com.example.masterarbeit_weihele.databinding.ActivityVitalsBinding;

public class VitalsActivity extends WakeLockActivity {

    private int heartRate;
    private boolean isToastShown = false;

    private ActivityVitalsBinding binding;
    private SharedPreferencesVals sharedPreferencesVals = new SharedPreferencesVals(this);
    private final BasicFunctions basicFunctions = new BasicFunctions(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVitalsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        basicFunctions.changeActivityOnRotation(FunctionsActivity.class, CommandsActivity.class);

        basicFunctions.getTime();
        getPreferences();
    }

    public void getPreferences(){
        sharedPreferencesVals.fetchVitalPreferenceVals();

        FrameLayout BPMView = findViewById(R.id.vitals_bpm_wrapper);
        FrameLayout StressView = findViewById(R.id.vitals_stress_wrapper);
        FrameLayout BodyTempView = findViewById(R.id.vitals_bodytemp_wrapper);
        FrameLayout BreatheFreqView = findViewById(R.id.vitals_breathe_freq_wrapper);

        setViewVisibility(BPMView, sharedPreferencesVals.getVitalsBPM());
        setViewVisibility(StressView, sharedPreferencesVals.getVitalsStress());
        setViewVisibility(BodyTempView, sharedPreferencesVals.getVitalsBodytemp());
        setViewVisibility(BreatheFreqView, sharedPreferencesVals.getVitalsBreatheFreq());
    }

    public void setViewVisibility(FrameLayout view, Boolean isVisible){
        if(isVisible){
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private BroadcastReceiver heartRateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("HEART_RATE_UPDATE")) {
                heartRate = intent.getIntExtra("heartRate", 0);

                TextView heartRateView = findViewById(R.id.vitals_bpm);
                heartRateView.setText(String.valueOf(heartRate));

                if (heartRate == 0 && !isToastShown) {
                    Toast.makeText(getApplicationContext(), "ermittle Daten...", Toast.LENGTH_LONG).show();
                    isToastShown = true;
                } else if (heartRate != 0) {
                    isToastShown = false;
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        getPreferences();

        IntentFilter intentFilter = new IntentFilter("HEART_RATE_UPDATE");
        registerReceiver(heartRateReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(heartRateReceiver);
    }

}


