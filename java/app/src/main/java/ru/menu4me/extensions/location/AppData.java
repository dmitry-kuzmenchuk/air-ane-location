package ru.menu4me.extensions.location;

import java.util.ArrayList;

public class AppData {
    public String clientInfo = "";
    public long listStartTime = 0;
    public long listEndTime = 0;
    public boolean isOutdated = false;
    public ArrayList<GData> geofences = null;

    public AppData() {
        this.geofences = new ArrayList<>();
    }
}
