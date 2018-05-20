package avdw.java.tdai.naivebot;

import avdw.java.tdai.naivebot.enums.BuildingType;
import avdw.java.tdai.naivebot.enums.MyLane;
import avdw.java.tdai.naivebot.enums.LaneType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildTest {

    @BeforeAll
    static void setup() {
    }

    @Test
    void buildFreeSpace() {
        Build build = new Build(BuildingType.ENERGY, LaneType.DEFENDING, LaneType.ATTACKING);
        assertEquals(ABehaviourTree.Status.Success, build.process(State.read("./src/test/resources/free-building-space.json")));
    }

    @Test
    void buildNonFreeSpace() {
        Build build = new Build(BuildingType.ATTACK, LaneType.DEFENDING, LaneType.ATTACKING);
        assertEquals(ABehaviourTree.Status.Failure, build.process(State.read("./src/test/resources/no-free-building-space.json")));
    }

    @Test
    void buildCannotAfford() {
        Build build = new Build(BuildingType.DEFENSE, LaneType.DEFENDING, LaneType.ATTACKING);
        assertEquals(ABehaviourTree.Status.Failure, build.process(State.read("./src/test/resources/cannot-afford-energy.json")));
    }

    @Test
    void buildLMyLaneDefending() {
        Build build = new Build(BuildingType.ATTACK, LaneType.DEFENDING, LaneType.ATTACKING);
        assertEquals(ABehaviourTree.Status.Success, build.process(State.read("./src/test/resources/mylane-defense.json")));
        assertEquals(ABehaviourTree.Status.Failure, build.process(State.read("./src/test/resources/mylane-no-defense.json")));
    }

    @Test
    void buildMyLaneAttacking() {
        Build build = new Build(BuildingType.ATTACK, LaneType.ATTACKING, LaneType.ATTACKING);
        assertEquals(ABehaviourTree.Status.Success, build.process(State.read("./src/test/resources/mylane-attacking.json")));
        assertEquals(ABehaviourTree.Status.Failure, build.process(State.read("./src/test/resources/mylane-no-attacking.json")));
    }

    @Test
    void buildMyLaneOnlyEnergy() {
        Build build = new Build(BuildingType.ATTACK, LaneType.ONLY_ENERGY, LaneType.ATTACKING);
        assertEquals(ABehaviourTree.Status.Success, build.process(State.read("./src/test/resources/mylane-only-energy.json")));
        assertEquals(ABehaviourTree.Status.Failure, build.process(State.read("./src/test/resources/mylane-no-only-energy.json")));
    }

    @Test
    void buildMyLaneOnlyAttacking() {
        Build build = new Build(BuildingType.ATTACK, LaneType.ONLY_ATTACKING, LaneType.ATTACKING);
        assertEquals(ABehaviourTree.Status.Success, build.process(State.read("./src/test/resources/mylane-only-attacking.json")));
        assertEquals(ABehaviourTree.Status.Failure, build.process(State.read("./src/test/resources/mylane-no-only-attacking.json")));
    }

    @Test
    void buildTheirLaneEmpty() {
        Build build = new Build(BuildingType.ATTACK, LaneType.ONLY_ATTACKING, LaneType.EMPTY);
        assertEquals(ABehaviourTree.Status.Success, build.process(State.read("./src/test/resources/theirlane-empty.json")));
        assertEquals(ABehaviourTree.Status.Failure, build.process(State.read("./src/test/resources/theirlane-no-empty.json")));
    }

    @Test
    void buildTheirLaneOnlyEnergy() {
        Build build = new Build(BuildingType.ATTACK, LaneType.ONLY_ATTACKING, LaneType.ONLY_ENERGY);
        assertEquals(ABehaviourTree.Status.Success, build.process(State.read("./src/test/resources/theirlane-only-energy.json")));
        assertEquals(ABehaviourTree.Status.Failure, build.process(State.read("./src/test/resources/theirlane-no-only-energy.json")));

    }

    @Test
    void buildTheirLaneNotAttacking() {
        Build build = new Build(BuildingType.ATTACK, LaneType.ONLY_ATTACKING, LaneType.NOT_ATTACKING);
        assertEquals(ABehaviourTree.Status.Success, build.process(State.read("./src/test/resources/theirlane-not-attacking.json")));
        assertEquals(ABehaviourTree.Status.Failure, build.process(State.read("./src/test/resources/theirlane-attacking.json")));
    }

    @Test
    void buildTheirLaneNotDefending() {

        Build build = new Build(BuildingType.ATTACK, LaneType.ONLY_ATTACKING, LaneType.NOT_DEFENDING);
        assertEquals(ABehaviourTree.Status.Success, build.process(State.read("./src/test/resources/theirlane-not-defending.json")));
        assertEquals(ABehaviourTree.Status.Failure, build.process(State.read("./src/test/resources/theirlane-defending.json")));
    }

    @Test
    void buildTheirLaneAttacking () {
        Build build = new Build(BuildingType.ATTACK, LaneType.ONLY_ATTACKING, LaneType.ATTACKING);
        assertEquals(ABehaviourTree.Status.Success, build.process(State.read("./src/test/resources/theirlane-attacking.json")));
        assertEquals(ABehaviourTree.Status.Failure, build.process(State.read("./src/test/resources/theirlane-no-attacking.json")));
    }
}