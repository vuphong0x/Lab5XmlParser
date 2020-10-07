package com.example.lab5xmlparser.Model;

import java.util.ArrayList;

public class RssObject {
    private String status;
    Feed FeedObject;
    ArrayList< Object > items = new ArrayList < Object > ();


    // Getter Methods

    public String getStatus() {
        return status;
    }

    public Feed getFeed() {
        return FeedObject;
    }

    // Setter Methods

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFeed(Feed feedObject) {
        this.FeedObject = feedObject;
    }
}
class Feed {
    private String url;
    private String title;
    private String link;
    private String author;
    private String description;
    private String image;


    // Getter Methods

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    // Setter Methods

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
