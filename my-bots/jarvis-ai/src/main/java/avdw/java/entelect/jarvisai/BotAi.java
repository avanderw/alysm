package avdw.java.entelect.jarvisai;

import avdw.java.entelect.core.api.BotBehaviourTree;
import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.action.*;
import avdw.java.entelect.core.behaviour.filter.DefendableLaneSelector;
import avdw.java.entelect.core.behaviour.filter.LaneSelector;
import avdw.java.entelect.core.behaviour.filter.SortedLaneSelector;
import avdw.java.entelect.core.behaviour.guard.Guard;
import avdw.java.entelect.core.state.*;

public class BotAi implements BotBehaviourTree {
    private GameState gameState;

    public BotAi(GameState gameState) {
        this.gameState = gameState;
    }

    public String run() {
        ABehaviourTree behaviourTree = new ABehaviourTree.Selector(
                new ABehaviourTree.Sequence(
                        new DebugStatement("IRON CURTAIN"),
                        new Guard(!gameState.isIronCurtainActive(PlayerType.A), "IRON_CURTAIN is not active"),
                        new Guard(gameState.isIronCurtainAvailable(PlayerType.A), "IRON_CURTAIN is available"),
                        new ABehaviourTree.Selector(
                                new ABehaviourTree.Sequence(
                                        new Guard("A{E} < $", gameState.getIronCurtainPrice()),
                                        new Guard("A{E} >= $", gameState.getIronCurtainPrice() - 1 * gameState.getEnergyGenerationFor(PlayerType.A)),
                                        new DoNothing()
                                ),
                                new ABehaviourTree.Sequence(
                                        new Guard("A{E} >= $", gameState.getIronCurtainPrice()),
                                        new BuildIronCurtain()
                                )
                        )
                ),
                new ABehaviourTree.Sequence(
                        new DebugStatement("DEFENCE"),
                        new Guard("A{E} >= $", gameState.getBuildingPrice(BuildingType.DEFENSE)),
                        new ABehaviourTree.Selector(
                                new SortedLaneSelector("A{ 6A[00], 1D[00], 5E[30] }; B{ 3A[73], 2D[00], 4E[30] }"),
                                new SortedLaneSelector("A{ 6A[00], 1D[11], 5E[30] }; B{ 3A[74], 2D[00], 4E[30] }")
                        ),
                        new BuildDefence()
                ),
                new ABehaviourTree.Sequence(
                        new DebugStatement("ATTACK"),
                        new ABehaviourTree.Selector(
                                new ABehaviourTree.Sequence(
                                        new DebugStatement("Use ATTACK for last defence"),
                                        new DefendableLaneSelector(),
                                        new BuildAttack()
                                ),
                                new ABehaviourTree.Sequence(
                                        new DebugStatement("Prioritised ATTACK"),
                                        new ABehaviourTree.Selector(
                                                new ABehaviourTree.AlwaysFail(new DebugStatement("Stop for IRON_CURTAIN")),
                                                new Guard("A{A} < $", gameState.countBuildingsFor(PlayerType.B, BuildingType.ATTACK) + 2),
                                                new Guard("A{G} >= 38")
                                        ),
                                        new ABehaviourTree.Selector(
                                                new ABehaviourTree.AlwaysFail(new DebugStatement("Start building")),
                                                new Guard("R == 12"),
                                                new Guard("B{A} > 0"),
                                                new Guard("B{T} > 0"),
                                                new Guard("A{G} >= $", gameState.getBuildingPrice(BuildingType.ATTACK))
                                        ),
                                        new ABehaviourTree.Selector(
                                                new Guard(!gameState.isEnemyIronCurtainActive(), "Enemy IRON_CURTAIN is not active"),
                                                new Guard("A{G} >= 38")
                                        ),
                                        new Guard("A{E} >= $", gameState.getBuildingPrice(BuildingType.ATTACK)),
                                        new ABehaviourTree.Selector(
                                                new ABehaviourTree.Sequence(
                                                        new Guard(gameState.isIronCurtainActive(PlayerType.A), "Is IRON_CURTAIN active"),
                                                        new SortedLaneSelector("A{ 3A[00], 6D[00], 5E[30] }; B{ 2A[30], 1D[00], 4E[30] }")
                                                ),
                                                new ABehaviourTree.Selector(
                                                        new SortedLaneSelector("A{ 4A[00], 6D[10], 2E[30] }; B{ 5A[30], 1D[00], 3E[30] }"),
                                                        new SortedLaneSelector("A{ 3A[12], 6D[10], 5E[30] }; B{ 4A[30], 1D[01], 2E[30] }")
                                                )
                                        ),
                                        new BuildAttack()
                                )
                        )
                ),
                new ABehaviourTree.Sequence(
                        new DebugStatement("ENERGY"),
                        new Guard("A{E} >= $", gameState.getBuildingPrice(BuildingType.ENERGY)),
                        new SortedLaneSelector("A{ 5A[30], 4D[20], 6E[01] }; B{ 2A[03], 1D[03], 3E[03] }"),
                        new BuildEnergy()
                ),
                new DoNothing()
        );
        behaviourTree.process(gameState);

        return gameState.command;
    }
}
