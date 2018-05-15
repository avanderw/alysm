package avdw.java.tdai.naivebot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnergyGenerationLessThanOppositionTest {

    @Test
    void process() {
        EnergyGenerationLessThanOpposition energyGenerationLessThanOpposition = new EnergyGenerationLessThanOpposition(State.read("./src/test/resources/basic/less-energy.json"));
        assertEquals(ABehaviourTree.Status.Success, energyGenerationLessThanOpposition.process());
    }
}