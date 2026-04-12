package edu.jsu.mcis.cs408.crosswordmagic;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WebServiceDAO {

    private final String ROOT_URL =
            "http://ec2-3-137-195-31.us-east-2.compute.amazonaws.com:8080/CrosswordMagicServer/puzzle";

    public JSONArray list() {

        JSONArray result = null;

        try {
            ExecutorService pool = Executors.newSingleThreadExecutor();
            Future<String> pending = pool.submit(new CallableHTTPRequest(ROOT_URL));
            String response = pending.get();
            pool.shutdown();

            result = new JSONArray(response);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String get(int id) {

        String url = ROOT_URL + "?id=" + id;
        String result = "";

        try {
            ExecutorService pool = Executors.newSingleThreadExecutor();
            Future<String> pending = pool.submit(new CallableHTTPRequest(url));
            result = pending.get();
            pool.shutdown();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static class CallableHTTPRequest implements Callable<String> {

        private String urlString;

        public CallableHTTPRequest(String urlString) {
            this.urlString = urlString;
        }

        @Override
        public String call() {

            StringBuilder response = new StringBuilder();

            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );

                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return response.toString();
        }
    }
}