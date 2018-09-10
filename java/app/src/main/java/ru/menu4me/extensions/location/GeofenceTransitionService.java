package ru.menu4me.extensions.location;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.GeofencingRequest;
import com.google.gson.Gson;

import java.util.List;

public class GeofenceTransitionService extends IntentService {
    String tag = "(Geofence Transition Service) ";

    public GeofenceTransitionService() {
        super("");
        Log.d(Constants.TAG, tag + "Constructor()");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.e(Constants.TAG, tag + "GeofencingEvent has error: " + geofencingEvent.getErrorCode());
            return;
        }

        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        if (geofenceTransition == GeofencingRequest.INITIAL_TRIGGER_ENTER ||
                geofenceTransition == GeofencingRequest.INITIAL_TRIGGER_EXIT ||
                geofenceTransition == GeofencingRequest.INITIAL_TRIGGER_DWELL) {

            List<Geofence> triggeredGeofences = geofencingEvent.getTriggeringGeofences();
            int index = 0;

            for (Geofence g : triggeredGeofences)
            {
                Log.d(Constants.TAG, tag + "Triggered geofence info[" + index + "]: " + g);

                Gson gson = new Gson();
                Log.d(Constants.TAG, tag + "Geofencing json data: " + gson.toJson(g));

                Intent requestIntent = new Intent().setAction(Constants.ACTION_REQUEST).addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                        .putExtra(Constants.EXTRA_ID, g.getRequestId());
                this.sendBroadcast(requestIntent);

                index++;
            }
        } else {
            Log.w(Constants.TAG, tag + "Invalid transition type");
        }
    }
}
