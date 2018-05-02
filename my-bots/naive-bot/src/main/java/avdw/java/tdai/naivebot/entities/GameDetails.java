package avdw.java.tdai.naivebot.entities;

import avdw.java.tdai.naivebot.enums.BuildingType;

import java.util.HashMap;

public class GameDetails {
    public int round;
    public int mapWidth;
    public int mapHeight;
    public HashMap<BuildingType, Integer> buildingPrices;
}

