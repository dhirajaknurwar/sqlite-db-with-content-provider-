package com.sqlitewithasynctask;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class DataSyncAlarm {
    //Sets an alarm for 12 hours
    public void setAlarm(Context context) {
        //long interval = 6 * 60 * 60 * 1000;
        long interval = 5 * 60 * 1000;

        Calendar calendar = Calendar.getInstance();
        //calendar.add(Calendar.HOUR, 6);
        calendar.add(Calendar.MINUTE, 5);

        Intent myIntent = new Intent(context, GetDataService.class);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(context, 1001, myIntent, 0);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    interval, pendingIntent);
        }
    }
}
