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

    Button buttonSetApiKey = (Button) findViewById(R.id.enter_api_key_button);
    Button buttonUseTestApiKey = (Button) findViewById(R.id.use_test_api_key_button);

    buttonSetApiKey.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        EditText apiKeyEditText = (EditText) findViewById(R.id.api_key_edit_text_view);
        Intent newsListActivityIntent = new Intent(MainActivity.this,
            NewsContentSearchActivity.class);
        newsListActivityIntent.putExtra("apiKey", apiKeyEditText.getText().toString());
        startActivity(newsListActivityIntent);
      }
    });

    buttonUseTestApiKey.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent newsListActivityIntent = new Intent(MainActivity.this,
            NewsContentSearchActivity.class);
        newsListActivityIntent.putExtra("apiKey", "test");
        startActivity(newsListActivityIntent);
      }
    });
  }
}
