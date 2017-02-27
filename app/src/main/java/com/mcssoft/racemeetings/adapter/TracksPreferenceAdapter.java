package com.mcssoft.racemeetings.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.database.SchemaConstants;
import com.mcssoft.racemeetings.interfaces.IItemClickListener;

/**
 * Adapter for the tracks preferences.
 */
public class TracksPreferenceAdapter extends RecyclerView.Adapter<TracksPreferenceViewHolder> {

    @Override
    public TracksPreferenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Note: don't need to keep a local copy of MeetingViewHolder, framework now supplies.
        if ( parent instanceof RecyclerView ) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_pref_track_row, parent, false);
            view.setFocusable(true);
            return new TracksPreferenceViewHolder(view, itemClickListener);
        } else {
            throw new RuntimeException("Not bound to RecyclerView");
        }
    }

    @Override
    public void onBindViewHolder(TracksPreferenceViewHolder holder, int position) {
        cursor.moveToPosition(position);

        String trackName = cursor.getString(trackNameColNdx);
        holder.getTrackName().setText(trackName);
        String clubName = cursor.getString(trackClubColNdx);
        holder.getClubName().setText(clubName);
        String checked = cursor.getString(trackIsPrefColNdx);

        if(checked.equals("N")) {
            holder.getTrackNameCb().setChecked(false);
        } else if (checked.equals("Y")) {
            holder.getTrackNameCb().setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    @Override
    public long getItemId(int position) {
        cursor.moveToPosition(position);
        return cursor.getLong(idColNdx);
    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        cursor.moveToFirst();

        idColNdx = cursor.getColumnIndex(SchemaConstants.TRACK_ROWID);
        trackNameColNdx = cursor.getColumnIndex(SchemaConstants.TRACK_NAME);
        trackClubColNdx = cursor.getColumnIndex(SchemaConstants.TRACK_CLUB_NAME);
        trackIsPrefColNdx = cursor.getColumnIndex(SchemaConstants.TRACK_IS_PREF);

        notifyDataSetChanged();
    }

    public void setOnItemClickListener(IItemClickListener listener) {
        this.itemClickListener = listener;
    }

    private View view;
    private Cursor cursor;

    private int idColNdx;
    private int trackNameColNdx;
    private int trackClubColNdx;
    private int trackIsPrefColNdx;

    private IItemClickListener itemClickListener;
}
