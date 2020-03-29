package com.example.hopinnow.helperclasses;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    public static boolean readSetting(Context context, boolean default_value, String key){
        //"page_settings"
        SharedPreferences sharedPreferences = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        boolean result = sharedPreferences.getBoolean(key,default_value);
        return result;

    }
    public static void saveSetting(Context context, boolean new_value, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, new_value);
        editor.apply();
    }}
