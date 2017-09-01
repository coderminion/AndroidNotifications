package com.coderminion.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String PIC_URL = "http://api.coderminion.com/minion.jpg";
    Button simpleNotification,inboxstyleNotification,bigtextstyleNotification,bigPictureStyleNotification,scheduledNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        interfaces();
    }

    private void init() {
        simpleNotification = (Button)findViewById(R.id.simpleNotification);
        inboxstyleNotification = (Button)findViewById(R.id.inboxstyleNotification);
        bigtextstyleNotification = (Button)findViewById(R.id.bigtextstyleNotification);
        bigPictureStyleNotification = (Button)findViewById(R.id.bigPictureStyleNotification);
        scheduledNotification = (Button)findViewById(R.id.scheduledNotification);
    }

    private void interfaces() {
        simpleNotification.setOnClickListener(this);
        inboxstyleNotification.setOnClickListener(this);
        bigtextstyleNotification.setOnClickListener(this);
        bigPictureStyleNotification.setOnClickListener(this);
        scheduledNotification.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.simpleNotification :
                simpleNotification();
            break;

            case R.id.inboxstyleNotification :
                inboxstyleNotification();
            break;

             case R.id.bigtextstyleNotification :
                 bigtextstyleNotification();
            break;

            case R.id.bigPictureStyleNotification :
                bigPictureStyleNotification();
            break;

            case R.id.scheduledNotification :
                scheduledNotification();
            break;
        }
    }

    private void scheduledNotification() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        Intent intent1 = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);//  EVERY 15 Minutes
    }

    private void bigPictureStyleNotification() {
        Glide.with(this)
                .asBitmap()
                .load(PIC_URL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        Notification notif = new Notification.Builder(MainActivity.this)
                                .setContentTitle("Title")
                                .setContentText("content")
                                .setSmallIcon(R.drawable.ic_notification)
                                .setStyle(new Notification.BigPictureStyle()
                                        .bigPicture(resource)
                                        .setBigContentTitle("big title"))
                                .build();
                        // Gets an instance of the NotificationManager service
                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        //to post your notification to the notification bar
                        mNotificationManager.notify(0, notif);
                    }
                });
    }

    private void bigtextstyleNotification() {
    //Assign inbox style notification
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
        bigText.setBigContentTitle("Big Text Notification");
        bigText.setSummaryText("By: Author of Lorem ipsum");

    //build notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle("Big Text notification")
                        .setContentText("This is test of big text style notification.")
                        .setStyle(bigText);

    // Gets an instance of the NotificationManager service
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    //to post your notification to the notification bar
        mNotificationManager.notify(0, mBuilder.build());
    }

    private void inboxstyleNotification() {
        //set intents and pending intents to call activity on click of "show activity" action button of notification
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent piResult = PendingIntent.getActivity(this,
                (int) Calendar.getInstance().getTimeInMillis(), resultIntent, 0);

//Assign inbox style notification
        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();

        inboxStyle.setBigContentTitle("Inbox Notification");
        inboxStyle.addLine("Hey vaibhav");
        inboxStyle.addLine("Hii how are you");
        inboxStyle.addLine("I'm waiting here");
        inboxStyle.addLine("Sup there");
        inboxStyle.addLine("Ummm no idea");
        inboxStyle.setSummaryText("+2 more");

//build notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle("Inbox style notification")
                        .setContentText("This is test of inbox style notification.")
                        .setStyle(inboxStyle)
                        .addAction(R.drawable.ic_notification, "show activity", piResult);

// Gets an instance of the NotificationManager service
        NotificationManager notificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//to post your notification to the notification bar
        notificationManager.notify(0, mBuilder.build());
    }

    private void simpleNotification() {
        //build notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle("Simple notification")
                        .setContentText("This is test of simple notification.");
        // Gets an instance of the NotificationManager service
        NotificationManager notificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //to post your notification to the notification bar
        notificationManager.notify(0 , mBuilder.build());
    }


}
