package com.downdemo.abhishekchandale.memorygamedemo.util;

import android.content.Context;
import android.content.SharedPreferences;

public final class Preferences {

    public static final String PLAYER_NAME = "player";
    public static final String IS_USER_LOGGEDIN = "isuserloggedin";
    public static final String PREF = "com.downdemo.abhishekchandale.memorygamedemo";
    public static final String FLAG = "flag";

    public static String getPlayerName(final Context context) {
        return Preferences.getPref(context, PLAYER_NAME, null);
    }

    public static void setPlayerName(final Context context, String endpoint) {
        Preferences.putPref(context, PLAYER_NAME, endpoint);
    }

    public static SharedPreferences get(final Context context) {
        return context.getSharedPreferences(PREF,
                Context.MODE_PRIVATE);
    }

    public static boolean getUserLoggedIn(final Context context) {
        return Preferences.getBooleanPref(context, IS_USER_LOGGEDIN, false);
    }

    public static void setUserLoggedIn(final Context context, boolean endpoint) {
        Preferences.putBooleanPref(context, IS_USER_LOGGEDIN, endpoint);
    }
    public static boolean getFlag(final Context context) {
        return Preferences.getBooleanPref(context, FLAG, false);
    }

    public static void setFlag(final Context context, boolean endpoint) {
        Preferences.putBooleanPref(context, FLAG, endpoint);
    }

    public static String getPref(final Context context, String pref,
                                 String def) {
        SharedPreferences prefs = Preferences.get(context);
        String val = prefs.getString(pref, def);

        if (val == null || val.equals("") || val.equals("null"))
            return def;
        else
            return val;
    }

    public static void putPref(final Context context, String pref,
                               String val) {
        SharedPreferences prefs = Preferences.get(context);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(pref, val);
        editor.commit();
    }

    public static long getLongPref(final Context context, String pref,
                                   long def) {
        SharedPreferences prefs = Preferences.get(context);
        long val = prefs.getLong(pref, def);
        if (val == 0)
            return def;
        else
            return val;
    }

    public static void putPrefLong(final Context context, String pref,
                                   long val) {
        SharedPreferences prefs = Preferences.get(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(pref, val);
        editor.commit();
    }

    public static boolean getBooleanPref(final Context context, String pref,
                                         boolean def) {
        return Preferences.get(context).getBoolean(pref, def);
    }


    public static void putBooleanPref(final Context context, String pref,
                                      boolean val) {
        SharedPreferences prefs = Preferences.get(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(pref, val);
        editor.commit();
    }

}

