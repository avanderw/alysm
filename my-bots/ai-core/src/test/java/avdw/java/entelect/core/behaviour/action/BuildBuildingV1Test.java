package avdw.java.entelect.core.behaviour.action;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.LaneType;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BuildBuildingV1Test {

    @BeforeAll
    static void setup() {
    }

    @Test
    void buildAttackNotAttackingOnlyEnergy() {
        BuildBuildingV1 buildBuildingV1 = new BuildBuildingV1(BuildingType.ATTACK, LaneType.NOT_ATTACKING, LaneType.ONLY_ENERGY);
        Assertions.assertEquals(ABehaviourTree.Status.Success, buildBuildingV1.process(State.read("./src/test/resources/bug/attack-energy-generation.json")));
    }


    @Test
    void buildAttackDefendedNotdDefending() {
        BuildBuildingV1 buildBuildingV1 = new BuildBuildingV1(BuildingType.ATTACK, LaneType.DEFENDING, LaneType.NOT_DEFENDING);
        Assertions.assertEquals(ABehaviourTree.Status.Success, buildBuildingV1.process(State.read("./src/test/resources/bug/should-buildBuildingV1-attack.json")));
    }

    @Test
    void buildFreeSpace() {
        BuildBuildingV1 buildBuildingV1 = new BuildBuildingV1(BuildingType.ENERGY, LaneType.DEFENDING, LaneType.ANY);
        Assertions.assertEquals(ABehaviourTree.Status.Success, buildBuildingV1.process(State.read("./src/test/resources/free-building-space.json")));
    }

    @Test
    void buildNonFreeSpace() {
        BuildBuildingV1 buildBuildingV1 = new BuildBuildingV1(BuildingType.ATTACK, LaneType.DEFENDING, LaneType.ATTACKING);
        Assertions.assertEquals(ABehaviourTree.Status.Failure, buildBuildingV1.process(State.read("./src/test/resources/no-free-building-space.json")));
    }

    @Test
    void buildCannotAfford() {
        BuildBuildingV1 buildBuildingV1 = new BuildBuildingV1(BuildingType.DEFENSE, LaneType.DEFENDING, LaneType.ATTACKING);
        Assertions.assertEquals(ABehaviourTree.Status.Failure, buildBuildingV1.process(State.read("./src/test/resources/cannot-afford-energy.json")));
    }

    @Test
    void buildLMyLaneDefending() {
        BuildBuildingV1 buildBuildingV1 = new BuildBuildingV1(BuildingType.ATTACK, LaneType.DEFENDING, LaneType.ATTACKING);
        Assertions.assertEquals(ABehaviourTree.Status.Success, buildBuildingV1.process(State.read("./src/test/resources/mylane-defense.json")));
        Assertions.assertEquals(ABehaviourTree.Status.Failure, buildBuildingV1.process(State.read("./src/test/resources/mylane-no-defense.json")));
    }

    @Test
    void buildDefendAnyAttacking() {
        BuildBuildingV1 buildBuildingV1 = new BuildBuildingV1(BuildingType.DEFENSE, LaneType.ANY, LaneType.ATTACKING);
        Assertions.assertEquals(ABehaviourTree.Status.Success, buildBuildingV1.process(State.read("./src/test/resources/defend-attacked-lane.json")));
    }

    @Test
    void buildEnergyEmptyEmpty() {
        BuildBuildingV1 buildBuildingV1 = new BuildBuildingV1(BuildingType.ENERGY, LaneType.EMPTY, LaneType.EMPTY);
        GameState state =State.read("./src/test/resources/energy-empty-empty.json");
        ABehaviourTree.Status status = buildBuildingV1.process(state);

        Assertions.assertEquals(ABehaviourTree.Status.Success, status);
        Assertions.assertNotEquals("", state.command);
    }

    @Test
    void buildAttackNotAttackingNotAttacking() {
        BuildBuildingV1 buildBuildingV1 = new BuildBuildingV1(BuildingType.ATTACK, LaneType.NOT_ATTACKING, LaneType.NOT_ATTACKING);
        Assertions.assertEquals(ABehaviourTree.Status.Success, buildBuildingV1.process(State.read("./src/test/resources/bug/not-building.json")));
    }
}