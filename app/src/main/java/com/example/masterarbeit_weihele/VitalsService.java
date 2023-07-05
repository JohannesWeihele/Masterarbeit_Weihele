package com.example.masterarbeit_weihele;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class VitalsService extends Service implements SensorEventListener {

    private static final String TAG = "HeartRateService";
    private SensorManager sensorManager;
    private Sensor heartRateSensor;
    private Vibrator vibrator;

    private SharedPreferencesVals sharedPreferencesVals;
    private int heartRate;
    private boolean isHeartRateApplied;
    private int critMinHeartRate;
    private int critMaxHeartRate;

    private boolean isToastShown = false;
    private boolean isEmergencyActivated = false;
    private boolean isFirstReading = true;
    private Handler handler;
    private Runnable vitalsCheckRunnable;
    private Toast toast;

    public VitalsService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startMonitoring();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMonitoring();
        stopSelf();
    }

    private void startMonitoring() {

        sharedPreferencesVals = new SharedPreferencesVals(this);
        sharedPreferencesVals.getVitalPreferenceVals();
        checkSharedPreferencesVals();

        if (heartRateSensor != null && isHeartRateApplied) {
            sensorManager.registerListener(this, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "Herzfrequenz-Übermittlung gestartet");
        } else {
            Log.d(TAG, "Herzfrequenzdaten nicht gefunden");
        }
    }

    private void checkSharedPreferencesVals(){
        SharedPreferences sharedPreferences = getSharedPreferences("Vitals", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(sharedPreferencesVals.vitalsBPMMinVal == null || sharedPreferencesVals.vitalsBPMMinVal.isEmpty()){
            editor.putString("VitalsBPMMinVal", "35");
            critMinHeartRate = 35;
        } else {
            critMinHeartRate = Integer.parseInt(sharedPreferencesVals.vitalsBPMMinVal);
        }

        if (sharedPreferencesVals.vitalsBPMMaxVal == null || sharedPreferencesVals.vitalsBPMMaxVal.isEmpty()){
            editor.putString("VitalsBPMMaxVal", "180");
            critMaxHeartRate = 180;
        } else {
            critMaxHeartRate = Integer.parseInt(sharedPreferencesVals.vitalsBPMMaxVal);
        }

        if (sharedPreferencesVals.vitalsBPM == null){
            editor.putBoolean("VitalsBPMVal", true);
            isHeartRateApplied = true;
        } else {
            isHeartRateApplied = sharedPreferencesVals.vitalsBPM;
        }

        editor.apply();

    }

    private void checkCriticalVitals() {
        if (heartRate <= critMinHeartRate || heartRate >= critMaxHeartRate && !isEmergencyActivated) {
            if (!isToastShown) {
                toast = Toast.makeText(this, "Herzfrequenz kritisch!", Toast.LENGTH_LONG);
                toast.show();
                isToastShown = true;
                vibrate();

                isEmergencyActivated = true;
                Intent intent = new Intent(VitalsService.this, EmergencyActivity.class);
                intent.putExtra("isVitalsEmergency", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } else {
            if (isToastShown) {
                toast.cancel();
                isToastShown = false;
            }
        }
    }


    private void stopMonitoring() {
        sensorManager.unregisterListener(this);
        Log.d(TAG, "Herzfrequenz-Übermittlung gestoppt");
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(isHeartRateApplied){
            if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
                heartRate = (int) event.values[0];
                Log.d(TAG, "Heart rate: " + heartRate);

                if(isFirstReading){
                    handler = new Handler();
                    vitalsCheckRunnable = new Runnable() {
                        @Override
                        public void run() {
                            checkCriticalVitals();
                        }
                    };

                    handler.postDelayed(vitalsCheckRunnable, 20000);
                } else {
                    checkCriticalVitals();
                }

                Intent intent = new Intent("HEART_RATE_UPDATE");
                intent.putExtra("heartRate", heartRate);
                sendBroadcast(intent);
            }
        } else {
            stopMonitoring();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void vibrate() {

        if (vibrator.hasVibrator()) {
            long[] pattern = {0, 2000, 500, 2000, 500, 2000, 500};
            int repeat = -1;
            vibrator.vibrate(pattern, repeat);
        }
    }
}
