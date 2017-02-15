package com.mcssoft.racemeetings.database;

import android.content.Context;
import android.net.Uri;

import com.mcssoft.racemeetings.interfaces.IAsyncResponse;
import com.mcssoft.racemeetings.utility.DownloadData;

import java.net.URL;

public class DatabaseUtility
        implements IAsyncResponse {

    public DatabaseUtility(Context context) {
        this.context = context;
    }

    /**
     * Sanity check that database base tables have values (rows data).
     */
    public void databaseCheck() {

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        baseTables = databaseHelper.checkTableRowCount(SchemaConstants.REGIONS_TABLE);

        if(!baseTables) {
            // Databases have no data so get the REGIONS and CLUBS data.
            loadRegionsTableData();
            loadClubsTableData();
        } else {
            String bp = "";
        }
    }

    /**
     * Async task results end up here.
     * @param theResults The results from the async task.
     */
    @Override
    public void processFinish(String theResults) {
        if(regions) {
            regionsResults = theResults;

        } else if (clubs) {
            clubsResults = theResults;
        }
    }

    private void loadRegionsTableData() {
        regions = true; clubs = false;
        try {
            DownloadData dld = new DownloadData(context);
            dld.asyncResponse = this;
            dld.setUrl(new URL(createRegionsUrl()));
            dld.execute();
        } catch(Exception ex) {
            String s = ex.getMessage();
        }
    }

    private void loadClubsTableData() {
        regions = false; clubs = true;
        try {
            DownloadData dld = new DownloadData(context);
            dld.asyncResponse = this;
            dld.setUrl(new URL(createClubsUrl()));
            dld.execute();
        } catch(Exception ex) {
            String s = ex.getMessage();
        }
    }

    private String createRegionsUrl() {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("www.racingqueensland.com.au")
                .appendPath("opendatawebservices")
                .appendPath("calendar.asmx")
                .appendPath("GetAvailableRegions");
//                .appendQueryParameter("MeetingId","89226");
        builder.build();
        return builder.toString();
    }

    private String createClubsUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("www.racingqueensland.com.au")
                .appendPath("opendatawebservices")
                .appendPath("calendar.asmx")
                .appendPath("GetAvailableClubs");
//                .appendQueryParameter("MeetingId","89226");
        builder.build();
        return builder.toString();
    }

    private String regionsResults;
    private String clubsResults;
    private boolean baseTables;
    private boolean regions;
    private boolean clubs;
    private Context context;
}
