package com.example.miret.newsapp;

import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class QueryUtils {

  private static final String LOG_TAG = QueryUtils.class.getSimpleName();

  private QueryUtils() {
  }

   static List<News> fetchNewsData(String requestUrl) {
    URL url = createUrl(requestUrl);
    String jsonResponse = null;
    try {
      jsonResponse = makeHttpRequest(url);
    } catch (IOException e) {
      Log.e(LOG_TAG, "Error closing input stream", e);
    }

    return extractResultsFromJson(jsonResponse);
  }

  private static URL createUrl(String stringUrl) {
    URL url = null;
    try {
      url = new URL(stringUrl);
    } catch (MalformedURLException e) {
      Log.e(LOG_TAG, "Problem building the URL", e);
    }
    return url;
  }

  private static String makeHttpRequest(URL url) throws IOException {
    String jsonResponse = "";
    if (url == null) {
      return jsonResponse;
    }

    HttpURLConnection urlConnection = null;
    InputStream inputStream = null;
    try {
      urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setReadTimeout(10000);
      urlConnection.setConnectTimeout(15000);
      urlConnection.setRequestMethod("GET");
      urlConnection.connect();

      if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        inputStream = urlConnection.getInputStream();
        jsonResponse = readFromStream(inputStream);
      } else {
        Log.e(LOG_TAG, "Error response code" + urlConnection.getResponseCode());
      }
    } catch (IOException e) {
      Log.e(LOG_TAG, "Problem retrieving the book JSON results.", e);
    } finally {
      if (urlConnection != null) {
        urlConnection.disconnect();
      }
      if (inputStream != null) {
        inputStream.close();
      }
    }
    return jsonResponse;
  }

  private static String readFromStream(InputStream inputStream) throws IOException {
    StringBuilder output = new StringBuilder();
    if (inputStream != null) {
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
          Charset.forName("UTF-8"));
      BufferedReader reader = new BufferedReader(inputStreamReader);
      String line = reader.readLine();
      while (line != null) {
        output.append(line);
        line = reader.readLine();
      }
    }
    return output.toString();
  }

  private static List<News> extractResultsFromJson(String newsJson) {
    if (TextUtils.isEmpty(newsJson)) {
      return null;
    }
    List<News> newsList = new ArrayList<>();
    try {
      JSONObject baseJsonResponse = new JSONObject(newsJson);
      JSONObject responseJsonObject = baseJsonResponse.getJSONObject("response");
      JSONArray newsArray = responseJsonObject.getJSONArray("results");

      for (int i = 0; i < newsArray.length(); i++) {
        JSONObject currentNews = newsArray.getJSONObject(i);
        String sectionName = currentNews.getString("sectionName");
        String webTitle = currentNews.getString("webTitle");
        String webPublicationDate;
        try {
          webPublicationDate = currentNews.getString("webPublicationDate");
        } catch (JSONException e) {
          webPublicationDate = "";
        }
        String webUrl;
        try {
          webUrl = currentNews.getString("webUrl");
        } catch (JSONException e) {
          webUrl = "";
        }

        News news = new News(sectionName, webTitle, webPublicationDate, webUrl);
        newsList.add(news);
      }
    } catch (JSONException e) {
      Log.e(LOG_TAG, "Problem parsing the news JSON results", e);
    }
    return newsList;
  }
}

