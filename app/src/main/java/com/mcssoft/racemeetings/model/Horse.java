package com.mcssoft.racemeetings.model;

/**
 * utility class to model the horse, jockey and trainer for the race.
 * Note: Based on 'raceId'.
 */
public class Horse {

    public Horse() {
        jockeyId = 0;
        trainerId = 0;
    }

    public int getRaceId() { return raceId; }

    public void setRaceId(int raceId) { this.raceId = raceId; }

    public int getHorseId() {
        return horseId;
    }

    public void setHorseId(int horseId) {
        this.horseId = horseId;
    }

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public String getHorseWeight() {
        return horseWeight;
    }

    public void setHorseWeight(String horseWeight) {
        this.horseWeight = horseWeight;
    }

    public int getJockeyId() {
        return jockeyId;
    }

    public void setJockeyId(int jockeyId) {
        this.jockeyId = jockeyId;
    }

    public String getJockeyName() {
        return jockeyName;
    }

    public void setJockeyName(String jockeyName) {
        this.jockeyName = jockeyName;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    private int raceId;      // not part of xml schema.
    private int horseId;
    private String horseName;
    private String horseWeight;
    private int jockeyId;
    private String jockeyName;
    private int trainerId;
    private String trainerName;
}

/*
Horses>
 <Horse Id="310788">
   <HorseName>All Girls Rock</HorseName>
   <Weight>56.0</Weight>
   <Jockey Id="540">
     <FullName>Bonnie Thomson</FullName>
   </Jockey>
   <Trainer Id="711">
     <FullName>Milton Baker</FullName>
   </Trainer>
 </Horse>
*/
