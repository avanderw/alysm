package avdw.java.entelect.core.behaviour.guard;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.Operation;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.PlayerType;
import avdw.java.entelect.core.state.State;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TotalBuildingCountTest {

    @Test
    void process() {
        TotalBuildingCount totalBuildingCount = new TotalBuildingCount(PlayerType.A, BuildingType.ENERGY, Operation.LESS_THAN, 2);
        assertEquals(ABehaviourTree.Status.Success, totalBuildingCount.process(State.read("./src/test/resources/start.json")));
        assertEquals(ABehaviourTree.Status.Failure, totalBuildingCount.process(State.read("./src/test/resources/two-energy-buildings.json")));
        assertEquals(ABehaviourTree.Status.Success, new TotalBuildingCount(PlayerType.A, BuildingType.ENERGY, Operation.GREATER_THAN, 1)
                .process(State.read("./src/test/resources/two-energy-buildings.json")));
        assertEquals(ABehaviourTree.Status.Failure, new TotalBuildingCount(PlayerType.A, BuildingType.ENERGY, Operation.GREATER_THAN, 3)
                .process(State.read("./src/test/resources/two-energy-buildings.json")));
    }
}