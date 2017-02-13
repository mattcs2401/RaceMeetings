package com.mcssoft.racemeetings.utility;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

import com.mcssoft.racemeetings.R;

import java.util.Map;

public class Preferences {

    private Preferences(Context context) {
        this.context = context;
        getPreferences();
    }

    public static synchronized Preferences getInstance(Context context) {
        if(!instanceExists()) {
            instance = new Preferences(context);
        }
        return instance;
    }

    public static synchronized Preferences getInstance() {
        return instance;
    }

    public static boolean instanceExists() {
        return instance != null ? true : false;
    }


    private Bundle getPreferences() {

        Map<String,?> prefsMap = getDefaultSharedPreferences().getAll();

        if(prefsMap.isEmpty()) {
            // No SharedPreferences set yet. App has probably been uninstalled then re-installed
            // and/or cache and data cleared. Set the app preferences defaults.
            PreferenceManager.setDefaultValues(context, R.xml.preferences, false);
            prefsMap = getDefaultSharedPreferences().getAll();
        }

        Bundle prefsState = new Bundle();

        for (String key : prefsMap.keySet()) {
            Object obj = prefsMap.get(key);
            prefsState.putString(key, obj.toString());
        }

        return prefsState;
    }

    public SharedPreferences getDefaultSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private Context context;
    private static volatile Preferences instance = null;
}

