package avdw.java.entelect.core.behaviour.filter;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.Operation;
import avdw.java.entelect.core.state.GameState;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LaneSelector extends ABehaviourTree<GameState> {
    private LaneFilter[] laneFilters;

    public LaneSelector(LaneFilter... laneFilters) {
        this.laneFilters = laneFilters;
    }

    @Override
    public Status process(GameState state) {
        state.selectedLanes.clear();
        Set<Integer> selectedLanes = new HashSet();

        IntStream.range(0, laneFilters.length).forEach(idx -> {
                    LaneFilter laneFilter = laneFilters[idx];
                    Set<Integer> lanes = state.getGameMap().stream()
                            .filter(c -> c.cellOwner == laneFilter.playerType)
                            .filter(c -> {
                                if (laneFilter.count == 0 || laneFilter.operation == Operation.LESS_THAN) {
                                    return true;
                                } else {
                                    return c.getBuildings().stream().anyMatch(b -> b.buildingType == laneFilter.buildingType);
                                }
                            })
                            .filter(c -> {
                                Long laneBuildingCount = state.getGameMap().stream()
                                        .filter(ce -> ce.cellOwner == laneFilter.playerType)
                                        .filter(ce -> ce.y == c.y)
                                        .filter(ce -> ce.getBuildings().stream().anyMatch(b -> b.buildingType == laneFilter.buildingType))
                                        .count();

                                switch (laneFilter.operation) {
                                    case GREATER_THAN:
                                        return laneBuildingCount > laneFilter.count;
                                    case LESS_THAN:
                                        return laneBuildingCount < laneFilter.count;
                                    case EQUALS:
                                        return laneBuildingCount == laneFilter.count.longValue();
                                }
                                return false;
                            })
                            .map(c -> c.y)
                            .collect(Collectors.toSet());

                    if (selectedLanes.isEmpty() && idx == 0) {
                        selectedLanes.addAll(lanes);
                    } else {
                        selectedLanes.retainAll(lanes);
                    }
                }
        );

        state.selectedLanes = selectedLanes;
        if (selectedLanes.isEmpty()) {
            return Status.Failure;
        } else {
            return Status.Success;
        }
    }
}
