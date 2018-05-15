package avdw.java.tdai.naivebot;

import avdw.java.tdai.naivebot.entities.GameState;
import avdw.java.tdai.naivebot.enums.PlayerType;

public class EnergyLessThanOpposition extends ABehaviourTree {
    private GameState gameState;

    public EnergyLessThanOpposition(GameState gameState) {
        super();
        this.gameState = gameState;
    }

    @Override
    public Status process() {
        if (gameState.getEnergyFor(PlayerType.A) < gameState.getEnergyFor(PlayerType.B)) {
            return Status.Success;
        } else {
            return Status.Failure;
        }
    }
}
