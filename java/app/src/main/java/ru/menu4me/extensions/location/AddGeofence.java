package ru.menu4me.extensions.location;

import android.content.Context;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

public class AddGeofence implements FREFunction {
    private String tag = "(Add Geofence) ";

    /**
     * Аргумент 1 = title (string)
     * Аргумент 2 = id (int)
     * Аргумент 3 = latitude (double)
     * Аргумент 4 = longitude (double)
     * Аргумент 5 = radius (float)
     * Аргумент 6 = expiration duration (int)
     * Аргумент 7 = transition type (int)
     */
    @Override
    public FREObject call(FREContext context, FREObject[] args) {
        Log.d(Constants.TAG, tag + "call()");
        Context appContext = context.getActivity().getApplicationContext();

        try {
            File dataFile = new File(appContext.getFilesDir(), Constants.FILE_DATA);
            AppData data;
            Gson gson = new Gson();
            String output = "";

            String title = args[0].getAsString();
            int id = args[1].getAsInt();
            double lat = args[2].getAsDouble();
            double lon = args[3].getAsDouble();
            float rad = (float) args[4].getAsDouble();
            int exp = args[5].getAsInt();
            int trs = args[6].getAsInt();
            GData gData = new GData(title, id, lat, lon, rad, exp, trs, 5); // <<=== dwell delay is always equal to 5ms
            
            if (dataFile.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(dataFile));
                String result = br.readLine();
                br.close();
                data = gson.fromJson(result, AppData.class);
            } else {
                data = new AppData();
            }

            data.geofences.add(gData);
            output = gson.toJson(data, AppData.class);
            OutputStream os = appContext.openFileOutput(Constants.FILE_DATA, Context.MODE_PRIVATE);
            os.write(output.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FREInvalidObjectException e) {
            e.printStackTrace();
        } catch (FRETypeMismatchException e) {
            e.printStackTrace();
        } catch (FREWrongThreadException e) {
            e.printStackTrace();
        }

        return null;
    }
}
