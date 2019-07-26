package com.example.ifasanat.UserSharedPrefManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.ifasanat.Activities.LoginActivity;

public class UserSharedPrefManager {
    private static final String USER_SHARED_PREF_NAME = "IfaSanatPrefManager";

    private static final String KEY_TOKEN = "token";
    private static final String KEY_NAME = "username";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_LOGIN = "IsLoggedIn";



    private SharedPreferences sharedPreferences;
    private Context context;

    public UserSharedPrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(USER_SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        if (token != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(KEY_TOKEN, token);
            edit.apply();
        }
    }
    public void saveName(String name) {
        if (name != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(KEY_NAME,name);
            edit.apply();
        }
    }
    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public String getName() {
        return sharedPreferences.getString(KEY_NAME,null);
    }


    // Get Login State
    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }


    public void logoutUser(){
        // Clearing all data from Shared Preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_TOKEN);

//        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }

    /**
     * Lunch First Time
     */
    public void setFirstTimeLaunch(boolean isFirstTime) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_FIRST_TIME_LAUNCH , isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


}
