package com.mcssoft.racemeetings.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.interfaces.IItemClickListener;
import com.mcssoft.racemeetings.interfaces.IItemLongClickListener;


public class MeetingsViewHolder  extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener {

    public MeetingsViewHolder(View view) {
        super(view);
        tvEmptyView = (TextView) view.findViewById(R.id.id_tv_nothingToShow);
    }

    public MeetingsViewHolder(View view, IItemClickListener listener, IItemLongClickListener longListener) {
        super(view);
//        tvMeetingId = (TextView) view.findViewById(R.id)
//        tvMeetingDate = (TextView) view.findViewById(R.id)
//        tvTrackName = (TextView) view.findViewById(R.id)
//        tvClubName = (TextView) view.findViewById(R.id)
//        tvRacingStatus = (TextView) view.findViewById(R.id)
//        tvNumRaces = (TextView) view.findViewById(R.id)
//        tvBarrierTrial = (TextView) view.findViewById(R.id)

        itemClickListener = listener;
        itemLongClickListener = longListener;
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Listeners">
    @Override
    public void onClick(View view) {
        if(itemClickListener != null){
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if(itemLongClickListener != null){
            itemLongClickListener.onItemLongClick(view, getAdapterPosition());
            return true;
        }
        return false;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Accessors">
    public TextView getMeetingId() { return tvMeetingId;  }
    public TextView getTvMeetingDate() { return tvMeetingDate; }
    public TextView getTrackName() { return tvTrackName; }
    public TextView getClubName() { return tvClubName; }
    public TextView getRacingStatus() { return tvRacingStatus; }
    public TextView getNumRaces() { return tvNumRaces; }
    public TextView getBarrierTrial() { return tvBarrierTrial; }

    public TextView getEmptyText() { return tvEmptyView; }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Private vars">
    private TextView tvMeetingId;
    private TextView tvMeetingDate;
    private TextView tvTrackName;
    private TextView tvClubName;
    private TextView tvRacingStatus;
    private TextView tvNumRaces;
    private TextView tvBarrierTrial;

    private TextView tvEmptyView;

    private IItemClickListener itemClickListener;
    private IItemLongClickListener itemLongClickListener;
    //</editor-fold> {
}
