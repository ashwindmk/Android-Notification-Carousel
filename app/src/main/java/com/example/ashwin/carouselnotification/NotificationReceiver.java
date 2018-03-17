package com.example.ashwin.carouselnotification;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import static com.example.ashwin.carouselnotification.NotificationBuilder.IMAGE;
import static com.example.ashwin.carouselnotification.NotificationBuilder.LEFT;
import static com.example.ashwin.carouselnotification.NotificationBuilder.RIGHT;
import static com.example.ashwin.carouselnotification.NotificationBuilder.carouselNotify;

/**
 * Created by Ashwin on 2/17/2018.
 */

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("carousel", "NotificationReceiver > onReceive() > intent: " + String.valueOf(intent));

        int id = 0;

        Bundle extras = null;
        if (intent != null) {
            extras = intent.getExtras();
            if (extras != null) {
                id = extras.getInt("id", 0);
            }
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (extras.containsKey("click")) {
            int click = extras.getInt("click");
            int index = extras.getInt("index");
            switch (click) {
                case LEFT:
                    carouselNotify(context, index, LEFT);
                    break;
                case RIGHT:
                    carouselNotify(context, index, RIGHT);
                    break;
                case IMAGE:
                    // This will not arrive here, it goes directly to the activity
                    notificationManager.cancel(id);
                    break;
            }
        } else {
            notificationManager.cancel(id);
        }

    }

}
