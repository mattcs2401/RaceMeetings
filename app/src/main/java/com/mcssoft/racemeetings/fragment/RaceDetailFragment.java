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

public class RaceDetailFragment extends Fragment {

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.race_detail_fragment, container, false);
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

    }

    private void getFromDatabase() {

    }

    private void setComponentValues() {
        cursor.moveToFirst();

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Local variables">
    private View rootView;
    private Cursor cursor;

    private TextView tvRaceId;
    private TextView tvRaceNo;
    private TextView tvRaceName;
    private TextView tvRaceTime;
    private TextView tvRaceClass;
    private TextView tvRaceDistance;
    private TextView tvTrackRating;
    private TextView tvPrizeTotal;
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
