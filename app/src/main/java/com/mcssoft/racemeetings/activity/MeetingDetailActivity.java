package com.mcssoft.racemeetings.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.fragment.MeetingDetailFragment;
import com.mcssoft.racemeetings.utility.Resources;

public class MeetingDetailActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_view_meeting_detail_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar_main);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Load the fragment.
        String fragment_tag = Resources.getInstance().getString(R.string.meeting_detail_fragment_tag);
        MeetingDetailFragment meetingDetailFragment = new MeetingDetailFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.content_main, meetingDetailFragment, fragment_tag)
                .addToBackStack(fragment_tag)
                .commit();
    }
}
