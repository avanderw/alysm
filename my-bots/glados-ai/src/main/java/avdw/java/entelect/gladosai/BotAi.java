package avdw.java.entelect.gladosai;

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

    public String run() {
        ABehaviourTree behaviourTree = new ABehaviourTree.Selector(
                new ABehaviourTree.Sequence(
                        new DebugStatement("ATTACK take advantage of DEFENSE"),
                        new ABehaviourTree.Selector(
                                new LaneSelector("A{ A = 0, D = 1, E > 3 }; B{ A * 0, D * 0, E * 0 }"),
                                new LaneSelector("A{ A = 0, D = 1, E = 3 }; B{ A * 0, D * 0, E * 0 }"),
                                new LaneSelector("A{ A = 0, D = 1, E = 2 }; B{ A * 0, D * 0, E * 0 }"),
                                new LaneSelector("A{ A = 0, D = 1, E = 1 }; B{ A * 0, D * 0, E * 0 }"),
                                new LaneSelector("A{ A = 0, D = 1, E = 0 }; B{ A * 0, D * 0, E * 0 }")
                        ),
                        new BuildBuilding(BuildingType.ATTACK, Direction.RIGHT)
                ),
                new ABehaviourTree.Sequence(
                        new DebugStatement("ATTACK to protect energy"),
                        new ABehaviourTree.Selector(
                                new LaneSelector("A{ A = 0, D = 0, E > 2 }; B{ A = 1, D = 0, E = 0 }")
                        ),
                        new BuildBuilding(BuildingType.ATTACK, Direction.RIGHT)
                ),
                new ABehaviourTree.Sequence(
                        new DebugStatement("DEFENSE strategy"),
                        new ABehaviourTree.Selector(
                                new ABehaviourTree.AlwaysFail(new DebugStatement("start DEFEND contested lanes")),
                                new LaneSelector("A{ A = 0, D = 0, E = 3 }; B{ A = 2, D * 0, E = 3 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 3 }; B{ A = 2, D * 0, E = 2 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 3 }; B{ A = 2, D * 0, E = 1 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 2 }; B{ A = 2, D * 0, E = 3 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 2 }; B{ A = 2, D * 0, E = 2 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 2 }; B{ A = 2, D * 0, E = 1 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 1 }; B{ A = 2, D * 0, E = 3 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 1 }; B{ A = 2, D * 0, E = 2 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 1 }; B{ A = 2, D * 0, E = 1 }"),
                                new ABehaviourTree.AlwaysFail(new DebugStatement("end DEFEND contested lanes")),

                                new LaneSelector("A{ A = 0, D = 0, E = 3 }; B{ A = 1, D = 0, E * 0 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 3, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 2, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 2, D = 0, E = 1 }")
                        ),
                        new BuildBuilding(BuildingType.DEFENSE, Direction.RIGHT)
                ),
                new ABehaviourTree.Sequence(
                        new DebugStatement("general ATTACK strategy"),
                        new ABehaviourTree.Selector(
                                new LaneSelector("A{ A = 1, D = 0, E > 1 }; B{ A = 1, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 1 }; B{ A = 1, D = 0, E = 2 }"),

                                new ABehaviourTree.AlwaysFail(new DebugStatement("ATTACK break 1")),
                                new LaneSelector("A{ A = 0, D = 0, E > 0 }; B{ A = 0, D = 0, E > 3 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 0, D = 0, E > 3 }"),
                                new LaneSelector("A{ A = 0, D = 0, E > 0 }; B{ A = 0, D = 0, E = 3 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 0, D = 0, E = 3 }"),

                                new ABehaviourTree.AlwaysFail(new DebugStatement("begin ATTACK ENERGY 2")),
                                new LaneSelector("A{ A = 0, D = 0, E = 3 }; B{ A = 2, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 2 }; B{ A = 2, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 1 }; B{ A = 2, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 2, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 3 }; B{ A = 1, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 2 }; B{ A = 1, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 1 }; B{ A = 1, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 1, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 3 }; B{ A = 0, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 2 }; B{ A = 0, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 1 }; B{ A = 0, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 0, D = 0, E = 2 }"),
                                new ABehaviourTree.AlwaysFail(new DebugStatement("MARK")),
                                new LaneSelector("A{ A = 1, D = 0, E = 3 }; B{ A = 2, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 2 }; B{ A = 2, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 1 }; B{ A = 2, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 0 }; B{ A = 2, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 3 }; B{ A = 1, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 2 }; B{ A = 1, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 1 }; B{ A = 1, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 0 }; B{ A = 1, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 3 }; B{ A = 0, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 2 }; B{ A = 0, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 1 }; B{ A = 0, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 0 }; B{ A = 0, D = 0, E = 2 }"),
                                new ABehaviourTree.AlwaysFail(new DebugStatement("end ATTACK ENERGY 2")),

                                new ABehaviourTree.AlwaysFail(new DebugStatement("begin ATTACK ENERGY 1")),
                                new LaneSelector("A{ A = 0, D = 0, E = 3 }; B{ A = 2, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 2 }; B{ A = 2, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 1 }; B{ A = 2, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 2, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 3 }; B{ A = 1, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 2 }; B{ A = 1, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 1 }; B{ A = 1, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 1, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 3 }; B{ A = 0, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 2 }; B{ A = 0, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 1 }; B{ A = 0, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 0, D = 0, E = 1 }"),
                                new ABehaviourTree.AlwaysFail(new DebugStatement("MARK")),
                                new LaneSelector("A{ A = 1, D = 0, E = 3 }; B{ A = 2, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 2 }; B{ A = 2, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 1 }; B{ A = 2, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 0 }; B{ A = 2, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 3 }; B{ A = 1, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 2 }; B{ A = 1, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 1 }; B{ A = 1, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 0 }; B{ A = 1, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 3 }; B{ A = 0, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 2 }; B{ A = 0, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 1 }; B{ A = 0, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 0 }; B{ A = 0, D = 0, E = 1 }"),
                                new ABehaviourTree.AlwaysFail(new DebugStatement("end ATTACK ENERGY 1")),

                                new ABehaviourTree.AlwaysFail(new DebugStatement("begin ATTACK ENERGY 0")),
                                new LaneSelector("A{ A = 0, D = 0, E = 3 }; B{ A = 2, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 2 }; B{ A = 2, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 1 }; B{ A = 2, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 2, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 3 }; B{ A = 1, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 2 }; B{ A = 1, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 1 }; B{ A = 1, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 1, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 3 }; B{ A = 0, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 2 }; B{ A = 0, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 1 }; B{ A = 0, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 0, D = 0, E = 0 }"),
                                new ABehaviourTree.AlwaysFail(new DebugStatement("MARK")),
                                new LaneSelector("A{ A = 1, D = 0, E = 3 }; B{ A = 2, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 2 }; B{ A = 2, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 1 }; B{ A = 2, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 0 }; B{ A = 2, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 3 }; B{ A = 1, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 2 }; B{ A = 1, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 1 }; B{ A = 1, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 0 }; B{ A = 1, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 3 }; B{ A = 0, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 2 }; B{ A = 0, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 1 }; B{ A = 0, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 0 }; B{ A = 0, D = 0, E = 0 }"),
                                new ABehaviourTree.AlwaysFail(new DebugStatement("end ATTACK ENERGY 0")),

                                new LaneSelector("A{ A = 0, D = 0, E > 1 }; B{ A = 1, D = 0, E * 0 }"),

                                new ABehaviourTree.AlwaysFail(new DebugStatement("ATTACK break 2")),
                                new LaneSelector("A{ A = 0, D = 0, E > 1 }; B{ A = 1, D = 0, E = 3 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 1 }; B{ A = 1, D = 0, E = 3 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 1, D = 0, E = 3 }"),
                                new LaneSelector("A{ A = 1, D = 0, E > 1 }; B{ A = 1, D = 0, E = 3 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 1 }; B{ A = 1, D = 0, E = 3 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 0 }; B{ A = 1, D = 0, E = 3 }"),

                                new LaneSelector("A{ A = 0, D = 0, E > 1 }; B{ A = 1, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 1 }; B{ A = 1, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 0, D = 0, E * 0 }; B{ A = 1, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 1, D = 0, E * 0 }; B{ A = 1, D = 0, E = 2 }"),

                                new ABehaviourTree.AlwaysFail(new DebugStatement("ATTACK break 3")),
                                new LaneSelector("A{ A = 0, D = 0, E = 1 }; B{ A = 1, D = 0, E * 0 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 1, D = 0, E * 0 }"),
                                new LaneSelector("A{ A = 1, D = 0, E > 1 }; B{ A = 1, D = 0, E * 0 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 1 }; B{ A = 1, D = 0, E * 0 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 0 }; B{ A = 1, D = 0, E * 0 }"),

                                new LaneSelector("A{ A = 1, D = 0, E > 0 }; B{ A = 2, D = 0, E > 1 }"),

                                new ABehaviourTree.AlwaysFail(new DebugStatement("ATTACK break 4")),
                                new LaneSelector("A{ A = 1, D = 0, E > 0 }; B{ A = 0, D = 0, E > 3 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 0 }; B{ A = 0, D = 0, E > 3 }"),

                                new LaneSelector("A{ A = 1, D = 0, E > 0 }; B{ A = 0, D = 0, E = 3 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 0 }; B{ A = 0, D = 0, E = 3 }"),

                                new LaneSelector("A{ A = 1, D = 0, E > 0 }; B{ A = 0, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 0 }; B{ A = 0, D = 0, E = 2 }"),

                                new LaneSelector("A{ A = 0, D = 0, E > 0 }; B{ A = 0, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 0, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 1, D = 0, E > 0 }; B{ A = 0, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 1, D = 0, E = 0 }; B{ A = 0, D = 0, E = 1 }"),

                                new LaneSelector("A{ A = 1, D = 0, E > 0 }; B{ A = 0, D = 0, E > 3 }"),
                                new LaneSelector("A{ A = 1, D = 0, E > 0 }; B{ A = 0, D = 0, E = 3 }"),
                                new LaneSelector("A{ A = 1, D = 0, E > 0 }; B{ A = 0, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 1, D = 0, E > 0 }; B{ A = 0, D = 0, E = 1 }")
                        ),
                        new BuildBuilding(BuildingType.ATTACK, Direction.RIGHT)
                ),
                new ABehaviourTree.Sequence(
                        new DebugStatement("general DEFENSE strategy"),
                        new ABehaviourTree.Selector(
                                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 2, D = 0, E > 1 }"),
                                new LaneSelector("A{ A = 0, D = 0, E > 0 }; B{ A > 0, D = 1, E > 0 }")
                        ),
                        new BuildBuilding(BuildingType.DEFENSE, Direction.RIGHT)
                ),
                new ABehaviourTree.Sequence(
                        new DebugStatement("reinforce ATTACK to 4, if energy-generation > BuildingType.ENERGY"),
                        new CompareGuard("energy-generation > BuildingType.ENERGY",
                                gameState.getEnergyGenerationFor(PlayerType.A),
                                Operation.GREATER_THAN,
                                gameState.getBuildingPrice(BuildingType.ENERGY)
                        ),
                        new ABehaviourTree.Selector(
                                new LaneSelector("A{ A < 4, D = 0, E = 0 }; B{ A * 0, D * 0, E * 0 }")
                        ),
                        new BuildBuilding(BuildingType.ATTACK, Direction.RIGHT)
                ),
                new ABehaviourTree.Sequence(
                        new DebugStatement("general ENERGY strategy"),
                        new CompareGuard("Is my energy generation less than the attack building price",
                                gameState.getEnergyGenerationFor(PlayerType.A),
                                Operation.LESS_THAN,
                                gameState.getBuildingPrice(BuildingType.ATTACK)
                        ),
                        new ABehaviourTree.Selector(
                                new ABehaviourTree.AlwaysFail(new DebugStatement("start ENERGY behind defense safety")),
                                new LaneSelector("A{ A = 2, D = 1, E = 0 }; B{ A * 0, D * 0, E * 0 }"),
                                new LaneSelector("A{ A = 2, D = 1, E = 1 }; B{ A * 0, D * 0, E * 0 }"),
                                new LaneSelector("A{ A = 2, D = 1, E = 2 }; B{ A * 0, D * 0, E * 0 }"),
                                new LaneSelector("A{ A = 1, D = 1, E = 0 }; B{ A * 0, D * 0, E * 0 }"),
                                new LaneSelector("A{ A = 1, D = 1, E = 1 }; B{ A * 0, D * 0, E * 0 }"),
                                new LaneSelector("A{ A = 1, D = 1, E = 2 }; B{ A * 0, D * 0, E * 0 }"),
                                new LaneSelector("A{ A = 0, D = 1, E = 0 }; B{ A * 0, D * 0, E * 0 }"),
                                new LaneSelector("A{ A = 0, D = 1, E = 1 }; B{ A * 0, D * 0, E * 0 }"),
                                new LaneSelector("A{ A = 0, D = 1, E = 2 }; B{ A * 0, D * 0, E * 0 }"),
                                new ABehaviourTree.AlwaysFail(new DebugStatement("end ENERGY behind defense safety")),

                                new ABehaviourTree.AlwaysFail(new DebugStatement("start ENERGY behind attack safety")),
                                new LaneSelector("A{ A = 2, D = 0, E < 3 }; B{ A = 0, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 2, D = 0, E < 3 }; B{ A = 0, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 2, D = 0, E < 3 }; B{ A = 0, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 1, D = 0, E < 3 }; B{ A = 0, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 1, D = 0, E < 3 }; B{ A = 0, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 1, D = 0, E < 3 }; B{ A = 0, D = 0, E = 2 }"),

                                new LaneSelector("A{ A = 2, D = 0, E < 3 }; B{ A = 1, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 2, D = 0, E < 3 }; B{ A = 1, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 2, D = 0, E < 3 }; B{ A = 1, D = 0, E = 2 }"),
                                new LaneSelector("A{ A = 1, D = 0, E < 3 }; B{ A = 1, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 1, D = 0, E < 3 }; B{ A = 1, D = 0, E = 1 }"),
                                new LaneSelector("A{ A = 1, D = 0, E < 3 }; B{ A = 1, D = 0, E = 2 }"),
                                new ABehaviourTree.AlwaysFail(new DebugStatement("end ENERGY behind attack safety")),

                                new LaneSelector("A{ A > 0, D > 0, E = 0 }; B{ A = 0, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 0, D > 0, E = 0 }; B{ A = 0, D = 0, E = 0 }"),
                                new LaneSelector("A{ A > 0, D = 0, E = 0 }; B{ A = 0, D = 0, E = 0 }"),
                                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 0, D = 0, E = 0 }"),

                                new LaneSelector("A{ A * 0, D * 0, E < 2 }; B{ A = 0, D = 0, E = 0 }"),
                                new LaneSelector("A{ A * 0, D * 0, E < 2 }; B{ A = 0, D = 0, E = 1 }"),
                                new LaneSelector("A{ A * 0, D * 0, E < 2 }; B{ A = 0, D = 0, E = 2 }"),
                                new LaneSelector("A{ A * 0, D * 0, E < 2 }; B{ A = 0, D = 0, E = 3 }"),
                                new LaneSelector("A{ A * 0, D * 0, E < 2 }; B{ A = 0, D = 0, E > 3 }")
                        ),
                        new BuildBuilding(BuildingType.ENERGY, Direction.LEFT)
                ),
                new ABehaviourTree.Sequence(
                        new DebugStatement("reinforce ENERGY to 2"),
                        new ABehaviourTree.Selector(
                                new LaneSelector("A{ A * 0, D * 0, E = 0 }; B{ A * 0, D * 0, E * 0 }"),
                                new LaneSelector("A{ A * 0, D * 0, E = 1 }; B{ A * 0, D * 0, E * 0 }")
                        ),
                        new BuildBuilding(BuildingType.ENERGY, Direction.LEFT)
                ),
                new ABehaviourTree.Sequence(
                        new DebugStatement("reinforce DEFENSE to 1"),
                        new ABehaviourTree.Selector(
                                new LaneSelector("A{ A * 0, D = 0, E * 0 }; B{ A * 0, D * 0, E * 0 }")
                        ),
                        new BuildBuilding(BuildingType.DEFENSE, Direction.RIGHT)
                ),
                new DoNothing()
        );
        behaviourTree.process(gameState);

        return gameState.command;
    }
}
