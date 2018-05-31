package avdw.java.entelect.core.behaviour.action;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.LaneType;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.CellStateContainer;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.PlayerType;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Build extends ABehaviourTree<GameState> {
    private final BuildingType buildingType;
    private final LaneType myLane;
    private final LaneType theirLane;

    public Build(BuildingType buildingType, LaneType myLane, LaneType theirLane) {

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

        Set<Integer> myLanes = selectLanesForPlayer(state, PlayerType.A, myLane);
        myLanes.retainAll(state.getGameMap().stream()
                .filter(cell -> cell.cellOwner == PlayerType.A)
                .filter(cell -> cell.getBuildings().isEmpty())
                .map(cell -> cell.y)
                .collect(Collectors.toList()));
        if (myLanes.isEmpty()) {
            return Status.Failure;
        }

        Set<Integer> theirLanes = selectLanesForPlayer(state, PlayerType.B, theirLane);
        if (theirLanes.isEmpty()) {
            return Status.Failure;
        }

        Set<Integer> unionLanes = new HashSet(myLanes);
        unionLanes.retainAll(theirLanes);
        if (unionLanes.isEmpty()) {
            return Status.Failure;
        }

        Integer lane = unionLanes.iterator().next();
        List<CellStateContainer> cells = state.getGameMap().stream()
                .filter(cell -> cell.y == lane)
                .filter(cell -> cell.cellOwner == PlayerType.A)
                .filter(cell -> cell.getBuildings().isEmpty())
                .collect(Collectors.toList());
        if (cells.isEmpty()) {
            return Status.Failure;
        }

        CellStateContainer cell;
        if (buildingType != BuildingType.DEFENSE) {
            cell = cells.stream().min(Comparator.comparingInt(c -> c.x)).get();
        } else {
            cell = cells.stream().max(Comparator.comparingInt(c -> c.x)).get();
        }

        state.command = buildCommand(cell.x, cell.y, buildingType);
        return Status.Success;
    }

    private Set<Integer> selectLanesForPlayer(GameState state, PlayerType playerType, LaneType laneType) {
        Set<Integer> lanes = new HashSet();
        switch (laneType) {
            case ANY:
                lanes.addAll(state.getGameMap().stream().map(cell -> cell.y).collect(Collectors.toList()));
                break;
            case EMPTY:
                lanes.addAll(selectLanes(state, playerType, BuildingType.EMPTY));
                lanes.removeAll(selectLanes(state, playerType, BuildingType.DEFENSE));
                lanes.removeAll(selectLanes(state, playerType, BuildingType.ATTACK));
                lanes.removeAll(selectLanes(state, playerType, BuildingType.ENERGY));
                break;
            case ATTACKING:
                lanes.addAll(selectLanes(state, playerType, BuildingType.ATTACK));
                break;
            case NOT_ATTACKING:
                lanes.addAll(state.getGameMap().stream().map(cell -> cell.y).collect(Collectors.toList()));
                lanes.removeAll(selectLanes(state, playerType, BuildingType.ATTACK));
                break;
            case DEFENDING:
                lanes.addAll(selectLanes(state, playerType, BuildingType.DEFENSE));
                break;
            case NOT_DEFENDING:
                lanes.addAll(state.getGameMap().stream().map(cell -> cell.y).collect(Collectors.toList()));
                lanes.removeAll(selectLanes(state, playerType, BuildingType.DEFENSE));
                break;
            case ONLY_ENERGY:
                lanes.addAll(selectLanes(state, playerType, BuildingType.ENERGY));
                lanes.removeAll(selectLanes(state, playerType, BuildingType.DEFENSE));
                lanes.removeAll(selectLanes(state, playerType, BuildingType.ATTACK));
                break;
            case ONLY_ATTACKING:
                lanes.addAll(selectLanes(state, playerType, BuildingType.ATTACK));
                lanes.removeAll(selectLanes(state, playerType, BuildingType.DEFENSE));
                lanes.removeAll(selectLanes(state, playerType, BuildingType.ENERGY));
                break;
        }

        return lanes;
    }

    private List<Integer> selectLanes(GameState state, PlayerType playerType, BuildingType buildingType) {
        return state.getGameMap().stream()
                .filter(cell -> cell.cellOwner == playerType)
                .filter(cell -> cell.getBuildings().isEmpty() == (buildingType == BuildingType.EMPTY))
                .filter(cell -> buildingType == BuildingType.EMPTY ? true : cell.getBuildings().stream().anyMatch(building -> building.buildingType == buildingType))
                .map(cell -> cell.y)
                .collect(Collectors.toList());
    }

    private boolean isSpaceToBuild(GameState state) {
        long numEmptyCells = state.getGameMap().stream()
                .filter(cell -> cell.cellOwner == PlayerType.A)
                .filter(cell -> cell.getBuildings().isEmpty())
                .count();

        return (numEmptyCells != 0);
    }

    static String buildCommand(int x, int y, BuildingType buildingType) {
        return String.format("%s,%d,%s", String.valueOf(x), y, buildingType.getCommandCode());
    }

}
