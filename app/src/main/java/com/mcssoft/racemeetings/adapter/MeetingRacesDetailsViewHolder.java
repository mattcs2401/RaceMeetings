package com.mcssoft.racemeetings.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.interfaces.IItemClickListener;


public class MeetingRacesDetailsViewHolder extends RecyclerView.ViewHolder {

    public MeetingRacesDetailsViewHolder(View view, IItemClickListener listener) {
        super(view);
        tvHorseName = (TextView) view.findViewById(R.id.id_tv_horse_name_val);
        tvWeight= (TextView) view.findViewById(R.id.id_tv_weight_val);
        tvJockeyName = (TextView) view.findViewById(R.id.id_tv_jockey_val);
        tvTrainerName = (TextView) view.findViewById(R.id.id_tv_trainer_val);

//        itemClickListener = listener;
//        view.setOnClickListener(this);
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Listeners">
//    @Override
//    public void onClick(View view) {
//        if(itemClickListener != null){
//            itemClickListener.onItemClick(view, getAdapterPosition());
//        }
//    }

    //<editor-fold defaultstate="collapsed" desc="Region: Accessors">
    public TextView getTvHorseName() { return tvHorseName;  }
    public TextView getTvWeight() { return tvWeight; }
    public TextView getTvJockeyName() { return tvJockeyName; }
    public TextView getTvTrainerName() { return tvTrainerName; }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Private vars">
    private TextView tvHorseName;
    private TextView tvWeight;
    private TextView tvJockeyName;
    private TextView tvTrainerName;
//    private IItemClickListener itemClickListener;
    //</editor-fold> {
}
