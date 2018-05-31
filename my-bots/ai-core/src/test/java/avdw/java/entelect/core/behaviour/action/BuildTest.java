package avdw.java.entelect.core.behaviour.action;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.LaneType;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BuildTest {

    @BeforeAll
    static void setup() {
    }

    @Test
    void buildAttackNotAttackingOnlyEnergy() {
        Build build = new Build(BuildingType.ATTACK, LaneType.NOT_ATTACKING, LaneType.ONLY_ENERGY);
        Assertions.assertEquals(ABehaviourTree.Status.Success, build.process(State.read("./src/test/resources/bug/attack-energy-generation.json")));
    }


    @Test
    void buildAttackDefendedNotdDefending() {
        Build build = new Build(BuildingType.ATTACK, LaneType.DEFENDING, LaneType.NOT_DEFENDING);
        Assertions.assertEquals(ABehaviourTree.Status.Success, build.process(State.read("./src/test/resources/bug/should-build-attack.json")));
    }

    @Test
    void buildFreeSpace() {
        Build build = new Build(BuildingType.ENERGY, LaneType.DEFENDING, LaneType.ANY);
        Assertions.assertEquals(ABehaviourTree.Status.Success, build.process(State.read("./src/test/resources/free-building-space.json")));
    }

    @Test
    void buildNonFreeSpace() {
        Build build = new Build(BuildingType.ATTACK, LaneType.DEFENDING, LaneType.ATTACKING);
        Assertions.assertEquals(ABehaviourTree.Status.Failure, build.process(State.read("./src/test/resources/no-free-building-space.json")));
    }

    @Test
    void buildCannotAfford() {
        Build build = new Build(BuildingType.DEFENSE, LaneType.DEFENDING, LaneType.ATTACKING);
        Assertions.assertEquals(ABehaviourTree.Status.Failure, build.process(State.read("./src/test/resources/cannot-afford-energy.json")));
    }

    @Test
    void buildLMyLaneDefending() {
        Build build = new Build(BuildingType.ATTACK, LaneType.DEFENDING, LaneType.ATTACKING);
        Assertions.assertEquals(ABehaviourTree.Status.Success, build.process(State.read("./src/test/resources/mylane-defense.json")));
        Assertions.assertEquals(ABehaviourTree.Status.Failure, build.process(State.read("./src/test/resources/mylane-no-defense.json")));
    }

    @Test
    void buildDefendAnyAttacking() {
        Build build = new Build(BuildingType.DEFENSE, LaneType.ANY, LaneType.ATTACKING);
        Assertions.assertEquals(ABehaviourTree.Status.Success, build.process(State.read("./src/test/resources/defend-attacked-lane.json")));
    }

    @Test
    void buildEnergyEmptyEmpty() {
        Build build = new Build(BuildingType.ENERGY, LaneType.EMPTY, LaneType.EMPTY);
        GameState state =State.read("./src/test/resources/energy-empty-empty.json");
        ABehaviourTree.Status status = build.process(state);

        Assertions.assertEquals(ABehaviourTree.Status.Success, status);
        Assertions.assertNotEquals("", state.command);
    }

    @Test
    void buildAttackNotAttackingNotAttacking() {
        Build build = new Build(BuildingType.ATTACK, LaneType.NOT_ATTACKING, LaneType.NOT_ATTACKING);
        Assertions.assertEquals(ABehaviourTree.Status.Success, build.process(State.read("./src/test/resources/bug/not-building.json")));
    }
}