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

public class InitData implements FREFunction {
    private static final String tag = "(Init Data) ";

    /**
     * Аргумент 1 = clientInfo (string)
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

            if (dataFile.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(dataFile));
                String result = br.readLine();
                br.close();
                data = gson.fromJson(result, AppData.class);
            } else {
                data = new AppData();
            }

            data.clientInfo = args[0].getAsString();
            output = gson.toJson(data, AppData.class);
            OutputStream os = appContext.openFileOutput(Constants.FILE_DATA, Context.MODE_PRIVATE);
            os.write(output.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FREWrongThreadException e) {
            e.printStackTrace();
        } catch (FREInvalidObjectException e) {
            e.printStackTrace();
        } catch (FRETypeMismatchException e) {
            e.printStackTrace();
        }

        return null;
    }
}
