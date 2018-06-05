package avdw.java.entelect.core.behaviour.guard;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.Operation;
import avdw.java.entelect.core.state.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EnergySupplyGenerationTest {

    @Test
    void processLessThanSuccess() {
        EnergyGeneration energyGeneration = new EnergyGeneration(Operation.LESS_THAN, 100);
        ABehaviourTree.Status status = energyGeneration.process(State.read("./src/test/resources/basic/less-energy.json"));
        Assertions.assertEquals(ABehaviourTree.Status.Success, status);
    }

    @Test
    void processLessThanFail() {
        EnergyGeneration energyGeneration = new EnergyGeneration(Operation.LESS_THAN, 5);
        ABehaviourTree.Status status = energyGeneration.process(State.read("./src/test/resources/basic/less-energy.json"));
        Assertions.assertEquals(ABehaviourTree.Status.Failure, status);
    }
}