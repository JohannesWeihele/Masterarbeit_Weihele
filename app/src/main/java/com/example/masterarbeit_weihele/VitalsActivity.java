package com.example.masterarbeit_weihele;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.masterarbeit_weihele.databinding.ActivityVitalsBinding;

import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthData;
import com.samsung.android.sdk.healthdata.HealthDataResolver;
import com.samsung.android.sdk.healthdata.HealthDataResolver.ReadRequest;
import com.samsung.android.sdk.healthdata.HealthDataResolver.ReadResult;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthResultHolder;

public class VitalsActivity extends Activity {

    private ActivityVitalsBinding binding;
    private HealthDataStore healthDataStore;
    private SharedPreferencesVals sharedPreferencesVals = new SharedPreferencesVals(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVitalsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkAccountPreferences();

        // Initialisiere die HealthDataStore
        healthDataStore = new HealthDataStore(this, new HealthDataStore.ConnectionListener() {
            @Override
            public void onConnected() {
                // Bei erfolgreicher Verbindung
                readHeartRate();
            }

            @Override
            public void onConnectionFailed(HealthConnectionErrorResult error) {
                // Bei Verbindungsfehlern
            }

            @Override
            public void onDisconnected() {
                // Bei Verbindungstrennung
            }
        });

        // Verbinde mit der HealthDataStore
        healthDataStore.connectService();
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


    private void readHeartRate() {
        HealthDataResolver resolver = new HealthDataResolver(healthDataStore, null);

        // Erstelle die Anfrage zum Lesen der Herzfrequenzdaten
        ReadRequest request = new ReadRequest.Builder()
                .setDataType(HealthConstants.HeartRate.HEALTH_DATA_TYPE)
                .build();

        // Führe die Anfrage aus
        resolver.read(request).setResultListener(new HealthResultHolder.ResultListener<ReadResult>() {
            @Override
            public void onResult(ReadResult readResult) {
                try {
                    for (HealthData data : readResult) {
                        // Lies die Herzfrequenzdaten aus
                        int heartRate = data.getInt(HealthConstants.HeartRate.HEART_RATE);

                        System.out.println(heartRate);
                    }
                } finally {
                    readResult.close();
                }
            }
        });
    }

}


