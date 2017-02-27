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

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.database.DatabaseHelper;
import com.mcssoft.racemeetings.database.SchemaConstants;
import com.mcssoft.racemeetings.interfaces.IAsyncResponse;
import com.mcssoft.racemeetings.meeting.Club;
import com.mcssoft.racemeetings.meeting.Region;
import com.mcssoft.racemeetings.meeting.Track;

/**
 * Utility class for database operations other than those of the MeetgingProvider/ContentResolver.
 */
public class DatabaseUtility implements IAsyncResponse {

    public DatabaseUtility(Context context) {
        this.context = context;
        checkTracks = false;
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Sanity check that database base tables have values (rows data).
     */
    public void databaseCheck() {
        // If REGIONS data doesn't exist. Note: it's likely CLUBS data won't exist either.
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
        XMLParser mxmlp = null;
        InputStream inStream = null;
        dbHelper = new DatabaseHelper(context);

        if(!checkTracks) {
            // Sort of work around as Track data is from raw resource.
            inStream = context.getResources().openRawResource(R.raw.tracks);
            mxmlp = new XMLParser(inStream);

            ArrayList<Track> tracks = mxmlp.parseTracksXml();
            insertFromList(SchemaConstants.TRACKS_TABLE, tracks);
            checkTracks = true;
        }

        inStream = new ByteArrayInputStream(theResults.getBytes());
        mxmlp = new XMLParser(inStream);

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
        return (getAllFromTable(tableName).getCount() > 0);
    }

    public void insertFromList(String tableName, ArrayList theList) {
        switch (tableName) {
            case SchemaConstants.REGIONS_TABLE:
                insertFromListRegions(theList);
                break;
            case SchemaConstants.CLUBS_TABLE:
                insertFromListClubs(theList);
                break;
            case SchemaConstants.TRACKS_TABLE:
                insertFromListTracks(theList);
                break;
        }
    }

    public Cursor getAllFromTable(String tableName) {
        String[] projection = getProjection(tableName);

        SQLiteDatabase db = dbHelper.getDatabase();
        db.beginTransaction();
        Cursor cursor = db.query(tableName, projection, null, null, null, null, null);
        db.endTransaction();

        return cursor;
    }

    /**
     * Utility wrapper method to query the database.
     * @param tableName The table name.
     * @param whereClause Where clause (without the "where").
     * @param whereVals Where clause values
     * @return A cursor over the result set.
     * Note: Returns all columns.
     */
    public Cursor getSelectionFromTable(String tableName, String whereClause, String[] whereVals) {
        return basicQuery(tableName, whereClause, whereVals);
    }

    /**
     * Utility method to update a single value in a single row.
     * @param tableName The table name.
     * @param where The where clause (without the "where").
     * @param rowId The table row id.
     * @param colName The table column name.
     * @param value The column value.
     * @return The update count.
     */
    public int updateTableByRowId(String tableName, String where, int rowId, String colName, String value) {
        SQLiteDatabase db = dbHelper.getDatabase();

        ContentValues cv = new ContentValues();
        cv.put(colName, value);

        db.beginTransaction();
        int counr = db.update(tableName, cv, where, new String[] {Integer.toString(rowId)});
        db.setTransactionSuccessful();
        db.endTransaction();

        return counr;
    }

    /**
     * Utility to create the '?' parameter part of an IN statement.
     * @param iterations The number of '?' characters to insert.
     * @return The formatted string e.g. " IN (?,?)".
     */
    public String createWhereIN(int iterations) {
        StringBuilder sb = new StringBuilder();
        sb.append(" IN (");
        for(int ndx = 0; ndx < iterations; ndx++) {
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length() - 1);   // remove last comma.
        sb.append(")");
        return sb.toString();
    }
    
    private void insertFromListRegions(ArrayList theList) {
        ContentValues cv;
        SQLiteDatabase db = dbHelper.getDatabase();

        for (Object object : theList) {
            Region region = (Region) object;
            cv = new ContentValues();
            cv.put(SchemaConstants.REGION_ID, region.getRegionId());
            cv.put(SchemaConstants.REGION_NAME, region.getRegionName());
            cv.put(SchemaConstants.REGION_S_NAME, region.getRegionSName());

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
        SQLiteDatabase db = dbHelper.getDatabase();

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

    private void insertFromListTracks(ArrayList theList) {
        ContentValues cv;
        SQLiteDatabase db = dbHelper.getDatabase();

        for (Object object : theList) {
            Track track = (Track) object;
            cv = new ContentValues();
            cv.put(SchemaConstants.TRACK_NAME, track.getTrackName());
            cv.put(SchemaConstants.TRACK_CLUB_NAME, track.getTrackClubName());
            cv.put(SchemaConstants.TRACK_IS_PREF, track.getTrackisPref());

            try {
                db.beginTransaction();
                db.insertOrThrow(SchemaConstants.TRACKS_TABLE, null, cv);
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
            DownloadData dld = new DownloadData(context, new URL(createRegionsUrl()),
                    Resources.getInstance().getString(R.string.init_regions_data));
            dld.asyncResponse = this;
            dld.execute();
        } catch(Exception ex) {
            String s = ex.getMessage();
        }
    }

    private void loadClubsTableData() {
        regions = false; clubs = true;
        try {
            DownloadData dld = new DownloadData(context, new URL(createClubsUrl()),
                    Resources.getInstance().getString(R.string.init_clubs_data));
            dld.asyncResponse = this;
            dld.execute();
        } catch(Exception ex) {
            String s = ex.getMessage();
        }
    }

    private String createRegionsUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.encodedPath(Resources.getInstance().getString(R.string.base_path_calendar))
                .appendPath(Resources.getInstance().getString(R.string.get_available_regions));
        builder.build();
        return builder.toString();
    }

    private String createClubsUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.encodedPath(Resources.getInstance().getString(R.string.base_path_calendar))
                .appendPath(Resources.getInstance().getString(R.string.get_available_clubs));
        builder.build();
        return builder.toString();
    }

    private String[] getProjection(String tableName) {
        String[] projection = {};
        switch (tableName) {
            case SchemaConstants.REGIONS_TABLE:
                projection = dbHelper.getProjection(DatabaseHelper.Projection.RegionSchema);
                break;
            case SchemaConstants.CLUBS_TABLE:
                projection = dbHelper.getProjection(DatabaseHelper.Projection.ClubSchema);
                break;
            case SchemaConstants.TRACKS_TABLE:
                projection = dbHelper.getProjection(DatabaseHelper.Projection.TrackSchema);
                break;
        }
        return  projection;
    }

    private Cursor basicQuery(String tableName, String whereClause, String[] whereVals) {
        SQLiteDatabase db = dbHelper.getDatabase();
        db.beginTransaction();
        Cursor cursor =  db.query(tableName, getProjection(tableName), whereClause, whereVals,
                null, null, null);
        db.endTransaction();
        return cursor;
    }

    private boolean clubs;        // flag to indicate async results are for CLUBS
    private boolean regions;      // flag to indicate async results are for REGIONS
    private boolean checkTracks;  // flag to indicate load TRACKS from raw resource.

    private Context context;
    private DatabaseHelper dbHelper;
}
