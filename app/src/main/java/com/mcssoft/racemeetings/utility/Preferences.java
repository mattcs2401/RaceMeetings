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

    public String getMeetingsShowTodayKey() {
        return Resources.getInstance().getString(R.string.pref_meetings_show_today_key);
    }

    public boolean getMeetingsShowToday() {
        return getDefaultSharedPreferences().getBoolean(getMeetingsShowTodayKey(), false);
    }

    public String getCacheMeetingsKey() {
        return Resources.getInstance().getString(R.string.pref_meetings_cache_key);
    }

    public String getExcludeBarrierTrialKey() {
        return Resources.getInstance().getString(R.string.pref_meetings_exclude_BT_key);
    }

    public boolean getExcludeBarrierTrial() {
        return getDefaultSharedPreferences().getBoolean(getExcludeBarrierTrialKey(), false);
    }

    public boolean getCacheMeetings() {
        return getDefaultSharedPreferences().getBoolean(getCacheMeetingsKey(), false);
    }

    public SharedPreferences getDefaultSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void destroy() {
        context = null;
        instance = null;
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
    //</editor-fold>

    private Context context;
    private static volatile Preferences instance = null;
}

