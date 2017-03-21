package com.mcssoft.racemeetings.activity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.database.DatabaseOperations;
import com.mcssoft.racemeetings.database.SchemaConstants;
import com.mcssoft.racemeetings.fragment.MeetingRacesFragment;
import com.mcssoft.racemeetings.utility.DownloadHelper;
import com.mcssoft.racemeetings.utility.Resources;

public class MeetingRacesActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialiseBaseUI();
        saveArgument();
        loadFragment();
    }

    private void loadFragment() {
        String fragment_tag = Resources.getInstance().getString(R.string.meeting_races_fragment_tag);
        MeetingRacesFragment meetingRacesFragment = new MeetingRacesFragment();
        meetingRacesFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction()
                .replace(R.id.content_main, meetingRacesFragment, fragment_tag)
                .addToBackStack(fragment_tag)
                .commit();
    }

    private void initialiseBaseUI() {
        setContentView(R.layout.content_view_meeting_races_activity);
        toolbar = (Toolbar) findViewById(R.id.id_toolbar_main);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle(Resources.getInstance().getString(R.string.appbar_title_meeting_races));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Save the "meeting id" argument to the shared preferences. Resolves a back navigation issue
     * from the race details activity to this.
     */
    private void saveArgument() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            String key = Resources.getInstance().getString(R.string.meeting_id_key);
            String argsPref = Resources.getInstance().getString(R.string.arguments_preference);
            String argument = bundle.getString(key);
            SharedPreferences sp = getSharedPreferences(argsPref, this.MODE_PRIVATE);
            sp.edit().putString(key, argument).apply();
        }
    }

    private Toolbar toolbar;
    private ActionBar actionBar;
}

