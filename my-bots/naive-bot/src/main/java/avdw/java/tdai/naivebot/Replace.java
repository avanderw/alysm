package avdw.java.tdai.naivebot;

import avdw.java.tdai.naivebot.entities.CellStateContainer;
import avdw.java.tdai.naivebot.entities.GameState;
import avdw.java.tdai.naivebot.enums.BuildingType;
import avdw.java.tdai.naivebot.enums.PlayerType;

import java.util.Optional;
import java.util.stream.Collectors;

public class Replace extends ABehaviourTree<GameState> {
    private final BuildingType obsolete;
    private BuildingType replacement;

    public Replace(BuildingType obsolete, BuildingType replacement) {
        this.obsolete = obsolete;
        this.replacement = replacement;
    }

    @Override
    public Status process(GameState state) {
        Optional<CellStateContainer> replaceCell = state.getGameMap().stream()
                .filter(cell->cell.cellOwner == PlayerType.A)
                .filter(cell->cell.getBuildings().stream().anyMatch(building->building.buildingType == obsolete))
                .findAny();

        if (!replaceCell.isPresent()) {
            return Status.Failure;
        }

        state.command = Build.buildCommand(replaceCell.get().x, replaceCell.get().y, replacement);
        return Status.Success;
    }
}
