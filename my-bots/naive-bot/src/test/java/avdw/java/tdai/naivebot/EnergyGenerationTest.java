package avdw.java.tdai.naivebot;

import avdw.java.tdai.naivebot.enums.Operation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnergyGenerationTest {

    @Test
    void processLessThanSuccess() {
        EnergyGeneration energyGeneration = new EnergyGeneration(Operation.LESS_THAN, 100);
        ABehaviourTree.Status status = energyGeneration.process(State.read("./src/test/resources/basic/less-energy.json"));
        assertEquals(ABehaviourTree.Status.Success, status);
    }

    @Test
    void processLessThanFail() {
        EnergyGeneration energyGeneration = new EnergyGeneration(Operation.LESS_THAN, 5);
        ABehaviourTree.Status status = energyGeneration.process(State.read("./src/test/resources/basic/less-energy.json"));
        assertEquals(ABehaviourTree.Status.Failure, status);
    }
}