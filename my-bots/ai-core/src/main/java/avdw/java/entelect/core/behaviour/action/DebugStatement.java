package avdw.java.entelect.core.behaviour.action;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.state.GameState;
import org.pmw.tinylog.Logger;

public class DebugStatement extends ABehaviourTree<GameState> {
    private String message;

    public DebugStatement(String message) {
        this.message = message;
    }

    @Override
    public Status process(GameState state) {
        Logger.debug(message);

        return Status.Success;
    }
}
