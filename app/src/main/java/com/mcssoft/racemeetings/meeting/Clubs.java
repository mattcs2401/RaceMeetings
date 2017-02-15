package com.mcssoft.racemeetings.meeting;

/**
 * Utility class to model avaialbe clubs.
 * From: http://www.racingqueensland.com.au/opendatawebservices/calendar.asmx/GetAvailableClubs
 */
public class Clubs {

    public Clubs() { }

    public Clubs(int clubId, int clubName) {
        this.clubId = clubId;
        this.clubName = clubName;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public int getClubName() {
        return clubName;
    }

    public void setClubName(int clubName) {
        this.clubName = clubName;
    }

    private int clubId;
    private int clubName;
}
