package ru.menu4me.extensions.location;

import android.content.Context;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

public class Extension implements FREExtension {
    private String tag = "(Extension) ";

    @Override
    public FREContext createContext(String contextType) {
        Log.d(Constants.TAG, tag + "createContext");
        return  new ExtensionContext();
    }

    @Override
    public void initialize() {
        Log.d(Constants.TAG, tag + "initialize()");
    }

    @Override
    public void dispose() {
        Log.d(Constants.TAG, tag + "dispose()");
    }
}
