package com.mcssoft.racemeetings.meeting;

public class Track {

    public Track(String trackName, String trackClubName) {
        this.trackName = trackName;
        this.trackClubName = trackClubName;
    }

    public String getTrackName() {

        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getTrackClubName() {
        return trackClubName;
    }

    public void setTrackClubName(String trackClubName) {
        this.trackClubName = trackClubName;
    }

    private String trackName;
    private String trackClubName;
}
