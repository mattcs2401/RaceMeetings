package com.mcssoft.racemeetings.utility;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.activity.MeetingRacesDetailsActivity;
import com.mcssoft.racemeetings.activity.MeetingRacesActivity;
import com.mcssoft.racemeetings.activity.MeetingsActivity;
import com.mcssoft.racemeetings.database.DatabaseOperations;
import com.mcssoft.racemeetings.database.SchemaConstants;
import com.mcssoft.racemeetings.interfaces.IAsyncResult;
import com.mcssoft.racemeetings.model.Club;
import com.mcssoft.racemeetings.model.Horse;
import com.mcssoft.racemeetings.model.Meeting;
import com.mcssoft.racemeetings.model.Race;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class DownloadHelper implements IAsyncResult {

    public DownloadHelper(Context context) {
        this.context = context;
    }

    /**
     * Async task results end up here.
     * @param results The results from the async task.
     */
    @Override
    public void result(String tableName, String results) {
        Intent intent;
        XMLParser mxmlp;
        InputStream inStream;
        String key;
        DatabaseOperations dbOper = new DatabaseOperations(context);

        switch (tableName) {
            case SchemaConstants.CLUBS_TABLE:
                inStream = new ByteArrayInputStream(results.getBytes());
                mxmlp = new XMLParser(inStream);
                ArrayList<Club> clubs = mxmlp.parseClubsXml();
                dbOper.insertFromList(SchemaConstants.CLUBS_TABLE, clubs, 0);
                break;
            case SchemaConstants.MEETINGS_TABLE:
                inStream = new ByteArrayInputStream(results.getBytes());
                mxmlp = new XMLParser(inStream);
                ArrayList<Meeting> meetings = mxmlp.parseMeetingsXml();
                if(meetings != null) {
                    dbOper.insertFromList(SchemaConstants.MEETINGS_TABLE, meetings, 0);
                }
                // Have to put it here because of inter process issues.
                intent = new Intent(context, MeetingsActivity.class);
                context.startActivity(intent);
                break;
            case SchemaConstants.RACES_TABLE:
                inStream = new ByteArrayInputStream(results.getBytes());
                mxmlp = new XMLParser(inStream);
                ArrayList<Race> races = mxmlp.parseRacesXml();
                if(races != null && races.size() > 0) {
                    dbOper.insertFromList(SchemaConstants.RACES_TABLE, races, Integer.parseInt(queryParam));
                }
                // Have to put it here because of inter process issues.
                intent = new Intent(context, MeetingRacesActivity.class);
                key = Resources.getInstance().getString(R.string.meeting_id_key);
                intent.putExtra(key, queryParam);
                context.startActivity(intent);
                break;
            case SchemaConstants.RACE_DETAILS_TABLE:
                inStream = new ByteArrayInputStream(results.getBytes());
                mxmlp = new XMLParser(inStream);
                ArrayList<Horse> horses = mxmlp.parseHorseXml(queryParam);
                if(horses != null && horses.size() > 0) {
                    dbOper.insertFromList(SchemaConstants.RACE_DETAILS_TABLE, horses, Integer.parseInt(queryParam));
                }
                intent = new Intent(context, MeetingRacesDetailsActivity.class);
                key = Resources.getInstance().getString(R.string.race_id_key);
                intent.putExtra(key, queryParam);
                context.startActivity(intent);
                break;
        }
    }

    public void getMeetingsByDate(String searchDate) {
        queryParam = searchDate;
        downloadTableData(SchemaConstants.MEETINGS_TABLE, searchDate);
    }

    public void getRacesForMeeting(String meetingId) {
        queryParam = meetingId;
        downloadTableData(SchemaConstants.RACES_TABLE, meetingId);
    }

    public void getHorsesForRace(String raceId) {
        queryParam = raceId;
        downloadTableData(SchemaConstants.RACE_DETAILS_TABLE, raceId);
    }

    public void downloadTableData(String table, @Nullable String queryParam) {
        URL url = null;
        String message = null;
        DownloadData dld;

        try {
            switch (table) {
                case SchemaConstants.CLUBS_TABLE:
                    url = new URL(createClubsUrl());
                    message = Resources.getInstance().getString(R.string.init_clubs_data);
                    break;
                case SchemaConstants.MEETINGS_TABLE:
                    url = new URL(createMeetingsUrl(queryParam));
                    message = Resources.getInstance().getString(R.string.get_meetings_details);
                    break;
                case SchemaConstants.RACES_TABLE:
                    url = new URL(createRacesUrl(queryParam));
                    message = Resources.getInstance().getString(R.string.get_races_details);
                    break;
                case SchemaConstants.RACE_DETAILS_TABLE:
                    url = new URL(createHorsesForRaceUrl(queryParam));
                    message = Resources.getInstance().getString(R.string.get_horses_details);
                    break;
            }
            dld = new DownloadData(context, url, message, table);
            dld.asyncResult = this;
            dld.execute();

        } catch(Exception ex) {
            Log.d("", ex.getMessage());
        }
    }

    private String createClubsUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.encodedPath(Resources.getInstance().getString(R.string.base_path_calendar))
                .appendPath(Resources.getInstance().getString(R.string.get_available_clubs));
        builder.build();
        return builder.toString();
    }

    private String createMeetingsUrl(String queryParam) {
        Uri.Builder builder = new Uri.Builder();
        builder.encodedPath(Resources.getInstance().getString(R.string.base_path_meetings))
                .appendPath(Resources.getInstance().getString(R.string.get_meetings_for_date))
                .appendQueryParameter(Resources.getInstance().getString(R.string.meeting_date), queryParam);

        builder.build();
        return builder.toString();
    }

    private String createRacesUrl(String queryParam) {
        Uri.Builder builder = new Uri.Builder();
        builder.encodedPath(Resources.getInstance().getString(R.string.base_path_meetings))
                .appendPath(Resources.getInstance().getString(R.string.get_races_for_meeting))
                .appendQueryParameter(Resources.getInstance().getString(R.string.meeting_id), queryParam);

        builder.build();
        return builder.toString();
    }

    private String createHorsesForRaceUrl(String queryParam) {
        Uri.Builder builder = new Uri.Builder();
        builder.encodedPath(Resources.getInstance().getString(R.string.base_path_meetings))
                .appendPath(Resources.getInstance().getString(R.string.get_horses_for_race))
                .appendQueryParameter(Resources.getInstance().getString(R.string.race_id), queryParam);

        builder.build();
        return builder.toString();
    }

    private Context context;
    private String queryParam;
}
