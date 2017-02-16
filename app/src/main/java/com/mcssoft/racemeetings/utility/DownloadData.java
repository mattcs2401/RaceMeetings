package com.mcssoft.racemeetings.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.mcssoft.racemeetings.interfaces.IAsyncResponse;

import java.io.InputStream;
import java.net.URL;

public class DownloadData extends AsyncTask<String,String,String> {

    public DownloadData(Context context, String message) {
        this.context = context;
        this.message = message;    }

    public DownloadData(Context context, URL url, String message) {
        super();
        this.context = context;
        this.message = message;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    protected String doInBackground(String... params) {

        String theResult = null;
        try {
            HttpWrapper sw = new HttpWrapper(url);
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
    private String message;
    private Context context;
    private ProgressDialog progressDialog;
    public IAsyncResponse asyncResponse = null;
}
