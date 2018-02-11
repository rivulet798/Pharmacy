package com.epam.service.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CaptureVerifyer {
    public static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public static boolean verify(String gRecaptchaResponse) {
        if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0) {
            return false;
        }

        try {
            URL verifyUrl = new URL(SITE_VERIFY_URL);

            // Open a Connection to URL above.
            HttpsURLConnection conn = (HttpsURLConnection) verifyUrl.openConnection();

            // Add the Header informations to the Request to prepare send to the server.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            // Data will be sent to the server.
            String postParams = "secret=" + Constants.SECRET_KEY + "&response=" + gRecaptchaResponse;

            // Send Request
            conn.setDoOutput(true);

            // Get the output stream of Connection.
            // Write data in this stream, which means to send data to Server.
            OutputStream outStream = conn.getOutputStream();
            outStream.write(postParams.getBytes());

            outStream.flush();
            outStream.close();

            // Response code return from Server.
            int responseCode = conn.getResponseCode();

            // Get the Input Stream of Connection to read data sent from the Server.
            InputStream is = conn.getInputStream();

            JsonReader jsonReader = new JsonReader(new InputStreamReader(is));
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(jsonReader);
            jsonReader.close();

            boolean success = Boolean.parseBoolean((jsonElement.getAsJsonObject().get("success")).toString());
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
