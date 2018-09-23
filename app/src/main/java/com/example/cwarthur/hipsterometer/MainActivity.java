package com.example.cwarthur.hipsterometer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getAlbum("20r762YmB5HeofjMCiPMLv");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("Done");
    }

    public void getAlbum(String albumID) {
        //API URL for albums https://api.spotify.com/v1/albums/{id}
        //My Dark Twisted Fantasy = 20r762YmB5HeofjMCiPMLv

        //client ID: c54c265e6c134dfda74581fb624bf394
        //client secret: 04a7173d1e18430d8546b162aa56a957


        //construct URL to hold Kanye's URL
        URL kanye = null;

        //construct URL for requesting an album from Spotify
        String darkTwisted = "https://api.spotify.com/v1/albums/" + albumID;

        //try to make a URL object
        try {
            kanye = new URL(darkTwisted);
        }

        //catch a malformed URL exception
        catch (MalformedURLException exception) {
            System.out.println("Fuck Kanye");
            return;
        }

        //construct empty URLConnection and InputStream
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        //attempt to open URLConnection
        try {
            urlConnection = (HttpURLConnection) kanye.openConnection();
            urlConnection.setRequestMethod("GET");
//            urlConnection.setRequestProperty("Authorization: Bearer {your access token}")

            urlConnection.connect();

            int httpResponse = urlConnection.getResponseCode();
            System.out.println(httpResponse);

            inputStream = urlConnection.getInputStream();
        }

        //catch IO exceptions from URL opening
        catch (IOException e) {
            System.out.println("URL didn't work");
        }

        //close everything that needs to be closed
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                try {
                    inputStream.close();
                } catch (IOException io) {
                    System.out.println("Didn't close");
                }
            }
        }

        StringBuilder output = new StringBuilder();

        try {
            if (inputStream != null) {
                //bytes to characters
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                //characters to chunks
                BufferedReader reader = new BufferedReader(inputStreamReader);

                //read a line
                String line = reader.readLine();

                //keep going until you run out of lines
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
        } catch (IOException e) {
            System.out.println("readLine did some shit");
        }

        System.out.println(output);
    }
}
