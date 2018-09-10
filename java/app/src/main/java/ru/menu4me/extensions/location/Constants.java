package ru.menu4me.extensions.location;

public class Constants {

    public static final String TAG = "Location";

    // When RegisterNewList() is called we're should send actual data into geofencing service,
    // but only once, without any alarms and stuff. Actually the same as ACTION_REFRESH.
    public static final String ACTION_UPDATE = "ru.menu4me.extensions.location.action.UPDATE";

    // When GeofenceTransitionService got triggered geofence it sends action to broadcast receiver
    // and then broadcast receiver by himself sends request to the server.
    // We're doing this coz GeofenceTransitionService have no context.
    public static final String ACTION_REQUEST = "ru.menu4me.extensions.location.action.REQUEST";

    // RegisterUpdates() sets repeating alarm, which one sends ACTION_REFRESH into broadcast receiver,
    // that means we need to refresh our list of geofences inside geofencing service, also it checks
    // is geofences are outdated, if yes - then do not refresh them until we are register new list.
    public static final String ACTION_REFRESH = "ru.menu4me.extensions.location.action.REFRESH";

    //public static final int PERIOD_HALF_MINUTE = 30000;
    public static final int PERIOD_MINUTE = 60000;
    public static final int PERIOD_HOUR = 3600000;

    public static final String FILE_DATA = "data.json";

    public static final String DATA_GEOFENCES = "geofencesData";

    public static final String DATA_LIFETIME = "lifetimeData";
    public static final String DATA_LOGIN = "loginData";
    public static final String DATA_SERVER = "serverData";
    public static final String DATA_CLIENT_INFO = "clientInfo";

    // TODO: paste link to api here
    public static final String SERVER_API_URL = "link_to_api";
    public static final String SERVER_SERVICE_CLIENT_USER = "ClientUserService";
    public static final String SERVER_METHOD_SET_GEO_POSITION = "SetGeoPosition";

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_LAT = "latitude";
    public static final String EXTRA_LON = "longitude";
    public static final String EXTRA_RAD = "radius";
    public static final String EXTRA_EXP = "expirationDuration";
    public static final String EXTRA_TRS = "transitionType";
}
