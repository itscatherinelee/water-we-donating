package edu.gatech.cs2340.waterwedonating;

/**
 * Object class containing data for each donation
 */
public class donationData {
    private String timestamp;
    private String location;
    private String shortDescription;
    private String fullDescription;
    private String value;
    private String category;

    /**
     * No-arg constructor
     */
    public donationData() {

    }

    /**
     * constructors that initializes donation data based on parameters
     * @param timestamp represents when donation was made
     * @param location represents location
     * @param shortDescription represents description of item
     * @param fullDescription represents more details about item
     * @param value represents value amount of donation
     * @param category represents donation category
     */
    public donationData(String timestamp, String location, String shortDescription, String fullDescription, String value, String category) {
        this.timestamp = timestamp;
        this.location = location;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.value = value;
        this.category = category;
    }

    /**
     * COnstructor accepting only two parameters
     * @param timestamp represents when donation was made
     * @param category represents donation category
     */
    public donationData(String timestamp, String category) {
        this.timestamp = timestamp;
        this.location = null;
        this.shortDescription = null;
        this.fullDescription = null;
        this.value = null;
        this.category = category;
    }


    /**
     * Getter for value
     * @return string value amount
     */
    public String getValue() {
        return value;
    }

    /**
     * Setter for value
     * @param value accepts value amount
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Getter for category
     * @return string category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Setter for category
     * @param category accepts string category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Getter for full description
     * @return string full description
     */
    public String getFullDescription() {
        return fullDescription;
    }

    /**
     * Setter for full description
     * @param fullDescription accepts string fullDescription
     */
    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    /**
     * Getter for short description
     * @return string short description
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Setter for short description
     * @param shortDescription accepts string for short description
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * returns location
     * @return string location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter for locations
     * @param location accepts location string
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter for time stamp
     * @return string timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Setter for timestamp
     * @param timestamp accepts string time stamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
