package avdw.java.tdai.naivebot;

import avdw.java.tdai.naivebot.entities.GameState;
import avdw.java.tdai.naivebot.enums.Operation;
import avdw.java.tdai.naivebot.enums.PlayerType;

public class Energy extends ABehaviourTree<GameState> {
    private Operation operation;
    private int value;

    public Energy(Operation operation, int value) {
        this.operation = operation;
        this.value = value;
    }

    @Override
    public Status process(GameState state) {
        switch (operation) {
            case LESS_THAN:
                return (state.getEnergyFor(PlayerType.A) < value) ? Status.Success : Status.Failure;
            case GREATER_THAN:
                return (state.getEnergyFor(PlayerType.A) > value) ? Status.Success : Status.Failure;
        }
        return Status.Failure;
    }
}
