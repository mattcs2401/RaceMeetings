package com.mcssoft.racemeetings.database;

public class SchemaConstants {
    // Database version and names.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "RACEMEETINGS";
    public static final String CLUBS_TABLE = "CLUBS";
    public static final String TRACKS_TABLE = "TRACKS";
    public static final String MEETINGS_TABLE = "MEETINGS";
    public static final String RACES_TABLE = "RACES";
    public static final String RACE_DETAILS_TABLE = "RACE_DETAILS";

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

    // Database columns RACES table.
    public static final String RACE_ROWID = "_id";
    public static final String RACE_ID = "RACE_ID";
    public static final String RACE_MEETING_ID = "RACE_MEETING_ID";
    public static final String RACE_NO = "RACE_NO";
    public static final String RACE_NAME = "RACE_NAME";
    public static final String RACE_TIME = "RACE_TIME";
    public static final String RACE_CLASS = "RACE_CLASS";
    public static final String RACE_DISTANCE = "RACE_DISTANCE";
    public static final String RACE_TRACK_RATING = "RACE_TRACK_RATING";
    public static final String RACE_PRIZE_TOTAL = "RACE_PRIZE";
    public static final String RACE_BONUS_TYPE = "BONUS_TYPE";
    public static final String RACE_BONUS_TOTAL = "BONUS_TOTAL";
    public static final String RACE_AGE_COND = "RACE_AGE_COND";
    public static final String RACE_SEX_COND = "RACE_SEX_COND";
    public static final String RACE_WEIGHT_COND = "RACE_WEIGHT_COND";
    public static final String RACE_APP_CLAIM = "RACE_APP_CLAIM";
    public static final String RACE_START_FEE = "RACE_START_FEE";
    public static final String RACE_ACCEPT_FEE = "RACE_ACCEPT_FEE";

    // Database columns for RACE_DETAILS table.
    public static final String RACE_DETAILS_ROWID = "_id";
    public static final String RACE_DETAILS_RACE_ID = "RACE_ID";
    public static final String RACE_DETAILS_HORSE_ID = "HORSE_ID";
    public static final String RACE_DETAILS_HORSENAME = "HORSE_NAME";
    public static final String RACE_DETAILS_WEIGHT = "WEIGHT";
    public static final String RACE_DETAILS_JOCKEY_ID = "JOCKEY_ID";
    public static final String RACE_DETAILS_JOCKEY_NAME = "JOCKEY_NAME";
    public static final String RACE_DETAILS_TRAINER_ID = "TRAINER_ID";
    public static final String RACE_DETAILS_TRAINER_NAME = "TRAINER_NAME";

    // CLUBS table create.
    public static final String CREATE_CLUBS_TABLE = "CREATE TABLE "
            + CLUBS_TABLE + " ("
            + CLUB_ROWID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CLUB_ID     + " INTEGER NOT NULL, "
            + CLUB_NAME   + " TEXT NOT NULL)";

    // TRACKS table create.
    public static final String CREATE_TRACKS_TABLE = "CREATE TABLE "
            + TRACKS_TABLE    + " ("
            + TRACK_ROWID     + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TRACK_NAME      + " TEXT NOT NULL, "
            + TRACK_CLUB_NAME + " TEXT NOT NULL, "
            + TRACK_IS_PREF   + " TEXT NOT NULL)";

    // MEETINGS table create.
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


    // RACES table create.
    public static final String CREATE_RACES_TABLE = "CREATE TABLE "
            + RACES_TABLE       + " ("
            + RACE_ROWID        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + RACE_ID           + " INTEGER NOT NULL, "
            + RACE_MEETING_ID   + " INTEGER NOT NULL, "
            + RACE_NO           + " INTEGER NOT NULL, "
            + RACE_NAME         + " TEXT NOT NULL, "
            + RACE_TIME         + " TEXT NOT NULL, "
            + RACE_CLASS        + " TEXT NOT NULL, "
            + RACE_DISTANCE     + " TEXT NOT NULL, "
            + RACE_TRACK_RATING + " TEXT NOT NULL, "
            + RACE_PRIZE_TOTAL  + " TEXT NOT NULL, "
            + RACE_BONUS_TYPE   + " TEXT NOT NULL, "
            + RACE_BONUS_TOTAL  + " TEXT NOT NULL, "
            + RACE_AGE_COND     + " TEXT NOT NULL, "
            + RACE_SEX_COND     + " TEXT NOT NULL, "
            + RACE_WEIGHT_COND  + " TEXT NOT NULL, "
            + RACE_APP_CLAIM    + " TEXT NOT NULL, "
            + RACE_START_FEE    + " TEXT NOT NULL, "
            + RACE_ACCEPT_FEE   + " TEXT NOT NULL)";

    // RACE_DETAILS table create.
    public static final String CREATE_RACE_DETAILS_TABLE = "CREATE TABLE "
            + RACE_DETAILS_TABLE        + " ("
            + RACE_DETAILS_ROWID        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + RACE_DETAILS_RACE_ID      + " INTEGER NOT NULL, "
            + RACE_DETAILS_HORSE_ID     + " INTEGER NOT NULL, "
            + RACE_DETAILS_HORSENAME    + " TEXT NOT NULL, "
            + RACE_DETAILS_WEIGHT       + " TEXT NOT NULL, "
            + RACE_DETAILS_JOCKEY_ID    + " INTEGER NOT NULL, "
            + RACE_DETAILS_JOCKEY_NAME  + " TEXT NOT NULL, "
            + RACE_DETAILS_TRAINER_ID   + " INTEGER NOT NULL, "
            + RACE_DETAILS_TRAINER_NAME + " TEXT NOT NULL)";

    public  static final String DROP_CLUBS_TABLE = "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + CLUBS_TABLE + ";";
    public  static final String DROP_TRACKS_TABLE = "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + TRACKS_TABLE + ";";
    public  static final String DROP_MEETINGS_TABLE = "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + MEETINGS_TABLE + ";";
    public  static final String DROP_RACES_TABLE = "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + RACES_TABLE + ";";
    public  static final String DROP_RACE_DETAILS_TABLE = "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + RACE_DETAILS_TABLE + ";";

    public static final String WHERE_FOR_TRACK_UPDATE = SchemaConstants.TRACK_ROWID + " = ?;";
    public static final String WHERE_FOR_GET_MEETINGS_BY_DATE = SchemaConstants.MEETING_DATE + " = ?;";
    public static final String WHERE_FOR_GET_MEETING = SchemaConstants.MEETING_ROWID + " = ?;";
    public static final String WHERE_FOR_GET_RACE_MEETINGID = SchemaConstants.RACE_MEETING_ID + " = ?;";
    public static final String WHERE_FOR_GET_RACE_DETAILS_RACEID = SchemaConstants.RACE_DETAILS_RACE_ID + " = ?;";
    public static final String WHERE_FOR_GET_RACE = SchemaConstants.RACE_ROWID + " = ?;";

}
