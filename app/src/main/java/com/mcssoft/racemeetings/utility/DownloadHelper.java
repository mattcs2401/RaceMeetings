package com.mcssoft.racemeetings.utility;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.activity.MeetingsActivity;
import com.mcssoft.racemeetings.database.SchemaConstants;
import com.mcssoft.racemeetings.interfaces.IAsyncResult;
import com.mcssoft.racemeetings.meeting.Club;
import com.mcssoft.racemeetings.meeting.Meeting;

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
        InputStream inStream;
        XMLParser mxmlp;
        DatabaseUtility dbUtil = new DatabaseUtility(context);

        switch (tableName) {
            case SchemaConstants.CLUBS_TABLE:
                inStream = new ByteArrayInputStream(results.getBytes());
                mxmlp = new XMLParser(inStream);
                ArrayList<Club> clubs = mxmlp.parseClubsXml();
                dbUtil.insertFromList(SchemaConstants.CLUBS_TABLE, clubs);
                break;
            case SchemaConstants.MEETINGS_TABLE:
                inStream = new ByteArrayInputStream(results.getBytes());
                mxmlp = new XMLParser(inStream);
                ArrayList<Meeting> meetings = mxmlp.parseMeetingsXml();
                if(meetings != null && meetings.size() > 0) {
                    dbUtil.checkAndDeleteOld(tableName);
                    dbUtil.insertFromList(SchemaConstants.MEETINGS_TABLE, meetings);
                } else {
                    dbUtil.checkAndDeleteOld(tableName);
                }
                // Have to put it here because of inter process issues.
                Intent intent = new Intent(context, MeetingsActivity.class);
                context.startActivity(intent);
                break;
        }
    }

    public void getMeetingsBydate(String searchDate) {
        DownloadHelper downloadHelper = new DownloadHelper(context);
        downloadHelper.downloadTableData(SchemaConstants.MEETINGS_TABLE, searchDate);
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
                    message = Resources.getInstance().getString(R.string.get_meetings_data);
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


    private Context context;
}
