package com.mcssoft.racemeetings.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.database.DatabaseOperations;
import com.mcssoft.racemeetings.database.SchemaConstants;
import com.mcssoft.racemeetings.utility.Resources;

public class MeetingDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.meeting_detail_fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialiseUI();
    }

    private void initialiseUI() {
        initialiseComponents();
        getFromDatabase();
        setComponentValues();
    }

    private void initialiseComponents() {
        tvMeetingId = (TextView) rootView.findViewById(R.id.id_tv_meetingdId_val);
        tvMeetingDate = (TextView) rootView.findViewById(R.id.id_tv_meetingDate_val);
        tvTrackName = (TextView) rootView.findViewById(R.id.id_tv_trackName_val);
        tvClubName = (TextView) rootView.findViewById(R.id.id_tv_clubName_val);
        tvRacingStatus = (TextView) rootView.findViewById(R.id.id_tv_racingStatus_val);
        tvNumRaces = (TextView) rootView.findViewById(R.id.id_tv_numRaces_val);
        tvBarrierTrial = (TextView) rootView.findViewById(R.id.id_tv_barrierTrail_val);
    }

    private void getFromDatabase() {
        int rowId = getActivity().getIntent().getIntExtra(Resources.getInstance().getString(R.string.meetings_rowid_key),
                Resources.getInstance().getInteger(R.integer.init_default));
        DatabaseOperations dbOper = new DatabaseOperations(getActivity());
        cursor = dbOper.getSelectionFromTable(SchemaConstants.MEETINGS_TABLE,
                null, //SchemaConstants.MEETING_COLUMNS,
                SchemaConstants.WHERE_FOR_GET_MEETING,
                new String[] {Integer.toString(rowId)});
    }

    private void setComponentValues() {
        cursor.moveToFirst();
        tvMeetingId.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.MEETING_ID)));
        tvMeetingDate.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.MEETING_DATE)));
        tvTrackName.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.MEETING_TRACK)));
        tvClubName.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.MEETING_CLUB)));
        tvRacingStatus.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.MEETING_STATUS)));
        tvNumRaces.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.MEETING_NO_RACES)));
        tvBarrierTrial.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.MEETING_IS_TRIAL)));
    }

    private View rootView;
    private Cursor cursor;

    private TextView tvMeetingId;
    private TextView tvMeetingDate;
    private TextView tvTrackName;
    private TextView tvClubName;
    private TextView tvRacingStatus;
    private TextView tvNumRaces;
    private TextView tvBarrierTrial;
}
/*
<Meeting Id="88788">
  <MeetingDate>2017-02-18</MeetingDate>
  <TrackName>Gold Coast</TrackName>
  <ClubName>Gold Coast Turf Club</ClubName>
  <RacingStatus>Provincial</RacingStatus>
  <NumberOfRaces>8</NumberOfRaces>
  <IsBarrierTrial>false</IsBarrierTrial>
</Meeting>-->
*/
