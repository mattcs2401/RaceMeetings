package com.mcssoft.racemeetings.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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
        databaseHelper = new DatabaseHelper(context);
    }

    /**
     * Sanity check that database base tables have values (rows data).
     */
    public void databaseCheck() {
        if(!checkTableRowCount(SchemaConstants.REGIONS_TABLE)) {
            loadRegionsTableData();
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
            insertFromList(SchemaConstants.REGIONS_TABLE, regions);

            // If REGIONS data doesn't exist then it's likely CLUBS data won't exist either.
            if(!checkTableRowCount(SchemaConstants.CLUBS_TABLE)) {
                loadClubsTableData();
            }
        } else if (clubs) {
            ArrayList<Club> clubs = mxmlp.parseClubsXml();
            insertFromList(SchemaConstants.CLUBS_TABLE, clubs);
        }
    }

    /**
     * Utility method to see if rows exist in the given table.
     * @param tableName The table to check.
     * @return True if the row count > 0.
     */
    public boolean checkTableRowCount(String tableName) {
        String[] projection = {};
        if(tableName == SchemaConstants.REGIONS_TABLE) {
            projection = databaseHelper.getProjection(DatabaseHelper.Projection.RegionSchema);
        } else if(tableName == SchemaConstants.CLUBS_TABLE) {
            projection = databaseHelper.getProjection(DatabaseHelper.Projection.ClubSchema);
        }
        SQLiteDatabase db = databaseHelper.getDatabase();
        db.beginTransaction();
        Cursor cursor = db.query(tableName, projection, null, null, null, null, null);
        db.endTransaction();
        return (cursor.getCount() > 0);
    }

    public void insertFromList(String tableName, ArrayList theList) {
        if(tableName.equals(SchemaConstants.REGIONS_TABLE)) {
            insertFromListRegions(theList);
        } else if (tableName.equals(SchemaConstants.CLUBS_TABLE)) {
            insertFromListClubs(theList);
        }
    }

    private void insertFromListRegions(ArrayList theList) {
        ContentValues cv;
        SQLiteDatabase db = databaseHelper.getDatabase();

        for (Object object : theList) {
            Region region = (Region) object;
            cv = new ContentValues();
            cv.put(SchemaConstants.REGIONS_ID, region.getRegionId());
            cv.put(SchemaConstants.REGIONS_NAME, region.getRegionName());
            cv.put(SchemaConstants.REGIONS_S_NAME, region.getRegionSName());

            try {
                db.beginTransaction();
                db.insertOrThrow(SchemaConstants.REGIONS_TABLE, null, cv);
                db.setTransactionSuccessful();
            } catch(SQLException ex) {
                ex.printStackTrace();
            } finally {
                db.endTransaction();
            }
        }
    }

    private void insertFromListClubs(ArrayList theList) {
        ContentValues cv;
        SQLiteDatabase db = databaseHelper.getDatabase();

        for (Object object : theList) {
            Club club = (Club) object;
            cv = new ContentValues();
            cv.put(SchemaConstants.CLUB_ID, club.getClubId());
            cv.put(SchemaConstants.CLUB_NAME, club.getClubName());

            try {
                db.beginTransaction();
                db.insertOrThrow(SchemaConstants.CLUBS_TABLE, null, cv);
                db.setTransactionSuccessful();
            } catch(SQLException ex) {
                ex.printStackTrace();
            } finally {
                db.endTransaction();
            }
        }
    }

    private void loadRegionsTableData() {
        regions = true; clubs = false;
        try {
            DownloadData dld = new DownloadData(context, new URL(createRegionsUrl()));
            dld.asyncResponse = this;
            dld.execute();
        } catch(Exception ex) {
            String s = ex.getMessage();
        }
    }

    private void loadClubsTableData() {
        regions = false; clubs = true;
        try {
            DownloadData dld = new DownloadData(context, new URL(createClubsUrl()));
            dld.asyncResponse = this;
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
