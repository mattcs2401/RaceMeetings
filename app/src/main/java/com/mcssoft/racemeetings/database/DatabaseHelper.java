package com.mcssoft.racemeetings.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        RegionsSchema, ClubsScema
    }

    public static String [] getProjection(Projection projection) {
        switch (projection) {
            case RegionsSchema:
                return getRegionsSchemaProjection();
            case ClubsScema:
                return getClubsProjection();
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
            projection = getRegionsSchemaProjection();
        } else if(tableName == SchemaConstants.CLUBS_TABLE) {
            projection = getClubsProjection();
        }
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        Cursor cursor = db.query(tableName, projection, null, null, null, null, null);
        db.endTransaction();
        return (cursor.getCount() > 0);
    }

    private static final String[] getRegionsSchemaProjection() {
        return new String[] {
            SchemaConstants.REGIONS_ROWID,
            SchemaConstants.REGIONS_ID,
            SchemaConstants.REGIONS_NAME,
            SchemaConstants.REGIONS_S_NAME
        };
    }

    private static final String[] getClubsProjection() {
        return new String [] {
            SchemaConstants.CLUB_ROWID,
            SchemaConstants.CLUB_ID,
            SchemaConstants.CLUB_NAME
        };
    }

    private SQLiteDatabase db;
}