package com.mcssoft.racemeetings.utility;

import android.util.Log;

import com.mcssoft.racemeetings.model.Club;
import com.mcssoft.racemeetings.model.Horse;
import com.mcssoft.racemeetings.model.Meeting;
import com.mcssoft.racemeetings.model.Race;
import com.mcssoft.racemeetings.model.Track;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;

public class XMLParser {

    public XMLParser(InputStream inStream) {
        initialise(inStream);
    }

    public ArrayList<Club> parseClubsXml() {
        ArrayList<Club> clubs = null;
        Club club = null;
        try {
            String elementName;
            int eventType = parser.getEventType();
            while(eventType != parser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        //regions = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        elementName = parser.getName();
                        if(elementName.equals("Clubs")) {
                            clubs = new ArrayList<>();
                        }
                        else if (elementName.equals("Club")) {
                            club = new Club();
                            club.setClubId(Integer.parseInt(parser.getAttributeValue(null, "Id")));
                        }
                        else if(elementName.equals("ClubName")) {
                            club.setClubName(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        elementName = parser.getName();
                        if(elementName.equals("Club") && club != null) {
                            clubs.add(club);
                        }
                }
                eventType = parser.next();
            }
        } catch(XmlPullParserException ex) {
            Log.d("", ex.getMessage());
        } finally {
            return clubs;
        }
    }

    public ArrayList<Track> parseTracksXml() {
        ArrayList<Track> tracks = null;
        Track track = null;
        try {
            String elementName;
            int eventType = parser.getEventType();
            while(eventType != parser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        //regions = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        elementName = parser.getName();
                        if(elementName.equals("Tracks")) {
                            tracks = new ArrayList<>();
                        }
                        else if (elementName.equals("Track")) {
                            track = new Track();
                        }
                        else if(elementName.equals("TrackName")) {
                            track.setTrackName(parser.nextText());
                        }
                        else if(elementName.equals("TrackClubName")) {
                            track.setTrackClubName(parser.nextText());
                        }
                        else if(elementName.equals("TrackIsPref")) {
                            track.setTrackisPref(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        elementName = parser.getName();
                        if(elementName.equals("Track") && track != null) {
                            tracks.add(track);
                        }
                }
                eventType = parser.next();
            }
        } catch(XmlPullParserException ex) {
            Log.d("", ex.getMessage());
        } finally {
            return tracks;
        }
    }

    public ArrayList<Meeting> parseMeetingsXml() {
        ArrayList<Meeting> meetings = null;
        Meeting meeting = null;
        try {
            String elementName;
            int eventType = parser.getEventType();
            while(eventType != parser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        //regions = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        elementName = parser.getName();
                        if(elementName.equals("Meetings")) {
                            meetings = new ArrayList<>();
                        }
                        else if (elementName.equals("Meeting")) {
                            meeting = new Meeting();
                            meeting.setMeetingId(Integer.parseInt(parser.getAttributeValue(null, "Id")));
                        }
                        else if(elementName.equals("MeetingDate")) {
                            meeting.setMeetingDate(parser.nextText());
                        }
                        else if(elementName.equals("TrackName")) {
                            meeting.setTrackName(parser.nextText());
                        }
                        else if(elementName.equals("ClubName")) {
                            meeting.setClubName(parser.nextText());
                        }
                        else if(elementName.equals("RacingStatus")) {
                            meeting.setRacingStatus(parser.nextText());
                        }
                        else if(elementName.equals("NumberOfRaces")) {
                            meeting.setNumberOfRaces(parser.nextText());
                        }
                        else if(elementName.equals("IsBarrierTrial")) {
                            meeting.setIsBarrierTrial(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        elementName = parser.getName();
                        if(elementName.equals("Meeting") && meeting != null) {
                            meetings.add(meeting);
                        }
                }
                eventType = parser.next();
            }
        } catch(XmlPullParserException ex) {
            Log.d("", ex.getMessage());
        } finally {
            return meetings;
        }
    }

    public ArrayList<Race> parseRacesXml() {
        ArrayList<Race> races = null;
        Race race = null;
        try {
            String elementName;
            int eventType = parser.getEventType();
            while(eventType != parser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        //regions = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        elementName = parser.getName();
                        if(elementName.equals("Races")) {
                            races = new ArrayList<>();
                        }
                        else if (elementName.equals("Race")) {
                            race = new Race();
                            race.setRaceId(Integer.parseInt(parser.getAttributeValue(null, "Id")));
                        }
                        else if(elementName.equals("RaceNumber")) {
                            race.setRaceNumber(Integer.parseInt(parser.nextText()));
                        }
                        else if(elementName.equals("RaceName")) {
                            race.setRaceName(parser.nextText());
                        }
                        else if(elementName.equals("RaceTime")) {
                            race.setRaceTime(parser.nextText());
                        }
                        else if(elementName.equals("Class")) {
                            race.setRaceClass(parser.nextText());
                        }
                        else if(elementName.equals("Distance")) {
                            race.setRaceDistance(parser.nextText());
                        }
                        else if(elementName.equals("TrackRating")) {
                            race.setRaceTrackRating(parser.nextText());
                        }
                        else if(elementName.equals("PrizeTotal")) {
                            race.setRacePrizeTotal(parser.nextText());
                        }
                        else if(elementName.equals("BonusType")) {
                            race.setRaceBonusType(parser.nextText());
                        }
                        else if(elementName.equals("BonusTotal")) {
                            race.setRaceBonusTotal(parser.nextText());
                        }
                        else if(elementName.equals("AgeCondition")) {
                            race.setRaceAgeCondition(parser.nextText());
                        }
                        else if(elementName.equals("SexCondition")) {
                            race.setRaceSexCondtion(parser.nextText());
                        }
                        else if(elementName.equals("WeightCondition")) {
                            race.setRaceWeightCondition(parser.nextText());
                        }
                        else if(elementName.equals("ApprenticeClaim")) {
                            race.setRaceApprenticeClaim(parser.nextText());
                        }
                        else if(elementName.equals("StartersFee")) {
                            race.setRaceStartersFee(parser.nextText());
                        }
                        else if(elementName.equals("AcceptanceFee")) {
                            race.setRaceAcceptanceFee(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        elementName = parser.getName();
                        if(elementName.equals("Race") && race != null) {
                            races.add(race);
                        }
                }
                eventType = parser.next();
            }
        } catch(XmlPullParserException ex) {
            Log.d("", ex.getMessage());
        } finally {
            return races;
        }
    }

    public ArrayList<Horse> parseHorseXml(String queryparam) {
        ArrayList<Horse> horses = null;
        Horse horse = null;
        try {
            String elementName;
            int eventType = parser.getEventType();
            while(eventType != parser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        //regions = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        elementName = parser.getName();
                        if(elementName.equals("Horses")) {
                            horses = new ArrayList<>();
                        }
                        else if (elementName.equals("Horse")) {
                            horse = new Horse();
                            horse.setHorseId(Integer.parseInt(parser.getAttributeValue(null, "Id")));
                        }
                        else if(elementName.equals("HorseName")) {
                            horse.setHorseName(parser.nextText());
                        }
                        else if(elementName.equals("Weight")) {
                            horse.setHorseWeight(parser.nextText());
                        }
                        else if(elementName.equals("Jockey")) {
                            horse.setJockeyId(Integer.parseInt(parser.getAttributeValue(null, "Id")));
                        }
                        else if(elementName.equals("FullName") && horse.getJockeyId() > 0 && horse.getTrainerId() == 0) {
                            horse.setJockeyName(parser.nextText());
                        }
                        else if(elementName.equals("Trainer")) {
                            horse.setTrainerId(Integer.parseInt(parser.getAttributeValue(null, "Id")));
                        }
                        else if(elementName.equals("FullName") && horse.getTrainerId() > 0) {
                            horse.setTrainerName(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        elementName = parser.getName();
                        if(elementName.equals("Horse") && horse != null) {
                            horse.setRaceId(Integer.parseInt(queryparam));
                            horses.add(horse);
                        }
                }
                eventType = parser.next();
            }
        } catch(XmlPullParserException ex) {
            Log.d("", ex.getMessage());
        } finally {
            return horses;
        }
    }

    private void initialise(InputStream inStream) {
        try {
            parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inStream, null);

        } catch(XmlPullParserException ex) {
            ex.printStackTrace();
        }
    }

    private XmlPullParser parser;
}
