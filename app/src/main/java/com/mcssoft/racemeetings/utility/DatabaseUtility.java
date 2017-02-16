package com.mcssoft.racemeetings.utility;

import android.content.Context;
import android.net.Uri;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import com.mcssoft.racemeetings.database.DatabaseHelper;
import com.mcssoft.racemeetings.database.SchemaConstants;
import com.mcssoft.racemeetings.interfaces.IAsyncResponse;
import com.mcssoft.racemeetings.meeting.Club;
import com.mcssoft.racemeetings.meeting.Region;
import com.mcssoft.racemeetings.utility.DownloadData;
import com.mcssoft.racemeetings.utility.MeetingXMLParser;

/**
 * Utility class for database operations other than those of the MeetgingProvider/ContentResolver.
 */
public class DatabaseUtility implements IAsyncResponse {

    public DatabaseUtility(Context context) {
        this.context = context;
    }

    /**
     * Sanity check that database base tables have values (rows data).
     */
    public void databaseCheck() {

        databaseHelper = new DatabaseHelper(context);

        if(!databaseHelper.checkTableRowCount(SchemaConstants.REGIONS_TABLE)) {
            loadRegionsTableData();
        }

        // Note: this won't be called if there is no REGIONS data as the async task returns to
        // method processFinished().
        if(!databaseHelper.checkTableRowCount(SchemaConstants.CLUBS_TABLE)) {
            loadClubsTableData();
        }
    }

    /**
     * Async task results end up here.
     * @param theResults The results from the async task.
     */
    @Override
    public void processFinish(String theResults) {
        databaseHelper = new DatabaseHelper(context);
        InputStream inStream = new ByteArrayInputStream(theResults.getBytes());
        MeetingXMLParser mxmlp = new MeetingXMLParser(inStream);

        if(regions) {
            ArrayList<Region> regions = mxmlp.parseRegionsXml();
            databaseHelper.insertFromList(SchemaConstants.REGIONS_TABLE, regions);

            // If REGIONS data doesn't exist then it's likely CLUBS data won't exist either.
            if(!databaseHelper.checkTableRowCount(SchemaConstants.CLUBS_TABLE)) {
                loadClubsTableData();
            }
        } else if (clubs) {
            ArrayList<Club> clubs = mxmlp.parseClubsXml();
            databaseHelper.insertFromList(SchemaConstants.CLUBS_TABLE, clubs);
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

    private boolean regions;           // flag to indicate async results are for REGIONS
    private boolean clubs;             // flag to indicate async results are for CLUBS
    private Context context;
    private DatabaseHelper databaseHelper;
}
