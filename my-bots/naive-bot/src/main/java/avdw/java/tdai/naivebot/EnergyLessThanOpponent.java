package avdw.java.tdai.naivebot;

import avdw.java.tdai.naivebot.entities.GameState;
import avdw.java.tdai.naivebot.enums.PlayerType;

public class EnergyLessThanOpponent extends ABehaviourTree<GameState> {

    public EnergyLessThanOpponent() {
        super();
    }

    @Override
    public Status process(GameState state) {
        if (state.getEnergyFor(PlayerType.A) < state.getEnergyFor(PlayerType.B)) {
            return Status.Success;
        } else {
            return Status.Failure;
        }
    }
}
