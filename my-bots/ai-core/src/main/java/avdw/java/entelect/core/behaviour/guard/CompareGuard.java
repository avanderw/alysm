package avdw.java.entelect.core.behaviour.guard;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.Operation;
import avdw.java.entelect.core.state.GameState;
import org.pmw.tinylog.Logger;

public class CompareGuard extends ABehaviourTree<GameState> {
    private String filterMessage;
    private final Long left;
    private final Operation operation;
    private final Long right;

    public CompareGuard(String filterMessage, Long left, Operation operation, Long right) {
        this.filterMessage = filterMessage;

        this.left = left;
        this.operation = operation;
        this.right = right;
    }

    @Override
    public Status process(GameState state) {
        Boolean isSuccess = Boolean.FALSE;

        switch (operation) {
            case GREATER_THAN:
                if (left > right) {
                    isSuccess = Boolean.TRUE;
                }
            case EQUALS:
                if (left == right) {
                    isSuccess = Boolean.TRUE;
                }
            case LESS_THAN_OR_EQUALS:
                if (left <= right) {
                    isSuccess = Boolean.TRUE;
                }
            case LESS_THAN:
                if (left < right) {
                    isSuccess = Boolean.TRUE;
                }
        }

        if (isSuccess) {
            Logger.debug(String.format("[PASS] %s ",filterMessage));
            return Status.Success;
        } else {

            Logger.debug(String.format("[FAIL] %s ",filterMessage));
            return Status.Failure;
        }
    }
}
