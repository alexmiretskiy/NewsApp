package com.example.miret.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

  private static final String DATE_SEPARATOR = "T";

  public NewsAdapter(@NonNull Context context, @NonNull List<News> newsList) {
    super(context, 0, newsList);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    View listItemView = convertView;
    if (listItemView == null) {
      listItemView = LayoutInflater.from(getContext())
          .inflate(R.layout.news_list_item, parent, false);
    }

    News currentNews = getItem(position);

    TextView sectionNameTextView = (TextView) listItemView
        .findViewById(R.id.section_name_text_view);
    sectionNameTextView.setText(currentNews.getSectionName());

    TextView webTitleTextView = (TextView) listItemView.findViewById(R.id.web_title_text_view);
    webTitleTextView.setText(currentNews.getWebTitle());

    String originalDate = currentNews.getWebPublicationDate();

    String[] parts = originalDate.split(DATE_SEPARATOR);
    String datePart = parts[0];
    String timePart = parts[1].replace("Z","");
    TextView webPublicationDateTextView = (TextView) listItemView
        .findViewById(R.id.web_publication_date_text_view);
    webPublicationDateTextView.setText(datePart);

    TextView webPublicationTimeTextView = (TextView) listItemView
        .findViewById(R.id.web_publication_time_text_view);
    webPublicationTimeTextView.setText(timePart);
    return listItemView;
  }
}
