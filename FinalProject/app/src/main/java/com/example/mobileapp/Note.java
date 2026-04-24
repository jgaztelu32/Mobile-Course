package com.example.mobileapp;

public class Note {
    private final long id;
    private final String title;
    private final String content;
    private final int category;

    public Note(long id, String title, String content, int category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public int getCategory() { return category; }
}