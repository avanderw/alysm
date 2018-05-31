package avdw.java.entelect.core.behaviour.guard;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.Operation;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.PlayerType;

public class BuildingCount extends ABehaviourTree<GameState> {

    private PlayerType playerType;
    private final BuildingType buildingType;
    private final Operation operation;
    private final Integer count;

    public BuildingCount(PlayerType playerType, BuildingType buildingType, Operation operation, Integer count) {
        this.playerType = playerType;
        this.buildingType = buildingType;
        this.operation = operation;
        this.count = count;
    }

    @Override
    public Status process(GameState state) {
        switch (operation) {
            case GREATER_THAN:
                if (state.countBuildingsFor(playerType, buildingType) > count) {
                    return Status.Success;
                }
                return Status.Failure;
            case LESS_THAN:
                if (state.countBuildingsFor(playerType, buildingType) < count) {
                    return Status.Success;
                }
                return Status.Failure;
        }

        return Status.Failure;
    }
}
