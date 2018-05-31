package avdw.java.entelect.core.behaviour.guard;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.Operation;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.PlayerType;

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
