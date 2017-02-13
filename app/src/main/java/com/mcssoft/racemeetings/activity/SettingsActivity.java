package com.mcssoft.racemeetings.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.fragment.SettingsFragment;


public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.settings_main);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
