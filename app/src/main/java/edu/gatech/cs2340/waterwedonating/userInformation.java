package edu.gatech.cs2340.waterwedonating;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Object class used to represent user information by
 * the data provided
 */
public class userInformation implements Serializable {

    private String name;
    private String emailOrUsername;
    private String type;
    private String id;

    /**
     * No-arg constructor
     */
    public userInformation(){

    }

    /**
     * Constructor that initializes the parameters
     * for a given user
     * @param type string represents type of user
     * @param name string represents user name
     * @param emailOrUsername string represents users email or unique  username
     * @param id string represents user authentication id
     */
    public userInformation(String type, String name, String emailOrUsername, String id) {
        this.type = type;
        this.name = name;
        this.emailOrUsername = emailOrUsername;
        this.id = id;
    }

    /**
     * Getter for email or username
     * @return string email or Username
     */
    public String getEmailOrUsername() {
        return emailOrUsername;
    }

    /**
     * @param emailOrUsername
     * Accepts string email or username to set
     */
    public void setEmailOrUsername(String emailOrUsername) {
        this.emailOrUsername = emailOrUsername;
    }

    /**
     * Getter for name
     * @return string name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name
     * @param name accepts string name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for type
     * @return string type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for type
     * @param type accepts string type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for id
     * @return string id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for id
     * @param id accepts string id
     */
    public void setId(String id) {
        this.id = id;
    }
}
