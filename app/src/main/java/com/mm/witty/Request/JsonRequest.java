package com.mm.witty.Request;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.mm.witty.Controller.JsonController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by MacBook on 8/3/17.
 */

public class JsonRequest extends JsonController {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String readJSONFeed(final String address) {
        StringBuilder sb = new StringBuilder();
        try (InputStream is = openHttpConnection(address)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            char buff[] = new char[100];
            int size;
            while ((size = reader.read(buff, 0, 100)) != -1) {
                sb.append(buff, 0, size);
            }
            is.close();
        } catch (IOException ex) {
            Log.d("Network", ex.getLocalizedMessage());
        }
        return sb.toString();
    }
}