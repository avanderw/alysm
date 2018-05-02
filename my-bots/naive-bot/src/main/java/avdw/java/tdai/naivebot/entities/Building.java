package avdw.java.tdai.naivebot.entities;

import avdw.java.tdai.naivebot.enums.BuildingType;
import avdw.java.tdai.naivebot.enums.PlayerType;

public class Building extends Cell {

    public int health;
    public int constructionTimeLeft;
    public int price;
    public int weaponDamage;
    public int weaponSpeed;
    public int weaponCooldownTimeLeft;
    public int weaponCooldownPeriod;
    public int destroyScore;
    public int energyGeneratedPerTurn;
    public BuildingType buildingType;
}
