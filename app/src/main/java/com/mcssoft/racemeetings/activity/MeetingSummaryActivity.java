package com.mcssoft.racemeetings.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.fragment.MeetingSummaryFragment;
import com.mcssoft.racemeetings.utility.Resources;

public class MeetingSummaryActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_view_meeting_detail_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar_main);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Resources.getInstance().getString(R.string.appbar_title_meeting_details));
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Load the fragment.
        String fragment_tag = Resources.getInstance().getString(R.string.meeting_detail_fragment_tag);
        MeetingSummaryFragment meetingDetailFragment = new MeetingSummaryFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.content_main, meetingDetailFragment, fragment_tag)
                .addToBackStack(fragment_tag)
                .commit();
    }
}
