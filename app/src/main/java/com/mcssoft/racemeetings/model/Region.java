package com.mcssoft.racemeetings.model;

/**
 * Utility class to model an avaialbe region.
 * From: http://www.racingqueensland.com.au/opendatawebservices/calendar.asmx/GetAvailableRegions
   <Region Id="1">
     <RegionName>South East Queensland</RegionName>
     <RegionShortName>SEQ</RegionShortName>
   </Region>
 */
public class Region {

    public Region() { }

    public Region(int regionId, String regionName, String regionSName) {
        this.regionId = regionId;
        this.regionName = regionName;
        this.regionSName = regionSName;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionSName() {
        return regionSName;
    }

    public void setRegionSName(String regionSName) {
        this.regionSName = regionSName;
    }

    private int regionId;
    private String regionName;
    private String regionSName;
}
