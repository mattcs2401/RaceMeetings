package com.mcssoft.racemeetings.meeting;

/*
 * Utility class to model:
 <Meeting Id="89226">
 <MeetingDate>2017-02-11</MeetingDate>
 <TrackName>Doomben</TrackName>
 <ClubName>Brisbane Racing Club@Doomben</ClubName>
 <RacingStatus>Metropolitan</RacingStatus>
 <NumberOfRaces>9</NumberOfRaces>
 <IsBarrierTrial>false</IsBarrierTrial>
 </Meeting>
 */

public class MeetingDetails {

    public MeetingDetails() { }

    public MeetingDetails(String[] details) {
        // TBA
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
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

    private String meetingId;
    private String meetingDate;
    private String trackName;
    private String clubName;
    private String racingStatus;
    private String numberOfRaces;
    private String isBarrierTrial;
}
