package avdw.java.entelect.core.behaviour.action;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReplaceBuildingTest {

    @Test
    void basicReplace() {
        ReplaceBuilding replaceBuilding = new ReplaceBuilding(BuildingType.ENERGY, BuildingType.ATTACK);
        Assertions.assertEquals(ABehaviourTree.Status.Success, replaceBuilding.process(State.read("./src/test/resources/bug/attack-energy-generation.json")));
    }
}