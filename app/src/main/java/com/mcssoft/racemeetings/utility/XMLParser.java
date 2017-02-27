package com.mcssoft.racemeetings.utility;

import com.mcssoft.racemeetings.meeting.Club;
import com.mcssoft.racemeetings.meeting.Region;
import com.mcssoft.racemeetings.meeting.Track;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;

public class XMLParser {

    public XMLParser(InputStream inStream) {
        initialise(inStream);
    }

    public ArrayList<Region> parseRegionsXml() {
        ArrayList<Region> regions = null;
        Region region = null;
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
                        if(elementName.equals("Regions")) {
                            regions = new ArrayList<>();
                        }
                        else if (elementName.equals("Region")) {
                            region = new Region();
                            region.setRegionId(Integer.parseInt(parser.getAttributeValue(null, "Id")));
                        }
                        else if(elementName.equals("RegionName")) {
                            region.setRegionName(parser.nextText());
                        }
                        else if(elementName.equals("RegionShortName")) {
                            region.setRegionSName(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        elementName = parser.getName();
                        if(elementName.equals("Region") && regions != null) {
                            regions.add(region);
                        }
                }
                eventType = parser.next();
            }
        } catch(XmlPullParserException ex) {
            ex.printStackTrace();
        } finally {
            return regions;
        }
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
            ex.printStackTrace();
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
            ex.printStackTrace();
        } finally {
            return tracks;
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
