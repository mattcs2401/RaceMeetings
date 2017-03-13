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
        databaseCheckOrUpdate();
        loadFragment();
    }

    private void databaseCheckOrUpdate() {
        int meetingRowId = getIntent().getExtras()
                .getInt(Resources.getInstance().getString(R.string.meetings_rowid_key));
        DatabaseOperations dbOper = new DatabaseOperations(this);
        Cursor cursor = dbOper.getSelectionFromTable(SchemaConstants.MEETINGS_TABLE, null,
                SchemaConstants.WHERE_FOR_GET_MEETING, new String[] {Integer.toString(meetingRowId)});
        cursor.moveToFirst();
        int meetingId = cursor.getInt(cursor.getColumnIndex(SchemaConstants.MEETING_ID));

        DownloadHelper downloadHelper = new DownloadHelper(this);
        downloadHelper.getRacesForMeeting(Integer.toString(meetingId));

        getIntent().getExtras().clear();
        getIntent().getExtras()
                .putInt(Resources.getInstance().getString(R.string.meetings_rowid_key), meetingId);
    }

    private void loadFragment() {
        String fragment_tag = Resources.getInstance().getString(R.string.meeting_races_fragment_tag);
        MeetingRacesFragment meetingRacesFragment = new MeetingRacesFragment();
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
// intent.putExtra(Resources.getInstance().getString(R.string.meetings_rowid_key), getDbRowId(position));
