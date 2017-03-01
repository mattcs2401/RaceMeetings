package com.mcssoft.racemeetings.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.interfaces.IDateSelect;

public class DateSearchFragment extends DialogFragment
        implements DialogInterface.OnClickListener {

    public DateSearchFragment() { }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        iDateSelect = (IDateSelect) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_search, null);

        datePicker = (DatePicker) view.findViewById(R.id.id_datePicker);

        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
        ab.setView(view) //R.layout.fragment_search)
        .setNegativeButton("Cancel", this)
        .setPositiveButton("OK", this);

        return ab.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        String[] dateVals = null;
        switch (which) {
            case AlertDialog.BUTTON_POSITIVE:
                dateVals = new String[3];
                dateVals[0] = String.valueOf(datePicker.getYear());
                dateVals[1] = String.valueOf((datePicker.getMonth() + 1)); // months indexed at 0 ??
                if(dateVals[1].length() == 1) {
                    dateVals[1] = "0" + dateVals[1];
                }
                dateVals[2] = String.valueOf(datePicker.getDayOfMonth());
                if(dateVals[2].length() == 1) {
                    dateVals[2] = "0" + dateVals[2];
                }
                break;
        }
        if(dateVals != null) {
            iDateSelect.iDateValues(dateVals);
        }
    }

    private DatePicker datePicker;
    public IDateSelect iDateSelect = null;
}

