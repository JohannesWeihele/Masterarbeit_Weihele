package com.example.masterarbeit_weihele.Classes.Basics;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesVals {

    private Context context;

    //Variables - Account
    private String accountName = "";
    private String accountAge = "";
    private String accountBodysize = "";
    private String accountBodyweight = "";

    //Variables - Communication
    private Boolean pushToTalkVal = false;

    //Variables - Emergency
    private Boolean emergencyFall = false;
    private String emergencyCancelTime = "";

    //Variables - Vitals
    private Boolean vitalsBPM = false;
    private Boolean vitalsStress = false;
    private Boolean vitalsBodytemp = false;
    private Boolean vitalsBreatheFreq = false;
    private String vitalsBPMMinVal = "";
    private String vitalsStressMinVal = "";
    private String vitalsBodyTempMinVal = "";
    private String vitalsBreatheFreqMinVal = "";
    private String vitalsBPMMaxVal = "";
    private String vitalsStressMaxVal = "";
    private String vitalsBodyTempMaxVal = "";
    private String vitalsBreatheFreqMaxVal = "";

    public SharedPreferencesVals(Context context) {
        this.context = context;
    }

    public void fetchAccountPreferenceVals(){
        SharedPreferences sharedPreferencesAccount = context.getSharedPreferences("Account", Context.MODE_PRIVATE);
        setAccountName(sharedPreferencesAccount.getString("accountName", "Johannes"));
        setAccountAge(sharedPreferencesAccount.getString("accountAge", "28"));
        setAccountBodysize(sharedPreferencesAccount.getString("accountBodysize", "178"));
        setAccountBodyweight(sharedPreferencesAccount.getString("accountBodyweight", "87"));
    }

    public void fetchCommunicationPreferenceVals(){
        SharedPreferences sharedPreferencesCommunication = context.getSharedPreferences("Communication", Context.MODE_PRIVATE);
        setPushToTalkVal(sharedPreferencesCommunication.getBoolean("pushToTalkVal", true));
    }

    public void fetchEmergencyPreferenceVals(){
        SharedPreferences sharedPreferencesEmergency = context.getSharedPreferences("Emergency", Context.MODE_PRIVATE);
        setEmergencyFall(sharedPreferencesEmergency.getBoolean("EmergencyFallVal", true));
        setEmergencyCancelTime(sharedPreferencesEmergency.getString("EmergencyCancelTime", "5"));
    }

    public void fetchVitalPreferenceVals(){
        SharedPreferences sharedPreferencesVitals = context.getSharedPreferences("Vitals", Context.MODE_PRIVATE);
        setVitalsBPM(sharedPreferencesVitals.getBoolean("VitalsBPMVal", true));
        setVitalsStress(sharedPreferencesVitals.getBoolean("VitalsStressVal", true));
        setVitalsBodytemp(sharedPreferencesVitals.getBoolean("VitalsBodytempVal", true));
        setVitalsBreatheFreq(sharedPreferencesVitals.getBoolean("VitalsBreatheFreq", true));
        setVitalsBPMMinVal(sharedPreferencesVitals.getString("VitalsBPMMinVal", "35"));
        setVitalsStressMinVal(sharedPreferencesVitals.getString("VitalsStressMinVal", "25"));
        setVitalsBodyTempMinVal(sharedPreferencesVitals.getString("VitalsBodyTempMinVal", "36"));
        setVitalsBreatheFreqMinVal(sharedPreferencesVitals.getString("VitalsBreatheFreqMinVal", "15"));
        setVitalsBPMMaxVal(sharedPreferencesVitals.getString("VitalsBPMMaxVal", "180"));
        setVitalsStressMaxVal(sharedPreferencesVitals.getString("VitalsStressMaxVal", "75"));
        setVitalsBodyTempMaxVal(sharedPreferencesVitals.getString("VitalsBodyTempMaxVal", "42"));
        setVitalsBreatheFreqMaxVal(sharedPreferencesVitals.getString("VitalsBreatheFreqMaxVal", "60"));
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountAge() {
        return accountAge;
    }

    public void setAccountAge(String accountAge) {
        this.accountAge = accountAge;
    }

    public String getAccountBodysize() {
        return accountBodysize;
    }

    public void setAccountBodysize(String accountBodysize) {
        this.accountBodysize = accountBodysize;
    }

    public String getAccountBodyweight() {
        return accountBodyweight;
    }

    public void setAccountBodyweight(String accountBodyweight) {
        this.accountBodyweight = accountBodyweight;
    }

    public Boolean getPushToTalkVal() {
        return pushToTalkVal;
    }

    public void setPushToTalkVal(Boolean pushToTalkVal) {
        this.pushToTalkVal = pushToTalkVal;
    }

    public Boolean getEmergencyFall() {
        return emergencyFall;
    }

    public void setEmergencyFall(Boolean emergencyFall) {
        this.emergencyFall = emergencyFall;
    }

    public String getEmergencyCancelTime() {
        return emergencyCancelTime;
    }

    public void setEmergencyCancelTime(String emergencyCancelTime) {
        this.emergencyCancelTime = emergencyCancelTime;
    }

    public Boolean getVitalsBPM() {
        return vitalsBPM;
    }

    public void setVitalsBPM(Boolean vitalsBPM) {
        this.vitalsBPM = vitalsBPM;
    }

    public Boolean getVitalsStress() {
        return vitalsStress;
    }

    public void setVitalsStress(Boolean vitalsStress) {
        this.vitalsStress = vitalsStress;
    }

    public Boolean getVitalsBodytemp() {
        return vitalsBodytemp;
    }

    public void setVitalsBodytemp(Boolean vitalsBodytemp) {
        this.vitalsBodytemp = vitalsBodytemp;
    }

    public Boolean getVitalsBreatheFreq() {
        return vitalsBreatheFreq;
    }

    public void setVitalsBreatheFreq(Boolean vitalsBreatheFreq) {
        this.vitalsBreatheFreq = vitalsBreatheFreq;
    }

    public String getVitalsBPMMinVal() {
        return vitalsBPMMinVal;
    }

    public void setVitalsBPMMinVal(String vitalsBPMMinVal) {
        this.vitalsBPMMinVal = vitalsBPMMinVal;
    }

    public String getVitalsStressMinVal() {
        return vitalsStressMinVal;
    }

    public void setVitalsStressMinVal(String vitalsStressMinVal) {
        this.vitalsStressMinVal = vitalsStressMinVal;
    }

    public String getVitalsBodyTempMinVal() {
        return vitalsBodyTempMinVal;
    }

    public void setVitalsBodyTempMinVal(String vitalsBodyTempMinVal) {
        this.vitalsBodyTempMinVal = vitalsBodyTempMinVal;
    }

    public String getVitalsBreatheFreqMinVal() {
        return vitalsBreatheFreqMinVal;
    }

    public void setVitalsBreatheFreqMinVal(String vitalsBreatheFreqMinVal) {
        this.vitalsBreatheFreqMinVal = vitalsBreatheFreqMinVal;
    }

    public String getVitalsBPMMaxVal() {
        return vitalsBPMMaxVal;
    }

    public void setVitalsBPMMaxVal(String vitalsBPMMaxVal) {
        this.vitalsBPMMaxVal = vitalsBPMMaxVal;
    }

    public String getVitalsStressMaxVal() {
        return vitalsStressMaxVal;
    }

    public void setVitalsStressMaxVal(String vitalsStressMaxVal) {
        this.vitalsStressMaxVal = vitalsStressMaxVal;
    }

    public String getVitalsBodyTempMaxVal() {
        return vitalsBodyTempMaxVal;
    }

    public void setVitalsBodyTempMaxVal(String vitalsBodyTempMaxVal) {
        this.vitalsBodyTempMaxVal = vitalsBodyTempMaxVal;
    }

    public String getVitalsBreatheFreqMaxVal() {
        return vitalsBreatheFreqMaxVal;
    }

    public void setVitalsBreatheFreqMaxVal(String vitalsBreatheFreqMaxVal) {
        this.vitalsBreatheFreqMaxVal = vitalsBreatheFreqMaxVal;
    }


}
