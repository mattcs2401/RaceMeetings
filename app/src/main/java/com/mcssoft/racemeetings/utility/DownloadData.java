package com.mcssoft.racemeetings.utility;

import android.content.Context;
import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class DownloadData extends AsyncTask<String,String,String> {

    public DownloadData(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... params) {

        String theResult = "";
        try {
            SoapWrapper sw = new SoapWrapper(context);
            theResult = sw.remoteRequest();
        }
        catch (Exception exception) {
           theResult = exception.toString();
        }
        return theResult;
    }

    private Context context;
}
