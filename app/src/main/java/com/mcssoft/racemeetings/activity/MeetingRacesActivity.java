package com.mcssoft.racemeetings.activity;

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

    private Toolbar toolbar;
    private ActionBar actionBar;
}

