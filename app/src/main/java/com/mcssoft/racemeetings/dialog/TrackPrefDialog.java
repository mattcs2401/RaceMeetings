package com.mcssoft.racemeetings.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.preference.DialogPreference;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.adapter.MeetingsAdapter;
import com.mcssoft.racemeetings.adapter.TrackPrefAdapter;
import com.mcssoft.racemeetings.database.SchemaConstants;
import com.mcssoft.racemeetings.interfaces.IItemClickListener;
import com.mcssoft.racemeetings.utility.DatabaseUtility;

public class TrackPrefDialog extends DialogPreference
    implements View.OnClickListener, IItemClickListener {

    public TrackPrefDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        checkPreference();
        setDialogLayoutResource(R.layout.dialog_pref_track);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        initialise(view);
    }

    @Override
    public void onClick(View view) { }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == DialogInterface.BUTTON_POSITIVE) {
            saveTrackPreference();
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    private void initialise(View view) {
        getTracks();
        setMeetingAdapter();
        setRecyclerView(view);
    }

    private void saveTrackPreference() {
    }

    private void checkPreference() {
    }

    private void getTracks() {
        DatabaseUtility dbUtil = new DatabaseUtility(this.getContext());
        cursor = dbUtil.getAllFromTable(SchemaConstants.TRACKS_TABLE);
    }

    private void setMeetingAdapter() {
        trackPrefAdapter = new TrackPrefAdapter();
        trackPrefAdapter.setOnItemClickListener(this);
        trackPrefAdapter.swapCursor(cursor);
    }

    private void setRecyclerView(View view) {
        if(recyclerView == null) {
            // Will be null when app 1st starts.
            recyclerView = (RecyclerView) view.findViewById(R.id.id_rv_track_pref);
        }
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.scrollToPosition(0);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(trackPrefAdapter);
    }


    private Cursor cursor;
    //private String[] defaultTracks;
    private RecyclerView recyclerView;
    private TrackPrefAdapter trackPrefAdapter;
}
