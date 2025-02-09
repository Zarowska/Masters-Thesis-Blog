package utils;

import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static io.gatling.javaapi.http.HttpDsl.http;

public class Authentication {

//    static final String host = "https://dev.cirkle.blog";
    static final String host = "http://localhost:8080";
//    static final String host = "http://10.10.10.7:8080";
//    static final String user = "jeanpierre.leclair%40live.com";
    static final String user = "oksana.zarowska%40gmail.com"; //oksana.zarowska@gmail.com
    static final String password = "string";
//    static final String password = "jeanpierre.leclair%40live.com";
    static final String bearerToken = getBearer(host, user, password);

    public static HttpProtocolBuilder httpProtocol = http.baseUrl(Authentication.host).acceptHeader("application/json")
            .header("Authorization", "Bearer " + Authentication.bearerToken);

    private static String getBearer(String host, String username, String password) {
        try {
            // The URL with query parameters
            String urlString = "%s/public/api/auth/basic?username=%s&password=%s".formatted(host, username, password);
            URL url = new URL(urlString);

            // Open connection and cast to HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method to POST
            connection.setRequestMethod("POST");

            // Add the header
            connection.setRequestProperty("accept", "*/*");

            // Since it's a POST, enable output (even though we don't send data)
            connection.setDoOutput(true);

            // Get response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder responseContent = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                responseContent.append(inputLine);
            }
            in.close();

            String body = responseContent.toString();
            int start = body.indexOf("\":\"");
            int end = body.indexOf("\"}");
            String token = responseContent.substring(start + 3, end);
            return token;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
