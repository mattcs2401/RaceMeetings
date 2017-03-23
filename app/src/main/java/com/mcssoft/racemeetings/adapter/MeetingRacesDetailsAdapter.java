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
 * Utility class that lists the Horse/Jockey/Trainer for a race.
 */
public class MeetingRacesDetailsAdapter extends RecyclerView.Adapter<MeetingRacesDetailsViewHolder> {

    @Override
    public MeetingRacesDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if ( parent instanceof RecyclerView ) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_race_details_row, parent, false);
            view.setFocusable(true);
            // don't need to keep a local copy, framework now supplies.
            return new MeetingRacesDetailsViewHolder(view, itemClickListener);
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

        idColNdx = cursor.getColumnIndex(SchemaConstants.RACE_DETAILS_ROWID);
        horseNameNdx = cursor.getColumnIndex(SchemaConstants.RACE_DETAILS_HORSENAME);
        horseWeightNdx = cursor.getColumnIndex(SchemaConstants.RACE_DETAILS_WEIGHT);
        jockeyNameNdx = cursor.getColumnIndex(SchemaConstants.RACE_DETAILS_JOCKEY_NAME);
        trainerNameNdx = cursor.getColumnIndex(SchemaConstants.RACE_DETAILS_TRAINER_NAME);

        notifyDataSetChanged();
    }

//    public void setOnItemClickListener(IItemClickListener listener) {
//        this.itemClickListener = listener;
//    }

    public Cursor getCursor() { return cursor; }

    public void setEmptyView(boolean emptyView) {
        this.emptyView = emptyView;
    }

    private void adapaterOnBindViewHolder(MeetingRacesDetailsViewHolder holder, int position) {
        cursor.moveToPosition(position);

        holder.getTvHorseName().setText(cursor.getString(horseNameNdx));
        holder.getTvWeight().setText(cursor.getString(horseWeightNdx));

        holder.getTvJockeyName().setText(getAbbreviatedName(cursor.getString(jockeyNameNdx)));
        holder.getTvTrainerName().setText(getAbbreviatedName(cursor.getString(trainerNameNdx)));
    }

    private String getAbbreviatedName(String name) {
        String rName;
        String[] aName = name.split(" ");
        int size = aName.length;
        rName = aName[0].substring(0,1) + ".";
        if(size > 2) {
            rName = rName + aName[size-1];
        } else {
            rName = rName + aName[1];
        }
        return rName;
    }


    private View view;
    private Cursor cursor;
    private boolean emptyView;

    private int idColNdx;
    private int horseNameNdx;
    private int horseWeightNdx;
    private int jockeyNameNdx;
    private int trainerNameNdx;

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