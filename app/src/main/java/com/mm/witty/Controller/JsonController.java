package com.mm.witty.Controller;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by MacBook on 8/3/17.
 */

public class JsonController {
    public InputStream openHttpConnection(String urlString) throws IOException
    {
        InputStream is = null;
        try {
            int res = -1;    // response
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            if (!(conn instanceof HttpURLConnection))
            {
                throw new IllegalArgumentException("Not an HttpURLConnection");
            }
            HttpURLConnection huc = (HttpURLConnection) conn;
            huc.setAllowUserInteraction(false);
            huc.setInstanceFollowRedirects(true);
            huc.setRequestMethod("GET");
            huc.connect();
            res = huc.getResponseCode();
            if (res == HttpURLConnection.HTTP_OK)
            {
                is = huc.getInputStream();
            }
            huc.disconnect();
        } catch (IOException ex)
        {
            Log.d("Networking", ex.getLocalizedMessage());
            throw ex;
        }
        return is;
    }

}


