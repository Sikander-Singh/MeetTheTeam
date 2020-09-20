package com.sikander.meettheteam.model;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

import java.io.Serializable;

public class TeamMember implements Serializable {
    private String id;
    private String name;
    private String dating_preferences;
    private String interests;
    private String personality;
    private String position;

    public TeamMember(){
        //nothing
    }
    public TeamMember(String id,String name, String dating_preferences, String interests, String personality, String position,String profile_image) {
        this.id=id;
        this.name = name;
        this.dating_preferences = dating_preferences;
        this.interests = interests;
        this.personality = personality;
        this.position = position;
        this.profile_image = profile_image;
    }

    private String profile_image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDating_preferences() {
        return dating_preferences;
    }

    public void setDating_preferences(String dating_preferences) {
        this.dating_preferences = dating_preferences;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }
}
