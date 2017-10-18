package com.example.miret.newsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SettingsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.settings_activity);

    Log.e("LOG", "onCreateSettingsActivity ");
  }

  public static class NewsPreferenceFragment extends PreferenceFragment implements
      OnPreferenceChangeListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.settings_main);

      Log.e("LOG", "onCreateNewsPreferenceFragment ");

      Preference defContent = findPreference(getString(R.string.settings_search_content_key));
      bindPreferenceSummaryToValue(defContent);

      Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
      bindPreferenceSummaryToValue(orderBy);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
      String stringValue = newValue.toString();
      preference.setSummary(stringValue);
      return true;
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
      preference.setOnPreferenceChangeListener(this);
      SharedPreferences preferences = PreferenceManager
          .getDefaultSharedPreferences(preference.getContext());
      String preferenceString = preferences.getString(preference.getKey(), "");
      onPreferenceChange(preference, preferenceString);
    }
  }
}
