package com.example.masterarbeit_weihele.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.masterarbeit_weihele.Classes.BasicFunctions;
import com.example.masterarbeit_weihele.R;
import com.example.masterarbeit_weihele.Classes.SharedPreferencesVals;
import com.example.masterarbeit_weihele.databinding.ActivityEmergencyBinding;

public class EmergencyActivity extends WakeLockActivity {

    //Basics
    private ActivityEmergencyBinding binding;
    private final BasicFunctions basicFunctions = new BasicFunctions(this);
    private final SharedPreferencesVals sharedPreferencesVals = new SharedPreferencesVals(this);

    //Variables
    private CountDownTimer countDownTimer;
    private Integer PreferenceCountDown;
    private SoundPool soundPool;
    private boolean isClicked = false;
    private boolean isVitalsEmergency = false;
    private int soundId;
    private boolean isToastShown = false;

    //Prefixes
    private static final String PREF_HEART_RATE_UPDATE = "HEART_RATE_UPDATE";
    private static final String PREF_VITALS_EMERGENCY = "isVitalsEmergency";
    private static final String PREF_HEART_RATE = "heartRate";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEmergencyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getPreferences();

        basicFunctions.changeActivityOnRotation(CommandsActivity.class, NavigationActivity.class);
        basicFunctions.getTime();

        checkEmergency();
    }

    private final BroadcastReceiver heartRateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(PREF_HEART_RATE_UPDATE)) {
                int heartRate = intent.getIntExtra(PREF_HEART_RATE, 0);

                TextView heartRateView = findViewById(R.id.emergency_bpm);
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

    public void checkEmergency(){
        Intent intent = getIntent();
        isVitalsEmergency = intent.getBooleanExtra(PREF_VITALS_EMERGENCY, false);

        if(isVitalsEmergency){
            startEmergency(null);
        }
    }

    public void startEmergency(View v) {

        TextView emergencyActivatedText = findViewById(R.id.emergency_activated_text);
        TextView emergencyBtnText = findViewById(R.id.emergency_text);
        FrameLayout emergencyVitals = findViewById(R.id.emergency_vitals_wrapper);
        Button emergencyCancelButton = findViewById(R.id.emergency_cancel_btn);

        if(!isClicked){
            isClicked = true;
            emergencyActivatedText.setText("Notfallsignal beginnt in " + PreferenceCountDown + " Sekunden...");
            emergencyActivatedText.setVisibility(View.VISIBLE);
            emergencyBtnText.setVisibility(View.GONE);
            emergencyCancelButton.setVisibility(View.VISIBLE);
            countDownTimer = new CountDownTimer(PreferenceCountDown * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    PreferenceCountDown--;
                    emergencyActivatedText.setText("Notfallsignal beginnt in " + PreferenceCountDown + " Sekunden...");
                }

                @Override
                public void onFinish() {
                    emergencyActivatedText.setVisibility(View.GONE);
                    emergencyVitals.setVisibility(View.VISIBLE);
                    playEmergencySound();
                }
            }.start();
        }
    }

    public void stopEmergency(View v){
        isClicked = false;
        TextView emergencyBtnText = findViewById(R.id.emergency_text);
        TextView emergencyActivatedText = findViewById(R.id.emergency_activated_text);
        Button emergencyCancelButton = findViewById(R.id.emergency_cancel_btn);
        FrameLayout emergencyVitals = findViewById(R.id.emergency_vitals_wrapper);

        emergencyBtnText.setVisibility(View.VISIBLE);
        emergencyActivatedText.setVisibility(View.GONE);
        emergencyCancelButton.setVisibility(View.GONE);
        emergencyVitals.setVisibility(View.GONE);

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        if(PreferenceCountDown == 0){
            soundPool.stop(soundId);
            soundPool.release();
        }
        PreferenceCountDown = Integer.valueOf(sharedPreferencesVals.getEmergencyCancelTime());
    }

    public void playEmergencySound(){

        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(attributes)
                .build();

        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {

            soundPool.play(soundId, 1.0f, 1.0f, 0, -1, 1.0f);
        });
        soundId = soundPool.load(this, R.raw.alarm_noise, 1);
    }

    public void setViewVisibility(FrameLayout view, Boolean isVisible){
        if(isVisible){
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    public void getPreferences(){
        sharedPreferencesVals.fetchVitalPreferenceVals();
        sharedPreferencesVals.fetchEmergencyPreferenceVals();
        PreferenceCountDown = Integer.valueOf(sharedPreferencesVals.getEmergencyCancelTime());
        System.out.println(PreferenceCountDown);

        FrameLayout BPMView = findViewById(R.id.emergency_bpm_wrapper);
        FrameLayout stressView = findViewById(R.id.emergency_stress_wrapper);
        FrameLayout bodyTempView = findViewById(R.id.emergency_bodytemp_wrapper);
        FrameLayout breatheFreqView = findViewById(R.id.emergency_breathe_freq_wrapper);

        setViewVisibility(BPMView, sharedPreferencesVals.getVitalsBPM());
        setViewVisibility(stressView, sharedPreferencesVals.getVitalsStress());
        setViewVisibility(bodyTempView, sharedPreferencesVals.getVitalsBodytemp());
        setViewVisibility(breatheFreqView, sharedPreferencesVals.getVitalsBreatheFreq());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferences();

        Intent intent = getIntent();
        isVitalsEmergency = intent.getBooleanExtra(PREF_VITALS_EMERGENCY, false);

        IntentFilter intentFilter = new IntentFilter(PREF_HEART_RATE_UPDATE);
        registerReceiver(heartRateReceiver, intentFilter);
    }

    @Override
    protected void onPause(){
        super.onPause();
        stopEmergency(null);
        unregisterReceiver(heartRateReceiver);
    }


}