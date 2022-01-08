package com.dumv.appdoctruyentranh.util;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class StoreUtil {
    public static SharedPreferences sharedPreferences;

    public static void save(Context context, String key, String values) {
        sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString(key, values);
        myEdit.commit();
    }

    public static String get(Context context, String key) {
        sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

}
