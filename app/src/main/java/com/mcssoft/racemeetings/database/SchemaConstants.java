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

    // Database version and names.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "RACEMEETINGS";
    public static final String REGIONS_TABLE = "REGIONS";
    public static final String CLUBS_TABLE = "CLUBS";
    public static final String TRACKS_TABLE = "TRACKS";

    // Database columns REGIONS table.
    public static final String REGION_ROWID = "_id";
    public static final String REGION_ID = "REGIONSID";
    public static final String REGION_NAME = "REGIONNAME";
    public static final String REGION_S_NAME = "REGIONSHORTNAME";

    // Database columns CLUBS table.
    public static final String CLUB_ROWID = "_id";
    public static final String CLUB_ID = "CLUBSD";
    public static final String CLUB_NAME = "CLUBNAME";

    // Database columns TRACKS table.
    public static final String TRACK_ROWID = "_id";
    public static final String TRACK_NAME = "TRACKNAME";
    public static final String TRACK_CLUB_NAME = "CLUBNAME";

    // Regions table create.
    public static final String CREATE_REGIONS_TABLE = "CREATE TABLE "
            + REGIONS_TABLE  + " ("
            + REGION_ROWID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + REGION_ID     + " INTEGER NOT NULL, "
            + REGION_NAME   + " TEXT NOT NULL, "
            + REGION_S_NAME + " TEXT NOT NULL)";

    // Clubs table create.
    public static final String CREATE_CLUBS_TABLE = "CREATE TABLE "
            + CLUBS_TABLE     + " ("
            + CLUB_ROWID      + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CLUB_ID         + " INTEGER NOT NULL, "
            + CLUB_NAME       + " TEXT NOT NULL)";

    // Tracks table create.
    public static final String CREATE_TRACKS_TABLE = "CREATE TABLE "
            + TRACKS_TABLE    + " ("
            + TRACK_ROWID     + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TRACK_NAME      + " TEXT NOT NULL, "
            + TRACK_CLUB_NAME + " TEXT)";

    public  static final String DROP_REGIONS_TABLE = "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + REGIONS_TABLE + ";";
    public  static final String DROP_CLUBS_TABLE = "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + CLUBS_TABLE + ";";
    public  static final String DROP_TRACKS_TABLE = "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + TRACKS_TABLE + ";";

    // marries with DatabaseHelper.getMeetingListItemProjection.
    public static final String SELECT_ALL_REGIONS = "SELECT * FROM " + REGIONS_TABLE;
    public static final String SELECT_ALL_CLUBS = "SELECT * FROM " + CLUBS_TABLE;
    public static final String SELECT_ALL_TRACKS = "SELECT * FROM " + TRACKS_TABLE;

    // Where a display change is required.
//    public final static String WHERE_FOR_DCHANGE = COLUMN_D_CHG_REQ + " = ? AND " +
//                                                   COLUMN_DATE_TIME + " < ?";

    // where for meetings to notify.
//    public static final String WHERE_FOR_NOTIFY = COLUMN_NOTIFIED + "='N'";

}
