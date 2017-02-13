package com.mcssoft.racemeetings.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.fragment.MainFragment;
import com.mcssoft.racemeetings.utility.DownloadData;
import com.mcssoft.racemeetings.utility.Preferences;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialise();

        if(savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.listing_container, mainFragment, "main_fragment_tag")
                    .addToBackStack(null)
                    .commit();
        } else {
            // TBA ...
            mainFragment = (MainFragment) getFragmentManager()
                    .getFragment(savedInstanceState,"main_fragment_tag");
        }
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_main);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listing_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Log.d(LOG_TAG, "onOptionsItemSelected");

        switch (item.getItemId()) {
//            case R.id.toolbar_menu_insert:
//                onEditMeeting(R.integer.new_meeting);
//                break;
            case R.id.toolbar_preference_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        textView = (TextView) findViewById(R.id.id_textview);
//        button = (Button) findViewById(R.id.id_button);
//        button.setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View view) {
//        DownloadData dld = new DownloadData(textView,this);
//        dld.execute();
        //String str = dld.get();

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
        Preferences.getInstance(this);
    }

    private MainFragment mainFragment;
    private TextView textView;
    private Button button;
}
