package com.mcssoft.racemeetings.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.adapter.MeetingRacesDetailsAdapter;
import com.mcssoft.racemeetings.database.DatabaseOperations;
import com.mcssoft.racemeetings.database.SchemaConstants;
import com.mcssoft.racemeetings.utility.ListingDivider;
import com.mcssoft.racemeetings.utility.Resources;

public class MeetingRacesDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.races_details_fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String key = Resources.getInstance().getString(R.string.race_id_key);
        String argsPref = Resources.getInstance().getString(R.string.arguments_preference);
        String raceId = getActivity()
                .getSharedPreferences(argsPref, getActivity().MODE_PRIVATE)
                .getString(key, null);
        DatabaseOperations dbOper = new DatabaseOperations(getActivity());
        cursor = dbOper.getSelectionFromTable(SchemaConstants.RACE_DETAILS_TABLE, null,
                SchemaConstants.WHERE_FOR_GET_RACE_DETAILS_RACEID, new String[] {raceId});
        cursor.moveToFirst();

        setMeetingAdapter();
        setRecyclerView(rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor = null;
    }

    private void setMeetingAdapter() {
        meetingRacesDetailsAdapter = new MeetingRacesDetailsAdapter();
        meetingRacesDetailsAdapter.swapCursor(cursor);
    }

    private void setRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.id_rv_races_details_listing);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.scrollToPosition(0);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new ListingDivider(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(meetingRacesDetailsAdapter);
    }

    private View rootView;
    private Cursor cursor;
    private MeetingRacesDetailsAdapter meetingRacesDetailsAdapter;
}
