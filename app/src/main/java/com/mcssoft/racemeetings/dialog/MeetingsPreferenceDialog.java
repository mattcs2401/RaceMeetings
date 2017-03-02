package com.mcssoft.racemeetings.dialog;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

import com.mcssoft.racemeetings.R;

/**
 * utility class where a user can set preferences related to all Meetings.
 */
public class MeetingsPreferenceDialog extends DialogPreference {

    public MeetingsPreferenceDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
//        setDialogLayoutResource(R.layout.dialog_pref_track);
    }
}
