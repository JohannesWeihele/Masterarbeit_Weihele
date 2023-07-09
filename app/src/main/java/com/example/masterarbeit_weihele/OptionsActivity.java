package com.example.masterarbeit_weihele;

import android.app.Activity;
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

import com.example.masterarbeit_weihele.databinding.ActivityOptionsBinding;
import com.jakewharton.processphoenix.ProcessPhoenix;

public class OptionsActivity extends WakeLockActivity {

    private ActivityOptionsBinding binding;
    private final BasicFunctions basicFunctions = new BasicFunctions(this);

    private SharedPreferencesVals sharedPreferencesVals = new SharedPreferencesVals(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        basicFunctions.hideDownIcon();
        basicFunctions.changeActivityOnRotation(MoreVitalsActivity.class, FunctionsActivity.class);
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
        sharedPreferencesVals.getAccountPreferenceVals();

        EditText nameView = findViewById(R.id.options_accountName);
        EditText ageView = findViewById(R.id.options_accountAge);
        EditText bodysizeView = findViewById(R.id.options_accountBodysize);
        EditText bodyweightView = findViewById(R.id.options_accountBodyweight);

        nameView.setText(sharedPreferencesVals.accountName);
        ageView.setText(sharedPreferencesVals.accountAge);
        bodysizeView.setText(sharedPreferencesVals.accountBodysize);
        bodyweightView.setText(sharedPreferencesVals.accountBodyweight);
    }

    public void updateAccountVals(View v) {
        EditText nameView = findViewById(R.id.options_accountName);
        EditText ageView = findViewById(R.id.options_accountAge);
        EditText bodysizeView = findViewById(R.id.options_accountBodysize);
        EditText bodyweightView = findViewById(R.id.options_accountBodyweight);

        if (TextUtils.isEmpty(nameView.getText())
                || TextUtils.isEmpty(ageView.getText())
                || TextUtils.isEmpty(bodysizeView.getText())
                || TextUtils.isEmpty(bodyweightView.getText())) {

            Toast.makeText(getApplicationContext(), "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show();
        } else {
            String accountName = nameView.getText().toString();
            String accountAge = ageView.getText().toString();
            String accountBodysize = bodysizeView.getText().toString();
            String accountBodyweight = bodyweightView.getText().toString();

            SharedPreferences sharedPreferences = getSharedPreferences("Account", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("accountName", accountName);
            editor.putString("accountAge", accountAge);
            editor.putString("accountBodysize", accountBodysize);
            editor.putString("accountBodyweight", accountBodyweight);
            editor.apply();

            Toast.makeText(getApplicationContext(), "Account aktualisiert", Toast.LENGTH_SHORT).show();

            setContentView(R.layout.activity_options);
        }
    }

    public void getCommunicationVals() {
        sharedPreferencesVals.getCommunicationPreferenceVals();

        Switch pushToTalk = findViewById(R.id.options_communication_switch);
        pushToTalk.setChecked(sharedPreferencesVals.pushToTalkVal);
    }

    public void updateCommunicationVals(View v) {
        Switch pushToTalk = findViewById(R.id.options_communication_switch);

        SharedPreferences sharedPreferences = getSharedPreferences("Communication", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("pushToTalkVal", pushToTalk.isChecked());
        editor.apply();

        Toast.makeText(getApplicationContext(), "Optionen aktualisiert", Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_options);
    }

    public void getEmergencyVals() {
        sharedPreferencesVals.getEmergencyPreferenceVals();

        Switch emergencyFallSwitch = findViewById(R.id.options_emergency_fall_switch);
        EditText emergencyCancelTimeView = findViewById(R.id.options_emergency_cancel_time);

        emergencyFallSwitch.setChecked(sharedPreferencesVals.emergencyFall);
        emergencyCancelTimeView.setText(sharedPreferencesVals.emergencyCancelTime);
    }

    public void updateEmergencyVals(View v) {
        Switch emergencyFallSwitch = findViewById(R.id.options_emergency_fall_switch);
        EditText emergencyCancelTimeView = findViewById(R.id.options_emergency_cancel_time);

        if (TextUtils.isEmpty(emergencyCancelTimeView.getText())) {
            Toast.makeText(getApplicationContext(), "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show();
        } else {
            String emergencyCancelTime = emergencyCancelTimeView.getText().toString();

            SharedPreferences sharedPreferences = getSharedPreferences("Emergency", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putBoolean("EmergencyFallVal",  emergencyFallSwitch.isChecked());
            editor.putString("EmergencyCancelTime", emergencyCancelTime);
            editor.apply();

            Toast.makeText(getApplicationContext(), "Optionen aktualisiert", Toast.LENGTH_SHORT).show();

            setContentView(R.layout.activity_options);
        }
    }

    public void getVitalVals() {
        sharedPreferencesVals.getVitalPreferenceVals();

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

        vitalsBPMSwitch.setChecked(sharedPreferencesVals.vitalsBPM);
        vitalsStressSwitch.setChecked(sharedPreferencesVals.vitalsStress);
        vitalsBodyTempSwitch.setChecked(sharedPreferencesVals.vitalsBodytemp);
        vitalsBreatheFreqSwitch.setChecked(sharedPreferencesVals.vitalsBreatheFreq);

        vitalsBPMMinView.setText(sharedPreferencesVals.vitalsBPMMinVal);
        vitalsStressMinView.setText(sharedPreferencesVals.vitalsStressMinVal);
        vitalsBodyTempMinView.setText(sharedPreferencesVals.vitalsBodyTempMinVal);
        vitalsBreatheFreqMinView.setText(sharedPreferencesVals.vitalsBreatheFreqMinVal);

        vitalsBPMMaxView.setText(sharedPreferencesVals.vitalsBPMMaxVal);
        vitalsStressMaxView.setText(sharedPreferencesVals.vitalsStressMaxVal);
        vitalsBodyTempMaxView.setText(sharedPreferencesVals.vitalsBodyTempMaxVal);
        vitalsBreatheFreqMaxView.setText(sharedPreferencesVals.vitalsBreatheFreqMaxVal);
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

        if (TextUtils.isEmpty(vitalsBPMMinVal) || TextUtils.isEmpty(vitalsStressMinVal) ||
                TextUtils.isEmpty(vitalsBodyTempMinVal) || TextUtils.isEmpty(vitalsBreatheFreqMinVal) ||
                TextUtils.isEmpty(vitalsBPMMaxVal) || TextUtils.isEmpty(vitalsStressMaxVal) ||
                TextUtils.isEmpty(vitalsBodyTempMaxVal) || TextUtils.isEmpty(vitalsBreatheFreqMaxVal)) {
            Toast.makeText(getApplicationContext(), "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show();
        } else {

            SharedPreferences sharedPreferences = getSharedPreferences("Vitals", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putBoolean("VitalsBPMVal", vitalsBPMSwitch.isChecked());
            editor.putBoolean("VitalsStressVal", vitalsStressSwitch.isChecked());
            editor.putBoolean("VitalsBodytempVal", vitalsBodyTempSwitch.isChecked());
            editor.putBoolean("VitalsBreatheFreq", vitalsBreatheFreqSwitch.isChecked());

            editor.putString("VitalsBPMMinVal", vitalsBPMMinVal);
            editor.putString("VitalsStressMinVal", vitalsStressMinVal);
            editor.putString("VitalsBodyTempMinVal", vitalsBodyTempMinVal);
            editor.putString("VitalsBreatheFreqMinVal", vitalsBreatheFreqMinVal);

            editor.putString("VitalsBPMMaxVal", vitalsBPMMaxVal);
            editor.putString("VitalsStressMaxVal", vitalsStressMaxVal);
            editor.putString("VitalsBodyTempMaxVal", vitalsBodyTempMaxVal);
            editor.putString("VitalsBreatheFreqMaxVal", vitalsBreatheFreqMaxVal);

            editor.apply();

            Toast.makeText(getApplicationContext(), "Optionen aktualisiert", Toast.LENGTH_SHORT).show();

            setContentView(R.layout.activity_options);
        }

        restartApp();

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