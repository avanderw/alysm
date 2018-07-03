package avdw.java.entelect.core.behaviour.filter;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.Operation;
import avdw.java.entelect.core.state.GameState;
import org.pmw.tinylog.Logger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LaneSelector extends ABehaviourTree<GameState> {
    private String filterMessage;
    private LaneFilter[] laneFilters;

    public LaneSelector(String filterMessage, LaneFilter... laneFilters) {
        this.filterMessage = filterMessage;
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
            Logger.debug(String.format("[FAILURE] Select a lane where {%s}", filterMessage));
            return Status.Failure;
        } else {
            Logger.debug(String.format("[SUCCESS] Select a lane where {%s}", filterMessage));
            return Status.Success;
        }
    }
}
