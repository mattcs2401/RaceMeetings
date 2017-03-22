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
 * Utility class that lists the Meetings for a particular (user selected) date.
 */

public class MeetingRacesDetailsAdapter extends RecyclerView.Adapter<MeetingRacesDetailsViewHolder> {

    @Override
    public MeetingRacesDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if ( parent instanceof RecyclerView ) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_race_details_row, parent, false);
            view.setFocusable(true);
            // don't need to keep a local copy, framework now supplies.
            return new MeetingRacesDetailsViewHolder(view);
        } else {
            throw new RuntimeException("Not bound to RecyclerView");
        }
    }

    @Override
    public void onBindViewHolder(MeetingRacesDetailsViewHolder holder, int position) {
        adapaterOnBindViewHolder(holder, position);
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

//        idColNdx = cursor.getColumnIndex(SchemaConstants.RACE_ROWID);
//        raceIdNdx = cursor.getColumnIndex(SchemaConstants.RACE_ID);
//        raceNoNdx = cursor.getColumnIndex(SchemaConstants.RACE_NO);
//        raceNameNdx = cursor.getColumnIndex(SchemaConstants.RACE_NAME);
//        raceTimeNdx = cursor.getColumnIndex(SchemaConstants.RACE_TIME);
//        raceClassNdx = cursor.getColumnIndex(SchemaConstants.RACE_CLASS);
//        raceDistNdx = cursor.getColumnIndex(SchemaConstants.RACE_DISTANCE);
////        raceRatingNdx = cursor.getColumnIndex(SchemaConstants.RACE_TRACK_RATING);
//        racePrizeNdx = cursor.getColumnIndex(SchemaConstants.RACE_PRIZE_TOTAL);

        notifyDataSetChanged();
    }

//    public void setOnItemClickListener(IItemClickListener listener) {
//        this.itemClickListener = listener;
//    }

//    public void setOnItemLongClickListener(IItemLongClickListener listener) {
//        this.itemLongClickListener = listener;
//    }

    public Cursor getCursor() { return cursor; }

    public void setEmptyView(boolean emptyView) {
        this.emptyView = emptyView;
    }

    private void adapaterOnBindViewHolder(MeetingRacesDetailsViewHolder holder, int position) {
        cursor.moveToPosition(position);

//        holder.getRaceId().setText(cursor.getString(raceIdNdx));
//        holder.getRaceNo().setText(cursor.getString(raceNoNdx));
//        holder.getRaceName().setText(cursor.getString(raceNameNdx));
//        holder.getRaceTime().setText(cursor.getString(raceTimeNdx));
//        holder.getRaceClass().setText(cursor.getString(raceClassNdx));
//        holder.getRaceDistance().setText((cursor.getString(raceDistNdx).split(" "))[0]);
//        holder.getRaceRating().setText(cursor.getString(raceRatingNdx));
//        holder.getRacePrize().setText(cursor.getString(racePrizeNdx));
    }


    private View view;
    private Cursor cursor;
    private boolean emptyView;

    private int idColNdx;
    private int raceIdNdx;
    private int raceNoNdx;
    private int raceNameNdx;
    private int raceTimeNdx;
    private int raceClassNdx;
    private int raceDistNdx;
    private int raceRatingNdx;
    private int racePrizeNdx;

    private IItemClickListener itemClickListener;
//    private IItemLongClickListener itemLongClickListener;
}
/*
Horses>
 <Horse Id="310788">
   <HorseName>All Girls Rock</HorseName>
   <Weight>56.0</Weight>
   <Jockey Id="540">
     <FullName>Bonnie Thomson</FullName>
   </Jockey>
   <Trainer Id="711">
     <FullName>Milton Baker</FullName>
   </Trainer>
 </Horse>
*/