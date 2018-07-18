package avdw.java.entelect.core.behaviour.action;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.state.*;
import org.pmw.tinylog.Logger;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;

public class BuildBuilding extends ABehaviourTree<GameState> {
    private final BuildingType buildingType;
    private final Direction direction;

    public BuildBuilding(BuildingType buildingType, Direction direction) {
        this.buildingType = buildingType;
        this.direction = direction;
    }

    @Override
    public Status process(GameState state) {
        if (state.selectedLanes.isEmpty()) {
            Logger.debug(String.format("[FAILURE] Building %s on the %s", buildingType, direction));
            return Status.Failure;
        }

        if (state.getEnergyFor(PlayerType.A) < state.getBuildingPrice(buildingType)) {
            Logger.debug(String.format("[FAILURE] Building %s on the %s", buildingType, direction));
            return Status.Failure;
        }

        Optional<CellStateContainer> cell = Optional.empty();
        Iterator<Integer> lanes = state.selectedLanes.iterator();
        if (!cell.isPresent() && lanes.hasNext()) {
            Integer lane = lanes.next();
            switch (direction) {
                case LEFT:
                    cell = state.getGameMap().stream()
                            .filter(c->c.cellOwner == PlayerType.A)
                            .filter(c -> c.y == lane)
                            .filter(c -> c.getBuildings().isEmpty())
                            .min(Comparator.comparingInt(c -> c.x));
                    break;
                case RIGHT:
                    cell = state.getGameMap().stream()
                            .filter(c->c.cellOwner == PlayerType.A)
                            .filter(c -> c.y == lane)
                            .filter(c -> c.getBuildings().isEmpty())
                            .max(Comparator.comparingInt(c -> c.x));
                    break;
            }
        }

        if (cell.isPresent()) {
            state.command = buildCommand(cell.get().x, cell.get().y, buildingType);
            Logger.debug(String.format("[SUCCESS] Building %s on the %s at [%s, %s]", buildingType, direction, cell.get().x, cell.get().y));
            return Status.Success;
        } else {
            Logger.debug(String.format("[FAILURE] Building %s on the %s", buildingType, direction));
            return Status.Failure;
        }
    }

    private static String buildCommand(int x, int y, BuildingType buildingType) {
        return String.format("%s,%d,%s", String.valueOf(x), y, buildingType.getCommandCode());
    }
}
