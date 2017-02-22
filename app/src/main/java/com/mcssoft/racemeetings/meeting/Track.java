package com.mcssoft.racemeetings.meeting;

public class Track {

    public Track() { }

    public Track(String trackName, String trackClubName, String trackIsPref) {
        this.trackName = trackName;
        this.trackClubName = trackClubName;
        this.trackisPref = trackIsPref;
    }

    public String getTrackName() { return trackName; }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getTrackClubName() {
        return trackClubName;
    }

    public void setTrackClubName(String trackClubName) {
        this.trackClubName = trackClubName;
    }

    public  String getTrackisPref() { return trackisPref; }

    public void setTrackisPref(String trackisPref) { this.trackisPref = trackisPref; }

    private String trackName;
    private String trackClubName;
    private String trackisPref;
}
