package com.mcssoft.racemeetings.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Database utility class.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, SchemaConstants.DATABASE_NAME, null, SchemaConstants.DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.beginTransaction();
        try {
            db.execSQL(SchemaConstants.DROP_CLUBS_TABLE);
            db.execSQL(SchemaConstants.DROP_TRACKS_TABLE);
            db.execSQL(SchemaConstants.DROP_MEETINGS_TABLE);
            db.execSQL(SchemaConstants.DROP_RACES_TABLE);
            db.execSQL(SchemaConstants.CREATE_CLUBS_TABLE);
            db.execSQL(SchemaConstants.CREATE_TRACKS_TABLE);
            db.execSQL(SchemaConstants.CREATE_MEETINGS_TABLE);
            db.execSQL(SchemaConstants.CREATE_RACES_TABLE);
            db.setTransactionSuccessful();
        } catch(SQLException sqle) {
            Log.d("Exception dB create: ", sqle.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SchemaConstants.DATABASE_NAME + "." + SchemaConstants.MEETINGS_TABLE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + SchemaConstants.DATABASE_NAME + "." + SchemaConstants.CLUBS_TABLE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + SchemaConstants.DATABASE_NAME + "." + SchemaConstants.TRACKS_TABLE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + SchemaConstants.DATABASE_NAME + "." + SchemaConstants.RACES_TABLE + ";");    }

    public SQLiteDatabase getDatabase() {
        return db;
    }

    public enum Projection {
        ClubSchema, TrackSchema, MeetingSchema, RaceSchema
    }

    public static String [] getProjection(Projection projection) {
        switch (projection) {
            case ClubSchema:
                return getClubProjection();
            case TrackSchema:
                return getTrackProjection();
            case MeetingSchema:
                return getMeetingsProjection();
            case RaceSchema:
                return getRacesProjection();
        }
        return  null;
    }

    private static final String[] getClubProjection() {
        return new String [] {
            SchemaConstants.CLUB_ROWID,
            SchemaConstants.CLUB_ID,
            SchemaConstants.CLUB_NAME
        };
    }

    private static final String[] getTrackProjection() {
        return new String [] {
            SchemaConstants.TRACK_ROWID,
            SchemaConstants.TRACK_NAME,
            SchemaConstants.TRACK_CLUB_NAME,
            SchemaConstants.TRACK_IS_PREF
        };
    }

    private static final String[] getMeetingsProjection() {
        return new String[] {
            SchemaConstants.MEETING_ROWID,
            SchemaConstants.MEETING_ID,
            SchemaConstants.MEETING_DATE,
            SchemaConstants.MEETING_TRACK,
            SchemaConstants.MEETING_CLUB,
            SchemaConstants.MEETING_STATUS,
            SchemaConstants.MEETING_NO_RACES,
            SchemaConstants.MEETING_IS_TRIAL
        };
    }

    private static final String[] getRacesProjection() {
        return new String[] {
            SchemaConstants.RACE_ROWID,
            SchemaConstants.RACE_ID,
            SchemaConstants.RACE_NO,
            SchemaConstants.RACE_NAME,
            SchemaConstants.RACE_TIME,
            SchemaConstants.RACE_CLASS,
            SchemaConstants.RACE_DISTANCE,
            SchemaConstants.RACE_TRACK_RATING,
            SchemaConstants.RACE_PRIZE_TOTAL,
            SchemaConstants.RACE_AGE_COND,
            SchemaConstants.RACE_SEX_COND,
            SchemaConstants.RACE_WEIGHT_COND,
            SchemaConstants.RACE_APP_CLAIM,
            SchemaConstants.RACE_START_FEE,
            SchemaConstants.RACE_ACCEPT_FEE
        };
    }

    private SQLiteDatabase db;
}