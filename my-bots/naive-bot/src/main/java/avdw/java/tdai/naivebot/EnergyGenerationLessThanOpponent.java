package avdw.java.tdai.naivebot;

import avdw.java.tdai.naivebot.entities.GameState;
import avdw.java.tdai.naivebot.enums.PlayerType;

public class EnergyGenerationLessThanOpponent extends ABehaviourTree<GameState> {

    @Override
    public Status process(GameState state) {
        if (state.getEnergyGenerationFor(PlayerType.A) < state.getEnergyGenerationFor(PlayerType.B)) {
            return Status.Success;
        } else {
            return Status.Failure;
        }
    }
}
