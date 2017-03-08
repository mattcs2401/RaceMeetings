package com.mcssoft.racemeetings.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.fragment.MeetingRacesFragment;
import com.mcssoft.racemeetings.utility.Resources;

public class MeetingRacesActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_view_meeting_races_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar_main);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Resources.getInstance().getString(R.string.appbar_title_meeting_races));
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Load the fragment.
        String fragment_tag = Resources.getInstance().getString(R.string.meeting_races_fragment_tag);
        MeetingRacesFragment meetingRacesFragment = new MeetingRacesFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.content_main, meetingRacesFragment, fragment_tag)
                .addToBackStack(fragment_tag)
                .commit();
    }
}
