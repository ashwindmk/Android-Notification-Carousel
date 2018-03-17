package com.example.ashwin.carouselnotification;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ReceiverActivity extends AppCompatActivity {

    private String mText = "";
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        int id = 0;
        Intent intent = getIntent();
        Log.w("carousel", "ReceiverActivity > onCreate() > intent: " + String.valueOf(intent));
        Bundle extras = null;
        if (intent != null) {
            extras = intent.getExtras();
            if (extras != null) {
                id = extras.getInt("id", 0);
            }
            mText = String.valueOf(extras);
        }
        mTextView = (TextView) findViewById(R.id.textView);
        mTextView.setText(mText);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }
}
