package com.mcssoft.racemeetings.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.database.DatabaseHelper;
import com.mcssoft.racemeetings.database.SchemaConstants;
import com.mcssoft.racemeetings.interfaces.IAsyncResult;
import com.mcssoft.racemeetings.meeting.Club;
import com.mcssoft.racemeetings.meeting.Meeting;
import com.mcssoft.racemeetings.meeting.Track;

/**
 * Utility class for database operations other than those of the MeetgingProvider/ContentResolver.
 */
public class DatabaseUtility implements IAsyncResult {

    public DatabaseUtility(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void checkClubs() {
        if(!checkTableRowCount(SchemaConstants.CLUBS_TABLE)) {
            downloadTableData(SchemaConstants.CLUBS_TABLE, null);
        }
    }

    public void checkTracks() {
        if(!checkTableRowCount(SchemaConstants.TRACKS_TABLE)) {
            downloadTableData(SchemaConstants.TRACKS_TABLE, null);
        }
    }

    /**
     * Async task results end up here.
     * @param results The results from the async task.
     */
    @Override
    public void result(String table, String results) {
        InputStream inStream = null;
        XMLParser mxmlp = null;

        switch (table) {
            case SchemaConstants.CLUBS_TABLE:
                inStream = new ByteArrayInputStream(results.getBytes());
                mxmlp = new XMLParser(inStream);
                ArrayList<Club> clubs = mxmlp.parseClubsXml();
                insertFromList(SchemaConstants.CLUBS_TABLE, clubs);
                break;
            case SchemaConstants.TRACKS_TABLE:
                inStream = context.getResources().openRawResource(R.raw.tracks);
                mxmlp = new XMLParser(inStream);
                ArrayList<Track> tracks = mxmlp.parseTracksXml();
                insertFromList(SchemaConstants.TRACKS_TABLE, tracks);
                break;
            case SchemaConstants.MEETINGS_TABLE:
                inStream = new ByteArrayInputStream(results.getBytes());
                mxmlp = new XMLParser(inStream);
                ArrayList<Meeting> meetings = mxmlp.parseMeetingsXml();
                insertFromList(SchemaConstants.MEETINGS_TABLE, meetings);
                break;
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
            case SchemaConstants.CLUBS_TABLE:
                insertFromListClubs(theList);
                break;
            case SchemaConstants.TRACKS_TABLE:
                insertFromListTracks(theList);
                break;
            case SchemaConstants.MEETINGS_TABLE:
                checkAndDeleteOld(tableName);    // meetings data doesn't persist.
                insertFromListMeetings(theList);
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

    private void insertFromListMeetings(ArrayList theList) {
        ContentValues cv;
        SQLiteDatabase db = dbHelper.getDatabase();

        for (Object object : theList) {
            Meeting meeting = (Meeting) object;
            cv = new ContentValues();
            cv.put(SchemaConstants.MEETING_ID, meeting.getMeetingId());
            cv.put(SchemaConstants.MEETING_DATE, meeting.getMeetingDate());
            cv.put(SchemaConstants.MEETING_TRACK, meeting.getTrackName());
            cv.put(SchemaConstants.MEETING_CLUB, meeting.getClubName());
            cv.put(SchemaConstants.MEETING_STATUS, meeting.getRacingStatus());
            cv.put(SchemaConstants.MEETING_NO_RACES, meeting.getNumberOfRaces());
            cv.put(SchemaConstants.MEETING_IS_TRIAL, meeting.getIsBarrierTrial());

            try {
                db.beginTransaction();
                db.insertOrThrow(SchemaConstants.MEETINGS_TABLE, null, cv);
                db.setTransactionSuccessful();
            } catch(SQLException ex) {
                Log.d("", ex.getMessage());
            } finally {
                db.endTransaction();
            }
        }
    }

    private void downloadTableData(String table, @Nullable String queryParam) {
        URL url = null;
        String message = null;
        DownloadData dld;

        try {
            switch (table) {
                case SchemaConstants.CLUBS_TABLE:
                    url = new URL(createClubsUrl());
                    message = Resources.getInstance().getString(R.string.init_clubs_data);
                    break;
                case SchemaConstants.TRACKS_TABLE:
                    url = new URL(createTracksUrl());
                    message = Resources.getInstance().getString(R.string.init_tracks_data);
                    break;
                case SchemaConstants.MEETINGS_TABLE:
                    url = new URL(createMeetingsUrl(queryParam));
                    message = Resources.getInstance().getString(R.string.init_meetings_data);
                    break;
            }
            dld = new DownloadData(context, url, message, table);
            dld.asyncResult = this;
            dld.execute();

        } catch(Exception ex) {
            Log.d("", ex.getMessage());
        }
    }

    private String createClubsUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.encodedPath(Resources.getInstance().getString(R.string.base_path_calendar))
                .appendPath(Resources.getInstance().getString(R.string.get_available_clubs));
        builder.build();
        return builder.toString();
    }

    private String createMeetingsUrl(String queryParam) {
        Uri.Builder builder = new Uri.Builder();
        builder.encodedPath(Resources.getInstance().getString(R.string.base_path_meetings))
               .appendPath(Resources.getInstance().getString(R.string.get_meetings_for_date))
               .appendQueryParameter(Resources.getInstance().getString(R.string.meeting_date), queryParam);

        builder.build();
        return builder.toString();
    }

    private String createTracksUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.encodedPath(Resources.getInstance().getString(R.string.base_path_meetings));
        builder.build();
        return builder.toString();
    }

    private String[] getProjection(String tableName) {
        String[] projection = {};
        switch (tableName) {
            case SchemaConstants.CLUBS_TABLE:
                projection = dbHelper.getProjection(DatabaseHelper.Projection.ClubSchema);
                break;
            case SchemaConstants.TRACKS_TABLE:
                projection = dbHelper.getProjection(DatabaseHelper.Projection.TrackSchema);
                break;
            case SchemaConstants.MEETINGS_TABLE:
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

    private void checkAndDeleteOld(String tableName) {
        if(checkTableRowCount(tableName)) {
            SQLiteDatabase db = dbHelper.getDatabase();
            db.beginTransaction();
            db.delete(tableName, null, null);
            db.endTransaction();
        }
    }

    private Context context;
    private DatabaseHelper dbHelper;
}
