package com.mcssoft.racemeetings.database;

public class SchemaConstants {

    // Content Provider Uri and Authority
//    public static final String MEETINGS = "meetings";
//    public static final String BASE = "com.mcssoft.racemeeting.";
//    public static final String AUTHORITY = BASE + "database.MeetingsProvider";
//    public static final String CONTENT_URI = "content://" + AUTHORITY + "/" + MEETINGS;
//    public static final String CURSOR_BASE_TYPE = "/" + BASE + MEETINGS;
//
//    // UriMatcher stuff
//    public static final int MEETINGS_TABLE = 0;
//    public static final int MEETINGS_RECORD = 1;

    // Database version and names.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "RACEMEETINGS";
    public static final String CLUBS_TABLE = "CLUBS";
    public static final String TRACKS_TABLE = "TRACKS";
    public static final String MEETINGS_TABLE = "MEETINGS";

    // Database columns CLUBS table.
    public static final String CLUB_ROWID = "_id";
    public static final String CLUB_ID = "CLUB_ID";
    public static final String CLUB_NAME = "CLUB_NAME";

    // Database columns TRACKS table.
    public static final String TRACK_ROWID = "_id";
    public static final String TRACK_NAME = "TRACK_NAME";
    public static final String TRACK_CLUB_NAME = "CLUB_NAME";
    public static final String TRACK_IS_PREF = "TRACK_IS_PREF";

    // Database columns MEETINGS table.
    public static final String MEETING_ROWID = "_id";
    public static final String MEETING_ID = "MEETING_ID";
    public static final String MEETING_DATE = "MEETING_DATE";
    public static final String MEETING_TRACK = "MEETING_TRACK";
    public static final String MEETING_CLUB = "MEETING_CLUB";
    public static final String MEETING_STATUS = "MEETING_STATUS";
    public static final String MEETING_NO_RACES = "MEETING_NO_RACES";
    public static final String MEETING_IS_TRIAL = "MEETING_IS_TRIAL";

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
            + TRACK_CLUB_NAME + " TEXT NOT NULL, "
            + TRACK_IS_PREF   + " TEXT NOT NULL)";

    // Meetings table create.
    public static final String CREATE_MEETINGS_TABLE = "CREATE TABLE "
            + MEETINGS_TABLE   + " ("
            + MEETING_ROWID    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MEETING_ID       + " INTEGER NOT NULL, "
            + MEETING_DATE     + " TEXT NOT NULL, "
            + MEETING_TRACK    + " TEXT NOT NULL, "
            + MEETING_CLUB     + " TEXT NOT NULL, "
            + MEETING_STATUS   + " TEXT NOT NULL, "
            + MEETING_NO_RACES + " TEXT NOT NULL, "
            + MEETING_IS_TRIAL + " TEXT NOT NULL)";


    public  static final String DROP_CLUBS_TABLE = "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + CLUBS_TABLE + ";";
    public  static final String DROP_TRACKS_TABLE = "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + TRACKS_TABLE + ";";
    public  static final String DROP_MEETINGS_TABLE = "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + MEETINGS_TABLE + ";";

    // marries with DatabaseHelper.getMeetingListItemProjection.
    public static final String SELECT_ALL_CLUBS = "SELECT * FROM " + CLUBS_TABLE;
    public static final String SELECT_ALL_TRACKS = "SELECT * FROM " + TRACKS_TABLE;
    public static final String SELECT_ALL_MEETINGS = "SELECT * FROM " + MEETINGS_TABLE;

    public static final String WHERE_FOR_TRACK_UPDATE = SchemaConstants.TRACK_ROWID + " = ?;";

}
