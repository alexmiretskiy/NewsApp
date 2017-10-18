package com.example.miret.newsapp;

class News {

  private String sectionName;
  private String webTitle;
  private String webPublicationDate;
  private String webUrl;

  News(String sectionName, String webTitle, String webPublicationDate, String webUrl) {
    this.sectionName = sectionName;
    this.webTitle = webTitle;
    this.webPublicationDate = webPublicationDate;
    this.webUrl = webUrl;
  }

  String getSectionName() {
    return sectionName;
  }

  String getWebTitle() {
    return webTitle;
  }

  String getWebPublicationDate() {
    return webPublicationDate;
  }

  String getWebUrl() {
    return webUrl;
  }
}
