package ru.menu4me.extensions.location;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

import java.util.HashMap;
import java.util.Map;

public class ExtensionContext extends FREContext {
    private String tag = "(Extension Context) ";

    @Override
    public Map<String, FREFunction> getFunctions() {
        Log.d(Constants.TAG, tag + "getFunctions");

        Map<String, FREFunction> functions = new HashMap<>();
        functions.put("registerUpdates", new RegisterUpdates());
        functions.put("unregisterUpdates", new UnregisterUpdates());
        functions.put("addGeofence", new AddGeofence());
        functions.put("registerNewList", new RegisterNewList());
        functions.put("initData", new InitData());
        functions.put("sendUpdateIntent", new SendUpdateIntent());
        return functions;
    }

    @Override
    public void dispose() {
        Log.d(Constants.TAG, tag + "dispose()'");
    }
}
