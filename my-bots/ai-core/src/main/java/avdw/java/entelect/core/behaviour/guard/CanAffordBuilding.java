package avdw.java.entelect.core.behaviour.guard;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.PlayerType;

public class CanAffordBuilding extends ABehaviourTree<GameState> {
    private BuildingType buildingType;

    public CanAffordBuilding(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    @Override
    public Status process(GameState state) {
        return (state.getEnergyFor(PlayerType.A) > state.getBuildingPrice(buildingType)) ? Status.Success : Status.Failure;
    }
}
