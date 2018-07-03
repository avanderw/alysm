package avdw.java.entelect.tinkerai;

import avdw.java.entelect.core.api.BotBehaviourTree;
import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.Operation;
import avdw.java.entelect.core.behaviour.action.BuildBuilding;
import avdw.java.entelect.core.behaviour.action.DoNothing;
import avdw.java.entelect.core.behaviour.filter.LaneFilter;
import avdw.java.entelect.core.behaviour.filter.LaneSelector;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.Direction;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.PlayerType;

public class BotAi implements BotBehaviourTree {
    private GameState gameState;

    public BotAi(GameState gameState) {
        this.gameState = gameState;
    }

    public String run() {
        ABehaviourTree behaviourTree = new ABehaviourTree.Selector(
                new ABehaviourTree.Sequence(
                        new LaneSelector("",
                                new LaneFilter(PlayerType.A, BuildingType.ATTACK, Operation.EQUALS, 0),
                                new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.EQUALS, 0),
                                new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.EQUALS, 0),
                                new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.GREATER_THAN, 0)
                        ),
                        new BuildBuilding(BuildingType.ATTACK, Direction.RIGHT)
                ),
                new ABehaviourTree.Sequence(
                        new LaneSelector("",
                                new LaneFilter(PlayerType.A, BuildingType.ATTACK, Operation.LESS_THAN, 3),
                                new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.GREATER_THAN, 0),
                                new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.EQUALS, 0),
                                new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.GREATER_THAN, 0)
                        ),
                        new BuildBuilding(BuildingType.ATTACK, Direction.RIGHT)
                ),
                new ABehaviourTree.Sequence(
                        new LaneSelector("",
                                new LaneFilter(PlayerType.A, BuildingType.ATTACK, Operation.EQUALS, 0),
                                new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.LESS_THAN, 3),
                                new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.EQUALS, 0)
                        ),
                        new BuildBuilding(BuildingType.ATTACK, Direction.RIGHT)
                ),
                new ABehaviourTree.Sequence(
                        new LaneSelector("",
                                new LaneFilter(PlayerType.A, BuildingType.ENERGY, Operation.EQUALS, 0),
                                new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.EQUALS, 0),
                                new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.EQUALS, 0),
                                new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.EQUALS, 0)
                        ),
                        new BuildBuilding(BuildingType.ENERGY, Direction.LEFT)
                ),

                new DoNothing()
        );
        behaviourTree.process(gameState);

        return gameState.command;
    }
}
