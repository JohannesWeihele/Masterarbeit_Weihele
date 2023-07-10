package com.example.masterarbeit_weihele.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.masterarbeit_weihele.Classes.BasicFunctions;
import com.example.masterarbeit_weihele.R;
import com.example.masterarbeit_weihele.Classes.SharedPreferencesVals;
import com.example.masterarbeit_weihele.databinding.ActivityOptionsBinding;
import com.jakewharton.processphoenix.ProcessPhoenix;

public class OptionsActivity extends WakeLockActivity {

    //Basics
    private ActivityOptionsBinding binding;
    private final BasicFunctions basicFunctions = new BasicFunctions(this);
    private final SharedPreferencesVals sharedPreferencesVals = new SharedPreferencesVals(this);

    //Prefixes
    private static final String PREFS_UPDATED = "Optionen aktualisiert";

    private static final String PREFS_ACCOUNT = "Account";
    private static final String PREFS_ACCOUNT_NAME = "accountName";
    private static final String PREFS_ACCOUNT_AGE = "accountAge";
    private static final String PREFS_ACCOUNT_BODYSIZE = "accountBodysize";
    private static final String PREFS_ACCOUNT_BODYWEIGHT = "accountBodyweight";

    private static final String PREFS_COMMUNICATION = "Communication";
    private static final String PREFS_COMMUNICATION_PUSHTOTALK = "pushToTalkVal";

    private static final String PREFS_EMERGENCY = "Emergency";
    private static final String PREFS_EMERGENCY_FALL = "EmergencyFallVal";
    private static final String PREFS_EMERGENCY_CANCEL_TIME = "EmergencyCancelTime";

    private static final String PREFS_VITALS = "Vitals";
    private static final String PREFS_VITALS_BPM = "VitalsBPMVal";
    private static final String PREFS_VITALS_STRESS = "VitalsStressVal";
    private static final String PREFS_VITALS_BODYTEMP = "VitalsBodytempVal";
    private static final String PREFS_VITALS_BREATHEFREQ = "VitalsBreatheFreq";

    private static final String PREFS_VITALS_BPM_MIN = "VitalsBPMMinVal";
    private static final String PREFS_VITALS_STRESS_MIN = "VitalsStressMinVal";
    private static final String PREFS_VITALS_BODYTEMP_MIN = "VitalsBodyTempMinVal";
    private static final String PREFS_VITALS_BREATHEFREQ_MIN = "VitalsBreatheFreqMinVal";

    private static final String PREFS_VITALS_BPM_MAX = "VitalsBPMMaxVal";
    private static final String PREFS_VITALS_STRESS_MAX = "VitalsStressMaxVal";
    private static final String PREFS_VITALS_BODYTEMP_MAX = "VitalsBodyTempMaxVal";
    private static final String PREFS_VITALS_BREATHEFREQ_MAX = "VitalsBreatheFreqMaxVal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        basicFunctions.hideDownIcon();
        basicFunctions.changeActivityOnRotation(MoreVitalsActivity.class, FunctionsActivity.class);
        basicFunctions.getTime();
    }

    public void optionClick(View v){
        Button clickedButton = (Button) v;

        switch (clickedButton.getTag().toString()){
            case "options_btn_vitals":
                setContentView(R.layout.activity_options_vitals);
                getVitalVals();
                break;
            case "options_btn_communication":
                setContentView(R.layout.activity_options_communication);
                getCommunicationVals();
                break;
            case "options_btn_emergency":
                setContentView(R.layout.activity_options_emergency);
                getEmergencyVals();
                break;
            case "options_btn_account":
                setContentView(R.layout.activity_options_account);
                getAccountVals();
                break;
        }
    }

    public void getAccountVals(){
        sharedPreferencesVals.fetchAccountPreferenceVals();

        EditText nameView = findViewById(R.id.options_accountName);
        EditText ageView = findViewById(R.id.options_accountAge);
        EditText bodysizeView = findViewById(R.id.options_accountBodysize);
        EditText bodyweightView = findViewById(R.id.options_accountBodyweight);

        nameView.setText(sharedPreferencesVals.getAccountName());
        ageView.setText(sharedPreferencesVals.getAccountAge());
        bodysizeView.setText(sharedPreferencesVals.getAccountBodysize());
        bodyweightView.setText(sharedPreferencesVals.getAccountBodyweight());
    }

    public void updateAccountVals(View v) {
        EditText nameView = findViewById(R.id.options_accountName);
        EditText ageView = findViewById(R.id.options_accountAge);
        EditText bodysizeView = findViewById(R.id.options_accountBodysize);
        EditText bodyweightView = findViewById(R.id.options_accountBodyweight);

        if (!areFieldsFilled(nameView, ageView, bodysizeView, bodyweightView)) {
            Toast.makeText(getApplicationContext(), "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show();
        } else {
            String accountName = nameView.getText().toString();
            String accountAge = ageView.getText().toString();
            String accountBodysize = bodysizeView.getText().toString();
            String accountBodyweight = bodyweightView.getText().toString();

            updatePreferences(PREFS_ACCOUNT,
                    new String[]{PREFS_ACCOUNT_NAME, PREFS_ACCOUNT_AGE, PREFS_ACCOUNT_BODYSIZE, PREFS_ACCOUNT_BODYWEIGHT},
                    new Object[]{accountName, accountAge, accountBodysize, accountBodyweight});

            Toast.makeText(getApplicationContext(), PREFS_UPDATED, Toast.LENGTH_SHORT).show();

            setContentView(R.layout.activity_options);
        }
    }

    public void getCommunicationVals() {
        sharedPreferencesVals.fetchCommunicationPreferenceVals();

        Switch pushToTalk = findViewById(R.id.options_communication_switch);
        pushToTalk.setChecked(sharedPreferencesVals.getPushToTalkVal());
    }

    public void updateCommunicationVals(View v) {
        Switch pushToTalk = findViewById(R.id.options_communication_switch);

        updatePreferences(PREFS_COMMUNICATION, new String[]{PREFS_COMMUNICATION_PUSHTOTALK}, new Object[]{pushToTalk.isChecked()});
        Toast.makeText(getApplicationContext(), PREFS_UPDATED, Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_options);
    }

    public void getEmergencyVals() {
        sharedPreferencesVals.fetchEmergencyPreferenceVals();

        Switch emergencyFallSwitch = findViewById(R.id.options_emergency_fall_switch);
        EditText emergencyCancelTimeView = findViewById(R.id.options_emergency_cancel_time);

        emergencyFallSwitch.setChecked(sharedPreferencesVals.getEmergencyFall());
        emergencyCancelTimeView.setText(sharedPreferencesVals.getEmergencyCancelTime());
    }

    public void updateEmergencyVals(View v) {
        Switch emergencyFallSwitch = findViewById(R.id.options_emergency_fall_switch);
        EditText emergencyCancelTimeView = findViewById(R.id.options_emergency_cancel_time);

        if (TextUtils.isEmpty(emergencyCancelTimeView.getText())) {
            Toast.makeText(getApplicationContext(), "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show();
        } else {
            String emergencyCancelTime = emergencyCancelTimeView.getText().toString();

            updatePreferences(PREFS_EMERGENCY,
                    new String[]{PREFS_EMERGENCY_FALL, PREFS_EMERGENCY_CANCEL_TIME},
                    new Object[]{emergencyFallSwitch.isChecked(), emergencyCancelTime});

            Toast.makeText(getApplicationContext(), PREFS_UPDATED, Toast.LENGTH_SHORT).show();

            setContentView(R.layout.activity_options);
        }
    }

    public void getVitalVals() {
        sharedPreferencesVals.fetchVitalPreferenceVals();

        Switch vitalsBPMSwitch = findViewById(R.id.options_vitals_bpm_switch);
        Switch vitalsStressSwitch = findViewById(R.id.options_vitals_stress_switch);
        Switch vitalsBodyTempSwitch = findViewById(R.id.options_vitals_bodytemp_switch);
        Switch vitalsBreatheFreqSwitch = findViewById(R.id.options_vitals_breathe_freq_switch);

        EditText vitalsBPMMinView = findViewById(R.id.options_vitals_bpm_min);
        EditText vitalsStressMinView = findViewById(R.id.options_vitals_stress_min);
        EditText vitalsBodyTempMinView = findViewById(R.id.options_vitals_bodytemp_min);
        EditText vitalsBreatheFreqMinView = findViewById(R.id.options_vitals_breathe_freq_min);

        EditText vitalsBPMMaxView = findViewById(R.id.options_vitals_bpm_max);
        EditText vitalsStressMaxView = findViewById(R.id.options_vitals_stress_max);
        EditText vitalsBodyTempMaxView = findViewById(R.id.options_vitals_bodytemp_max);
        EditText vitalsBreatheFreqMaxView = findViewById(R.id.options_vitals_breathe_freq_max);

        vitalsBPMSwitch.setChecked(sharedPreferencesVals.getVitalsBPM());
        vitalsStressSwitch.setChecked(sharedPreferencesVals.getVitalsStress());
        vitalsBodyTempSwitch.setChecked(sharedPreferencesVals.getVitalsBodytemp());
        vitalsBreatheFreqSwitch.setChecked(sharedPreferencesVals.getVitalsBreatheFreq());

        vitalsBPMMinView.setText(sharedPreferencesVals.getVitalsBPMMinVal());
        vitalsStressMinView.setText(sharedPreferencesVals.getVitalsStressMinVal());
        vitalsBodyTempMinView.setText(sharedPreferencesVals.getVitalsBodyTempMinVal());
        vitalsBreatheFreqMinView.setText(sharedPreferencesVals.getVitalsBreatheFreqMinVal());

        vitalsBPMMaxView.setText(sharedPreferencesVals.getVitalsBPMMaxVal());
        vitalsStressMaxView.setText(sharedPreferencesVals.getVitalsStressMaxVal());
        vitalsBodyTempMaxView.setText(sharedPreferencesVals.getVitalsBodyTempMaxVal());
        vitalsBreatheFreqMaxView.setText(sharedPreferencesVals.getVitalsBreatheFreqMaxVal());
    }

    public void updateVitalVals(View v) {
        Switch vitalsBPMSwitch = findViewById(R.id.options_vitals_bpm_switch);
        Switch vitalsStressSwitch = findViewById(R.id.options_vitals_stress_switch);
        Switch vitalsBodyTempSwitch = findViewById(R.id.options_vitals_bodytemp_switch);
        Switch vitalsBreatheFreqSwitch = findViewById(R.id.options_vitals_breathe_freq_switch);

        EditText vitalsBPMMinView = findViewById(R.id.options_vitals_bpm_min);
        EditText vitalsStressMinView = findViewById(R.id.options_vitals_stress_min);
        EditText vitalsBodyTempMinView = findViewById(R.id.options_vitals_bodytemp_min);
        EditText vitalsBreatheFreqMinView = findViewById(R.id.options_vitals_breathe_freq_min);

        EditText vitalsBPMMaxView = findViewById(R.id.options_vitals_bpm_max);
        EditText vitalsStressMaxView = findViewById(R.id.options_vitals_stress_max);
        EditText vitalsBodyTempMaxView = findViewById(R.id.options_vitals_bodytemp_max);
        EditText vitalsBreatheFreqMaxView = findViewById(R.id.options_vitals_breathe_freq_max);

        String vitalsBPMMinVal = vitalsBPMMinView.getText().toString().trim();
        String vitalsStressMinVal = vitalsStressMinView.getText().toString().trim();
        String vitalsBodyTempMinVal = vitalsBodyTempMinView.getText().toString().trim();
        String vitalsBreatheFreqMinVal = vitalsBreatheFreqMinView.getText().toString().trim();

        String vitalsBPMMaxVal = vitalsBPMMaxView.getText().toString().trim();
        String vitalsStressMaxVal = vitalsStressMaxView.getText().toString().trim();
        String vitalsBodyTempMaxVal = vitalsBodyTempMaxView.getText().toString().trim();
        String vitalsBreatheFreqMaxVal = vitalsBreatheFreqMaxView.getText().toString().trim();

        if (!areFieldsFilled(vitalsBPMMinView, vitalsStressMinView, vitalsBodyTempMinView,
                vitalsBreatheFreqMinView, vitalsBPMMaxView, vitalsStressMaxView, vitalsBodyTempMaxView, vitalsBreatheFreqMaxView)){
            Toast.makeText(getApplicationContext(), "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show();
        } else {
            updatePreferences(PREFS_VITALS,
                    new String[]{PREFS_VITALS_BPM, PREFS_VITALS_STRESS, PREFS_VITALS_BODYTEMP, PREFS_VITALS_BREATHEFREQ,
                            PREFS_VITALS_BPM_MIN, PREFS_VITALS_STRESS_MIN, PREFS_VITALS_BODYTEMP_MIN, PREFS_VITALS_BREATHEFREQ_MIN,
                            PREFS_VITALS_BPM_MAX, PREFS_VITALS_STRESS_MAX, PREFS_VITALS_BODYTEMP_MAX, PREFS_VITALS_BREATHEFREQ_MAX},
                    new Object[]{vitalsBPMSwitch.isChecked(), vitalsStressSwitch.isChecked(), vitalsBodyTempSwitch.isChecked(), vitalsBreatheFreqSwitch.isChecked(),
                            vitalsBPMMinVal, vitalsStressMinVal, vitalsBodyTempMinVal, vitalsBreatheFreqMinVal,
                            vitalsBPMMaxVal, vitalsStressMaxVal, vitalsBodyTempMaxVal, vitalsBreatheFreqMaxVal});

            Toast.makeText(getApplicationContext(), PREFS_UPDATED, Toast.LENGTH_SHORT).show();

            setContentView(R.layout.activity_options);
        }
        restartApp();
    }

    private void updatePreferences(String prefsName, String[] keys, Object[] values) {
        SharedPreferences sharedPreferences = getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            Object value = values[i];
            if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof String) {
                editor.putString(key, (String) value);
            }
        }
        editor.apply();
    }

    public boolean areFieldsFilled(EditText... fields) {
        for (EditText field : fields) {
            if (TextUtils.isEmpty(field.getText())) {
                return false;
            }
        }
        return true;
    }

    private void restartApp() {
        Intent intent = new Intent(this, MainActivity.class);
        restartLoading();
        ProcessPhoenix.triggerRebirth(this, intent);
    }

    private void restartLoading() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Neustart...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

}