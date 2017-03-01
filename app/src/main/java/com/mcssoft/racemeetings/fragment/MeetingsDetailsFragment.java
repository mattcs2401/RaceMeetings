package com.mcssoft.racemeetings.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.adapter.MeetingsAdapter;
import com.mcssoft.racemeetings.interfaces.IItemClickListener;
import com.mcssoft.racemeetings.interfaces.IItemLongClickListener;

/**
 * Fragment to display the details of meetings (results returned by a search by meeting date).
 */
public class MeetingsDetailsFragment extends Fragment
        implements IItemClickListener, IItemLongClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Log.d(LOG_TAG, "onCreateView");
        rootView = inflater.inflate(R.layout.fragment_meetings_details, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setMeetingAdapter();
//        activityCreated = true;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//    }

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


    private View rootView;
//    private boolean activityCreated;
//    private RecyclerView recyclerView;
    private MeetingsAdapter meetingsAdapter;
}
