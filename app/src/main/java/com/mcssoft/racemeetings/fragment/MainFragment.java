package com.mcssoft.racemeetings.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.utility.Preferences;
import com.mcssoft.racemeetings.utility.Resources;

import java.util.Set;

public class MainFragment extends Fragment {

    /** TBA - If the show today's meetings preference is not set, and the network exists,
              this fragment will display blank.
     **/

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
        if(args != null) {
            String key = Resources.getInstance().getString(R.string.network_exists_key);

            if(args.containsKey(key)) {          // key exists ?
               if(args.getBoolean(key, false)) { // get the value.

                    tvMessage = (TextView) rootView.findViewById(R.id.id_tv_message);
                    String message = Resources.getInstance().getString(R.string.no_network)
                                   + Resources.getInstance().getString(R.string.CR_LF)
                                   + Resources.getInstance().getString(R.string.check_device);
                    tvMessage.setText(message);
               }
            }

        }
    }

    View rootView;
    TextView tvMessage;
}
