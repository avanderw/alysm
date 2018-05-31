package avdw.java.entelect.naivebot;

import avdw.java.entelect.core.api.BotBehaviourTree;
import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.LaneType;
import avdw.java.entelect.core.behaviour.Operation;
import avdw.java.entelect.core.behaviour.action.Build;
import avdw.java.entelect.core.behaviour.action.DoNothing;
import avdw.java.entelect.core.behaviour.action.Replace;
import avdw.java.entelect.core.behaviour.guard.CanAfford;
import avdw.java.entelect.core.behaviour.guard.Energy;
import avdw.java.entelect.core.behaviour.guard.EnergyGeneration;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.PlayerType;

public class NaiveBotAi implements BotBehaviourTree {
    private GameState gameState;

    public NaiveBotAi(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public String run() {

        ABehaviourTree behaviourTree = new ABehaviourTree.Selector(
                new ABehaviourTree.Sequence(
                        new ABehaviourTree.Selector(
                                new EnergyGeneration(Operation.LESS_THAN, gameState.getEnergyGenerationFor(PlayerType.B)),
                                new EnergyGeneration(Operation.LESS_THAN, gameState.getMostExpensiveBuildingPrice())),
                        new CanAfford(BuildingType.ENERGY),
                        new ABehaviourTree.Selector(
                                new Build(BuildingType.ENERGY, LaneType.DEFENDING, LaneType.EMPTY),
                                new Build(BuildingType.ENERGY, LaneType.DEFENDING, LaneType.ONLY_ENERGY),
                                new Build(BuildingType.ENERGY, LaneType.EMPTY, LaneType.EMPTY),
                                new Build(BuildingType.ENERGY, LaneType.DEFENDING, LaneType.NOT_ATTACKING))),
                new ABehaviourTree.Sequence(
                        new CanAfford(BuildingType.ATTACK),
                        new ABehaviourTree.Selector(
                                new Build(BuildingType.ATTACK, LaneType.DEFENDING, LaneType.ONLY_ENERGY),
                                new Build(BuildingType.ATTACK, LaneType.NOT_ATTACKING, LaneType.ONLY_ENERGY),
                                new Build(BuildingType.ATTACK, LaneType.DEFENDING, LaneType.NOT_DEFENDING),
                                new Build(BuildingType.ATTACK, LaneType.DEFENDING, LaneType.EMPTY),
                                new Build(BuildingType.ATTACK, LaneType.ATTACKING, LaneType.ONLY_ENERGY),
                                new Build(BuildingType.ATTACK, LaneType.ATTACKING, LaneType.EMPTY),
                                new Build(BuildingType.ATTACK, LaneType.ATTACKING, LaneType.NOT_DEFENDING),
                                new Build(BuildingType.ATTACK, LaneType.ATTACKING, LaneType.ANY),
                                new Build(BuildingType.ATTACK, LaneType.NOT_ATTACKING, LaneType.NOT_ATTACKING))),
                new ABehaviourTree.Sequence(
                        new CanAfford(BuildingType.DEFENSE),
                        new ABehaviourTree.Selector(
                                new Build(BuildingType.DEFENSE, LaneType.ONLY_ENERGY, LaneType.ATTACKING),
                                new Build(BuildingType.DEFENSE, LaneType.ONLY_ATTACKING, LaneType.ATTACKING),
                                new Build(BuildingType.DEFENSE, LaneType.ONLY_ENERGY, LaneType.NOT_ATTACKING),
                                new Build(BuildingType.DEFENSE, LaneType.ONLY_ATTACKING, LaneType.NOT_ATTACKING),
                                new Build(BuildingType.DEFENSE, LaneType.ONLY_ENERGY, LaneType.EMPTY),
                                new Build(BuildingType.DEFENSE, LaneType.ONLY_ATTACKING, LaneType.EMPTY),
                                new Build(BuildingType.DEFENSE, LaneType.ANY, LaneType.ATTACKING))),
                new ABehaviourTree.Sequence(
                        new Energy(Operation.GREATER_THAN, 3 * gameState.getMostExpensiveBuildingPrice()),
                        new ABehaviourTree.Selector(
                                new Replace(BuildingType.ENERGY, BuildingType.ATTACK))
                ),
                new DoNothing());
        behaviourTree.process(gameState);

        return gameState.command;

        //if enemy has an attack building and i dont have a blocking wall, then block from front
        //if there is a row where i don't have energy and there is no enemy attack build energy in the back row
        //if i have a defense building on a row, then build an attack building behind it.
        //if i don't need to do anything then do either attack or defend randomly based on chance (65% attack, 35% defense)
    }
}
