package avdw.java.entelect.core.behaviour.filter;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.Operation;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.PlayerType;
import avdw.java.entelect.core.state.State;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

import static org.junit.Assert.*;

public class LaneSelectorTest {
    @BeforeClass
    public static void beforeClass() {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} [{thread}] {class}.{method}() {level}: {message}")
                .level(Level.DEBUG)
                .activate();
    }

    @Test
    public void testEquality_Constructors_Success() {
        LaneSelector originalConstructor = new LaneSelector("original constructor",
                new LaneFilter(PlayerType.A, BuildingType.ENERGY, Operation.GREATER_THAN, 0),
                new LaneFilter(PlayerType.A, BuildingType.ATTACK, Operation.EQUALS, 0)
        );
        LaneSelector shorthandConstructor = new LaneSelector("A{ E > 0, A = 0 }");

        GameState state = State.read("./src/test/resources/2018070321240526.json");
        ABehaviourTree.Status originalStatus = originalConstructor.process(state);
        Integer origSelect = state.selectedLanes.size();
        ABehaviourTree.Status shortHandStatus = shorthandConstructor.process(state);
        Integer shorthandSelect = state.selectedLanes.size();

        assertEquals(originalStatus, shortHandStatus);
        assertEquals(originalConstructor.laneFilters.length, shorthandConstructor.laneFilters.length);
        assertEquals(origSelect, shorthandSelect);
    }
}