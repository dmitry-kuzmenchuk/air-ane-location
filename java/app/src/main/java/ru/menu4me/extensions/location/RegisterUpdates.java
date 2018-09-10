package ru.menu4me.extensions.location;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class RegisterUpdates implements FREFunction {
    private String tag = "(Register Updates) ";

    @Override
    public FREObject call(FREContext context, FREObject[] args) {
        Log.d(Constants.TAG, tag + "call()");
        Context appContext = context.getActivity().getApplicationContext();

        Intent refreshIntent = new Intent().setAction(Constants.ACTION_REFRESH).addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(appContext, 0, refreshIntent, 0);

        AlarmManager alarmManager = (AlarmManager) appContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + Constants.PERIOD_HOUR, Constants.PERIOD_HOUR, refreshPendingIntent);

        return null;
    }
}
