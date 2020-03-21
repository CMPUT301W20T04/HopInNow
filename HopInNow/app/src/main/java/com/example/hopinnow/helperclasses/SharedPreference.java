package com.example.hopinnow.helperclasses;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    public static boolean readSetting(Context context, boolean default_value){
        SharedPreferences sharedPreferences = context.getSharedPreferences("page_settings",Context.MODE_PRIVATE);
        boolean result = sharedPreferences.getBoolean("page_settings",default_value);
        return result;

    }
    public static void saveSetting(Context context, boolean new_value){
        SharedPreferences sharedPreferences = context.getSharedPreferences("page_settings",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("page_settings", new_value);
        editor.apply();
    }}
