package com.github.zakru.hermittracker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Hermit {

    private static final String API_URL = "https://hermitcraft.com/api/videos?type=%s&start=%s";

    private final Gson gson = new Gson();

    public List<Video> videos(String type, String start) throws IOException {
        URLConnection connection = new URL(String.format(API_URL, URLEncoder.encode(type, "UTF-8"), URLEncoder.encode(start, "UTF-8"))).openConnection();
        connection.addRequestProperty("Accept-Charset", "UTF-8");

        return gson.fromJson(new InputStreamReader(connection.getInputStream()), new TypeToken<ArrayList<Video>>(){}.getType());
    }
}
