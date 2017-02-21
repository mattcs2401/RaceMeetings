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
            db.execSQL(SchemaConstants.DROP_REGIONS_TABLE);
            db.execSQL(SchemaConstants.DROP_CLUBS_TABLE);
            db.execSQL(SchemaConstants.DROP_TRACKS_TABLE);
            db.execSQL(SchemaConstants.CREATE_REGIONS_TABLE);
            db.execSQL(SchemaConstants.CREATE_CLUBS_TABLE);
            db.execSQL(SchemaConstants.CREATE_TRACKS_TABLE);
            db.setTransactionSuccessful();
        } catch(SQLException sqle) {
            Log.d("Exception dB create: ", sqle.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SchemaConstants.DATABASE_NAME + "." + SchemaConstants.REGIONS_TABLE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + SchemaConstants.DATABASE_NAME + "." + SchemaConstants.CLUBS_TABLE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + SchemaConstants.DATABASE_NAME + "." + SchemaConstants.TRACKS_TABLE + ";");
    }

    public SQLiteDatabase getDatabase() {
        return db;
    }

    public enum Projection {
        RegionSchema, ClubSchema, TrackSchema
    }

    public static String [] getProjection(Projection projection) {
        switch (projection) {
            case RegionSchema:
                return getRegionProjection();
            case ClubSchema:
                return getClubProjection();
            case TrackSchema:
                return getTrackProjection();
        }
        return  null;
    }

    private static final String[] getRegionProjection() {
        return new String[] {
            SchemaConstants.REGION_ROWID,
            SchemaConstants.REGION_ID,
            SchemaConstants.REGION_NAME,
            SchemaConstants.REGION_S_NAME
        };
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
                SchemaConstants.TRACK_CLUB_NAME
        };
    }

    private SQLiteDatabase db;
}