package edu.gatech.cs2340.waterwedonating;

import android.os.Parcelable;

import java.io.Serializable;

public class userInformation implements Serializable {

    private String name;
    private String emailOrUsername;
    private String type;
    private String id;

    public userInformation(){

    }

    public userInformation(String type, String name, String emailOrUsername, String id) {
        this.type = type;
        this.name = name;
        this.emailOrUsername = emailOrUsername;
        this.id = id;
    }

    public String getEmailOrUsername() {
        return emailOrUsername;
    }

    public void setEmailOrUsername(String emailOrUsername) {
        this.emailOrUsername = emailOrUsername;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
