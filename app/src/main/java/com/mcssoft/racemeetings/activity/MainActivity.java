package com.mcssoft.racemeetings.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mcssoft.racemeetings.R;
import com.mcssoft.racemeetings.utility.DownloadData;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        textView = (TextView) findViewById(R.id.id_textview);
        button = (Button) findViewById(R.id.id_button);
        button.setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        DownloadData dld = new DownloadData(textView,this);
        dld.execute();
        //String str = dld.get();

    }

    private TextView textView;
    private Button button;
}
