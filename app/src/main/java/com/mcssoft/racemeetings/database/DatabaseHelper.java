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
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "onCreate");

        db.beginTransaction();
        try {
            db.execSQL(SchemaConstants.DROP_REGIONS_TABLE);
            db.execSQL(SchemaConstants.DROP_CLUBS_TABLE);
            db.execSQL(SchemaConstants.CREATE_REGIONS_TABLE);
            db.execSQL(SchemaConstants.CREATE_CLUBS_TABLE);
            db.setTransactionSuccessful();
        } catch(SQLException sqle) {
            Log.d(LOG_TAG, "Exception thrown on database create: " + sqle.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(this.getClass().getCanonicalName(), "onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS " + SchemaConstants.DATABASE_NAME + "." + SchemaConstants.REGIONS_TABLE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + SchemaConstants.DATABASE_NAME + "." + SchemaConstants.CLUBS_TABLE + ";");    }

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

    private String LOG_TAG = this.getClass().getCanonicalName();
}