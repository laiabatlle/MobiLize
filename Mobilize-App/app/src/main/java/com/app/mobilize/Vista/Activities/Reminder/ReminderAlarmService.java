package com.app.mobilize.Vista.Activities.Reminder;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;

import com.app.mobilize.R;
import com.app.mobilize.Vista.Activities.AddAlertActivity;
import com.app.mobilize.Vista.Activities.Data.AlarmReminderContract;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

public class ReminderAlarmService extends IntentService {
    private static final String TAG = ReminderAlarmService.class.getSimpleName();

    private static final int NOTIFICATION_ID = 42;

    public static PendingIntent getReminderPendingIntent(Context context, Uri uri) {
        Intent action = new Intent(context, ReminderAlarmService.class);
        action.setData(uri);
        return PendingIntent.getService(context, 0, action, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public ReminderAlarmService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Uri uri = intent.getData();

        Intent action = new Intent(this, AddAlertActivity.class);
        action.setData(uri);
        PendingIntent operation = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(action)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        /*Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        String description = "";
        try {
            if (cursor != null && cursor.moveToFirst()) {
                description = AlarmReminderContract.getColumnString(cursor, AlarmReminderContract.AlarmReminderEntry.KEY_TITLE);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        Notification note = new NotificationCompat.Builder(this, "default")
                .setContentTitle(getString(R.string.reminder))
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_add_alert)
                .setContentIntent(operation)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setAutoCancel(true)
                .build();

        manager.notify(NOTIFICATION_ID, note);*/
    }
}
