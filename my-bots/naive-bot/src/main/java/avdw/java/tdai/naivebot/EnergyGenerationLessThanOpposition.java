package avdw.java.tdai.naivebot;

import avdw.java.tdai.naivebot.entities.GameState;

public class EnergyGenerationLessThanOpposition extends ABehaviourTree {
    private GameState gameState;

    public EnergyGenerationLessThanOpposition(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public Status process() {
        return null;
    }
}
