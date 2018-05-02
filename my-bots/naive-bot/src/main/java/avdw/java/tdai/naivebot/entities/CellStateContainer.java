package avdw.java.tdai.naivebot.entities;

import avdw.java.tdai.naivebot.entities.Building;
import avdw.java.tdai.naivebot.enums.PlayerType;

import java.util.ArrayList;
import java.util.List;

public class CellStateContainer {
    public int x;
    public int y;
    public PlayerType cellOwner;
    protected List<Building> buildings;
    protected List<Missile> missiles;

    public CellStateContainer(int x, int y, PlayerType cellOwner) {
        this.x = x;
        this.y = y;
        this.cellOwner = cellOwner;
        this.buildings = new ArrayList<Building>();
        this.missiles = new ArrayList<Missile>();
    }

    public List<Building> getBuildings(){
        return this.buildings;
    }
}
