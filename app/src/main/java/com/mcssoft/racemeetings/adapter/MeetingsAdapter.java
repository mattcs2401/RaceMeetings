package com.mcssoft.racemeetings.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.database.SchemaConstants;
import com.mcssoft.racemeetings.interfaces.*;

/**
 * Utility class that lists the Meetings for a particular (user selected) date.
 */

public class MeetingsAdapter extends RecyclerView.Adapter<MeetingsViewHolder> {

    @Override
    public MeetingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.d(LOG_TAG, "onCreateViewHolder");
        // Note: don't need to keep a local copy of MeetingViewHolder, framework now supplies.
        if ( parent instanceof RecyclerView ) {
            if(!emptyView) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_row, parent, false);
                view.setFocusable(true);
                // don't need to keep a local copy, framework now supplies.
                return new MeetingsViewHolder(view, itemClickListener, itemLongClickListener);
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meetings_empty, parent, false);
                return new MeetingsViewHolder(view);
            }
        } else {
            throw new RuntimeException("Not bound to RecyclerView");
        }
    }

    @Override
    public void onBindViewHolder(MeetingsViewHolder holder, int position) {
//        Log.d(LOG_TAG, "onBindViewHolder");
        if(!emptyView) {
            adapaterOnBindViewHolder(holder, position);
        } else {
            holder.getEmptyText().getText().toString();
        }
    }

    @Override
    public int getItemCount() {
        if(emptyView) {
            return  1; // need to do this so the onCreateViewHolder fires.
        } else {
            if(cursor != null) {
                return cursor.getCount();
            } else {
                return 0;
            }
        }
    }

    @Override
    public long getItemId(int position) {
        cursor.moveToPosition(position);
        return cursor.getLong(idColNdx);
    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        cursor.moveToFirst();

        idColNdx = cursor.getColumnIndex(SchemaConstants.MEETING_ROWID);
        meetingId = cursor.getColumnIndex(SchemaConstants.MEETING_ID);

//        trackNameColNdx = cursor.getColumnIndex(SchemaConstants.TRACK_NAME);
//        trackClubColNdx = cursor.getColumnIndex(SchemaConstants.TRACK_CLUB_NAME);
//        trackIsPrefColNdx = cursor.getColumnIndex(SchemaConstants.TRACK_IS_PREF);

        notifyDataSetChanged();
    }

    public void setOnItemClickListener(IItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setOnItemLongClickListener(IItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }

    private void adapaterOnBindViewHolder(MeetingsViewHolder holder, int position) {

    }

    private View view;
    private Cursor cursor;
    private boolean emptyView;

    private int idColNdx;
    private int meetingId;

    private IItemClickListener itemClickListener;
    private IItemLongClickListener itemLongClickListener;
}
