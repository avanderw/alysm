package avdw.java.entelect.core.behaviour.filter;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.Operation;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.PlayerType;
import org.pmw.tinylog.Logger;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LaneSelector extends ABehaviourTree<GameState> {
    private String filterMessage;
    final LaneFilter[] laneFilters;

    public LaneSelector(String filterMessage, LaneFilter... laneFilters) {
        this.filterMessage = filterMessage;
        this.laneFilters = laneFilters;
    }

    public LaneSelector(String shorthand) {
        List<LaneFilter> extractedFilters = new ArrayList();
        String[] players = shorthand.split(";");
        Arrays.stream(players).forEach(player -> {
            player = player.trim();
            PlayerType playerType;
            switch (player.charAt(0)) {
                case 'a':
                case 'A':
                    playerType = PlayerType.A;
                    break;
                case 'b':
                case 'B':
                    playerType = PlayerType.B;
                    break;
                default:
                    throw new RuntimeException(shorthand);
            }
            String[] buildings = player.substring(2, player.length() - 1).split(",");
            Arrays.stream(buildings).forEach(building -> {
                building=  building.trim();
                String[] operations = building.split(" ");
                BuildingType buildingType;
                switch (operations[0].trim().toUpperCase()) {
                    case "E":
                        buildingType = BuildingType.ENERGY;
                        break;
                    case "A":
                        buildingType = BuildingType.ATTACK;
                        break;
                    case "D":
                        buildingType = BuildingType.DEFENSE;
                        break;
                    default:
                        throw new RuntimeException(shorthand);
                }

                Operation operation = Operation.map(operations[1].trim());
                extractedFilters.add(new LaneFilter(playerType, buildingType, operation, Integer.parseInt(operations[2].trim())));
            });
        });

        laneFilters = extractedFilters.toArray(new LaneFilter[]{});
        filterMessage = shorthand;
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
            Logger.debug(String.format("[FAILURE] Select a lane where %s", filterMessage));
            return Status.Failure;
        } else {
            Logger.debug(String.format("[SUCCESS] Select a lane where %s", filterMessage));
            return Status.Success;
        }
    }
}
