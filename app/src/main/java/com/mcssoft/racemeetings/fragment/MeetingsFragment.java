package com.mcssoft.racemeetings.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.adapter.MeetingsAdapter;
import com.mcssoft.racemeetings.database.SchemaConstants;
import com.mcssoft.racemeetings.interfaces.IItemClickListener;
import com.mcssoft.racemeetings.interfaces.IItemLongClickListener;
import com.mcssoft.racemeetings.utility.DatabaseUtility;
import com.mcssoft.racemeetings.utility.Resources;

/**
 * Fragment to display the details of meetings (results returned by a search by meeting date).
 */
public class MeetingsFragment extends Fragment
        implements IItemClickListener, IItemLongClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_meetings, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DatabaseUtility dbUtil = new DatabaseUtility(getActivity());
        cursor = dbUtil.getAllFromTable(SchemaConstants.MEETINGS_TABLE);

        setMeetingAdapter();
        setRecyclerView(rootView);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor = null;
    }

    @Override
    public void onItemClick(View view, int position) {
        // TODO - implement a view showing all the available details for the meeting selected.
        String bp = "";
    }

    @Override
    public void onItemLongClick(View view, int position) {
        // TBA.
        String bp = "";
    }

    private void setRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.id_rv_meetings_details__listing);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.scrollToPosition(0);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new ListingDivider(getActivity(), LinearLayoutManager.VERTICAL));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(meetingsAdapter);
    }

    private void setMeetingAdapter() {
        meetingsAdapter = new MeetingsAdapter();
        meetingsAdapter.setOnItemClickListener(this);
        meetingsAdapter.setOnItemLongClickListener(this);
        meetingsAdapter.swapCursor(cursor);
        if(cursor.getCount() == 0) {
            meetingsAdapter.setEmptyView(true);
        } else {
            meetingsAdapter.setEmptyView(false);
        }
    }

    private View rootView;
    private Cursor cursor;
    private MeetingsAdapter meetingsAdapter;
}
