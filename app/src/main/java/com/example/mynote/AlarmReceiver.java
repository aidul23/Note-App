package com.example.mynote;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;

import java.net.URI;


public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //get id and message from intent
        int notificationId = intent.getIntExtra("notificationId",0);
        String message = intent.getStringExtra("todo");


        //when notification is tapped call MainActivity
        Intent mainIntent = new Intent(context,MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,0, mainIntent,0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //prepare notification
        Notification.Builder builder = new Notification.Builder(context);

        builder.setLights(Color.YELLOW,200,200);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(sound);

        long vibrate[] = {100,500,100,500};
        builder.setVibrate(vibrate);
        builder.setSmallIcon(R.drawable.ic_notifications).setContentTitle("My Note").setContentText(message).setWhen(System.currentTimeMillis()).setAutoCancel(true).setContentIntent(contentIntent);

        //Notify
        notificationManager.notify(notificationId,builder.build());
    }
}
