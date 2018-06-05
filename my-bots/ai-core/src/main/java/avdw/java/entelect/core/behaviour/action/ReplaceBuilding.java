package avdw.java.entelect.core.behaviour.action;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.CellStateContainer;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.PlayerType;

import java.util.Optional;

public class ReplaceBuilding extends ABehaviourTree<GameState> {
    private final BuildingType obsolete;
    private BuildingType replacement;

    public ReplaceBuilding(BuildingType obsolete, BuildingType replacement) {
        this.obsolete = obsolete;
        this.replacement = replacement;
    }

    @Override
    public Status process(GameState state) {
        Optional<CellStateContainer> replaceCell = state.getGameMap().stream()
                .filter(cell -> cell.cellOwner == PlayerType.A)
                .filter(cell -> cell.getBuildings().stream().anyMatch(building -> building.buildingType == obsolete))
                .findAny();

        if (!replaceCell.isPresent()) {
            return Status.Failure;
        }

        state.command = BuildBuildingV1.buildCommand(replaceCell.get().x, replaceCell.get().y, replacement);
        return Status.Success;
    }
}
