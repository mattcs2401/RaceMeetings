package com.mcssoft.racemeetings.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.interfaces.IItemClickListener;
import com.mcssoft.racemeetings.interfaces.IItemLongClickListener;


public class MeetingRacesViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener { //}, View.OnLongClickListener {

    public MeetingRacesViewHolder(View view) {
        super(view);
        tvEmptyView = (TextView) view.findViewById(R.id.id_tv_nothingToShow);
    }

    public MeetingRacesViewHolder(View view, IItemClickListener listener) { //, IItemLongClickListener longListener) {
        super(view);
//        tvRaceId = (TextView) view.findViewById(R.id.id_tv_)
        tvRaceNo = (TextView) view.findViewById(R.id.id_tv_race_no_val);
        tvRaceName = (TextView) view.findViewById(R.id.id_tv_race_name_val);
        tvRaceTime = (TextView) view.findViewById(R.id.id_tv_race_time_val);
        tvRaceClass = (TextView) view.findViewById(R.id.id_tv_race_class_val);
        tvRaceDistance = (TextView) view.findViewById(R.id.id_tv_race_dist_val);
        tvRaceRating = (TextView) view.findViewById(R.id.id_tv_race_rating_val);
        tvRacePrize = (TextView) view.findViewById(R.id.id_tv_race_prize_val);

        itemClickListener = listener;
        view.setOnClickListener(this);
//        itemLongClickListener = longListener;
//        view.setOnLongClickListener(this);
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Listeners">
    @Override
    public void onClick(View view) {
        if(itemClickListener != null){
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

//    @Override
//    public boolean onLongClick(View view) {
//        if(itemLongClickListener != null){
//            itemLongClickListener.onItemLongClick(view, getAdapterPosition());
//            return true;
//        } else {
//            return false;
//        }
//    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Accessors">
//    public TextView getTvRaceId() { return tvRaceId;  }
    public TextView getRaceNo() { return tvRaceNo; }
    public TextView getRaceName() { return tvRaceName; }
    public TextView getRaceTime() { return tvRaceTime; }
    public TextView getRaceClass() { return tvRaceClass; }
    public TextView getRaceDistanave() { return tvRaceDistance; }
    public TextView getRaceRating() { return tvRaceRating; }
    public TextView getRacePrize() { return tvRacePrize; }

    public TextView getEmptyText() { return tvEmptyView; }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Private vars">
    private TextView tvRaceId;
    private TextView tvRaceNo;
    private TextView tvRaceName;
    private TextView tvRaceTime;
    private TextView tvRaceClass;
    private TextView tvRaceDistance;
    private TextView tvRaceRating;
    private TextView tvRacePrize;

    private TextView tvEmptyView;

    private IItemClickListener itemClickListener;
//    private IItemLongClickListener itemLongClickListener;
    //</editor-fold> {
}
