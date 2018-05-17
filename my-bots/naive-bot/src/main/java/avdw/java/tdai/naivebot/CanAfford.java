package avdw.java.tdai.naivebot;

import avdw.java.tdai.naivebot.entities.GameState;
import avdw.java.tdai.naivebot.enums.BuildingType;
import avdw.java.tdai.naivebot.enums.PlayerType;

public class CanAfford extends ABehaviourTree<GameState> {
    private BuildingType buildingType;

    public CanAfford(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    @Override
    public Status process(GameState state) {
        return (state.getEnergyFor(PlayerType.A) > state.getBuildingPrice(buildingType)) ? Status.Success : Status.Failure;
    }
}
