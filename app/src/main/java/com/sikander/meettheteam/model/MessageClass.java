package com.sikander.meettheteam.model;
import java.util.Date;

public class MessageClass {
    private String messageId;
    private String messageText;
    private String messageUser;
    private long messageTime;
    public MessageClass() {
    }

    public MessageClass(String messageId, String messageText, String messageUser) {
        this.messageId = messageId;
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageTime = new Date().getTime();
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
