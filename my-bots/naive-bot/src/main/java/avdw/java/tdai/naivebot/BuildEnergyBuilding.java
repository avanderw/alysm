package avdw.java.tdai.naivebot;

import avdw.java.tdai.naivebot.entities.GameState;
import avdw.java.tdai.naivebot.enums.BuildingType;
import avdw.java.tdai.naivebot.enums.PlayerType;

public class BuildEnergyBuilding extends ABehaviourTree<GameState> {
    private BotResponse botResponse;

    public BuildEnergyBuilding(BotResponse botResponse) {
        super();
        this.botResponse = botResponse;
    }

    @Override
    public Status process(GameState state) {
        if (state.getEnergyFor(PlayerType.A) < state.getBuildingPrice(BuildingType.ENERGY)) {
            return Status.Failure;
        } else {

            return Status.Success;
        }
    }
}
