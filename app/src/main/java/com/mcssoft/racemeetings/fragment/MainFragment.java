package com.mcssoft.racemeetings.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcssoft.racemeetings.R;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Log.d(LOG_TAG, "onCreateView");
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.id_toolbar_frag_main);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return rootView;
    }


    private View rootView;
}
