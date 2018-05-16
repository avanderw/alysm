package avdw.java.tdai.naivebot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnergyGenerationLessThanOpponentTest {

    @Test
    void process() {
        EnergyGenerationLessThanOpponent energyGenerationLessThanOpposition = new EnergyGenerationLessThanOpponent();
        assertEquals(ABehaviourTree.Status.Success, energyGenerationLessThanOpposition.process(State.read("./src/test/resources/basic/less-energy.json")));
    }
}