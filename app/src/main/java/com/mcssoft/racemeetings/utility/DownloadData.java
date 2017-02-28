package com.mcssoft.racemeetings.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import java.net.URL;

import com.mcssoft.racemeetings.interfaces.IAsyncResult;

/**
 * Utility class - generic async task used for getting data via http.
 * Results are returned via the IAsyncResult interface.
 */
public class DownloadData extends AsyncTask<String,String,String> {

    /**
     * Constructor.
     * @param context The app cpntext.
     * @param message A message for the progress dialog.
     */
    public DownloadData(Context context, String message) {
        this.context = context;
        this.message = message;    }

    /**
     * Constructor.
     * @param context The app cpntext.
     * @param url The url for the http operation.
     * @param message A message for the progress dialog.
     */
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
        asyncResult.downloadFinish(theResult);
    }

    /**
     * Set the url for the http operation.
     * @param url The url for the http operation.
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    private URL url;
    private String message;
    private Context context;
    private ProgressDialog progressDialog;
    public IAsyncResult asyncResult = null;
}
