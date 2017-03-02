package com.mcssoft.racemeetings.meeting;

/*
 * Utility class to model:
<Meeting>
  <Meeting Id="88788">
    <MeetingDate>2017-02-18</MeetingDate>
    <TrackName>Gold Coast</TrackName>
    <ClubName>Gold Coast Turf Club</ClubName>
    <RacingStatus>Provincial</RacingStatus>
    <NumberOfRaces>8</NumberOfRaces>
    <IsBarrierTrial>false</IsBarrierTrial>
  </Meeting>
 */

public class Meeting {

    public Meeting() { }

    public Meeting(String[] details) {
        // TBA
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getRacingStatus() {
        return racingStatus;
    }

    public void setRacingStatus(String racingStatus) {
        this.racingStatus = racingStatus;
    }

    public String getNumberOfRaces() {
        return numberOfRaces;
    }

    public void setNumberOfRaces(String numberOfRaces) {
        this.numberOfRaces = numberOfRaces;
    }

    public String getIsBarrierTrial() {
        return isBarrierTrial;
    }

    public void setIsBarrierTrial(String isBarrierTrial) {
        this.isBarrierTrial = isBarrierTrial;
    }

    private int meetingId;
    private String meetingDate;
    private String trackName;
    private String clubName;
    private String racingStatus;
    private String numberOfRaces;
    private String isBarrierTrial;
}
