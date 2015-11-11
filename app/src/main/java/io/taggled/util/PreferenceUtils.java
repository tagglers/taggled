package io.taggled.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by jhoon on 11/10/15.
 */
public class PreferenceUtils {
    private static final String PREF_EMAIL = "user_email";
    private static final String PREF_NAME = "user_name";

    public static String getUserEmail(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(PREF_EMAIL, "");
    }

    public static void setUserEmail(Context context, String email){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_EMAIL, email);
        editor.commit();
    }

    public static String getUserName(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(PREF_NAME, "");
    }

    public static void setUserName(Context context, String name){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_NAME, name);
        editor.commit();
    }
}
