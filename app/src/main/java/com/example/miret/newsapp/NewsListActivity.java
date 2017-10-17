package com.example.miret.newsapp;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
  String requestUrl;

  TextView emptyStateTextView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.news_list_activity);

    Intent intent = getIntent();
    requestUrl = intent.getStringExtra("requestUrl");

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
    return new NewsLoader(this, requestUrl);
  }

  @Override
  public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {
    View loadingIndicator = findViewById(R.id.loading_indicator);
    loadingIndicator.setVisibility(View.GONE);

    emptyStateTextView.setText(R.string.No_news_found);

    adapter.clear();
    if (newsList != null && !newsList.isEmpty()) {
      adapter.addAll(newsList);
    }
  }

  @Override
  public void onLoaderReset(Loader<List<News>> loader) {
    adapter.clear();
  }
}
