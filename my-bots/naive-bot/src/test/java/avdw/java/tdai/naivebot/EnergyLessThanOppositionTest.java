package avdw.java.tdai.naivebot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnergyLessThanOppositionTest {

    @Test
    void process() {
        EnergyLessThanOpposition energyLessThanOpposition = new EnergyLessThanOpposition(State.read("./src/test/resources/basic/less-energy.json"));
        assertEquals(ABehaviourTree.Status.Failure, energyLessThanOpposition.process());
    }
}