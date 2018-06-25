package avdw.java.entelect.core.behaviour.guard;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.Operation;
import avdw.java.entelect.core.state.GameState;

public class CompareGuard extends ABehaviourTree<GameState> {
    private final Long left;
    private final Operation operation;
    private final Long right;

    public CompareGuard(Long left, Operation operation, Long right) {

        this.left = left;
        this.operation = operation;
        this.right = right;
    }

    @Override
    public Status process(GameState state) {
        switch (operation) {
            case GREATER_THAN:
                if (left > right) {
                    return Status.Success;
                }
            case EQUALS:
                if (left == right) {
                    return Status.Success;
                }
            case LESS_THAN_OR_EQUALS:
                if (left <= right) {
                    return Status.Success;
                }
            case LESS_THAN:
                if (left < right) {
                    return Status.Success;
                }
        }

        return Status.Failure;
    }
}
