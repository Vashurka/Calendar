package com.kotlincalendar.lehoczkin.calendar_v2;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        String name = intent.getExtras().getString("name");

        builder.setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.kalendar)
                .setContentTitle("Mai névnap(ok):")
                .setContentText(name);
//                .setStyle(new NotificationCompat.BigTextStyle().bigText("A mai névnapok a következők: " + name + " !"));

        Intent myIntent = new Intent(context,MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());
    }
}
