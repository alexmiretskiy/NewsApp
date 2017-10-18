package com.example.miret.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button button = (Button) findViewById(R.id.find_button);
    button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        EditText contentEditText = (EditText) findViewById(R.id.content_edit_text_view);
        EditText apiKeyEditText = (EditText) findViewById(R.id.api_key_edit_text_view);
        Intent newsListActivityIntent = new Intent(MainActivity.this, NewsListActivity.class);
        newsListActivityIntent.putExtra("content", contentEditText.getText().toString());
        newsListActivityIntent.putExtra("apiKey", apiKeyEditText.getText().toString());
        String s = getString(R.string.settings_search_content_default);
        startActivity(newsListActivityIntent);
      }
    });
  }
}
