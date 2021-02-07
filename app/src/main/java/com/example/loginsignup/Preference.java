package com.example.loginsignup;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preference {
    private Context context;
    private SharedPreferences preferences;
    private static final String PNAMEKey = "pnk";
    private static final String PCNICKey = "pck";
    private static final String PBLOODKey = "pbk";
    private static final String PCONTACTKey = "pcck";
    private static final String PGENDERKey = "pgk";
    private static final String HOSPITALNAMEKey = "hnk";
    private static final String ATTENDENTNAMEKey = "ank";
    private static final String ATTENDENTCONTACTKey = "ack";
    private static final String DONATENAME = "dn";
    private static final String DONATEFNAME = "dfn";
    private static final String DONATEADDRESS = "da";
    private static final String DONATECONTACT = "dc";
    private static final String DONATEAGE = "dda";
    private static final String USERTYPE = "userType";
    private static final String USERNAME = "userName";



    public Preference(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUserType(int b) {
        preferences.edit().putInt(USERTYPE,b).apply();
    }

    public Integer getUserType() {
        return preferences.getInt(USERTYPE,-1);
    }
    public void setUserName(String b) {
        preferences.edit().putString(USERNAME,b).apply();
    }

    public String getUserName() {
        return preferences.getString(USERNAME,"null");
    }


    public void setLogin(boolean b) {
        preferences.edit().putBoolean("login",b).apply();
    }

    public boolean getLogin() {
        return preferences.getBoolean("login",false);
    }
    public void setUser(boolean b) {
        preferences.edit().putBoolean("user",b).apply();
    }

    public boolean getUser() {
        return preferences.getBoolean("user",false);
    }

    ///////////////////////////

    public void setName(String b) {
        preferences.edit().putString("name",b).apply();
    }
    public void setPass(String b) {
        preferences.edit().putString("pass",b).apply();
    }

    public String getPass() {
        return preferences.getString("pass","null");
    }
    public String getName() {
        return preferences.getString("name","null");
    }


    ///////////////////////////////

    public void setPatientName(String Name) {
        preferences.edit().putString(PNAMEKey, Name).apply();
    }

    public String getPatientName() {
        return preferences.getString(PNAMEKey, "null");
    }
    public void setPatientCnic(String gender) {
        preferences.edit().putString(PCNICKey, gender).apply();
    }

    public String getPatientCNIC() {
        return preferences.getString(PCNICKey, "null");
    }
    public void setPatientContact(String gender) {
        preferences.edit().putString(PCONTACTKey, gender).apply();
    }

    public String getPatientContact() {
        return preferences.getString(PCONTACTKey, "null");
    }
    public void setPatientBlood(String gender) {
        preferences.edit().putString(PBLOODKey, gender).apply();
    }

    public String getPatientBlood() {
        return preferences.getString(PBLOODKey, "null");
    }
    public void setHospital(String gender) {
        preferences.edit().putString(HOSPITALNAMEKey, gender).apply();
    }

    public String getHospital() {
        return preferences.getString(HOSPITALNAMEKey, "null");
    }
    public void setAttendentName(String gender) {
        preferences.edit().putString(ATTENDENTNAMEKey, gender).apply();
    }

    public String getAttendentName() {
        return preferences.getString(ATTENDENTNAMEKey, "null");
    }

    public void setAttendentContact(String gender) {
        preferences.edit().putString(ATTENDENTCONTACTKey, gender).apply();
    }

    public String getAttendentContact() {
        return preferences.getString(ATTENDENTCONTACTKey, "null");
    }


    public void setGender(String gender) {
        preferences.edit().putString(PGENDERKey, gender).apply();
    }

    public String getGender() {
        return preferences.getString(PGENDERKey, "null");
    }

    ///////



    public void setDonateName(String gender) {
        preferences.edit().putString(DONATENAME, gender).apply();
    }

    public String getDonateName() {
        return preferences.getString(DONATENAME, "null");
    }

    public void setDonateAddress(String gender) {
        preferences.edit().putString(DONATEADDRESS, gender).apply();
    }

    public String getDonateAddress() {
        return preferences.getString(DONATEADDRESS, "null");
    }
    public void setDonateContact(String gender) {
        preferences.edit().putString(DONATECONTACT, gender).apply();
    }

    public String getDonateContact() {
        return preferences.getString(DONATECONTACT, "null");
    }

    public void setDonateAge(String gender) {
        preferences.edit().putString(DONATEAGE, gender).apply();
    }

    public String getDonateAge() {
        return preferences.getString(DONATEAGE, "null");
    }
    public void setDonatFName(String gender) {
        preferences.edit().putString(DONATEFNAME, gender).apply();
    }

    public String getDonateFName() {
        return preferences.getString(DONATENAME, "null");
    }


}
