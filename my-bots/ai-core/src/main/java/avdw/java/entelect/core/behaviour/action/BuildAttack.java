package avdw.java.entelect.core.behaviour.action;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.CellStateContainer;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.PlayerType;
import org.pmw.tinylog.Logger;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;

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

        Iterator<Integer> lanes = state.selectedLanes.iterator();
        Integer lane = lanes.next();
        Optional<CellStateContainer> cell = state.getGameMap().stream()
                .filter(c -> c.cellOwner == PlayerType.A)
                .filter(c -> c.y == lane)
                .filter(c -> c.getBuildings().isEmpty())
                .min(Comparator.comparingInt(c -> c.x > 1 ? c.x : 10));

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