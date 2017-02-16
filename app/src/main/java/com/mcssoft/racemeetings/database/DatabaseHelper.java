package com.mcssoft.racemeetings.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mcssoft.racemeetings.meeting.Club;
import com.mcssoft.racemeetings.meeting.Region;

import java.util.ArrayList;

/**
 * Database utility class (basically used by the MeetingProvider and associated ContentResolver).
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
            db.execSQL(SchemaConstants.CREATE_REGIONS_TABLE);
            db.execSQL(SchemaConstants.CREATE_CLUBS_TABLE);
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
    }

    public SQLiteDatabase getDatabase() {
        return db;
    }

    public enum Projection {
        RegionSchema, ClubSchema
    }

    public static String [] getProjection(Projection projection) {
        switch (projection) {
            case RegionSchema:
                return getRegionProjection();
            case ClubSchema:
                return getClubProjection();
        }
        return  null;
    }

    private static final String[] getRegionProjection() {
        return new String[] {
            SchemaConstants.REGIONS_ROWID,
            SchemaConstants.REGIONS_ID,
            SchemaConstants.REGIONS_NAME,
            SchemaConstants.REGIONS_S_NAME
        };
    }

    private static final String[] getClubProjection() {
        return new String [] {
            SchemaConstants.CLUB_ROWID,
            SchemaConstants.CLUB_ID,
            SchemaConstants.CLUB_NAME
        };
    }

    private SQLiteDatabase db;
}