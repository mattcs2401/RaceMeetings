package com.mcssoft.racemeetings.utility;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

import com.mcssoft.racemeetings.R;

import java.util.Map;

public class Preferences {

    //<editor-fold defaultstate="collapsed" desc="Region: Access">
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
    //</editor-fold>

    /**
     * Get the id of the radio button selected for the network access preference.
     * @return The button id.
     */
    public int networkPrefButtonId() {
        return getDefaultSharedPreferences().getInt(Resources.getInstance()
                .getString(R.string.pref_network_access_button_id_key), R.integer.init_default);
    }

    public String networkPrefTag() {
        return getDefaultSharedPreferences().getString(Resources.getInstance()
                .getString(R.string.pref_network_access_tag_key), null);
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
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

    public void destroy() {
        context = null;
        instance = null;
    }
    //</editor-fold>

    private Context context;
    private static volatile Preferences instance = null;
}

