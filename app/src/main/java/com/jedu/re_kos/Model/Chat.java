package com.jedu.re_kos.Model;

public class Chat {
    private String title;
    private String description;
    private String time;

    public Chat(String senderId, String time, String description, String title) {
        this.senderId = senderId;
        this.time = time;
        this.description = description;
        this.title = title;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    private String senderId;

    public Chat(String title, String description, String time) {
        this.title = title;
        this.description = description;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}