package com.mcssoft.racemeetings.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.fragment.MainFragment;
import com.mcssoft.racemeetings.utility.Preferences;
import com.mcssoft.racemeetings.utility.Resources;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialise();

        String fragment_tag = Resources.getInstance().getString(R.string.main_fragment_tag);

        if(savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.content_main, mainFragment, fragment_tag)
                    .addToBackStack(null)
                    .commit();
        } else {
            // TBA ...
            mainFragment = (MainFragment) getFragmentManager()
                    .getFragment(savedInstanceState, fragment_tag);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.listing_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Log.d(LOG_TAG, "onOptionsItemSelected");

        switch (item.getItemId()) {
//            case R.id.toolbar_menu_insert:
//                onEditMeeting(R.integer.new_meeting);
//                break;
//            case R.id.toolbar_preference_settings:
//                startActivity(new Intent(this, SettingsActivity.class));
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Resources.getInstance().destroy();
        Preferences.getInstance().destroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch(id) {
            case R.id.id_nav_menu_1:
                break;
            case R.id.id_nav_menu_2:
                break;
            case R.id.id_nav_menu_3:
                break;
            case R.id.id_nav_menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
//        if (id == R.id.id_nav_menu_1) {
//
//        } else if (id == R.id.id_nav_menu_2) {
//
//        } else if (id == R.id.id_nav_menu_3) {
//
//        } else if (id == R.id.id_nav_menu_settings) {
//            startActivity(new Intent(this, SettingsActivity.class));
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean checkForNetwork() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void initialise() {
        setContentView(R.layout.activity_main);
        mainFragment = new MainFragment();
        Resources.getInstance(this);
        Preferences.getInstance(this);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar_frag_main);
//        setSupportActionBar(toolbar);
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private MainFragment mainFragment;
}
