package com.mcssoft.racemeetings.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

public class DownloadData extends AsyncTask<String,String,String> {

    public DownloadData(TextView textView, Context context) {
        super();
        this.context = context;
        this.textView = textView;
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
        textView.setText(theResult);
    }

//    String theResult;
    private TextView textView;
    private Context context;
    private ProgressDialog progressDialog;
}
