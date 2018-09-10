package ru.menu4me.extensions.location;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LocationBroadcastReceiver extends BroadcastReceiver implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private String tag = "(Location Broadcast Receiver) ";

    private static final MediaType MEDIATYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    GoogleApiClient googleApiClient;
    OkHttpClient httpClient;
    Context context;
    AppData appData;
    Gson gson;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(Constants.TAG, tag + "onReceive() with intent: " + intent.getAction());
        this.context = context;
        gson = new Gson();

        try {
            File dataFile = new File(context.getFilesDir(), Constants.FILE_DATA);
            if (dataFile.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(dataFile));
                String result = br.readLine();
                br.close();
                appData = gson.fromJson(result, AppData.class);
            } else {
                appData = new AppData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (intent.getAction()) {
            case Constants.ACTION_UPDATE: {
                if (appData.geofences.size() > 0) {
                    googleApiClient = new GoogleApiClient.Builder(context).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
                    googleApiClient.connect();
                } else {
                    Log.e(Constants.TAG, tag + "Can not create googleApiClient coz geofences list are empty");
                }
                break;
            }
            case Constants.ACTION_REQUEST: {
                int geofenceId = Integer.parseInt(intent.getStringExtra(Constants.EXTRA_ID));
                RequestObject ro = new RequestObject(Constants.SERVER_SERVICE_CLIENT_USER, Constants.SERVER_METHOD_SET_GEO_POSITION);
                ro.buildSetGeoPositionRequest(appData, geofenceId);
                try {
                    sendPost(Constants.SERVER_API_URL, gson.toJson(ro, RequestObject.class));
                } catch (Exception e) {
                    Log.e(Constants.TAG, tag + "Exception: " + e);
                    e.printStackTrace();
                }
                break;
            }
            case Constants.ACTION_REFRESH: {
                if (checkIsOutdated()) {
                    googleApiClient = new GoogleApiClient.Builder(context).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
                    googleApiClient.connect();
                } else {
                    Log.e(Constants.TAG, tag + "Data is outdated, please register new list");
                }
                break;
            }
        }
    }

    private boolean checkIsOutdated() {
        if (!appData.isOutdated && System.currentTimeMillis() > appData.listEndTime)
        {
            appData.isOutdated = true;
            try {
                String output = gson.toJson(appData, AppData.class);
                OutputStream os = context.openFileOutput(Constants.FILE_DATA, Context.MODE_PRIVATE);
                os.write(output.getBytes());
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return appData.isOutdated;
    }

    private GeofencingRequest getGeofencingRequest(GData gData) {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(gData.transitionType);
        builder.addGeofence(gData.toGeofence());
        return builder.build();
    }

    public void sendPost(String url, String json) throws Exception {
        if (httpClient == null) {
            httpClient = new OkHttpClient();
        }

        RequestBody body = RequestBody.create(MEDIATYPE_JSON, json);
        Request request = new Request.Builder().url(url).post(body).addHeader("content-type", "application/json; charset=utf-8").build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(Constants.TAG, tag + "IOException: " + e);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }

                    String responseResult = response.body().string();
                    ServerResult result = gson.fromJson(responseResult, ServerResult.class);

                    Log.d(Constants.TAG, tag + "Response = " + responseResult);
                } catch (IOException e) {
                    Log.d(Constants.TAG, tag + "IOException: " + e);
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(Constants.TAG, tag + "OnConnected()");

        try {
            Intent transitionIntent = new Intent(context, GeofenceTransitionService.class);
            PendingIntent geofencingIntent = PendingIntent.getService(context, 0, transitionIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            for (GData g : appData.geofences) {
                Log.d(Constants.TAG, tag + "Geofence: " + g.toGeofence().toString());
                LocationServices.GeofencingApi.addGeofences(googleApiClient, getGeofencingRequest(g), geofencingIntent).setResultCallback(new ResultCallback<Status>() {
                    private String tag = "(GeofencingApi.addGeofences) ";

                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            Log.d(Constants.TAG, tag + "onResult() success");
                        } else {
                            Log.w(Constants.TAG, tag + "onResult() fail");
                        }
                    }
                });
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        googleApiClient.disconnect();
    }

    @Override
    public void onConnectionSuspended(int status) {
        Log.d(Constants.TAG, tag + "OnConnectionSuspended(): " + status);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.e(Constants.TAG, tag + "OnConnectionFailed() " + result);
        Log.e(Constants.TAG, tag + "OnConnectionFailed() " + result.getErrorMessage());
    }
}
