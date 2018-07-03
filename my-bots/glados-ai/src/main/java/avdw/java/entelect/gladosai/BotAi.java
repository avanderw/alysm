package avdw.java.entelect.gladosai;

import avdw.java.entelect.core.api.BotBehaviourTree;
import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.Operation;
import avdw.java.entelect.core.behaviour.action.BuildBuilding;
import avdw.java.entelect.core.behaviour.action.DebugStatement;
import avdw.java.entelect.core.behaviour.action.DoNothing;
import avdw.java.entelect.core.behaviour.filter.LaneFilter;
import avdw.java.entelect.core.behaviour.filter.LaneSelector;
import avdw.java.entelect.core.behaviour.guard.CompareGuard;
import avdw.java.entelect.core.state.*;

public class BotAi implements BotBehaviourTree {
    private GameState gameState;

    public BotAi(GameState gameState) {
        this.gameState = gameState;
    }

    public String run() {
        ABehaviourTree behaviourTree = new ABehaviourTree.Selector(
                new ABehaviourTree.Sequence(
                        new DebugStatement("General ATTACK strategy"),
                        new ABehaviourTree.Selector(
                                new LaneSelector("my energy has built up with no defence",
                                        new LaneFilter(PlayerType.A, BuildingType.ENERGY, Operation.GREATER_THAN, 1),
                                        new LaneFilter(PlayerType.A, BuildingType.ATTACK, Operation.LESS_THAN, 2)
                                )
                        ),
                        new BuildBuilding(BuildingType.ATTACK, Direction.RIGHT)
                ),
                new ABehaviourTree.Sequence(
                        new DebugStatement("General ENERGY strategy"),
                        new CompareGuard("Is my energy generation less than the most expensive building",
                                gameState.getEnergyGenerationFor(PlayerType.A),
                                Operation.LESS_THAN,
                                gameState.getMostExpensiveBuildingPrice()
                        ),
                        new ABehaviourTree.Selector(
                                new LaneSelector("I have some defence and my opponent has nothing",
                                        new LaneFilter(PlayerType.A, BuildingType.DEFENSE, Operation.GREATER_THAN, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.EQUALS, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.EQUALS, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.EQUALS, 0)
                                ),
                                new LaneSelector("my opponent has nothing",
                                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.EQUALS, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.EQUALS, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.EQUALS, 0)
                                )
                        ),
                        new BuildBuilding(BuildingType.ENERGY, Direction.LEFT)
                ),
                new DoNothing()
        );
        behaviourTree.process(gameState);

        return gameState.command;
    }
}
