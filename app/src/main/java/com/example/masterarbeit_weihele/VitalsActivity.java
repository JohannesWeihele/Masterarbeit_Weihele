package com.example.masterarbeit_weihele;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.masterarbeit_weihele.databinding.ActivityVitalsBinding;
import com.samsung.android.sdk.healthdata.HealthDataStore;


public class VitalsActivity extends Activity {

    private ActivityVitalsBinding binding;
    private SharedPreferencesVals sharedPreferencesVals = new SharedPreferencesVals(this);
    private HealthDataStore healthDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVitalsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkAccountPreferences();
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkAccountPreferences();
    }

    public void checkAccountPreferences(){
        sharedPreferencesVals.getVitalPreferenceVals();

        FrameLayout BPMView = findViewById(R.id.vitals_bpm_wrapper);
        FrameLayout StressView = findViewById(R.id.vitals_stress_wrapper);
        FrameLayout BodyTempView = findViewById(R.id.vitals_bodytemp_wrapper);
        FrameLayout BreatheFreqView = findViewById(R.id.vitals_breathe_freq_wrapper);

        setViewVisibility(BPMView, sharedPreferencesVals.vitalsBPM);
        setViewVisibility(StressView, sharedPreferencesVals.vitalsStress);
        setViewVisibility(BodyTempView, sharedPreferencesVals.vitalsBodytemp);
        setViewVisibility(BreatheFreqView, sharedPreferencesVals.vitalsBreatheFreq);
    }

    public void setViewVisibility(FrameLayout view, Boolean isVisible){
        if(isVisible){
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

}
