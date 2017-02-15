package com.mcssoft.racemeetings.utility;


import android.content.Context;
import android.net.Uri;
import android.os.StrictMode;

import com.mcssoft.racemeetings.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.CharBuffer;

import javax.net.ssl.HttpsURLConnection;


public class HttpWrapper {

    public HttpWrapper(Context context, URL url) {
        this.context = context;
        this.url = url;
    }

    public String remoteRequest() {
//
//        Uri.Builder builder = new Uri.Builder();
//        builder.scheme("http")
//                .authority("www.racingqueensland.com.au")
//                .appendPath("opendatawebservices")
//                .appendPath("meetings.asmx")
//                .appendPath("GetMeetingDetails")
//                .appendQueryParameter("MeetingId","89226");
//        builder.build();

//        URL url = null;
        InputStream stream = null;
        HttpURLConnection connection = null;
        String result = "";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
//            url = new URL(builder.toString());
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
                result = readStream(stream);
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

    private String readStream(InputStream stream) throws IOException {
        String line = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        stream.close();
        return sb.toString();
    }

    private Context context;
    private URL url;
}
