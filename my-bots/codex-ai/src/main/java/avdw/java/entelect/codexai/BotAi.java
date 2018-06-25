package avdw.java.entelect.codexai;

import avdw.java.entelect.core.api.BotBehaviourTree;
import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.Operation;
import avdw.java.entelect.core.behaviour.action.BuildBuilding;
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
                        generalAttackStrategy(),
                        new BuildBuilding(BuildingType.ATTACK, Direction.RIGHT)
                ),
                new ABehaviourTree.Sequence(
                        new ABehaviourTree.Selector(
                                new CompareGuard(
                                        gameState.getEnergyGenerationFor(PlayerType.A),
                                        Operation.LESS_THAN,
                                        gameState.getMostExpensiveBuildingPrice()
                                )
                        ),
                        generalEnergyStrategy(),
                        new BuildBuilding(BuildingType.ENERGY, Direction.LEFT)
                ),
                new DoNothing()
        );
        behaviourTree.process(gameState);

        return gameState.command;
    }

    private ABehaviourTree generalDefendStrategy() {
        ABehaviourTree aBehaviourTree = new ABehaviourTree.Selector(
                new LaneSelector(
                        new LaneFilter(PlayerType.A, BuildingType.DEFENSE, Operation.EQUALS, 0)
                )
        );

        return aBehaviourTree;
    }

    private ABehaviourTree generalAttackStrategy() {
        ABehaviourTree aBehaviourTree = new ABehaviourTree.Selector(
                new LaneSelector( // ensure defense on great weakness
                        new LaneFilter(PlayerType.A, BuildingType.ENERGY, Operation.GREATER_THAN, 1),
                        new LaneFilter(PlayerType.A, BuildingType.ATTACK, Operation.LESS_THAN, 2)
                ),
                new LaneSelector( // stop their fresh attack
                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.GREATER_THAN, 1),
                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.EQUALS, 0)
                ),
                new LaneSelector( // open a new attack against their weakness whilst protecting my weakness
                        new LaneFilter(PlayerType.A, BuildingType.ATTACK, Operation.EQUALS, 0),
                        new LaneFilter(PlayerType.A, BuildingType.ENERGY, Operation.GREATER_THAN, 0),
                        new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.GREATER_THAN, 0),
                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.EQUALS, 0),
                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.EQUALS, 0)
                ),
                new LaneSelector( // open a new attack against their great weakness
                        new LaneFilter(PlayerType.A, BuildingType.ATTACK, Operation.EQUALS, 0),
                        new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.GREATER_THAN, 1),
                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.EQUALS, 0),
                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.EQUALS, 0)
                ),
                new LaneSelector( // open a new attack against their weakness
                        new LaneFilter(PlayerType.A, BuildingType.ATTACK, Operation.EQUALS, 0),
                        new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.GREATER_THAN, 0),
                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.EQUALS, 0),
                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.EQUALS, 0)
                ),
                new LaneSelector( // reinforce an attack against their weakness
                        new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.GREATER_THAN, 0),
                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.EQUALS, 0),
                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.EQUALS, 0)
                ),
                new LaneSelector( // protect against my weakness
                        new LaneFilter(PlayerType.A, BuildingType.ENERGY, Operation.GREATER_THAN, 0),
                        new LaneFilter(PlayerType.A, BuildingType.ATTACK, Operation.EQUALS, 0)
                ),
                new LaneSelector( // reinforce attacks until they can destroy a defence building
                        new LaneFilter(PlayerType.A, BuildingType.ATTACK, Operation.GREATER_THAN, 0),
                        new LaneFilter(PlayerType.A, BuildingType.ATTACK, Operation.LESS_THAN, 4)
                )
        );

        return aBehaviourTree;
    }

    public ABehaviourTree generalEnergyStrategy() {
        ABehaviourTree aBehaviourTree = new ABehaviourTree.Selector(
                new LaneSelector(
                        new LaneFilter(PlayerType.A, BuildingType.DEFENSE, Operation.GREATER_THAN, 0),
                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.EQUALS, 0),
                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.EQUALS, 0),
                        new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.EQUALS, 0)
                ),
                new LaneSelector(
                        new LaneFilter(PlayerType.A, BuildingType.ENERGY, Operation.EQUALS, 0),
                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.EQUALS, 0),
                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.EQUALS, 0)
                ),
                new LaneSelector(
                        new LaneFilter(PlayerType.A, BuildingType.DEFENSE, Operation.GREATER_THAN, 0),
                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.EQUALS, 0),
                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.EQUALS, 0),
                        new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.GREATER_THAN, 0)
                ),
                new LaneSelector(
                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.EQUALS, 0),
                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.EQUALS, 0),
                        new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.GREATER_THAN, 0)
                ),
                new LaneSelector(
                        new LaneFilter(PlayerType.A, BuildingType.DEFENSE, Operation.GREATER_THAN, 0),
                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.EQUALS, 0),
                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.GREATER_THAN, 0),
                        new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.GREATER_THAN, 0)
                ),
                new LaneSelector(
                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.EQUALS, 0),
                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.GREATER_THAN, 0),
                        new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.GREATER_THAN, 0)
                ),
                new LaneSelector(
                        new LaneFilter(PlayerType.A, BuildingType.DEFENSE, Operation.GREATER_THAN, 0),
                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.GREATER_THAN, 0),
                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.GREATER_THAN, 0),
                        new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.GREATER_THAN, 0)
                ),
                new LaneSelector(
                        new LaneFilter(PlayerType.B, BuildingType.ATTACK, Operation.GREATER_THAN, 0),
                        new LaneFilter(PlayerType.B, BuildingType.DEFENSE, Operation.GREATER_THAN, 0),
                        new LaneFilter(PlayerType.B, BuildingType.ENERGY, Operation.GREATER_THAN, 0)
                )
        );

        return aBehaviourTree;
    }
}
