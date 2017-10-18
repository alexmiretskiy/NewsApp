package com.example.miret.newsapp;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class NewsListActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

  private static final int NEWS_LOADER_ID = 1;
  private NewsAdapter adapter;
  private static final String GUARDIANS_REQUEST_URL = "https://content.guardianapis.com/search";

  TextView emptyStateTextView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.news_list_activity);

    ListView newsListView = (ListView) findViewById(R.id.list_view);

    emptyStateTextView = (TextView) findViewById(R.id.empty_view);
    newsListView.setEmptyView(emptyStateTextView);

    adapter = new NewsAdapter(this, new ArrayList<News>());
    newsListView.setAdapter(adapter);

    newsListView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        News currentNews = adapter.getItem(position);

        Uri newsUri = Uri.parse(currentNews.getWebUrl());

        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
        startActivity(websiteIntent);
      }
    });

    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(
        Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    Log.e("LOG", "initLoader - onCreate");
    if (networkInfo != null && networkInfo.isConnected()) {
      getLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
    } else {
      View loadingIndicator = findViewById(R.id.loading_indicator);
      loadingIndicator.setVisibility(View.GONE);

      emptyStateTextView.setText(R.string.No_internet_connection);
    }
  }

  @Override
  public Loader<List<News>> onCreateLoader(int id, Bundle args) {
    Intent intent = getIntent();
    String searchContent = intent.getStringExtra("content");
    String apiKey = intent.getStringExtra("apiKey");

    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

    if (searchContent != null) {
      sharedPrefs.edit().putString(getString(R.string.settings_search_content_key), searchContent)
          .apply();
    }
    String defContent = sharedPrefs.getString(getString(R.string.settings_search_content_key),
        getString(R.string.settings_search_content_default));

    String orderBy = sharedPrefs.getString(getString(R.string.settings_order_by_key),
        getString(R.string.settings_order_by_default));

    if (apiKey == null || apiKey.isEmpty()) {
      apiKey = sharedPrefs
          .getString(getString(R.string.apikey_key), getString(R.string.apikey_value));
    }

    Uri baseUri = Uri.parse(GUARDIANS_REQUEST_URL);
    Uri.Builder uriBuilder = baseUri.buildUpon();

    uriBuilder.appendQueryParameter("order-by", orderBy);
    uriBuilder.appendQueryParameter("q", defContent);
    uriBuilder.appendQueryParameter("api-key", apiKey);

    Log.e("LOG",
        "onCreateLoader " + uriBuilder.toString() + " " + apiKey);
    return new NewsLoader(this, uriBuilder.toString());
  }

  @Override
  public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {
    View loadingIndicator = findViewById(R.id.loading_indicator);
    loadingIndicator.setVisibility(View.GONE);
    Log.e("LOG", "onLoadFinished");
    emptyStateTextView.setText(R.string.No_news_found);

    adapter.clear();
    if (newsList != null && !newsList.isEmpty()) {
      adapter.addAll(newsList);
    }
  }

  @Override
  public void onLoaderReset(Loader<List<News>> loader) {
    adapter.clear();
    Log.e("LOG", "onLoaderReset");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      Intent settingsIntent = new Intent(this, SettingsActivity.class);
      startActivity(settingsIntent);
    }
    Log.e("LOG", "onOptionsItemSelected");
    return super.onOptionsItemSelected(item);
  }
}
