package com.mcssoft.racemeetings.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.fragment.RaceDetailsFragment;
import com.mcssoft.racemeetings.utility.Resources;

public class RaceDetailsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_view_race_details_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar_main);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Resources.getInstance().getString(R.string.appbar_title_race_details));
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Load the fragment.
        String fragment_tag = Resources.getInstance().getString(R.string.race_details_fragment_tag);
        RaceDetailsFragment raceDetailsFragment = new RaceDetailsFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.content_main, raceDetailsFragment, fragment_tag)
                .addToBackStack(fragment_tag)
                .commit();
    }
}
