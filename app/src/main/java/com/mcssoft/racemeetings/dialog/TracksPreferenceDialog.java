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
import com.mcssoft.racemeetings.adapter.TracksPreferenceAdapter;
import com.mcssoft.racemeetings.database.SchemaConstants;
import com.mcssoft.racemeetings.interfaces.IItemClickListener;
import com.mcssoft.racemeetings.database.DatabaseOperations;

import java.util.ArrayList;

/**
 * Utility class to show a "preference" dialog for tracks.
 * Note: The preferences are in the TRACKS table of the database with an "is_pref" indicator column.
 *       This was done so more tracks could be added/removed as required.
 */
public class TracksPreferenceDialog extends DialogPreference
    implements View.OnClickListener, IItemClickListener {

    public TracksPreferenceDialog(Context context, AttributeSet attrs) {
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
            saveTracksPreferences();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        TextView track = (TextView) view.findViewById(R.id.id_tv_trackrow_track);
        TextView club = (TextView) view.findViewById(R.id.id_tv_trackrow_club);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.id_cb_trackrow);

        if(checkBox.isChecked()) {
            checkBox.setChecked(false);
        } else {
            checkBox.setChecked(true);
        }
        changeList.add(track.getText().toString());
    }

    private void initialise(View view) {
        changeList = new ArrayList<String>();

        DatabaseOperations dbUtil = new DatabaseOperations(this.getContext());
        cursor = dbUtil.getAllFromTable(SchemaConstants.TRACKS_TABLE);

        setTracksAdapter();
        setRecyclerView(view);
    }

    private void saveTracksPreferences() {
        // Process the change list, anything checked is unchecked and vice vesa.
        DatabaseOperations dbOper = new DatabaseOperations(this.getContext());
        cursor = dbOper.getSelectionFromTable(SchemaConstants.TRACKS_TABLE, null,
                SchemaConstants.TRACK_NAME + dbOper.createWhereIN(changeList.size()),
                getChangeListAsArray());

        int rowId;
        String isPref;

        int rowIdColNdx = cursor.getColumnIndex(SchemaConstants.TRACK_ROWID);
        int isPrefColNdx = cursor.getColumnIndex(SchemaConstants.TRACK_IS_PREF);

        while (cursor.moveToNext()) {
            rowId = cursor.getInt(rowIdColNdx);
            isPref = cursor.getString(isPrefColNdx);

            if(isPref.equals("Y")) { isPref = "N"; } else { isPref = "Y"; }

            dbOper.updateTableByRowId(SchemaConstants.TRACKS_TABLE,
                    SchemaConstants.WHERE_FOR_TRACK_UPDATE, rowId, SchemaConstants.TRACK_IS_PREF, isPref);
        }
    }

    private void setTracksAdapter() {
        tracksAdapter = new TracksPreferenceAdapter();
        tracksAdapter.setOnItemClickListener(this);
        tracksAdapter.swapCursor(cursor);
    }

    private void setRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.id_rv_track_pref);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(tracksAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.scrollToPosition(0);
        recyclerView.setLayoutManager(llm);
//        recyclerView.addItemDecoration(new ListingDivider(context, LinearLayoutManager.VERTICAL));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

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
    private TracksPreferenceAdapter tracksAdapter;
}
