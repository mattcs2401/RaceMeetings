package com.mcssoft.racemeetings.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.fragment.RaceDetailFragment;
import com.mcssoft.racemeetings.utility.Resources;


public class RaceDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_view_race_detail_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar_main);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Resources.getInstance().getString(R.string.appbar_title_meeting_details));
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Load the fragment.
        String fragment_tag = Resources.getInstance().getString(R.string.race_detail_fragment_tag);
        RaceDetailFragment raceDetailFragment = new RaceDetailFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.content_main, raceDetailFragment, fragment_tag)
                .addToBackStack(fragment_tag)
                .commit();
    }

}
