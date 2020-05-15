package com.app.mobilize.Vista.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.app.mobilize.R;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, intent.getExtras().getString("title"))
                .setSmallIcon(R.drawable.logo_mobilize)
                .setContentTitle(intent.getExtras().getString("title"))
                .setContentText(intent.getExtras().getString("text"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());
    }
}
