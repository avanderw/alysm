package avdw.java.tdai.naivebot;

import avdw.java.tdai.naivebot.entities.CellStateContainer;
import avdw.java.tdai.naivebot.entities.GameState;
import avdw.java.tdai.naivebot.enums.BuildingType;
import avdw.java.tdai.naivebot.enums.MyLane;
import avdw.java.tdai.naivebot.enums.PlayerType;
import avdw.java.tdai.naivebot.enums.TheirLane;

import java.util.List;
import java.util.stream.Collectors;

public class Build extends ABehaviourTree<GameState> {
    private BuildingType buildingType;
    private MyLane myLane;
    private TheirLane theirLane;

    public Build(BuildingType buildingType, MyLane myLane, TheirLane theirLane) {

        this.buildingType = buildingType;
        this.myLane = myLane;
        this.theirLane = theirLane;
    }

    @Override
    public Status process(GameState state) {
        if (state.getEnergyFor(PlayerType.A) < state.getBuildingPrice(buildingType)) {
            return Status.Failure;
        }

        if (!isSpaceToBuild(state)) return Status.Failure;
        if (!isMyLaneValid(state, myLane)) return Status.Failure;
        if (!isTheirLaneValid(state, theirLane)) return Status.Failure;

        return Status.Failure;
    }

    private boolean isMyLaneValid(GameState state, MyLane myLane) {
        BuildingType buildingToCheckFor = null;
        switch (myLane) {
            case DEFENDING:
                return state.getGameMap().stream()
                        .filter(cell -> cell.cellOwner == PlayerType.A)
                        .filter(cell -> !cell.getBuildings().isEmpty())
                        .filter(cell -> cell.getBuildings().stream().anyMatch(building -> building.buildingType == BuildingType.DEFENSE))
                        .count() > 0;
            case ATTACKING:
                List<CellStateContainer> attackBuildings = state.getGameMap().stream()
                        .filter(cell -> cell.cellOwner == PlayerType.A)
                        .filter(cell -> !cell.getBuildings().isEmpty())
                        .filter(cell -> cell.getBuildings().stream().anyMatch(building -> building.buildingType == BuildingType.ATTACK))
                        .collect(Collectors.toList());
                if (attackBuildings.isEmpty()) {
                    return false;
                }

                
            case ONLY_ENERGY:
            case ONLY_ATTACKING:
            case EMPTY:
                return true;
            default:
                return false;
        }
    }

    private boolean isSpaceToBuild(GameState state) {
        long numEmptyCells = state.getGameMap().stream()
                .filter(cell -> cell.cellOwner == PlayerType.A)
                .filter(cell -> cell.getBuildings().isEmpty())
                .count();

        return (numEmptyCells != 0);
    }
}
