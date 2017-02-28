package com.mcssoft.racemeetings.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.mcssoft.racemeetings.R;

import java.util.Calendar;
import java.util.zip.Inflater;

public class DateSearchFragment extends DialogFragment
        implements DialogInterface.OnClickListener,
        DatePickerDialog.OnDateSetListener {

    public DateSearchFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.fragment_search, null);

//        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
//        ab.setView(R.layout.fragment_search);
//        return ab.create();

        // Android developers example:
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        String bp = "";

    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String bp = "";
    }
}

/* Example from RaceMeeting

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        boolean is24Hour = false;
        Bundle args = getArguments();

        if(args.getString(MeetingResources.getInstance().getString(R.string.time_format))
                .equals(MeetingResources.getInstance()
                        .getString(R.string.time_format_pref_24hr_key))) {
            is24Hour = true;
        }

        return new android.app.TimePickerDialog(
                getActivity(),
                (OnTimeSetListener) getFragmentManager().findFragmentByTag(MeetingResources
                        .getInstance().getString(R.string.edit_fragment_tag)),
                args.getInt(MeetingResources.getInstance().getString(R.string.hour)),
                args.getInt(MeetingResources.getInstance().getString(R.string.mins)),
                is24Hour);
    }
*/
