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

    /**
     * Utility method to see if rows exist in the given table.
     * @param tableName The table to check.
     * @return True if the row count > 0.
     */
    public boolean checkTableRowCount(String tableName) {
        String[] projection = {};
        if(tableName == SchemaConstants.REGIONS_TABLE) {
            projection = getProjection(Projection.RegionSchema);
        } else if(tableName == SchemaConstants.CLUBS_TABLE) {
            projection = getProjection(Projection.ClubSchema);
        }
        SQLiteDatabase db = getWritableDatabase();
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
        SQLiteDatabase db = getWritableDatabase();

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
        SQLiteDatabase db = getWritableDatabase();

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