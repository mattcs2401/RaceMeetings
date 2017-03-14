package com.mcssoft.racemeetings.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.activity.MeetingDetailActivity;
import com.mcssoft.racemeetings.activity.MeetingRacesActivity;
import com.mcssoft.racemeetings.adapter.MeetingsAdapter;
import com.mcssoft.racemeetings.database.SchemaConstants;
import com.mcssoft.racemeetings.interfaces.IItemClickListener;
import com.mcssoft.racemeetings.interfaces.IItemLongClickListener;
import com.mcssoft.racemeetings.database.DatabaseOperations;
import com.mcssoft.racemeetings.utility.DownloadHelper;
import com.mcssoft.racemeetings.utility.ListingDivider;
import com.mcssoft.racemeetings.utility.Resources;

/**
 * Fragment to display the details of meetings (results returned by a search by model date).
 */
public class MeetingsFragment extends Fragment
        implements IItemClickListener,
                   IItemLongClickListener,
                   PopupMenu.OnMenuItemClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.meetings_fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DatabaseOperations dbOper = new DatabaseOperations(getActivity());
        cursor = dbOper.getAllFromTable(SchemaConstants.MEETINGS_TABLE);

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
        this.position = position;
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.meetings_context_menu);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.meetings_context_menu_races:
                getDataForMeetingRaces(getDbRowId(position));
                break;
            case R.id.meetings_context_menu_detail:
                intent = new Intent(getActivity(), MeetingDetailActivity.class);
                intent.putExtra(Resources.getInstance().getString(R.string.meetings_rowid_key), getDbRowId(position));
                startActivity(intent);
                break;
        }
        return false;
    }

    @Override
    public void onItemLongClick(View view, int position) {
        // TBA.
        String bp = "";
    }

    private void setRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.id_rv_meetings_details_listing);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.scrollToPosition(0);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new ListingDivider(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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

    private int getDbRowId(int position) {
        meetingsAdapter.getItemId(position);
        Cursor cursor = meetingsAdapter.getCursor();
        return cursor.getInt(cursor.getColumnIndex(SchemaConstants.MEETING_ROWID));
    }

    private void getDataForMeetingRaces(int meetingRowId) {
        // Note: MeetingRacesActivity will launch from the iAsyncResult.result() method.
        DatabaseOperations dbOper = new DatabaseOperations(getActivity());
        Cursor cursor = dbOper.getSelectionFromTable(SchemaConstants.MEETINGS_TABLE, null,
                SchemaConstants.WHERE_FOR_GET_MEETING, new String[] {Integer.toString(meetingRowId)});
        cursor.moveToFirst();
        int meetingId = cursor.getInt(cursor.getColumnIndex(SchemaConstants.MEETING_ID));

        DownloadHelper downloadHelper = new DownloadHelper(getActivity());
        downloadHelper.getRacesForMeeting(Integer.toString(meetingId));
    }

    private int position;
    private View rootView;
    private Cursor cursor;
    private MeetingsAdapter meetingsAdapter;
}
