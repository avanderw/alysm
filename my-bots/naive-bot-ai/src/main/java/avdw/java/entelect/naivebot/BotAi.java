package avdw.java.entelect.naivebot;

import avdw.java.entelect.core.api.BotBehaviourTree;
import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.LaneType;
import avdw.java.entelect.core.behaviour.Operation;
import avdw.java.entelect.core.behaviour.action.BuildBuildingV1;
import avdw.java.entelect.core.behaviour.action.DoNothing;
import avdw.java.entelect.core.behaviour.action.ReplaceBuilding;
import avdw.java.entelect.core.behaviour.guard.CanAffordBuilding;
import avdw.java.entelect.core.behaviour.guard.EnergySupply;
import avdw.java.entelect.core.behaviour.guard.EnergyGeneration;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.PlayerType;

public class BotAi implements BotBehaviourTree {
    private GameState gameState;

    public BotAi(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public String run() {

        ABehaviourTree behaviourTree = new ABehaviourTree.Selector(
                new ABehaviourTree.Sequence(
                        new ABehaviourTree.Selector(
                                new EnergyGeneration(Operation.LESS_THAN, gameState.getEnergyGenerationFor(PlayerType.B)),
                                new EnergyGeneration(Operation.LESS_THAN, gameState.getMostExpensiveBuildingPrice())),
                        new CanAffordBuilding(BuildingType.ENERGY),
                        new ABehaviourTree.Selector(
                                new BuildBuildingV1(BuildingType.ENERGY, LaneType.DEFENDING, LaneType.EMPTY),
                                new BuildBuildingV1(BuildingType.ENERGY, LaneType.DEFENDING, LaneType.ONLY_ENERGY),
                                new BuildBuildingV1(BuildingType.ENERGY, LaneType.EMPTY, LaneType.EMPTY),
                                new BuildBuildingV1(BuildingType.ENERGY, LaneType.DEFENDING, LaneType.NOT_ATTACKING))),
                new ABehaviourTree.Sequence(
                        new CanAffordBuilding(BuildingType.ATTACK),
                        new ABehaviourTree.Selector(
                                new BuildBuildingV1(BuildingType.ATTACK, LaneType.DEFENDING, LaneType.ONLY_ENERGY),
                                new BuildBuildingV1(BuildingType.ATTACK, LaneType.NOT_ATTACKING, LaneType.ONLY_ENERGY),
                                new BuildBuildingV1(BuildingType.ATTACK, LaneType.DEFENDING, LaneType.NOT_DEFENDING),
                                new BuildBuildingV1(BuildingType.ATTACK, LaneType.DEFENDING, LaneType.EMPTY),
                                new BuildBuildingV1(BuildingType.ATTACK, LaneType.ATTACKING, LaneType.ONLY_ENERGY),
                                new BuildBuildingV1(BuildingType.ATTACK, LaneType.ATTACKING, LaneType.EMPTY),
                                new BuildBuildingV1(BuildingType.ATTACK, LaneType.ATTACKING, LaneType.NOT_DEFENDING),
                                new BuildBuildingV1(BuildingType.ATTACK, LaneType.ATTACKING, LaneType.ANY),
                                new BuildBuildingV1(BuildingType.ATTACK, LaneType.NOT_ATTACKING, LaneType.NOT_ATTACKING))),
                new ABehaviourTree.Sequence(
                        new CanAffordBuilding(BuildingType.DEFENSE),
                        new ABehaviourTree.Selector(
                                new BuildBuildingV1(BuildingType.DEFENSE, LaneType.ONLY_ENERGY, LaneType.ATTACKING),
                                new BuildBuildingV1(BuildingType.DEFENSE, LaneType.ONLY_ATTACKING, LaneType.ATTACKING),
                                new BuildBuildingV1(BuildingType.DEFENSE, LaneType.ONLY_ENERGY, LaneType.NOT_ATTACKING),
                                new BuildBuildingV1(BuildingType.DEFENSE, LaneType.ONLY_ATTACKING, LaneType.NOT_ATTACKING),
                                new BuildBuildingV1(BuildingType.DEFENSE, LaneType.ONLY_ENERGY, LaneType.EMPTY),
                                new BuildBuildingV1(BuildingType.DEFENSE, LaneType.ONLY_ATTACKING, LaneType.EMPTY),
                                new BuildBuildingV1(BuildingType.DEFENSE, LaneType.ANY, LaneType.ATTACKING))),
                new ABehaviourTree.Sequence(
                        new EnergySupply(Operation.GREATER_THAN, 3 * gameState.getMostExpensiveBuildingPrice()),
                        new ABehaviourTree.Selector(
                                new ReplaceBuilding(BuildingType.ENERGY, BuildingType.ATTACK))
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
