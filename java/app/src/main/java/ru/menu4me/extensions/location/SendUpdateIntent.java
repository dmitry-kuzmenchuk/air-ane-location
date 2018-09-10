package ru.menu4me.extensions.location;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class SendUpdateIntent implements FREFunction {
    private static final String tag = "(Send Update Intent) ";

    @Override
    public FREObject call(FREContext context, FREObject[] args) {
        Log.d(Constants.TAG, tag + "call()");
        Context appContext = context.getActivity().getApplicationContext();
        Intent updateIntent = new Intent().setAction(Constants.ACTION_UPDATE).addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        appContext.sendBroadcast(updateIntent);
        return null;
    }
}
