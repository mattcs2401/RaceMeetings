package com.mcssoft.racemeetings.utility;

import com.mcssoft.racemeetings.meeting.Club;
import com.mcssoft.racemeetings.meeting.Region;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;

//http://www.journaldev.com/10653/android-xml-parsing-xmlpullparser-example-tutorial

public class MeetingXMLParser {

    public MeetingXMLParser(InputStream inStream) {
        this.inStream = inStream;
        initialise();
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

    private void initialise() {
        try {
            factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inStream, null);

        } catch(XmlPullParserException ex) {
            ex.printStackTrace();
        }
    }

    private InputStream inStream;
    private XmlPullParserFactory factory;
    private XmlPullParser parser;
}
