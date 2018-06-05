package avdw.java.entelect.core.behaviour.filter;

import avdw.java.entelect.core.behaviour.Operation;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.PlayerType;

public class LaneFilter {
    public final PlayerType playerType;
    public final BuildingType buildingType;
    public final Operation operation;
    public final Integer count;

    public LaneFilter(PlayerType playerType, BuildingType buildingType, Operation operation, Integer count) {
        this.playerType = playerType;
        this.buildingType = buildingType;
        this.operation = operation;
        this.count = count;
    }
}
