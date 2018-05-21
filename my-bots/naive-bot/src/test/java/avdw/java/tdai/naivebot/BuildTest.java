package avdw.java.tdai.naivebot;

import avdw.java.tdai.naivebot.entities.GameState;
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
    void buildAttackDefendedNotdDefending() {
        Build build = new Build(BuildingType.ATTACK, LaneType.DEFENDING, LaneType.NOT_DEFENDING);
        assertEquals(ABehaviourTree.Status.Success, build.process(State.read("./src/test/resources/bug/should-build-attack.json")));
    }

    @Test
    void buildFreeSpace() {
        Build build = new Build(BuildingType.ENERGY, LaneType.DEFENDING, LaneType.ANY);
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
    void buildEnergyEmptyEmpty() {
        Build build = new Build(BuildingType.ENERGY, LaneType.EMPTY, LaneType.EMPTY);
        GameState state =State.read("./src/test/resources/energy-empty-empty.json");
        ABehaviourTree.Status status = build.process(state);

        assertEquals(ABehaviourTree.Status.Success, status);
        assertNotEquals("", state.command);
    }
}