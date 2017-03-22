package com.mcssoft.racemeetings.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.interfaces.IItemClickListener;


public class MeetingRacesDetailsViewHolder extends RecyclerView.ViewHolder {

    public MeetingRacesDetailsViewHolder(View view) {
        super(view);
        tvEmptyView = (TextView) view.findViewById(R.id.id_tv_nothingToShow);
    }

    public MeetingRacesDetailsViewHolder(View view, IItemClickListener listener) { //, IItemLongClickListener longListener) {
        super(view);
//        tvRaceId = (TextView) view.findViewById(R.id.id_tv_)
//        tvRaceNo = (TextView) view.findViewById(R.id.id_tv_race_no);
//        tvRaceName = (TextView) view.findViewById(R.id.id_tv_race_name);
//        tvRaceTime = (TextView) view.findViewById(R.id.id_tv_race_time);
//        tvRaceClass = (TextView) view.findViewById(R.id.id_tv_race_class);
//        tvRaceDistance = (TextView) view.findViewById(R.id.id_tv_race_distance);
//        tvRaceRating = (TextView) view.findViewById(R.id.id_tv_race_rating);
//        tvRacePrize = (TextView) view.findViewById(R.id.id_tv_race_prize);

//        itemClickListener = listener;
//        view.setOnClickListener(this);
//        itemLongClickListener = longListener;
//        view.setOnLongClickListener(this);
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Listeners">
//    @Override
//    public void onClick(View view) {
//        if(itemClickListener != null){
//            itemClickListener.onItemClick(view, getAdapterPosition());
//        }
//    }

    //<editor-fold defaultstate="collapsed" desc="Region: Accessors">
//    public TextView getTvRaceId() { return tvRaceId;  }
//    public TextView getRaceNo() { return tvRaceNo; }
//    public TextView getRaceName() { return tvRaceName; }
//    public TextView getRaceTime() { return tvRaceTime; }
//    public TextView getRaceClass() { return tvRaceClass; }
//    public TextView getRaceDistance() { return tvRaceDistance; }
//    public TextView getRaceRating() { return tvRaceRating; }
//    public TextView getRacePrize() { return tvRacePrize; }
//    public TextView getEmptyText() { return tvEmptyView; }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Private vars">
//    private TextView tvRaceId;
//    private TextView tvRaceNo;
//    private TextView tvRaceName;
//    private TextView tvRaceTime;
//    private TextView tvRaceClass;
//    private TextView tvRaceDistance;
//    private TextView tvRaceRating;
//
    private TextView tvEmptyView;

//    private IItemClickListener itemClickListener;
//    private IItemLongClickListener itemLongClickListener;
    //</editor-fold> {
}
