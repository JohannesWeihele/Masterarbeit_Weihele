package com.example.masterarbeit_weihele;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.masterarbeit_weihele.databinding.ActivityEmergencyBinding;

public class EmergencyActivity extends Activity {

    private ActivityEmergencyBinding binding;

    private TextView emergencyBtnText;
    private TextView emergencyActivatedText;
    private FrameLayout emergencyVitals;
    private CountDownTimer countDownTimer;

    private Button emergencyCancelButton;
    private int countdownSeconds = 5;
    private SoundPool soundPool;
    private boolean isInitialized = false;
    private int soundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEmergencyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        emergencyActivatedText = findViewById(R.id.emergency_activated_text);
        emergencyBtnText = findViewById(R.id.emergency_text);
        emergencyVitals = findViewById(R.id.emergency_vitals_wrapper);
        emergencyCancelButton = findViewById(R.id.emergency_cancel_btn);
    }

    public void playEmergencySound(){
        isInitialized = false;

        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(attributes)
                .build();

        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
            isInitialized = true;
            soundPool.play(soundId, 1.0f, 1.0f, 0, -1, 1.0f);
        });

        soundId = soundPool.load(this, R.raw.alarm_noise, 1);
    }

    public void startEmergency(View v) {
        emergencyActivatedText.setText("Notfallsignal beginnt in " + countdownSeconds + " Sekunden...");
        emergencyActivatedText.setVisibility(View.VISIBLE);
        emergencyBtnText.setVisibility(View.GONE);
        emergencyCancelButton.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(countdownSeconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdownSeconds--;
                emergencyActivatedText.setText("Notfallsignal beginnt in " + countdownSeconds + " Sekunden...");
            }

            @Override
            public void onFinish() {
                emergencyActivatedText.setVisibility(View.GONE);
                emergencyVitals.setVisibility(View.VISIBLE);
                playEmergencySound();
            }
        }.start();
    }
    public void stopEmergency(View v){
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
        countdownSeconds = 5;

        soundPool.stop(soundId);
        soundPool.release();
        isInitialized = false;
    }
}