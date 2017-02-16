package com.mcssoft.racemeetings.meeting;

/**
 * Utility class to model avaialbe clubs.
 * From: http://www.racingqueensland.com.au/opendatawebservices/calendar.asmx/GetAvailableClubs
 * <Clubs>
     <Club Id="50254">
       <ClubName>Alpha Jockey Club</ClubName>
     </Club>
 */
public class Club {

    public Club() { }

    public Club(int clubId, String clubName) {
        this.clubId = clubId;
        this.clubName = clubName;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    private int clubId;
    private String clubName;
}
