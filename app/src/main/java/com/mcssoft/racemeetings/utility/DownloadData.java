package com.mcssoft.racemeetings.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.mcssoft.racemeetings.interfaces.IAsyncResponse;

public class DownloadData extends AsyncTask<String,String,String> {

    public DownloadData(String theResults, Context context) {
        super();
        this.context = context;
        this.theResults = theResults;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
//        progressDialog.setTitle("Downloading");
        progressDialog.setMessage("Downloading ...");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

        String theResult = "";
        try {
            HttpWrapper sw = new HttpWrapper(context);
            theResult = sw.remoteRequest();
        }
        catch (Exception exception) {
           theResult = exception.toString();
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
        //this.theResults = theResult;
        asyncResponse.processFinish(theResult);
    }

//    String theResult;
    private String theResults;
    private Context context;
    private ProgressDialog progressDialog;
    public IAsyncResponse asyncResponse = null;
}
