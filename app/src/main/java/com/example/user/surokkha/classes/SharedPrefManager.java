package com.example.user.surokkha.classes;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;
    final String prefName = "MySharedPref";
    final String keyPhone = "phone";

    public SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getmInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userlogin(String phone) {
        SharedPreferences sp = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(keyPhone, phone);
        editor.apply();
        return true;
    }

    public boolean isloggedin() {
        SharedPreferences sp = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        if (sp.getString(keyPhone, null) != null) {
            return true;
        } else
            return false;
    }

    public boolean logout() {
        SharedPreferences sp = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getUser() {
        SharedPreferences sp = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sp.getString(keyPhone, null);
    }

}
