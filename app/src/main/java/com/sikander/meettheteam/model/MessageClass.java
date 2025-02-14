package com.sikander.meettheteam.model;
import java.io.Serializable;
import java.util.Date;

public class MessageClass implements Serializable {
    private String id;
    private String message;
    private String user;
    private long time;
    public MessageClass() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public MessageClass(String id, String message, String user, long time) {
        this.id = id;
        this.message = message;
        this.user = user;
        this.time = time;
    }
}
