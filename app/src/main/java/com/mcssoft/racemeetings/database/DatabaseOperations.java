package com.mcssoft.racemeetings.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.database.DatabaseHelper;
import com.mcssoft.racemeetings.database.SchemaConstants;
import com.mcssoft.racemeetings.model.Club;
import com.mcssoft.racemeetings.model.Meeting;
import com.mcssoft.racemeetings.model.Track;
import com.mcssoft.racemeetings.utility.DownloadHelper;
import com.mcssoft.racemeetings.utility.XMLParser;

/**
 * Utility class for database operations.
 */
public class DatabaseOperations {

    public DatabaseOperations(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void checkClubs() {
        if(!checkTableRowCount(SchemaConstants.CLUBS_TABLE)) {
            DownloadHelper downloadHelper = new DownloadHelper(context);
            downloadHelper.downloadTableData(SchemaConstants.CLUBS_TABLE, null);
        }
    }

    public void checkTracks() {
        if(!checkTableRowCount(SchemaConstants.TRACKS_TABLE)) {
            InputStream inStream = context.getResources().openRawResource(R.raw.tracks);
            XMLParser mxmlp = new XMLParser(inStream);
            ArrayList<Track> tracks = mxmlp.parseTracksXml();
            insertFromList(SchemaConstants.TRACKS_TABLE, tracks);
        }
    }

    public Cursor getAllFromTable(String tableName) {
        SQLiteDatabase db = dbHelper.getDatabase();
        db.beginTransaction();
        Cursor cursor = db.query(tableName, getProjection(tableName), null, null, null, null, null);
        db.endTransaction();

        return cursor;
    }

    /**
     * Utility wrapper method to query the database.
     * @param tableName The table name.
     * @param columnNames The table columns required (Null equals all columns).
     * @param whereClause Where clause (without the "where").
     * @param whereVals Where clause values
     * @return A cursor over the result set.
     * Note: Returns all columns.
     */
    public Cursor getSelectionFromTable(String tableName, @Nullable String[] columnNames, String whereClause, String[] whereVals) {
        if(columnNames == null) {
            columnNames = getProjection(tableName);
        }
        SQLiteDatabase db = dbHelper.getDatabase();
        db.beginTransaction();
        Cursor cursor =  db.query(tableName, columnNames, whereClause, whereVals,
                null, null, null);
        db.endTransaction();
        return cursor;
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

    public void insertFromList(String tableName, ArrayList theList) {
        switch (tableName) {
            case SchemaConstants.CLUBS_TABLE:
                insertFromListClubs(theList);
                break;
            case SchemaConstants.TRACKS_TABLE:
                insertFromListTracks(theList);
                break;
            case SchemaConstants.MEETINGS_TABLE:
                insertFromListMeetings(theList);
                break;
        }
    }

    public void checkAndDeleteOld(String tableName) {
        if(checkTableRowCount(tableName)) {
            SQLiteDatabase db = dbHelper.getDatabase();
            db.beginTransaction();
            int count = db.delete(tableName, null, null);
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

    /**
     * Utility method to see if rows exist in the given table.
     * @param tableName The table to check.
     * @return True if the row count > 0.
     */
    private boolean checkTableRowCount(String tableName) {
        return (getAllFromTable(tableName).getCount() > 0);
    }

    private void insertFromListClubs(ArrayList theList) {
        if(theList.size() > 0) {
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
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    db.endTransaction();
                }
            }
        }
    }

    private void insertFromListTracks(ArrayList theList) {
        if(theList.size() > 0) {
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
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    db.endTransaction();
                }
            }
        }
    }

    private void insertFromListMeetings(ArrayList theList) {
        if(theList.size() > 0) {
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
                } catch (SQLException ex) {
                    Log.d("", ex.getMessage());
                } finally {
                    db.endTransaction();
                }
            }
        }
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
                projection = dbHelper.getProjection(DatabaseHelper.Projection.MeetingsSchema);
                break;
        }
        return  projection;
    }


    private Context context;
    private DatabaseHelper dbHelper;

}
