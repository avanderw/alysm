package avdw.java.entelect.core.behaviour.guard;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CanAffordTest {

    @Test
    void process() {
        CanAfford canAfford = new CanAfford(BuildingType.ENERGY);
        Assertions.assertEquals(ABehaviourTree.Status.Success, canAfford.process(State.read("./src/test/resources/can-afford-energy.json")));
        Assertions.assertEquals(ABehaviourTree.Status.Failure, canAfford.process(State.read("./src/test/resources/cannot-afford-energy.json")));
    }
}