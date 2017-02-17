package com.mcssoft.racemeetings.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.utility.Preferences;
import com.mcssoft.racemeetings.utility.Resources;

public class TrackDialog extends DialogPreference
    implements View.OnClickListener {

    public TrackDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        checkPreference();
        setDialogLayoutResource(R.layout.dialog_pref_track);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        initialise(view);
    }

    @Override
    public void onClick(View view) { }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == DialogInterface.BUTTON_POSITIVE) {
            saveTrackPreference();
        }
    }

    private void initialise(View view) {

    }

    private void saveTrackPreference() {
    }

    private void checkPreference() {
    }


}
