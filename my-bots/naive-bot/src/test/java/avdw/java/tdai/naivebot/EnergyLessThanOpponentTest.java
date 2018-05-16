package avdw.java.tdai.naivebot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnergyLessThanOpponentTest {

    @Test
    void process() {
        EnergyLessThanOpponent energyLessThanOpposition = new EnergyLessThanOpponent();
        assertEquals(ABehaviourTree.Status.Failure, energyLessThanOpposition.process(State.read("./src/test/resources/basic/less-energy.json")));
    }
}