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

    private static final String ROOT_URL =
            "http://ec2-3-137-195-31.us-east-2.compute.amazonaws.com:8080/CrosswordMagicServer/puzzle";

    public JSONArray list() {

        JSONArray result = new JSONArray();

        try {
            ExecutorService pool = Executors.newSingleThreadExecutor();
            Future<String> task = pool.submit(new CallableHTTPRequest(ROOT_URL));

            String response = task.get();
            pool.shutdown();

            if (response != null && !response.trim().isEmpty()) {
                result = new JSONArray(response);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String get(int id) {

        String result = "";
        String url = ROOT_URL + "?id=" + id;

        try {
            ExecutorService pool = Executors.newSingleThreadExecutor();
            Future<String> task = pool.submit(new CallableHTTPRequest(url));

            result = task.get();
            pool.shutdown();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static class CallableHTTPRequest implements Callable<String> {

        private final String urlString;

        public CallableHTTPRequest(String urlString) {
            this.urlString = urlString;
        }

        @Override
        public String call() {

            StringBuilder response = new StringBuilder();
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );

                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                if (connection != null) {
                    connection.disconnect();
                }
            }

            return response.toString();
        }
    }
}