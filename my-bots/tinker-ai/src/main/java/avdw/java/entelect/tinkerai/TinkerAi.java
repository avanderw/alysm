package avdw.java.entelect.tinkerai;

import avdw.java.entelect.core.api.BotBehaviourTree;
import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.LaneType;
import avdw.java.entelect.core.behaviour.Operation;
import avdw.java.entelect.core.behaviour.action.Build;
import avdw.java.entelect.core.behaviour.action.DoNothing;
import avdw.java.entelect.core.behaviour.guard.BuildingCount;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.PlayerType;

public class TinkerAi implements BotBehaviourTree {
    private GameState gameState;

    public TinkerAi(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public String run() {
        ABehaviourTree behaviourTree = new ABehaviourTree.Selector(
                new ABehaviourTree.Sequence(
                        new BuildingCount(PlayerType.A, BuildingType.ENERGY, Operation.LESS_THAN, 2),
                        new ABehaviourTree.Selector(
                            new Build(BuildingType.ENERGY, LaneType.DEFENDING, LaneType.NOT_ATTACKING),
                            new Build(BuildingType.ENERGY, LaneType.ANY, LaneType.NOT_ATTACKING),
                            new Build(BuildingType.ENERGY, LaneType.DEFENDING, LaneType.ANY),
                            new Build(BuildingType.ENERGY, LaneType.ANY, LaneType.ANY)
                        )
                ),
                new Build(BuildingType.ATTACK, LaneType.NOT_ATTACKING, LaneType.ONLY_ENERGY),
                new Build(BuildingType.ATTACK, LaneType.NOT_ATTACKING, LaneType.ONLY_ATTACKING),
                new Build(BuildingType.DEFENSE, LaneType.NOT_DEFENDING, LaneType.ATTACKING),
                new Build(BuildingType.DEFENSE, LaneType.NOT_DEFENDING, LaneType.NOT_ATTACKING),
                new Build(BuildingType.ATTACK, LaneType.ANY, LaneType.NOT_DEFENDING),
                new Build(BuildingType.ATTACK, LaneType.ANY, LaneType.ATTACKING),
                new Build(BuildingType.ATTACK, LaneType.ANY, LaneType.ANY),
                new DoNothing()
        );
        behaviourTree.process(gameState);

        return gameState.command;
    }
}
