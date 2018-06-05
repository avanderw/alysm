package avdw.java.entelect.codexai;

import avdw.java.entelect.core.api.BotBehaviourTree;
import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.Operation;
import avdw.java.entelect.core.behaviour.action.BuildBuilding;
import avdw.java.entelect.core.behaviour.action.DoNothing;
import avdw.java.entelect.core.behaviour.filter.LaneFilter;
import avdw.java.entelect.core.behaviour.filter.LaneSelector;
import avdw.java.entelect.core.behaviour.guard.CompareGuard;
import avdw.java.entelect.core.behaviour.guard.TotalBuildingCount;
import avdw.java.entelect.core.state.*;

public class BotAi implements BotBehaviourTree {
    private GameState gameState;

    public BotAi(GameState gameState) {
        this.gameState = gameState;
    }

    public String run() {
        ABehaviourTree behaviourTree = new ABehaviourTree.Selector(
                new ABehaviourTree.Sequence(
                        new CompareGuard(
                                gameState.countBuildingsFor(PlayerType.A, BuildingType.ENERGY),
                                Operation.LESS_THAN,
                                gameState.countBuildingsFor(PlayerType.B, BuildingType.ENERGY) +1
                        ),
                        new ABehaviourTree.Selector(
                                new LaneSelector(
                                        new LaneFilter(PlayerType.A, BuildingType.ENERGY, Operation.EQUALS, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.EQUALS, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.EQUALS, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.EQUALS, 0)
                                ),
                                new LaneSelector(
                                        new LaneFilter(PlayerType.A, BuildingType.ENERGY, Operation.EQUALS, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.EQUALS, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.EQUALS, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.GREATER_THAN, 0)
                                ),
                                new LaneSelector(
                                        new LaneFilter(PlayerType.A, BuildingType.ENERGY, Operation.LESS_THAN, 2),
                                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.EQUALS, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.EQUALS, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.EQUALS, 0)
                                ),
                                new LaneSelector(
                                        new LaneFilter(PlayerType.A, BuildingType.ENERGY, Operation.LESS_THAN, 2),
                                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.EQUALS, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.EQUALS, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.GREATER_THAN, 0)
                                ),
                                new LaneSelector(
                                        new LaneFilter(PlayerType.A, BuildingType.ENERGY, Operation.EQUALS, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.EQUALS, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.GREATER_THAN, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.GREATER_THAN, 0)
                                ),
                                new LaneSelector(
                                        new LaneFilter(PlayerType.A, BuildingType.ENERGY, Operation.EQUALS, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.GREATER_THAN, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.GREATER_THAN, 0),
                                        new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.GREATER_THAN, 0)
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
