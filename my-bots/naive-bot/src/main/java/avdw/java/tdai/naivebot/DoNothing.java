package avdw.java.tdai.naivebot;

import avdw.java.tdai.naivebot.entities.GameState;

class DoNothing extends ABehaviourTree<GameState> {

    @Override
    public Status process(GameState state) {

        return Status.Success;
    }
}
