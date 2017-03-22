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
import com.mcssoft.racemeetings.activity.MeetingRaceSummaryActivity;
import com.mcssoft.racemeetings.adapter.MeetingRacesAdapter;
import com.mcssoft.racemeetings.database.DatabaseOperations;
import com.mcssoft.racemeetings.database.SchemaConstants;
import com.mcssoft.racemeetings.interfaces.IItemClickListener;
import com.mcssoft.racemeetings.utility.DownloadHelper;
import com.mcssoft.racemeetings.utility.ListingDivider;
import com.mcssoft.racemeetings.utility.Resources;

public class MeetingRacesFragment extends Fragment
        implements IItemClickListener,
                   PopupMenu.OnMenuItemClickListener {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.races_fragment, container, false);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            String key = Resources.getInstance().getString(R.string.meeting_id_key);
            String argsPref = Resources.getInstance().getString(R.string.arguments_preference);
            String meetingId = getActivity()
                    .getSharedPreferences(argsPref, getActivity().MODE_PRIVATE)
                    .getString(key, null);
            DatabaseOperations dbOper = new DatabaseOperations(getActivity());
            cursor = dbOper.getSelectionFromTable(SchemaConstants.RACES_TABLE, null,
                    SchemaConstants.WHERE_FOR_GET_RACE_MEETINGID, new String[] {meetingId});
            cursor.moveToFirst();

            setMeetingAdapter();
            setRecyclerView(rootView);
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
            popupMenu.inflate(R.menu.races_context_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.races_context_menu_info:
                getDataForRaceDetails(getDbRowId(position));
                break;
            case R.id.races_context_menu_detail:
                intent = new Intent(getActivity(), MeetingRaceSummaryActivity.class);
                intent.putExtra(Resources.getInstance().getString(R.string.races_rowid_key), getDbRowId(position));
                startActivity(intent);
                break;
        }
        return false;
    }
//        @Override
//        public void onItemLongClick(View view, int position) {
//            // TBA.
//            String bp = "";
//        }

    private void getDataForRaceDetails(int raceRowId) {
        DatabaseOperations dbOper = new DatabaseOperations(getActivity());
        Cursor cursor = dbOper.getSelectionFromTable(SchemaConstants.RACES_TABLE, null,
                SchemaConstants.WHERE_FOR_GET_RACE, new String[] {Integer.toString(raceRowId)});
        cursor.moveToFirst();
        int raceId = cursor.getInt(cursor.getColumnIndex(SchemaConstants.RACE_ID));

        DownloadHelper downloadHelper = new DownloadHelper(getActivity());
        downloadHelper.getHorsesForRace(Integer.toString(raceId));
    }

    private void setRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.id_rv_races_summary_listing);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.scrollToPosition(0);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new ListingDivider(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(meetingRacesAdapter);
    }

    private void setMeetingAdapter() {

        meetingRacesAdapter = new MeetingRacesAdapter();
        meetingRacesAdapter.setOnItemClickListener(this);
//        meetingRacesAdapter.setOnItemLongClickListener(this);
        meetingRacesAdapter.swapCursor(cursor);
        if(cursor.getCount() == 0) {
            meetingRacesAdapter.setEmptyView(true);
        } else {
            meetingRacesAdapter.setEmptyView(false);
        }
    }

    private int getDbRowId(int position) {
        meetingRacesAdapter.getItemId(position);
        Cursor cursor = meetingRacesAdapter.getCursor();
        return cursor.getInt(cursor.getColumnIndex(SchemaConstants.RACE_ROWID));
    }

    private int position;
    private View rootView;
    private Cursor cursor;
    private MeetingRacesAdapter meetingRacesAdapter;

}
