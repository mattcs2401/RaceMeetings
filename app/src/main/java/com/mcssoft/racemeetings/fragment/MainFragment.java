package com.mcssoft.racemeetings.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.utility.Resources;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        checkArguments();
    }

    // Check the arguments for existance of "no_network_key". Existance of a network was checked in
    // the activity
    private void checkArguments() {
        Bundle args = getArguments();
        if(args != null) {
            if(args.containsKey(Resources.getInstance().getString(R.string.no_network_key))) {

                tvMessage = (TextView) rootView.findViewById(R.id.id_tv_message);
                String message = "No network connection exists."
                        + Resources.getInstance().getString(R.string.CR_LF)
                        + "Check your device settings.";
                tvMessage.setText(message);
            }
        }
    }

    View rootView;
    TextView tvMessage;
}
