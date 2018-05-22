package avdw.java.tdai.naivebot;

import avdw.java.tdai.naivebot.enums.BuildingType;
import avdw.java.tdai.naivebot.enums.LaneType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReplaceTest {

    @Test
    void basicReplace() {
        Replace replace = new Replace(BuildingType.ENERGY, BuildingType.ATTACK);
        assertEquals(ABehaviourTree.Status.Success, replace.process(State.read("./src/test/resources/bug/attack-energy-generation.json")));
    }
}