package com.example.miret.newsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

  private String primaryRequestUrl = "https://content.guardianapis.com/search?q=box&api-key=test";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button button = (Button) findViewById(R.id.find_button);
    button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        EditText editText = (EditText) findViewById(R.id.edit_text_view);
        Intent newsListActivityIntent = new Intent(MainActivity.this, NewsListActivity.class);
        newsListActivityIntent.putExtra("requestUrl", primaryRequestUrl);
        startActivity(newsListActivityIntent);
      }
    });
  }
}
