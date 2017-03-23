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

public class MeetingRaceSummaryFragment extends Fragment {

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.race_summary_fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialiseUI();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    private void initialiseUI() {
        initialiseComponents();   // instantiate the TextViews.
        getFromDatabase();        // get the meeting details into a local cursor.
        setComponentValues();     // set the values for the TextViews from the local cursor.
    }

    private void initialiseComponents() {
        tvMeetingId = (TextView) rootView.findViewById(R.id.id_tv_race_meeting_id_val);
        tvRaceId = (TextView) rootView.findViewById(R.id.id_tv_race_id_val);
        tvRaceNo = (TextView) rootView.findViewById(R.id.id_tv_race_no_val);
        tvRaceName = (TextView) rootView.findViewById(R.id.id_tv_race_name_val);
        tvRaceTime = (TextView) rootView.findViewById(R.id.id_tv_race_time_val);
        tvRaceClass = (TextView) rootView.findViewById(R.id.id_tv_race_class_val);
        tvRaceDistance = (TextView) rootView.findViewById(R.id.id_tv_race_distance_val);
        tvTrackRating = (TextView) rootView.findViewById(R.id.id_tv_track_rating_val);
        tvPrizeTotal = (TextView) rootView.findViewById(R.id.id_tv_prize_val);
        tvBonusType = (TextView) rootView.findViewById(R.id.id_tv_bonus_val);
        tvBonusTotal = (TextView) rootView.findViewById(R.id.id_tv_bonus_total_avl);
        tvAgeCond = (TextView) rootView.findViewById(R.id.id_tv_age_restrict_val);
        tvSexCond = (TextView) rootView.findViewById(R.id.id_tv_sex_restrict_val);
        tvWeightCond = (TextView) rootView.findViewById(R.id.id_tv_weight_restrict_val);
        tvAppClaim = (TextView) rootView.findViewById(R.id.id_tv_app_claim_val);
        tvStartersFee = (TextView) rootView.findViewById(R.id.id_tv_starters_fee_val);
        tvAcceptFee = (TextView) rootView.findViewById(R.id.id_tv_accept_fee_val);
    }

    private void getFromDatabase() {
        int rowId = getActivity().getIntent()
                .getIntExtra(Resources.getInstance().getString(R.string.races_rowid_key),
                        Resources.getInstance().getInteger(R.integer.init_default));
        DatabaseOperations dbOper = new DatabaseOperations(getActivity());
        cursor = dbOper.getSelectionFromTable(SchemaConstants.RACES_TABLE,
                null,
                SchemaConstants.WHERE_FOR_GET_RACE,
                new String[] {Integer.toString(rowId)});
    }

    private void setComponentValues() {
        cursor.moveToFirst();
        tvMeetingId.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.RACE_MEETING_ID)));
        tvRaceId.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.RACE_ID)));
        tvRaceNo.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.RACE_NO)));
        tvRaceName.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.RACE_NAME)));
        tvRaceTime.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.RACE_TIME)));
        tvRaceClass.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.RACE_CLASS)));
        tvRaceDistance.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.RACE_DISTANCE)));
        tvTrackRating.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.RACE_TRACK_RATING)));
        tvPrizeTotal.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.RACE_PRIZE_TOTAL)));
        tvBonusType.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.RACE_BONUS_TYPE)));
        tvBonusTotal.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.RACE_BONUS_TOTAL)));
        tvAgeCond.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.RACE_AGE_COND)));
        tvSexCond.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.RACE_SEX_COND)));
        tvWeightCond.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.RACE_WEIGHT_COND)));
        tvAppClaim.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.RACE_APP_CLAIM)));
        tvStartersFee.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.RACE_START_FEE)));
        tvAcceptFee.setText(cursor.getString(cursor.getColumnIndex(SchemaConstants.RACE_ACCEPT_FEE)));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Local variables">
    private View rootView;
    private Cursor cursor;

    private TextView tvMeetingId;
    private TextView tvRaceId;
    private TextView tvRaceNo;
    private TextView tvRaceName;
    private TextView tvRaceTime;
    private TextView tvRaceClass;
    private TextView tvRaceDistance;
    private TextView tvTrackRating;
    private TextView tvPrizeTotal;
    private TextView tvBonusType;
    private TextView tvBonusTotal;
    private TextView tvAgeCond;
    private TextView tvSexCond;
    private TextView tvWeightCond;
    private TextView tvAppClaim;
    private TextView tvStartersFee;
    private TextView tvAcceptFee;
    //</editor-fold>
}
//<Race Id="439963">
//<RaceNumber>1</RaceNumber>
//<RaceName>APEX - BUILDING BETTER COMMUNITIES C,G&E Class 1 Handicap</RaceName>
//<RaceTime>12:52PM</RaceTime>
//<Class>Class 1</Class>
//<Distance>1400 metres</Distance>
//<TrackRating>Good</TrackRating>
//<PrizeTotal>$14,000.00</PrizeTotal>
//<AgeCondition>No age restriction</AgeCondition>
//<SexCondition>Colts, Geldings and Entires</SexCondition>
//<WeightCondition>Handicap</WeightCondition>
//<ApprenticeClaim>true</ApprenticeClaim>
//<StartersFee>$173.00</StartersFee>
//<AcceptanceFee>$0.00</AcceptanceFee>
//</Race>
