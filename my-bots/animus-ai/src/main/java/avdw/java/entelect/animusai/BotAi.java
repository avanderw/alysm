package avdw.java.entelect.animusai;

import avdw.java.entelect.core.api.BotBehaviourTree;
import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.Operation;
import avdw.java.entelect.core.behaviour.action.BuildBuilding;
import avdw.java.entelect.core.behaviour.action.DebugStatement;
import avdw.java.entelect.core.behaviour.action.DoNothing;
import avdw.java.entelect.core.behaviour.filter.LaneFilter;
import avdw.java.entelect.core.behaviour.filter.LaneSelector;
import avdw.java.entelect.core.behaviour.filter.SortedLaneSelector;
import avdw.java.entelect.core.behaviour.guard.CompareGuard;
import avdw.java.entelect.core.state.*;

public class BotAi implements BotBehaviourTree {
    private GameState gameState;

    public BotAi(GameState gameState) {
        this.gameState = gameState;
    }

    // analyse how long a tower lives, fires, given lane info, then score placement
    // top 3 energy, attack placements
    // top 3 energy, attack enemy placements most damage
    public String run() {
        ABehaviourTree behaviourTree = new ABehaviourTree.Selector(
                new ABehaviourTree.Sequence(
                        new CompareGuard("PlayerType.A.energy > BuildingType.ATTACK",
                                gameState.getEnergyFor(PlayerType.A),
                                Operation.GREATER_THAN_EQUAL,
                                gameState.getBuildingPrice(BuildingType.ATTACK)),
                        new CompareGuard("There exists an enemy ATTACK building",
                                gameState.countBuildingsFor(PlayerType.B, BuildingType.ATTACK),
                                Operation.GREATER_THAN,
                                0L
                        ),
                        new ABehaviourTree.Selector(
                                new ABehaviourTree.Sequence(
                                        new DebugStatement("ATTACK exceptions"),
                                        new ABehaviourTree.Selector(
                                                new LaneSelector("A{ A = 1, D = 0, E = 2 }; B{ A = 1, D = 0, E = 2 }"),
                                                new SortedLaneSelector("A{ 6A[00], 1D[11], 5E[20] }; B{ 3A[32], 2D[00], 4E[30] }")
                                        ),
                                        new BuildBuilding(BuildingType.ATTACK, Direction.RIGHT)
                                ),
                                new ABehaviourTree.Sequence(// cooldown on construction check ATTACK place
                                        new DebugStatement("DEFENSE strategy"),
                                        new ABehaviourTree.Selector(
                                                new SortedLaneSelector("A{ 6A[00], 1D[00], 5E[20] }; B{ 3A[43], 2D[00], 4E[30] }")
                                        ),
                                        new BuildBuilding(BuildingType.DEFENSE, Direction.RIGHT)
                                ),
                                new ABehaviourTree.Sequence(// "A{ A = 1, D = 0, E = 2 }; B{ A = 1, D = 0, E = 2 }" (cooldown check [lowest cooldown wins])
                                        new DebugStatement("ATTACK strategy"),
                                        new SortedLaneSelector("A{ 2A[01], 6D[10], 5E[30] }; B{ 4A[20], 1D[01], 3E[30] }"),
                                        new BuildBuilding(BuildingType.ATTACK, Direction.RIGHT)
                                )
                        )
                ),
                new ABehaviourTree.Sequence(
                        new CompareGuard("Is my energy generation less than the attack building price",
                                gameState.getEnergyGenerationFor(PlayerType.A),
                                Operation.LESS_THAN,
                                gameState.getBuildingPrice(BuildingType.ATTACK)
                        ),
                        new DebugStatement("ENERGY strategy"),
                        new SortedLaneSelector("A{ 6A[20], 3D[10], 5E[01] }; B{ 1A[01], 2D[01], 4E[03] }"),
                        new BuildBuilding(BuildingType.ENERGY, Direction.LEFT)
                ),
                new ABehaviourTree.Sequence(
                        new DebugStatement("ATTACK first fallback"),
                        new SortedLaneSelector("A{ 2A[02], 6D[00], 5E[30] }; B{ 4A[20], 1D[01], 3E[30] }"),
                        new BuildBuilding(BuildingType.ATTACK, Direction.RIGHT)
                ),
                new DoNothing()
        );
        behaviourTree.process(gameState);

        return gameState.command;
    }
}
