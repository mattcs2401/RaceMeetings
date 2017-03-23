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
import com.mcssoft.racemeetings.model.Club;
import com.mcssoft.racemeetings.model.Horse;
import com.mcssoft.racemeetings.model.Meeting;
import com.mcssoft.racemeetings.model.Race;
import com.mcssoft.racemeetings.model.Track;
import com.mcssoft.racemeetings.utility.DownloadHelper;
import com.mcssoft.racemeetings.utility.Preferences;
import com.mcssoft.racemeetings.utility.XMLParser;

/**
 * Utility class for database operations.
 */
public class DatabaseOperations {

    public DatabaseOperations(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Get the Clubs table data (first checks to see if records exist).
     */
    public void checkClubs() {
        if(!checkTableRowCount(SchemaConstants.CLUBS_TABLE)) {
            DownloadHelper downloadHelper = new DownloadHelper(context);
            downloadHelper.downloadTableData(SchemaConstants.CLUBS_TABLE, null);
        }
    }

    /**
     * Get the Tracks table data (first checks to see if records exist).
     */
    public void checkTracks() {
        if(!checkTableRowCount(SchemaConstants.TRACKS_TABLE)) {
            InputStream inStream = context.getResources().openRawResource(R.raw.tracks);
            XMLParser mxmlp = new XMLParser(inStream);
            ArrayList<Track> tracks = mxmlp.parseTracksXml();
            insertFromList(SchemaConstants.TRACKS_TABLE, tracks, 0);
        }
    }

    /**
     * Search the Meetings table based on the given parameter.
     * @param searchDate The Meeting's MeetingDate value.
     * @return True if records exist with the given date.
     */
    public boolean checkMeetingsByDate(String searchDate) {
        // Note: the column select is purely arbitary (only interested in row count).
        Cursor cursor = getSelectionFromTable(SchemaConstants.MEETINGS_TABLE,
                                              new String[] {SchemaConstants.MEETING_ID},
                                              SchemaConstants.WHERE_FOR_GET_MEETINGS_BY_DATE,
                                              new String[] {searchDate});
        cursor.moveToFirst();
        return (cursor.getCount() > 0);
    }

    /**
     * Get all the records in a table.
     * @param tableName The table name.
     * @return A cursor over the records.
     *         Note: No guarantee the cursor contains anything.
     */
    public Cursor getAllFromTable(String tableName) {
        SQLiteDatabase db = dbHelper.getDatabase();
        db.beginTransaction();
        Cursor cursor = db.query(tableName, getProjection(tableName), null, null, null, null, null);
        db.endTransaction();
        return cursor;
    }

    /**
     * Delete all the records in a table.
     * @param tableName The table name.
     * @return The number of rows deleted.
     */
    public int deleteAllFromTable(String tableName) {
        int rows = 0;
        if(checkTableRowCount(tableName)) {
            SQLiteDatabase db = dbHelper.getDatabase();
            db.beginTransaction();
            rows = db.delete(tableName, "1", null);
            db.setTransactionSuccessful();
            db.endTransaction();
        }
        return rows;
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
//        db.setTransactionSuccessful();
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

    public void insertFromList(String tableName, ArrayList theList, int identifier) {
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
            case SchemaConstants.RACES_TABLE:
                insertFromListRaces(theList, identifier);
                break;
            case SchemaConstants.RACE_DETAILS_TABLE:
                insertFromListRaceDetails(theList, identifier);
                break;
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
        // Note: MEETINGS table values are regenerated every time the list of meetings changes.
        if(theList.size() > 0) {
            ContentValues cv;
            SQLiteDatabase db = dbHelper.getDatabase();

            for (Object object : theList) {
                Meeting meeting = (Meeting) object;

                boolean exclBarrierTrial = Preferences.getInstance().getExcludeBarrierTrial();  // preference.
                boolean isBarrierTrial = meeting.getIsBarrierTrial().equals("true");            // in data.

                if (!exclBarrierTrial || (exclBarrierTrial && !isBarrierTrial)) {

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
    }

    private void insertFromListRaces(ArrayList theList, int meetingId) {
        if(theList.size() > 0) {
            ContentValues cv;
            db = dbHelper.getDatabase();

            if(!meetingIdExists(meetingId)) {  // only insert new races.

                for (Object object : theList) {
                    Race race = (Race) object;

                    cv = new ContentValues();
                    cv.put(SchemaConstants.RACE_ID, race.getRaceId());
                    cv.put(SchemaConstants.RACE_MEETING_ID, meetingId);
                    cv.put(SchemaConstants.RACE_NO, race.getRaceNumber());
                    cv.put(SchemaConstants.RACE_NAME, race.getRaceName());

                    String raceTime = race.getRaceTime();
                    if(raceTime == null) { raceTime = "00:00"; }
                    cv.put(SchemaConstants.RACE_TIME, raceTime);

                    cv.put(SchemaConstants.RACE_CLASS, race.getRaceClass());
                    cv.put(SchemaConstants.RACE_DISTANCE, race.getRaceDistance());

                    String trackRating = race.getRaceTrackRating();
                    if(trackRating == null) { trackRating = "No rating"; };
                    cv.put(SchemaConstants.RACE_TRACK_RATING, trackRating);

                    cv.put(SchemaConstants.RACE_PRIZE_TOTAL, race.getRacePrizeTotal());
                    cv.put(SchemaConstants.RACE_AGE_COND, race.getRaceAgeCondition());
                    cv.put(SchemaConstants.RACE_SEX_COND, race.getRaceSexCondtion());
                    cv.put(SchemaConstants.RACE_WEIGHT_COND, race.getRaceWeightCondition());
                    cv.put(SchemaConstants.RACE_APP_CLAIM, race.getRaceApprenticeClaim());
                    cv.put(SchemaConstants.RACE_START_FEE, race.getRaceStartersFee());
                    cv.put(SchemaConstants.RACE_ACCEPT_FEE, race.getRaceAcceptanceFee());

                    try {
                        db.beginTransaction();
                        db.insertOrThrow(SchemaConstants.RACES_TABLE, null, cv);
                        db.setTransactionSuccessful();
                    } catch (SQLException ex) {
                        Log.d("", ex.getMessage());
                    } finally {
                        db.endTransaction();
                    }
                }
            }
        }
    }

    private void insertFromListRaceDetails(ArrayList theList, int raceId) {
        if(theList.size() > 0) {
            ContentValues cv;
            db = dbHelper.getDatabase();

            if(!raceIdExists(raceId)) {  // only insert new race detailss.

                for (Object object : theList) {
                    Horse horse = (Horse) object;

                    cv = new ContentValues();
                    cv.put(SchemaConstants.RACE_DETAILS_RACE_ID, raceId);
                    cv.put(SchemaConstants.RACE_DETAILS_HORSE_ID, horse.getHorseId());
                    cv.put(SchemaConstants.RACE_DETAILS_HORSENAME, horse.getHorseName());
                    cv.put(SchemaConstants.RACE_DETAILS_WEIGHT, horse.getHorseWeight());
                    cv.put(SchemaConstants.RACE_DETAILS_JOCKEY_ID, horse.getJockeyId());
                    cv.put(SchemaConstants.RACE_DETAILS_JOCKEY_NAME, horse.getJockeyName());
                    cv.put(SchemaConstants.RACE_DETAILS_TRAINER_ID, horse.getTrainerId());
                    cv.put(SchemaConstants.RACE_DETAILS_TRAINER_NAME, horse.getTrainerName());

                    try {
                        db.beginTransaction();
                        db.insertOrThrow(SchemaConstants.RACE_DETAILS_TABLE, null, cv);
                        db.setTransactionSuccessful();
                    } catch (SQLException ex) {
                        Log.d("", ex.getMessage());
                    } finally {
                        db.endTransaction();
                    }
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
                projection = dbHelper.getProjection(DatabaseHelper.Projection.MeetingSchema);
                break;
        }
        return  projection;
    }

    private boolean meetingIdExists(int meetingId) {
        boolean retVal = false;
        Cursor cursor = getSelectionFromTable(SchemaConstants.RACES_TABLE, null,
                SchemaConstants.WHERE_FOR_GET_RACE_MEETINGID, new String[] { Integer.toString(meetingId)});
        if(cursor.getCount() > 0) {
            retVal = true;
        }
        return retVal;
    }

    private boolean raceIdExists(int raceId) {
        boolean retVal = false;
        Cursor cursor = getSelectionFromTable(SchemaConstants.RACE_DETAILS_TABLE, null,
                SchemaConstants.WHERE_FOR_GET_RACE_DETAILS_RACEID, new String[] { Integer.toString(raceId)});
        if(cursor.getCount() > 0) {
            retVal = true;
        }
        return retVal;
    }

    private Context context;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

}
