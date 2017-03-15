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

import java.util.Set;

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

    // Check the arguments for existance of "network_exists_key".
    private void checkArguments() {
        Bundle args = getArguments();
        Set<String> argsKeySet = args.keySet();

        for(String key : argsKeySet) {

        }

//        if(args != null) {
//            key = Resources.getInstance().getString(R.string.network_exists_key);
//            if(args.containsKey(key)) {
//               if(args.getBoolean(key, false)) {
//
//                    tvMessage = (TextView) rootView.findViewById(R.id.id_tv_message);
//                    String message = Resources.getInstance().getString(R.string.no_network)
//                                   + Resources.getInstance().getString(R.string.CR_LF)
//                                   + Resources.getInstance().getString(R.string.check_device);
//                    tvMessage.setText(message);
//               }
//            }
//
//        }
    }

    View rootView;
    TextView tvMessage;
}
