package com.mcssoft.racemeetings.utility;


import android.content.Context;
import android.net.Uri;
import android.os.StrictMode;

import com.mcssoft.racemeetings.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class HttpWrapper {

    public HttpWrapper(Context context) {
        this.context = context;
    }

    public String remoteRequest() {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("www.racingqueensland.com.au")
                .appendPath("opendatawebservices")
                .appendPath("meetings.asmx")
                .appendPath("GetMeetingDetails")
                .appendQueryParameter("MeetingId","89226");
        builder.build();

        URL url = null;
        InputStream stream = null;
        HttpURLConnection connection = null;
        String result = "";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            url = new URL(builder.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }
            stream = connection.getInputStream();
            if (stream != null) {
                // Converts Stream to String with max length of 500.
                result = readStream(stream, 500);
            }
        }
        catch (Exception exception) {
            result = exception.toString();
        }
        finally {
            if(connection != null) {
                connection.disconnect();
            }
            if(stream != null) {
                try {
                    stream.close();
                } catch(Exception exception) {
                    result = exception.toString();
                }
            }
        }

        return result;
    }

    private String readStream(InputStream stream, int maxLength) throws IOException {
        String result = null;
        // Read InputStream using the UTF-8 charset.
        InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
        // Create temporary buffer to hold Stream data with specified max length.
        char[] buffer = new char[maxLength];
        // Populate temporary buffer with Stream data.
        int numChars = 0;
        int readSize = 0;
        while (numChars < maxLength && readSize != -1) {
            numChars += readSize;
            int pct = (100 * numChars) / maxLength;
            readSize = reader.read(buffer, numChars, buffer.length - numChars);
        }
        if (numChars != -1) {
            // The stream was not empty.
            // Create String that is actual length of response body if actual length was less than
            // max length.
            numChars = Math.min(numChars, maxLength);
            result = new String(buffer, 0, numChars);
        }
        return result;
    }

    private Context context;
}
