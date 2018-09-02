package avdw.java.entelect.jarvisai;

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
    // cooldown on construction check ATTACK place
    public String run() {
        ABehaviourTree behaviourTree = new ABehaviourTree.Selector(
                new ABehaviourTree.Sequence(
                        new DebugStatement("IRON CURTAIN"),
                        new Guard(!gameState.isIronCurtainActive(PlayerType.A)),
                        new Guard(gameState.isIronCurtainAvailable(PlayerType.A)),
                        new Guard("A{E} >= $", gameState.getBuildingPrice(BuildingType.IRON_CURTAIN)),
                        new BuildIronCurtain()
                ),
                new ABehaviourTree.Sequence(
                        new DebugStatement("TESLA TOWER"),
                        new Guard("A{T} <= $", gameState.maxTesla()),
                        new Guard("A{E} >= $", gameState.getBuildingPrice(BuildingType.TESLA_TOWER)),
                        new BuildTesla()
                ),
                new ABehaviourTree.Sequence(
                        new DebugStatement("DEFENCE"),
                        new Guard("A{E} >= $", gameState.getBuildingPrice(BuildingType.DEFENSE)),
                        new SortedLaneSelector("A{ 6A[00], 1D[00], 5E[20] }; B{ 3A[43], 2D[00], 4E[30] }"),
                        new BuildDefence()
                ),
                new ABehaviourTree.Sequence(
                        new DebugStatement("ATTACK"),
                        new ABehaviourTree.Selector(
                                new Guard("B{A} > 0"),
                                new Guard("B{T} > 0")
                        ),
                        new Guard("A{E} >= $", gameState.getBuildingPrice(BuildingType.ATTACK)),
                        new SortedLaneSelector("A{ 2A[01], 6D[10], 5E[20] }; B{ 4A[20], 1D[01], 3E[30] }"),
                        new BuildAttack()
                ),
                new ABehaviourTree.Sequence(
                        new DebugStatement("ENERGY"),
                        new Guard("A{E} >= $", gameState.getBuildingPrice(BuildingType.ENERGY)),
                        new SortedLaneSelector("A{ 6A[30], 4D[20], 5E[01] }; B{ 2A[03], 1D[03], 3E[03] }"),
                        new BuildEnergy()
                ),
                new DoNothing()
        );
        behaviourTree.process(gameState);

        return gameState.command;
    }
}
