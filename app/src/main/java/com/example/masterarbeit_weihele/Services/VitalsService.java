package com.example.masterarbeit_weihele.Services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.example.masterarbeit_weihele.Activities.EmergencyActivity;
import com.example.masterarbeit_weihele.Classes.SharedPreferencesVals;

public class VitalsService extends Service implements SensorEventListener {

    //Basics
    private final SharedPreferencesVals sharedPreferencesVals = new SharedPreferencesVals(this);

    //Variables
    private SensorManager sensorManager;
    private Sensor heartRateSensor;
    private Vibrator vibrator;

    private int heartRate;
    private boolean isHeartRateApplied;
    private int critMinHeartRate;
    private int critMaxHeartRate;

    private boolean isToastShown = false;
    private boolean isEmergencyActivated = false;
    private Handler handler;
    private Runnable vitalsCheckRunnable;
    private long lastCriticalValsCheckTime = System.currentTimeMillis();
    private Toast toast;

    //Prefixes
    private static final String TAG = "HeartRateService";
    private static final String PREF_HEARTRATE_UPDATE = "HEART_RATE_UPDATE";
    private static final String PREF_INTENT_HEARTRATE_EXTRA = "heartRate";
    private static final String PREF_INTENT_EMERCENCY_EXTRA = "isVitalsEmergency";

    private static final String PREFS_VITALS = "Vitals";
    private static final String PREFS_VITALS_BPM = "VitalsBPMVal";
    private static final String PREFS_VITALS_BPM_MIN = "VitalsBPMMinVal";
    private static final String PREFS_VITALS_BPM_MAX = "VitalsBPMMaxVal";

    public VitalsService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        sharedPreferencesVals.fetchVitalPreferenceVals();

        handler = new Handler();
        vitalsCheckRunnable = new Runnable() {
            @Override
            public void run() {
                checkCriticalVitals();
                handler.postDelayed(this, 30000);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startMonitoring();
        return START_STICKY;
    }

    private void startMonitoring() {

        getPreferences();

        if (heartRateSensor != null && isHeartRateApplied) {
            sensorManager.registerListener(this, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "Herzfrequenz-Übermittlung gestartet");
        } else {
            Log.d(TAG, "Herzfrequenzdaten nicht gefunden");
        }
    }

    private void stopMonitoring() {
        sensorManager.unregisterListener(this);
        Log.d(TAG, "Herzfrequenz-Übermittlung gestoppt");
    }

    private void getPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_VITALS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(sharedPreferencesVals.getVitalsBPMMinVal() == null || sharedPreferencesVals.getVitalsBPMMinVal().isEmpty()){
            editor.putString(PREFS_VITALS_BPM_MIN, "35");
            critMinHeartRate = 35;
        } else {
            critMinHeartRate = Integer.parseInt(sharedPreferencesVals.getVitalsBPMMinVal());
        }

        if (sharedPreferencesVals.getVitalsBPMMaxVal() == null || sharedPreferencesVals.getVitalsBPMMaxVal().isEmpty()){
            editor.putString(PREFS_VITALS_BPM_MAX, "180");
            critMaxHeartRate = 180;
        } else {
            critMaxHeartRate = Integer.parseInt(sharedPreferencesVals.getVitalsBPMMaxVal());
        }

        if (sharedPreferencesVals.getVitalsBPM() == null){
            editor.putBoolean(PREFS_VITALS_BPM, true);
            isHeartRateApplied = true;
        } else {
            isHeartRateApplied = sharedPreferencesVals.getVitalsBPM();
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
                intent.putExtra(PREF_INTENT_EMERCENCY_EXTRA, true);
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(isHeartRateApplied){
            if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
                heartRate = (int) event.values[0];
                Log.d(TAG, "Heart rate: " + heartRate);

                long currentTime = System.currentTimeMillis();

                System.out.println("Herzfrequenz-Check: " + (currentTime - lastCriticalValsCheckTime));
                if (currentTime - lastCriticalValsCheckTime >= 30000) {

                    if (handler != null && vitalsCheckRunnable != null && !handler.hasCallbacks(vitalsCheckRunnable)) {
                        handler.post(vitalsCheckRunnable);
                    }

                    lastCriticalValsCheckTime = currentTime;
                }

                Intent intent = new Intent(PREF_HEARTRATE_UPDATE);
                intent.putExtra(PREF_INTENT_HEARTRATE_EXTRA, heartRate);
                sendBroadcast(intent);
            }
        } else {
            stopMonitoring();
        }
    }

    private void vibrate() {

        if (vibrator.hasVibrator()) {
            long[] pattern = {0, 2000, 500, 2000, 500, 2000, 500};
            int repeat = -1;
            vibrator.vibrate(pattern, repeat);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMonitoring();
        stopSelf();
    }
}
