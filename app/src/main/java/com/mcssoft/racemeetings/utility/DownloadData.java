package com.mcssoft.racemeetings.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.mcssoft.racemeetings.interfaces.IAsyncResponse;

import java.io.InputStream;
import java.net.URL;

public class DownloadData extends AsyncTask<String,String,String> {

    public DownloadData(Context context) {
        this.context = context;
        this.theResults = theResults;
    }

    public DownloadData(Context context, URL url) {
        super();
        this.context = context;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Downloading ...");
        progressDialog.show();
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    protected String doInBackground(String... params) {

        String theResult = null;
        try {
            HttpWrapper sw = new HttpWrapper(context, url);
            theResult = sw.remoteRequest();
        }
        catch (Exception exception) {
           exception.printStackTrace();
        }
        return theResult;
    }

    /*
      Runs on UI thread after doInBackground().
     */
    @Override
    protected void onPostExecute(String theResult) {
        super.onPostExecute(theResult);
        progressDialog.dismiss();
        asyncResponse.processFinish(theResult);
    }

    private URL url;
    private URL url2;
    private String theResults;
    private Context context;
    private ProgressDialog progressDialog;
    public IAsyncResponse asyncResponse = null;
}
