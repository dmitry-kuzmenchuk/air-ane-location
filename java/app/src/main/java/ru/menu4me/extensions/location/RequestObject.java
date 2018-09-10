package ru.menu4me.extensions.location;

import android.content.Context;

import com.google.gson.Gson;

public class RequestObject {
    public String service = "";
    public String method = "";
    public RequestData data = null;

    public RequestObject(String service, String method) {
        this.service = service;
        this.method = method;
    }

    public void buildSetGeoPositionRequest(AppData appData, int geofenceId) {
        this.data = new RequestData();
        this.data.clientInfo = appData.clientInfo;
        for (GData g : appData.geofences) {
            if (g.id == geofenceId) {
                this.data.point = g;
            }
        }
    }
}
