package edu.gatech.cs2340.waterwedonating;

public class donationData {
    private String timestamp;
    private String location;
    private String shortDescription;
    private String fullDescription;
    private String value;
    private String category;

    public donationData() {

    }
    public donationData(String timestamp, String location, String shortDescription, String fullDescription, String value, String category) {
        this.timestamp = timestamp;
        this.location = location;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.value = value;
        this.category = category;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
