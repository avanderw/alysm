package avdw.java.tdai.naivebot;

import avdw.java.tdai.naivebot.enums.BuildingType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CanAffordTest {

    @Test
    void process() {
        CanAfford canAfford = new CanAfford(BuildingType.ENERGY);
        assertEquals(ABehaviourTree.Status.Success, canAfford.process(State.read("./src/test/resources/can-afford-energy.json")));
        assertEquals(ABehaviourTree.Status.Failure, canAfford.process(State.read("./src/test/resources/cannot-afford-energy.json")));
    }
}