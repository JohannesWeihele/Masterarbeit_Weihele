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
        pushToTalkVal = sharedPreferencesCommunication.getBoolean("pushToTalkVal", true);
    }

    public void getEmergencyPreferenceVals(){
        SharedPreferences sharedPreferencesEmergency = context.getSharedPreferences("Emergency", Context.MODE_PRIVATE);
        emergencyFall = sharedPreferencesEmergency.getBoolean("EmergencyFallVal", true);
        emergencyBPM = sharedPreferencesEmergency.getBoolean("EmergencyBPMVal", true);
        emergencyStress = sharedPreferencesEmergency.getBoolean("EmergencyStressVal", true);
        emergencyBodytemp = sharedPreferencesEmergency.getBoolean("EmergencyBodytempVal", true);
        emergencyBreatheFreq = sharedPreferencesEmergency.getBoolean("EmergencyBreatheVal", true);
        emergencyCancelTime = sharedPreferencesEmergency.getString("EmergencyCancelTime", "5");
    }

    public void getVitalPreferenceVals(){
        SharedPreferences sharedPreferencesVitals = context.getSharedPreferences("Vitals", Context.MODE_PRIVATE);
        vitalsBPM = sharedPreferencesVitals.getBoolean("VitalsBPMVal", true);
        vitalsStress = sharedPreferencesVitals.getBoolean("VitalsStressVal", true);
        vitalsBodytemp = sharedPreferencesVitals.getBoolean("VitalsBodytempVal", true);
        vitalsBreatheFreq = sharedPreferencesVitals.getBoolean("VitalsBreatheFreq", true);
        vitalsBPMMinVal = sharedPreferencesVitals.getString("VitalsBPMMinVal", "35");
        vitalsStressMinVal = sharedPreferencesVitals.getString("VitalsStressMinVal", "25");
        vitalsBodyTempMinVal = sharedPreferencesVitals.getString("VitalsBodyTempMinVal", "36");
        vitalsBreatheFreqMinVal = sharedPreferencesVitals.getString("VitalsBreatheFreqMinVal", "15");
        vitalsBPMMaxVal = sharedPreferencesVitals.getString("VitalsBPMMaxVal", "180");
        vitalsStressMaxVal = sharedPreferencesVitals.getString("VitalsStressMaxVal", "75");
        vitalsBodyTempMaxVal = sharedPreferencesVitals.getString("VitalsBodyTempMaxVal", "42");
        vitalsBreatheFreqMaxVal = sharedPreferencesVitals.getString("VitalsBreatheFreqMaxVal", "60");
    }

}
