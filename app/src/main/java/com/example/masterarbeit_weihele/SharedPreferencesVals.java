package com.example.masterarbeit_weihele;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesVals {

    Context context;
    String accountName = "";
    String accountAge = "";
    String accountBodysize = "";
    String accountBodyweight = "";
    Boolean pushToTalkVal = false;
    Boolean emergencyFall = false;
    Boolean emergencyBPM = false;
    Boolean emergencyStress = false;
    Boolean emergencyBodytemp = false;
    Boolean emergencyBreatheFreq = false;
    String emergencyCancelTime = "";
    Boolean vitalsBPM = false;
    Boolean vitalsStress = false;
    Boolean vitalsBodytemp = false;
    Boolean vitalsBreatheFreq = false;
    String vitalsBPMMinVal = "";
    String vitalsStressMinVal = "";
    String vitalsBodyTempMinVal = "";
    String vitalsBreatheFreqMinVal = "";
    String vitalsBPMMaxVal = "";
    String vitalsStressMaxVal = "";
    String vitalsBodyTempMaxVal = "";
    String vitalsBreatheFreqMaxVal = "";

    public SharedPreferencesVals(Context context) {
        this.context = context;
    }

    public void getAccountPreferenceVals(){
        SharedPreferences sharedPreferencesAccount = context.getSharedPreferences("Account", Context.MODE_PRIVATE);
        accountName = sharedPreferencesAccount.getString("accountName", "");
        accountAge = sharedPreferencesAccount.getString("accountAge", "");
        accountBodysize = sharedPreferencesAccount.getString("accountBodysize", "");
        accountBodyweight = sharedPreferencesAccount.getString("accountBodyweight", "");
    }

    public void getCommunicationPreferenceVals(){
        SharedPreferences sharedPreferencesCommunication = context.getSharedPreferences("Communication", Context.MODE_PRIVATE);
        pushToTalkVal = sharedPreferencesCommunication.getBoolean("pushToTalkVal", false);
    }

    public void getEmergencyPreferenceVals(){
        SharedPreferences sharedPreferencesEmergency = context.getSharedPreferences("Emergency", Context.MODE_PRIVATE);
        emergencyFall = sharedPreferencesEmergency.getBoolean("EmergencyFallVal", false);
        emergencyBPM = sharedPreferencesEmergency.getBoolean("EmergencyBPMVal", false);
        emergencyStress = sharedPreferencesEmergency.getBoolean("EmergencyStressVal", false);
        emergencyBodytemp = sharedPreferencesEmergency.getBoolean("EmergencyBodytempVal", false);
        emergencyBreatheFreq = sharedPreferencesEmergency.getBoolean("EmergencyBreatheVal", false);
        emergencyCancelTime = sharedPreferencesEmergency.getString("EmergencyCancelTime", "");
    }

    public void getVitalPreferenceVals(){
        SharedPreferences sharedPreferencesVitals = context.getSharedPreferences("Vitals", Context.MODE_PRIVATE);
        vitalsBPM = sharedPreferencesVitals.getBoolean("VitalsBPMVal", false);
        vitalsStress = sharedPreferencesVitals.getBoolean("VitalsStressVal", false);
        vitalsBodytemp = sharedPreferencesVitals.getBoolean("VitalsBodytempVal", false);
        vitalsBreatheFreq = sharedPreferencesVitals.getBoolean("VitalsBreatheFreq", false);
        vitalsBPMMinVal = sharedPreferencesVitals.getString("VitalsBPMMinVal", "");
        vitalsStressMinVal = sharedPreferencesVitals.getString("VitalsStressMinVal", "");
        vitalsBodyTempMinVal = sharedPreferencesVitals.getString("VitalsBodyTempMinVal", "");
        vitalsBreatheFreqMinVal = sharedPreferencesVitals.getString("VitalsBreatheFreqMinVal", "");
        vitalsBPMMaxVal = sharedPreferencesVitals.getString("VitalsBPMMaxVal", "");
        vitalsStressMaxVal = sharedPreferencesVitals.getString("VitalsStressMaxVal", "");
        vitalsBodyTempMaxVal = sharedPreferencesVitals.getString("VitalsBodyTempMaxVal", "");
        vitalsBreatheFreqMaxVal = sharedPreferencesVitals.getString("VitalsBreatheFreqMaxVal", "");
    }

}
