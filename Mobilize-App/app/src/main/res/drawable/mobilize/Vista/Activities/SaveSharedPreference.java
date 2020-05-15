package com.app.mobilize.Vista.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    private static final String PREF_EMAIL= "email";

    private static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setEmail(Context ctx, String email)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_EMAIL, email);
        editor.apply();
    }

    public static String getEmail(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_EMAIL, "");
    }

    public static void clearEmail(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.apply();
    }
}
