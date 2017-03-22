package com.mcssoft.racemeetings.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.fragment.MeetingRacesDetailsFragment;
import com.mcssoft.racemeetings.fragment.MeetingRacesFragment;
import com.mcssoft.racemeetings.utility.Resources;

public class MeetingRacesDetailsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialiseBaseUI();
        saveArgument();
        loadFragment();
    }

    private void initialiseBaseUI() {
        setContentView(R.layout.content_view_race_details_activity);
        toolbar = (Toolbar) findViewById(R.id.id_toolbar_main);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle(Resources.getInstance().getString(R.string.appbar_title_race_details));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Save the "race id" argument to the shared preferences.
     */
    private void saveArgument() {
        // TBA - may not be required
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            String key = Resources.getInstance().getString(R.string.race_id_key);
            String argsPref = Resources.getInstance().getString(R.string.arguments_preference);
            String argument = bundle.getString(key);
            SharedPreferences sp = getSharedPreferences(argsPref, this.MODE_PRIVATE);
            sp.edit().putString(key, argument).apply();
        }
    }

    private void loadFragment() {
        String fragment_tag = Resources.getInstance().getString(R.string.meeting_races_details_fragment_tag);
        MeetingRacesDetailsFragment meetingRacesDetailsFragment = new MeetingRacesDetailsFragment();
        meetingRacesDetailsFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction()
                .replace(R.id.content_main, meetingRacesDetailsFragment, fragment_tag)
                .addToBackStack(fragment_tag)
                .commit();
    }

    private Toolbar toolbar;
    private ActionBar actionBar;
}
