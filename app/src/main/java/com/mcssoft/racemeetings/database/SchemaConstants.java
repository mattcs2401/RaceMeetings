package com.mcssoft.racemeetings.database;

public class SchemaConstants {

    // Content Provider Uri and Authority
    public static final String MEETINGS = "meetings";
    public static final String BASE = "com.mcssoft.racemeeting.";
    public static final String AUTHORITY = BASE + "database.MeetingsProvider";
    public static final String CONTENT_URI = "content://" + AUTHORITY + "/" + MEETINGS;
    public static final String CURSOR_BASE_TYPE = "/" + BASE + MEETINGS;

    // UriMatcher stuff
    public static final int MEETINGS_TABLE = 0;
    public static final int MEETINGS_RECORD = 1;

    // Database columns REGIONS table.
    public static final String REGIONS_ROWID = "_id";
    public static final String REGIONS_ID = "REGIONSID";
    public static final String REGIONS_NAME = "REGIONNAME";
    public static final String REGIONS_S_NAME = "REGIONSHORTNAME";

    // Database columns CLUBS table.
    public static final String CLUB_ROWID = "_id";
    public static final String CLUB_ID = "CLUBSD";
    public static final String CLUB_NAME = "CLUBNAME";

    // Database version and names.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "RACEMEETINGS";
    public static final String REGIONS_TABLE = "REGIONS";
    public static final String CLUBS_TABLE = "CLUBS";

    // Database table create.
    public static final String CREATE_REGIONS_TABLE = "CREATE TABLE "
            + REGIONS_TABLE  + " ("
            + REGIONS_ROWID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + REGIONS_ID     + " INTEGER NOT NULL, "
            + REGIONS_NAME   + " TEXT NOT NULL, "
            + REGIONS_S_NAME + " TEXT NOT NULL)";

    public static final String SELECT_REGIONS = "SELECT "
            + REGIONS_ROWID + ","
            + REGIONS_ID    + ","
            + REGIONS_NAME  + ","
            + REGIONS_S_NAME + " FROM REGIONS;";

    // Database table create.
    public static final String CREATE_CLUBS_TABLE = "CREATE TABLE "
            + CLUBS_TABLE  + " ("
            + CLUB_ROWID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CLUB_ID     + " INTEGER NOT NULL, "
            + CLUB_NAME   + " TEXT NOT NULL)";

    public  static final String DROP_REGIONS_TABLE = "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + REGIONS_TABLE + ";";
    public  static final String DROP_CLUBS_TABLE = "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + CLUBS_TABLE + ";";

//    public static final String SORT_ORDER = COLUMN_DATE_TIME + " ASC, " + COLUMN_RACE_SEL;

    // marries with DatabaseHelper.getMeetingListItemProjection.
    public static final String SELECT_ALL_REGIONS = "SELECT * FROM " + REGIONS_TABLE;
    public static final String SELECT_ALL_CLUBS = "SELECT * FROM " + CLUBS_TABLE;

    // Where a display change is required.
//    public final static String WHERE_FOR_DCHANGE = COLUMN_D_CHG_REQ + " = ? AND " +
//                                                   COLUMN_DATE_TIME + " < ?";

    // where for meetings to notify.
//    public static final String WHERE_FOR_NOTIFY = COLUMN_NOTIFIED + "='N'";

}
