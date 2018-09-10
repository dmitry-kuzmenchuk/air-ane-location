package ru.menu4me.extensions.location;

import android.content.Context;

import com.google.android.gms.location.Geofence;
import com.google.gson.Gson;

public class GData {
    public int id = 0;
    public double latitude = 0, longitude = 0;
    public float radius = 0;
    public long expDuration = 0;
    public int transitionType = 0;
    public int loiteringDelay = 0;
    public String title = "";

    /**
     * Creates a bundle of geofence data.
     * To convert this data into actual geofence use toGeofence() method
     *
     * @param id             Identifier / Идентефикатор
     * @param latitude       Latitude / Широта
     * @param longitude      Longitude Долгота
     * @param radius         Radius / Радиус (Use no more than 100 meters. More precision causes much more power consumption)
     * @param expDuration    Lifetime or expiration duration / Длительность жизни (Use seconds)
     * @param transitionType Triggering or transition type / Тип срабатывания
     * @param loiteringDelay Triggering or loitering delay / Задержка срабатывания триггера геозоны (Used only if u're usign DWELL transition type)
     */
    public GData(String title, int id, double latitude, double longitude, float radius, long expDuration, int transitionType, int loiteringDelay) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.expDuration = expDuration * 1000;
        this.transitionType = transitionType;
        this.loiteringDelay = loiteringDelay;
        this.title = title;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Geofence toGeofence() {
        return new Geofence.Builder().setCircularRegion(latitude, longitude, radius)
                .setExpirationDuration(expDuration).setRequestId(String.valueOf(id))
                .setTransitionTypes(transitionType).setLoiteringDelay(loiteringDelay).build();
    }
}