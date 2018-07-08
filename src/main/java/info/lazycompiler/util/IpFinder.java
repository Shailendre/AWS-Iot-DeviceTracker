package info.lazycompiler.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IpFinder {

    private final String ADDR   = System.getProperty("ip_finder_url");

    private HttpURLConnection connection;

    public IpFinder() throws Exception {
        URL url = new URL(ADDR);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
    }



    public String tracePublicIpOfThisDevice() throws Exception {
        StringBuilder res = new StringBuilder("");

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
            res.append(bufferedReader.readLine());
        }

        // trim to remove extra space from front and end
        res.trimToSize();

        return res.toString();

    }
}
