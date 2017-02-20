package com.mcssoft.racemeetings.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.adapter.MeetingsAdapter;
import com.mcssoft.racemeetings.interfaces.IItemClickListener;
import com.mcssoft.racemeetings.utility.Preferences;
import com.mcssoft.racemeetings.utility.Resources;

public class TrackDialog extends DialogPreference
    implements View.OnClickListener {

    public TrackDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        checkPreference();
        setDialogLayoutResource(R.layout.dialog_pref_track2);
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

    private void initialise(View view) {
        setMeetingAdapter();
        setRecyclerView(view);
    }

    private void saveTrackPreference() {
    }

    private void checkPreference() {
    }

    private void setMeetingAdapter() {
        meetingsAdapter = new MeetingsAdapter();
//        meetingsAdapter.setOnItemClickListener(this);
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
        recyclerView.setAdapter(meetingsAdapter);
    }



    private String[] defaultTracks;
    private RecyclerView recyclerView;
    private MeetingsAdapter meetingsAdapter;
}
