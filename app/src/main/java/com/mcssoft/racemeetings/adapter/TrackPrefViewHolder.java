package com.mcssoft.racemeetings.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.interfaces.IItemClickListener;
import com.mcssoft.racemeetings.interfaces.IItemLongClickListener;


public class TrackPrefViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    public TrackPrefViewHolder(View view) {
        super(view);
        // TBA.
    }

    public TrackPrefViewHolder(View view, IItemClickListener listener) {
        super(view);
        tvTrackName = (TextView) view.findViewById(R.id.id_tv_track_row);
        cbTrackName = (CheckBox) view.findViewById(R.id.id_cb_track_row);

        // Set the listeners.
        itemClickListener = listener;
        view.setOnClickListener(this);
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Listeners">
    @Override
    public void onClick(View view) {
        if(itemClickListener != null){
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Accessors">
    public TextView getTrackName() { return tvTrackName; }
    public CheckBox getTrackNameCb() { return  cbTrackName; }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Private vars">
    private TextView tvTrackName;
    private CheckBox cbTrackName;
    private IItemClickListener itemClickListener;
    //</editor-fold> {
}
