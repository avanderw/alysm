package avdw.java.entelect.core.behaviour.action;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.CellStateContainer;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.PlayerType;
import org.pmw.tinylog.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class BuildAttack extends ABehaviourTree<GameState> {
    @Override
    public Status process(GameState state) {
        if (state.selectedLanes.isEmpty()) {
            Logger.debug(String.format("[FAILURE] No lanes to build ATTACK"));
            return Status.Failure;
        }

        if (state.getEnergyFor(PlayerType.A) < state.getBuildingPrice(BuildingType.ATTACK)) {
            Logger.debug(String.format("[FAILURE] Not enough energy to build ATTACK"));
            return Status.Failure;
        }

        final Integer[] minX = {Integer.MAX_VALUE};
        final Integer[] priorityLane = {-1};
        state.selectedLanes.stream().forEach(lane -> {
            List<CellStateContainer> laneCells = state.getGameMap().stream()
                    .filter(c -> c.y == lane)
                    .collect(Collectors.toList());

            OptionalInt lowestX = laneCells.stream()
                    .filter(c -> !c.getMissiles().isEmpty())
                    .filter(c -> c.getMissiles().stream().anyMatch(m -> m.isPlayers(PlayerType.B)))
                    .mapToInt(c -> c.x).min();
            if (lowestX.isPresent()) {
                if (lowestX.getAsInt() < minX[0]) {
                    if (laneCells.stream().filter(c -> c.x == lowestX.getAsInt() - 3).
                            anyMatch(c -> c.getBuildings().isEmpty() || c.x > 7)) {
                        priorityLane[0] = lane;
                        minX[0] = lowestX.getAsInt();
                    }
                }
            }
        });
        Integer lane;
        if (priorityLane[0] == -1) {
            Iterator<Integer> lanes = state.selectedLanes.iterator();
            lane = lanes.next();
        } else {
            lane = priorityLane[0];
        }


        List<CellStateContainer> buildSites = state.getGameMap().stream()
                .filter(c -> c.cellOwner == PlayerType.A)
                .filter(c -> c.y == lane)
                .filter(c -> c.getBuildings().isEmpty())
                .collect(Collectors.toList());
        Logger.debug(String.format("Build sites %s", buildSites));

        Optional<CellStateContainer> missileCell = state.getGameMap().stream()
                .filter(c -> c.y == lane)
                .filter(c -> c.getMissiles().stream()
                        .anyMatch(m -> m.isPlayers(PlayerType.B))
                ).min(Comparator.comparingInt(c -> c.x));
        Logger.debug(String.format("Missile cell %s", missileCell));

        Optional<CellStateContainer> attackCell = state.getGameMap().stream()
                .filter(c -> c.y == lane)
                .filter(c -> c.cellOwner == PlayerType.B)
                .filter(c -> !c.getBuildings().isEmpty())
                .filter(c -> c.getBuildings().get(0).buildingType == BuildingType.ATTACK)
                .min(Comparator.comparingInt(c -> c.x));

        Optional<CellStateContainer> cell = Optional.empty();
        if (missileCell.isPresent()) {
            cell = buildSites.stream().filter(c -> c.x == missileCell.get().x - 3).findAny();
        }

        if (!cell.isPresent() && attackCell.isPresent()) {
            cell = buildSites.stream().filter(c -> c.x == attackCell.get().x - 3).findAny();
        }

        if (!cell.isPresent()) {
            cell = buildSites.stream().max(Comparator.comparingInt(c -> c.x));
        }

        if (cell.isPresent()) {
            state.command = String.format("%s,%s,%s", cell.get().x, cell.get().y, BuildingType.ATTACK.getCommandCode());
            Logger.debug(String.format("[SUCCESS] Building ATTACK at [%s, %s]", cell.get().x, cell.get().y));
            return Status.Success;
        } else {
            Logger.debug(String.format("[FAILURE] Building ATTACK on lane %s", lane));
            return Status.Failure;
        }
    }
}