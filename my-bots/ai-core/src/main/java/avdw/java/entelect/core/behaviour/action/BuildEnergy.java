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

public class BuildEnergy extends ABehaviourTree<GameState> {
    @Override
    public Status process(GameState state) {
        if (state.selectedLanes.isEmpty()) {
            Logger.debug(String.format("[FAILURE] No lanes to build ENERGY"));
            return Status.Failure;
        }

        if (state.getEnergyFor(PlayerType.A) < state.getBuildingPrice(BuildingType.ENERGY)) {
            Logger.debug(String.format("[FAILURE] Not enough energy to build ENERGY"));
            return Status.Failure;
        }

        Iterator<Integer> lanes = state.selectedLanes.iterator();
        Integer lane = lanes.next();
        Optional<CellStateContainer> cell = state.getGameMap().stream()
                .filter(c -> c.cellOwner == PlayerType.A)
                .filter(c -> c.y == lane)
                .filter(c -> c.getBuildings().isEmpty())
                .min(Comparator.comparingInt(c -> c.x));

        if (cell.isPresent()) {
            state.command = String.format("%s,%s,%s", cell.get().x, cell.get().y, BuildingType.ENERGY.getCommandCode());
            Logger.debug(String.format("[SUCCESS] Building ENERGY at [%s, %s]", cell.get().x, cell.get().y));
            return Status.Success;
        } else {
            Logger.debug(String.format("[FAILURE] Building ENERGY on lane %s", lane));
            return Status.Failure;
        }
    }
}