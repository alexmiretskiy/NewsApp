package com.example.miret.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NewsContentSearchActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.news_content_search_activity);

    Button searchContentButton = (Button) findViewById(R.id.find_button);
    searchContentButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        EditText contentEditText = (EditText) findViewById(R.id.content_edit_text_view);

        Intent newsListActivityIntent = new Intent(NewsContentSearchActivity.this,
            NewsListActivity.class);
        newsListActivityIntent.putExtra("content", contentEditText.getText().toString());
        newsListActivityIntent.putExtra("apiKey", getIntent().getStringExtra("apiKey"));
        startActivity(newsListActivityIntent);
      }
    });
  }
}
