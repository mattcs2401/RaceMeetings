package com.mcssoft.racemeetings.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.adapter.MeetingsAdapter;
import com.mcssoft.racemeetings.utility.Preferences;
import com.mcssoft.racemeetings.utility.Resources;
import com.mcssoft.racemeetings.interfaces.*;

public class MainFragment extends Fragment
        implements IItemClickListener, IItemLongClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Log.d(LOG_TAG, "onCreateView");
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setMeetingAdapter();
        activityCreated = true;
    }

    @Override
    public void onStart() {
        super.onStart();

        if(checkForNetwork()) {

            String bp = "";
        } else {

        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    private void setMeetingAdapter() {
        meetingsAdapter = new MeetingsAdapter();
        meetingsAdapter.setOnItemClickListener(this);
        meetingsAdapter.setOnItemLongClickListener(this);
    }
    /**
     * Check the network type in the Preferences against the actual active network type.
     * @return True if the Preferences network type is the same as the actual active network type.
     */
    private boolean checkForNetwork() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null) {
            String prefNetworkType = Preferences.getInstance().networkPrefTag();
            String networkType = networkInfo.getTypeName();

            // this may return false as well.
            return (prefNetworkType.equals(networkType) && networkInfo.isConnected());

        } else {
            return false;
        }
    }

    private View rootView;
    private boolean activityCreated;
    private RecyclerView recyclerView;
    private MeetingsAdapter meetingsAdapter;
}
