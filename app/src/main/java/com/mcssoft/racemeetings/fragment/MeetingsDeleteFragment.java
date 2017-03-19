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

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.database.DatabaseOperations;
import com.mcssoft.racemeetings.database.SchemaConstants;
import com.mcssoft.racemeetings.interfaces.IMeetingsDelete;
import com.mcssoft.racemeetings.utility.Resources;

public class MeetingsDeleteFragment extends DialogFragment
        implements DialogInterface.OnClickListener {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Setup the interface.
        iMeetingDelete = (IMeetingsDelete) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.delete_fragment, null);

        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
        ab.setView(view)
          .setNegativeButton(Resources.getInstance().getString(R.string.button_cancel_text), this)
          .setPositiveButton(Resources.getInstance().getString(R.string.button_ok_text), this);

        return ab.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        int rows = 0;
        switch (which) {
            case AlertDialog.BUTTON_POSITIVE:
                DatabaseOperations dbOper = new DatabaseOperations(getActivity());
                rows = dbOper.deleteAllFromTable(SchemaConstants.MEETINGS_TABLE);
                break;
        }
        if(rows > 0) {
            iMeetingDelete.iMeetingsDelete(rows);
        }
    }

    private IMeetingsDelete iMeetingDelete = null;
}