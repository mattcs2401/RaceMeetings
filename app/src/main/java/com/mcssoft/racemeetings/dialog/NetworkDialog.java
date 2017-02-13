package com.mcssoft.racemeetings.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mcssoft.racemeetings.R;

public class NetworkDialog extends DialogPreference
        implements View.OnClickListener{

    public NetworkDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        checkPreference();
        setDialogLayoutResource(R.layout.dialog_pref_network);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        initialise(view);

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == DialogInterface.BUTTON_POSITIVE) {
            saveNetworkPreference();
        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.id_rb_only_wifi:
                break;
            case R.id.id_rb_any_network:
                break;
        }
    }

    private void initialise(View view) {
        radioGroup = (RadioGroup) view.findViewById(R.id.id_rg_network);
        rbShowWifi = (RadioButton) view.findViewById(R.id.id_rb_only_wifi);
        rbShowAny = (RadioButton) view.findViewById(R.id.id_rb_any_network);

        rbShowWifi.setOnClickListener(this);
        rbShowAny.setOnClickListener(this);

//        radioButtonId = MeetingPreferences.getInstance().meetingRaceShowPref();
    }

    private void saveNetworkPreference() {

    }

    private void checkPreference() {

    }

    private RadioGroup radioGroup;
    private RadioButton rbShowWifi;
    private RadioButton rbShowAny;
    private int radioButtonId;
}
