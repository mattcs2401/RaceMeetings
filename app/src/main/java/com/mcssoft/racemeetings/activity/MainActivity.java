package com.mcssoft.racemeetings.activity;

import android.app.DialogFragment;
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
import android.view.MenuItem;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.fragment.DateSearchFragment;
import com.mcssoft.racemeetings.interfaces.IDateSelect;
import com.mcssoft.racemeetings.database.DatabaseOperations;
import com.mcssoft.racemeetings.fragment.MainFragment;
import com.mcssoft.racemeetings.utility.DownloadHelper;
import com.mcssoft.racemeetings.utility.Preferences;
import com.mcssoft.racemeetings.utility.Resources;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                   IDateSelect {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources.getInstance(this);        // setup resources access.
        Preferences.getInstance(this);      // setup preferences access.

        netWorkExists = checkForNetwork();  // does an active network exist?
        if(netWorkExists) {
            // data check.
            DatabaseOperations dbUtil = new DatabaseOperations(this);
            dbUtil.checkClubs();
            dbUtil.checkTracks();
        } else {
            // no active network connection exists.
            // TODO - no active network connection on app start.
        }

        initialiseUI();                     // initialise UI components.

        // Setup main fragment.
        String fragment_tag = Resources.getInstance().getString(R.string.main_fragment_tag);
        MainFragment mainFragment = new MainFragment();
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
        int id = item.getItemId();
        switch(id) {
            case R.id.id_nav_menu_search:
                DialogFragment dateSearchFragment = new DateSearchFragment();
                dateSearchFragment.show(getFragmentManager(),
                        Resources.getInstance().getString(R.string.date_search_fragment_tag));
                break;
            case R.id.id_nav_menu_meetings_today:
                loadMeetingsActivityByProxy(getDate());
                break;
            case R.id.id_nav_menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Implement IDateSelect interface.
     * @param values The date values; [0] YYYY, [1] MM, [2] DD.
     */
    @Override
    public void iDateValues(String[] values) {
        String searchdate = formatSearchDateValues(values);;
        loadMeetingsActivityByProxy(searchdate);
    }

    private void loadMeetingsActivityByProxy(String searchDate) {
        DatabaseOperations dbUtil = new DatabaseOperations(this);

        // Note: MeetingsActivity is launched as a result of this.
        DownloadHelper downloadHelper = new DownloadHelper(this);
        downloadHelper.getMeetingsBydate(searchDate);
    }

    private void initialiseUI() {
        setContentView(R.layout.content_view_main_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar_main);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Check the network type in the Preferences against the actual active network type.
     * @return True if the Preferences network type is the same as the actual active network type.
     */
    private boolean checkForNetwork() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected());
    }

    private String formatSearchDateValues(String[] values) {
        StringBuilder sb = new StringBuilder();
        sb.append(values[0])
        .append("-")
        .append(values[1])
        .append("-")
        .append(values[2]);
        return sb.toString();
    }

    private String getDate() {
        return new SimpleDateFormat(Resources.getInstance()
                .getString(R.string.date_format_yyyyMMdd), Locale.getDefault()).format(new Date());
    }

    private boolean netWorkExists;          // flag to indicate if an available network exists.

}
