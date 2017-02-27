package com.mcssoft.racemeetings.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.preference.DialogPreference;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.adapter.MeetingsAdapter;
import com.mcssoft.racemeetings.adapter.TrackPrefAdapter;
import com.mcssoft.racemeetings.database.SchemaConstants;
import com.mcssoft.racemeetings.interfaces.IItemClickListener;
import com.mcssoft.racemeetings.utility.DatabaseUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to show a "preference" dialog for tracks.
 * Note: The preferences are in the TRACKS table of the database with an "is_pref" indicator column.
 *       This was done so more tracks could be added/removed as required.
 */
public class TrackPrefDialog extends DialogPreference
    implements View.OnClickListener, IItemClickListener {

    public TrackPrefDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
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
        TextView textView = (TextView) view.findViewById(R.id.id_tv_track_row);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.id_cb_track_row);

        if(checkBox.isChecked()) {
            checkBox.setChecked(false);
        } else {
            checkBox.setChecked(true);
        }
        changeList.add(textView.getText().toString());
    }

    private void initialise(View view) {
        changeList = new ArrayList<String>();
        getTracks();
        setMeetingAdapter();
        setRecyclerView(view);
    }

    private void saveTrackPreference() {
        // Process the change list, anything checked is unchecked and vice vesa.
        DatabaseUtility dbUtil = new DatabaseUtility(this.getContext());
        cursor = dbUtil.getSelectionFromTable(SchemaConstants.TRACKS_TABLE,
                SchemaConstants.TRACK_NAME + dbUtil.createWhereIN(changeList.size()),
                getChangeListAsArray());

        int rowId;
        String isPref;

        int rowIdColNdx = cursor.getColumnIndex(SchemaConstants.TRACK_ROWID);
        int isPrefColNdx = cursor.getColumnIndex(SchemaConstants.TRACK_IS_PREF);

        while (cursor.moveToNext()) {
            rowId = cursor.getInt(rowIdColNdx);
            isPref = cursor.getString(isPrefColNdx);

            if(isPref.equals("Y")) { isPref = "N"; } else { isPref = "Y"; }

            dbUtil.updateTableByRowId(SchemaConstants.TRACKS_TABLE,
                    SchemaConstants.WHERE_FOR_TRACK_UPDATE, rowId, SchemaConstants.TRACK_IS_PREF, isPref);
        }
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
        recyclerView = (RecyclerView) view.findViewById(R.id.id_rv_track_pref);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(trackPrefAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.scrollToPosition(0);
        recyclerView.setLayoutManager(llm);
    }

    private String[] getChangeListAsArray() {
        int size = changeList.size();
        String[] changes = new String[size];
        for(int ndx=0; ndx < size; ndx++) {
            changes[ndx] = changeList.get(ndx);
        }
        return changes;
    }

    private Cursor cursor;
    private RecyclerView recyclerView;
    private ArrayList<String> changeList;
    private TrackPrefAdapter trackPrefAdapter;
}
