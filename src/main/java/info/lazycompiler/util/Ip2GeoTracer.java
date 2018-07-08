package info.lazycompiler.util;

import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;

@Component("ip2GeoTracer")
public class Ip2GeoTracer {

    private final String ADDR = System.getProperty("location_retriever_base_url");
    private HttpsURLConnection connection;

    private IpFinder ipFinder = new IpFinder();

    public Ip2GeoTracer() throws Exception {
        URL url = new URL(ADDR + ipFinder.tracePublicIpOfThisDevice() + "/json");
        this.connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
    }

    public String getResponse() throws IOException {
        StringBuilder res = new StringBuilder("");

        if (connection.getResponseCode() == 200) {
            try ( BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {

                String in ;
                while ((in = bufferedReader.readLine()) != null){
                    res.append(in);
                }

                return res.toString();
            }

        }

        throw new ConnectException("Could not reach " + ADDR + ": " + connection.getResponseCode());

    }
}
