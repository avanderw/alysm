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
                        new ABehaviourTree.Selector(
                                new LaneSelector("A{D > 0, A < 2}"),
                                attackWeakAttack(),
                                new LaneSelector("A{A = 1, E > 0}; B{A = 2, E > 1, D = 0}"),
                                attackEnergyGeneration()
                        ),
                        new BuildBuilding(BuildingType.ATTACK, Direction.RIGHT)
                ),
                new ABehaviourTree.Sequence(
                        new DebugStatement("general DEFENSE strategy"),
                        new ABehaviourTree.Selector(
                            new LaneSelector("A{A = 0, E = 0, D = 0}; B{A = 2, E > 1, D = 0}")
                        ),
                        new BuildBuilding(BuildingType.DEFENSE, Direction.RIGHT)
                ),
                new ABehaviourTree.Sequence(
                        new DebugStatement("General ENERGY strategy"),
                        new CompareGuard("Is my energy generation less than the most expensive building",
                                gameState.getEnergyGenerationFor(PlayerType.A),
                                Operation.LESS_THAN,
                                gameState.getMostExpensiveBuildingPrice()
                        ),
                        new ABehaviourTree.Selector(
                                new LaneSelector("A{D > 0}; B{A = 0, D = 0, E = 0}"),
                                new LaneSelector("          B{A = 0, D = 0, E = 0}")
                        ),
                        new BuildBuilding(BuildingType.ENERGY, Direction.LEFT)
                ),
                new DoNothing()
        );
        behaviourTree.process(gameState);

        return gameState.command;
    }
    ABehaviourTree attackWeakAttack() {
        return new ABehaviourTree.Selector(
                new ABehaviourTree.AlwaysFail(new DebugStatement("ATTACK where B has Attack = 1")),
                new LaneSelector("A{E > 1, A = 0}; B{A = 1, D = 0, E = 3}"),
                new LaneSelector("A{E > 0, A = 0}; B{A = 1, D = 0, E = 3}"),
                new LaneSelector("A{       A = 0}; B{A = 1, D = 0, E = 3}"),
                new LaneSelector("A{E > 1, A = 1}; B{A = 1, D = 0, E = 3}"),
                new LaneSelector("A{E > 0, A = 1}; B{A = 1, D = 0, E = 3}"),
                new LaneSelector("A{       A = 1}; B{A = 1, D = 0, E = 3}"),
                new LaneSelector("A{E > 1, A = 0}; B{A = 1, D = 0, E = 2}"),
                new LaneSelector("A{E > 0, A = 0}; B{A = 1, D = 0, E = 2}"),
                new LaneSelector("A{       A = 0}; B{A = 1, D = 0, E = 2}"),
                new LaneSelector("A{E > 1, A = 1}; B{A = 1, D = 0, E = 2}"),
                new LaneSelector("A{E > 0, A = 1}; B{A = 1, D = 0, E = 2}"),
                new LaneSelector("A{       A = 1}; B{A = 1, D = 0, E = 2}"),
                new LaneSelector("A{E > 1, A = 0}; B{A = 1, D = 0, E > 0}"),
                new LaneSelector("A{E > 0, A = 0}; B{A = 1, D = 0, E > 0}"),
                new LaneSelector("A{       A = 0}; B{A = 1, D = 0, E > 0}"),
                new LaneSelector("A{E > 1, A = 1}; B{A = 1, D = 0, E > 0}"),
                new LaneSelector("A{E > 0, A = 1}; B{A = 1, D = 0, E > 0}"),
                new LaneSelector("A{       A = 1}; B{A = 1, D = 0, E > 0}")
        );
    }

    ABehaviourTree attackEnergyGeneration() {
        return new ABehaviourTree.Selector(
                new ABehaviourTree.AlwaysFail(new DebugStatement("ATTACK where B has Energy > 0")),
                new LaneSelector("A{ A = 0, E > 0 }; B{ D = 0, E > 3, A = 0 }"),
                new LaneSelector("A{ A = 0, E > 0 }; B{ D = 0, E = 3, A = 0 }"),
                new LaneSelector("A{ A = 0, E > 0 }; B{ D = 0, E > 1, A = 0 }"),
                new LaneSelector("A{ A = 0, E > 0 }; B{ D = 0, E > 0, A = 0 }"),
                new LaneSelector("A{ A = 1, E > 0 }; B{ D = 0, E > 3, A = 0 }"),
                new LaneSelector("A{ A = 1, E > 0 }; B{ D = 0, E = 3, A = 0 }"),
                new LaneSelector("A{ A = 1, E > 0 }; B{ D = 0, E > 1, A = 0 }"),
                new LaneSelector("A{ A = 1, E > 0 }; B{ D = 0, E > 0, A = 0 }")
        );
    }
}
