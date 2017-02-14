package com.mcssoft.racemeetings.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.utility.Preferences;
import com.mcssoft.racemeetings.utility.Resources;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Log.d(LOG_TAG, "onCreateView");
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
//        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.id_toolbar_frag_main);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        if(!checkForNetwork()) {

            String bp = "";
        } else {

        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private boolean checkForNetwork() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null) {
            String prefNetworkType = Preferences.getInstance().networkPrefTag();
            String networkType = networkInfo.getTypeName();

            // this may return false as well.
            return (prefNetworkType.equals(networkType) && networkInfo.isConnected());

        } else {
            return false;
        }
    }

    private View rootView;
}
