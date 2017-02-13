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

        radioButtonId = Preferences.getInstance().networkPrefButtonId();

        if(rbShowWifi.getId() == radioButtonId) {
            rbShowWifi.setChecked(true);
            rbValue = Resources.getInstance().getString(R.string.network_wifi_connected_tag);
        } else {
            // no need to check for id, but we still record on save.
            rbShowAny.setChecked(true);
            rbValue = Resources.getInstance().getString(R.string.network_any_connected_tag);        }
    }

    private void saveNetworkPreference() {
        int buttonId = radioGroup.getCheckedRadioButtonId();

        if(buttonId != radioButtonId) {
            // only save if there was a change.
            SharedPreferences.Editor spe
                    = Preferences.getInstance().getDefaultSharedPreferences().edit();
            spe.putInt(Resources.getInstance()
                    .getString(R.string.pref_network_access_button_id_key), buttonId).apply();
            spe.putString(Resources.getInstance()
                    .getString(R.string.pref_network_access_tag_key), rbValue).apply();
            notifyChanged();
        }
    }

    private void checkPreference() {
        // Has to be PreferenceManager if it's the 1st time the app is run.
        if (!(PreferenceManager.getDefaultSharedPreferences(getContext())
                .contains(Resources.getInstance().getString(R.string.pref_network_access_button_id_key)))) {

            // If the preference doesn't exist, set the default for this preference (Wi-Fi).
            SharedPreferences.Editor spe =
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.dialog_pref_network, null);
            RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.id_rg_network);

            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(0);
            spe.putInt(Resources.getInstance()
                .getString(R.string.pref_network_access_button_id_key), radioButton.getId()).apply();
            spe.putString(Resources.getInstance()
                    .getString(R.string.pref_network_access_tag_key),
                    Resources.getInstance().getString(R.string.network_wifi_connected_tag)).apply();
        }
    }

    private RadioGroup radioGroup;
    private RadioButton rbShowWifi;
    private RadioButton rbShowAny;
    private int radioButtonId;
    private String rbValue;
}
