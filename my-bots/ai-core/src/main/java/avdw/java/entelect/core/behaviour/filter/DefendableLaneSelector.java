package avdw.java.entelect.core.behaviour.filter;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.PlayerType;
import org.pmw.tinylog.Logger;

import java.util.Set;
import java.util.stream.Collectors;

public class DefendableLaneSelector extends ABehaviourTree<GameState> {
    @Override
    public Status process(GameState state) {
        Set<Integer> selectedLanes = state.getGameMap().stream()
                .filter(c -> c.x > 3 && c.x < 6)
                .filter(c -> !c.getMissiles().isEmpty())
                .filter(c -> c.getMissiles().stream().anyMatch(m -> m.isPlayers(PlayerType.B)))
                .filter(c1 -> state.getGameMap().stream()
                        .filter(c2 -> c2.y == c1.y)
                        .filter(c2 -> c2.x == c1.x - 3)
                        .anyMatch(c2 -> c2.getBuildings().isEmpty())

                )
                .filter(c1 -> state.getGameMap().stream()
                        .filter(c2 -> c2.y == c1.y)
                        .filter(c2 -> c2.x < c1.x && c2.x > c1.x - 3)
                        .allMatch(c2 -> c2.getBuildings().isEmpty())
                )
                .filter(c1 -> state.getPlayers().stream().filter(p -> p.playerType == PlayerType.A).mapToInt(p -> p.health).sum() > 25
                        ? state.getGameMap().stream()
                        .filter(c2 -> c2.y == c1.y)
                        .filter(c2 -> c2.x < 2)
                        .anyMatch(c2 -> c2.getBuildings().stream().anyMatch(b -> b.buildingType == BuildingType.ENERGY))
                        : Boolean.TRUE
                )
                .map(c -> c.y)
                .collect(Collectors.toSet());

        if (selectedLanes.isEmpty()) {
            Logger.debug(String.format("[FAILURE] No defendable lanes"));
            return Status.Failure;
        } else {
            Logger.debug(String.format("[SUCCESS] defendable lanes %s", selectedLanes));
            state.selectedLanes = selectedLanes;
            return Status.Success;
        }
    }
}
