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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.database.SchemaConstants;
import com.mcssoft.racemeetings.fragment.MeetingsDeleteFragment;
import com.mcssoft.racemeetings.fragment.MeetingsSearchFragment;
import com.mcssoft.racemeetings.interfaces.IDateSelect;
import com.mcssoft.racemeetings.database.DatabaseOperations;
import com.mcssoft.racemeetings.fragment.MainFragment;
import com.mcssoft.racemeetings.interfaces.IMeetingsDelete;
import com.mcssoft.racemeetings.utility.DownloadHelper;
import com.mcssoft.racemeetings.utility.Preferences;
import com.mcssoft.racemeetings.utility.Resources;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                   IDateSelect, IMeetingsDelete {

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    @Override
    protected void onCreate(Bundle inState) {
        super.onCreate(inState);

        Resources.getInstance(this);   // setup resources access.
        Preferences.getInstance(this); // setup preferences access.

        if(checkForNetwork()) {
            checkDatabaseTables();     // TBA - need a way to bypass this when onCreate again.
            if(Preferences.getInstance().getMeetingsShowToday()) {
                // go direct to showing today's meetings.
                loadMeetingsActivity(getDate());
            }
        } else {
            if(arguments == null) {
                arguments = new Bundle();
            }
            arguments.putBoolean(Resources.getInstance().getString(R.string.network_exists_key),false);
        }

        initialiseBaseUI();       // initialise UI components.
        loadFragment(inState);    // load the activity's associated MainFragment.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listing_toolbar_menu, menu);
        return true;
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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Listeners">
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.id_nav_menu_search:
                DialogFragment dateSearchFragment = new MeetingsSearchFragment();
                dateSearchFragment.show(getFragmentManager(),
                        Resources.getInstance().getString(R.string.date_search_fragment_tag));
                break;
            case R.id.id_nav_menu_meetings_today:
                loadMeetingsActivity(getDate());
                break;
            case R.id.id_nav_menu_clear_meetings:
                DialogFragment deleteFragment = new MeetingsDeleteFragment();
                deleteFragment.show(getFragmentManager(), null);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_preference_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Interface">
    /**
     * Implement IDateSelect interface.
     * @param values The date values; [0] YYYY, [1] MM, [2] DD.
     */
    @Override
    public void iDateValues(String[] values) {
        String searchdate = formatSearchDateValues(values);
        loadMeetingsActivity(searchdate);
    }

    /**
     * Implement the IMeetingsDelete interface.
     * @param rows The number of rows deleted.
     */
    @Override
    public void iMeetingsDelete(int[] rows) {
        Toast.makeText(this, Integer.toString(rows[0]) + " meeting entries deleted.", Toast.LENGTH_SHORT).show();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    private void loadMeetingsActivity(String searchDate) {
        DatabaseOperations dbOper = new DatabaseOperations(this);
        // TODO - logic flaw between the cache preference and the default show preference..
        if(Preferences.getInstance().getCacheMeetings()) {
            // Check if meetings with that date already exist.
            if(!dbOper.checkMeetingsByDate(searchDate)) {
                // No meetings of that date exist so download.
                getMeetingsByDate(searchDate);
            } else {
                // Meetings with that date exist.
                Intent intent = new Intent(this, MeetingsActivity.class);
                startActivity(intent);
            }
        } else {
            // Preference is not set so delete old meetings.
            dbOper.deleteAllFromTable(SchemaConstants.MEETINGS_TABLE);
            dbOper.deleteAllFromTable(SchemaConstants.RACES_TABLE);
            // Note: MeetingsActivity is launched as a result of this.
            getMeetingsByDate(searchDate);
        }
    }

    private void getMeetingsByDate(String searchDate) {
        // Note: MeetingsActivity is launched as a result of this.
        DownloadHelper downloadHelper = new DownloadHelper(this);
        downloadHelper.getMeetingsByDate(searchDate);
    }

    private void checkDatabaseTables() {
        DatabaseOperations dbOper = new DatabaseOperations(this);
        dbOper.checkClubs();
        dbOper.checkTracks();
    }

    private void initialiseBaseUI() {
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

    private void loadFragment(Bundle savedInstanceState) {
        String fragment_tag = Resources.getInstance().getString(R.string.main_fragment_tag);
        MainFragment mainFragment = new MainFragment();
        if(arguments != null) {
            mainFragment.setArguments(arguments);
        }

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
    //</editor-fold>

    private Bundle arguments;
}
