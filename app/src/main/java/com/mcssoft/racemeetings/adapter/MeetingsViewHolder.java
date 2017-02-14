package com.mcssoft.racemeetings.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.interfaces.IItemClickListener;
import com.mcssoft.racemeetings.interfaces.IItemLongClickListener;


public class MeetingsViewHolder  extends RecyclerView.ViewHolder
        implements View.OnClickListener,
        View.OnLongClickListener {

    public MeetingsViewHolder(View view) {
        super(view);
        tvEmptyView = (TextView) view.findViewById(R.id.id_tvEmptyView);
    }

    public MeetingsViewHolder(View view, IItemClickListener listener, IItemLongClickListener longListener) {
        super(view);
        // Set the ViewHolder components.

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


    public TextView getEmptyText() { return tvEmptyView; }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Private vars">
    private TextView tvEmptyView;

    private IItemClickListener itemClickListener;
    private IItemLongClickListener itemLongClickListener;
    //</editor-fold> {
}
