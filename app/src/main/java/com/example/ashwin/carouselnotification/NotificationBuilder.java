package com.example.ashwin.carouselnotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * Created by Ashwin on 2/18/2018.
 */

public class NotificationBuilder {

    public static final int NOTIFICATION_ID = 6;
    public static final int MAIN_REQUEST_CODE = 1, LEFT_ACTION_REQUEST_CODE = 3, RIGHT_ACTION_REQUEST_CODE = 5;
    public static final int IMAGE = 0, LEFT = 1, RIGHT = 2;
    private static final boolean IS_BUGGY_DEVICE = false;

    public static void carouselNotify(Context context, int index, int direction) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.cart_icon);

        // Remote view
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.carousel_remote_view);
        remoteView.setImageViewResource(R.id.icon, R.mipmap.ic_launcher_round);
        remoteView.setTextViewText(R.id.titleTextView, "Custom Title");
        remoteView.setTextViewText(R.id.textTextView, "carousel");

        // Image
        if (direction == LEFT) {
            index = index - 1;
            if (index < 1) {
                index = 3;
            }
        } else if (direction == RIGHT) {
            index = index + 1;
            if (index > 3) {
                index = 1;
            }
        }
        if (index == 1) {
            remoteView.setImageViewResource(R.id.imageImageView, R.drawable.banner_1);
        } else if (index == 2) {
            remoteView.setImageViewResource(R.id.imageImageView, R.drawable.banner_2);
        } else if (index == 3) {
            remoteView.setImageViewResource(R.id.imageImageView, R.drawable.banner_3);
        }
        Intent intent = new Intent(context, ReceiverActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id", NOTIFICATION_ID);
        intent.putExtra("index", index);
        intent.putExtra("click", IMAGE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, MAIN_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.imageImageView, pendingIntent);

        // Left arrow
        remoteView.setImageViewResource(R.id.leftImageView, R.drawable.ic_carousal_left_arrow);
        Intent leftIntent = new Intent(context, NotificationReceiver.class);
        leftIntent.putExtra("id", NOTIFICATION_ID);
        leftIntent.putExtra("click", LEFT);
        leftIntent.putExtra("index", index);
        PendingIntent leftPendingIntent = PendingIntent.getBroadcast(context, LEFT_ACTION_REQUEST_CODE, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.leftImageView, leftPendingIntent);

        // Right arrow
        remoteView.setImageViewResource(R.id.rightImageView, R.drawable.ic_carousal_right_arrow);
        Intent rightIntent = new Intent(context, NotificationReceiver.class);
        rightIntent.putExtra("id", NOTIFICATION_ID);
        rightIntent.putExtra("click", RIGHT);
        rightIntent.putExtra("index", index);
        PendingIntent rightPendingIntent = PendingIntent.getBroadcast(context, RIGHT_ACTION_REQUEST_CODE, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.rightImageView, rightPendingIntent);

        builder.setCustomBigContentView(remoteView);
        remoteView.setOnClickPendingIntent(R.id.notification_remote_view, pendingIntent);

        // Content
        if (IS_BUGGY_DEVICE) {
            // for normal devices
            builder.setContentTitle("Content Title");
            builder.setContentText("carousel");
            builder.setContentIntent(pendingIntent);
        } else {
            // for buggy devices
            // some buggy devices fire the content intent even if you click on left/right arrows, which causes the notification tray to slide up.
            // to make this work on such devices, we will not set content intent, instead use a custom view and click listener.
            RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.content_view);
            contentView.setImageViewResource(R.id.icon, R.mipmap.ic_launcher);
            contentView.setTextViewText(R.id.titleTextView, "Custom Title");
            contentView.setTextViewText(R.id.textTextView, "carousel");
            contentView.setOnClickPendingIntent(R.id.notification_content, pendingIntent);
            builder.setCustomContentView(contentView);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_1";
            CharSequence channelName = "Channel 1";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setShowBadge(true);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}
