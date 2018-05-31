package avdw.java.entelect.core.behaviour.action;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.state.GameState;

public class DoNothing extends ABehaviourTree<GameState> {

    @Override
    public Status process(GameState state) {

        return Status.Success;
    }
}
