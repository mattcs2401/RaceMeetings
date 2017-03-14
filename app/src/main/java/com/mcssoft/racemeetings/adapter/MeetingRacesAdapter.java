package com.mcssoft.racemeetings.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.database.SchemaConstants;
import com.mcssoft.racemeetings.interfaces.IItemClickListener;
import com.mcssoft.racemeetings.interfaces.IItemLongClickListener;

/**
 * Utility class that lists the Meetings for a particular (user selected) date.
 */

public class MeetingRacesAdapter extends RecyclerView.Adapter<MeetingRacesViewHolder> {

    @Override
    public MeetingRacesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if ( parent instanceof RecyclerView ) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_race_row, parent, false);
            view.setFocusable(true);
            // don't need to keep a local copy, framework now supplies.
            return new MeetingRacesViewHolder(view, itemClickListener, itemLongClickListener);
        } else {
            throw new RuntimeException("Not bound to RecyclerView");
        }
    }

    @Override
    public void onBindViewHolder(MeetingRacesViewHolder holder, int position) {
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

        idColNdx = cursor.getColumnIndex(SchemaConstants.RACE_ROWID);
        raceIdNdx = cursor.getColumnIndex(SchemaConstants.RACE_ID);
        raceNoNdx = cursor.getColumnIndex(SchemaConstants.RACE_NO);
// /        meetingDateNdx = cursor.getColumnIndex(SchemaConstants.MEETING_DATE);
//        trackNameNdx = cursor.getColumnIndex(SchemaConstants.MEETING_TRACK);
//        clubNameNdx = cursor.getColumnIndex(SchemaConstants.MEETING_CLUB);
//        racingStatusNdx = cursor.getColumnIndex(SchemaConstants.MEETING_STATUS);
//        numRacesNdx = cursor.getColumnIndex(SchemaConstants.MEETING_NO_RACES);
//        bariierTrialNdx = cursor.getColumnIndex(SchemaConstants.MEETING_IS_TRIAL);

        notifyDataSetChanged();
    }

    public void setOnItemClickListener(IItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setOnItemLongClickListener(IItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }

    public Cursor getCursor() { return cursor; }

    public void setEmptyView(boolean emptyView) {
        this.emptyView = emptyView;
    }

    private void adapaterOnBindViewHolder(MeetingRacesViewHolder holder, int position) {
        cursor.moveToPosition(position);

//        holder.getMeetingId().setText(cursor.getString(meetingIdNdx));
//        holder.getTvMeetingDate().setText(cursor.getString(meetingDateNdx));
//        String[] strArr = cursor.getString(meetingDateNdx).split("-");

//        holder.getTrackName().setText(cursor.getString(trackNameNdx));
//        holder.getClubName().setText(cursor.getString(clubNameNdx));
//        holder.getRacingStatus().setText(cursor.getString(racingStatusNdx));
//        holder.getNumRaces().setText(cursor.getString(numRacesNdx));
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
    private IItemLongClickListener itemLongClickListener;
}
/*
  <Race Id="439963">
    <RaceNumber>1</RaceNumber>
    <RaceName>APEX - BUILDING BETTER COMMUNITIES C,G&E Class 1 Handicap</RaceName>
    <RaceTime>12:52PM</RaceTime>
    <Class>Class 1</Class>
    <Distance>1400 metres</Distance>
    <TrackRating>Good</TrackRating>
    <PrizeTotal>$14,000.00</PrizeTotal>
    <AgeCondition>No age restriction</AgeCondition>
    <SexCondition>Colts, Geldings and Entires</SexCondition>
    <WeightCondition>Handicap</WeightCondition>
    <ApprenticeClaim>true</ApprenticeClaim>
    <StartersFee>$173.00</StartersFee>
    <AcceptanceFee>$0.00</AcceptanceFee>
  </Race>
*/