package avdw.java.entelect.core.behaviour.filter;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.behaviour.action.DebugStatement;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.State;
import com.sun.net.httpserver.Authenticator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

import static org.junit.Assert.*;

public class SortedLaneSelectorTest {
    @BeforeClass
    public static void beforeClass() {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} [{thread}] {class}.{method}() {level}: {message}")
                .level(Level.DEBUG)
                .activate();
    }

    @Test
    public void process() {
        ABehaviourTree<GameState> tree = new ABehaviourTree.Selector(
                new LaneSelector("A{ A = 0, D = 0, E = 3 }; B{ A = 2, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 0, D = 0, E = 2 }; B{ A = 2, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 0, D = 0, E = 1 }; B{ A = 2, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 2, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 0, D = 0, E = 3 }; B{ A = 1, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 0, D = 0, E = 2 }; B{ A = 1, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 0, D = 0, E = 1 }; B{ A = 1, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 1, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 0, D = 0, E = 3 }; B{ A = 0, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 0, D = 0, E = 2 }; B{ A = 0, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 0, D = 0, E = 1 }; B{ A = 0, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 0, D = 0, E = 0 }; B{ A = 0, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 1, D = 0, E = 3 }; B{ A = 2, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 1, D = 0, E = 2 }; B{ A = 2, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 1, D = 0, E = 1 }; B{ A = 2, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 1, D = 0, E = 0 }; B{ A = 2, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 1, D = 0, E = 3 }; B{ A = 1, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 1, D = 0, E = 2 }; B{ A = 1, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 1, D = 0, E = 1 }; B{ A = 1, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 1, D = 0, E = 0 }; B{ A = 1, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 1, D = 0, E = 3 }; B{ A = 0, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 1, D = 0, E = 2 }; B{ A = 0, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 1, D = 0, E = 1 }; B{ A = 0, D = 0, E = 0 }"),
                new LaneSelector("A{ A = 1, D = 0, E = 0 }; B{ A = 0, D = 0, E = 0 }")
        );

        ABehaviourTree<GameState> priorityTree = new SortedLaneSelector("A{ *A, *D, 3E[D3] }; B{ 2A[D2], *D, 1E[D0], *T }");

        GameState state = State.read("./src/test/resources/2018070321240526.json");
        ABehaviourTree.Status treeStatus = tree.process(state);
        Integer treeLanes = state.selectedLanes.size();
        ABehaviourTree.Status priorityTreeStatus = priorityTree.process(state);
        Integer priorityTreeLanes = state.selectedLanes.size();
        assertEquals(treeStatus, priorityTreeStatus);
        assertEquals(treeLanes, priorityTreeLanes);

        state = State.read("./src/test/resources/defend-attacked-lane.json");
        treeStatus = tree.process(state);
        treeLanes = state.selectedLanes.size();
        priorityTreeStatus = priorityTree.process(state);
        priorityTreeLanes = state.selectedLanes.size();
        assertEquals(treeStatus, priorityTreeStatus);
        assertEquals(treeLanes, priorityTreeLanes);

        state = State.read("./src/test/resources/free-building-space.json");
        treeStatus = tree.process(state);
        treeLanes = state.selectedLanes.size();
        priorityTreeStatus = priorityTree.process(state);
        priorityTreeLanes = state.selectedLanes.size();
        assertEquals(treeStatus, priorityTreeStatus);
        assertEquals(treeLanes, priorityTreeLanes);

        state = State.read("./src/test/resources/mylane-no-defense.json");
        treeStatus = tree.process(state);
        treeLanes = state.selectedLanes.size();
        priorityTreeStatus = priorityTree.process(state);
        priorityTreeLanes = state.selectedLanes.size();
        assertEquals(treeStatus, priorityTreeStatus);
        assertEquals(treeLanes, priorityTreeLanes);

        state = State.read("./src/test/resources/reinforce-attack.json");
        treeStatus = tree.process(state);
        treeLanes = state.selectedLanes.size();
        priorityTreeStatus = priorityTree.process(state);
        priorityTreeLanes = state.selectedLanes.size();
        assertEquals(treeStatus, priorityTreeStatus);
        assertEquals(treeLanes, priorityTreeLanes);
    }

    @Test
    public void testRange() {
        ABehaviourTree<GameState> priorityTree = new SortedLaneSelector("A{ *A, *D[11], 3E[D2] }; B{ 1A[23], *D, 2E[32], *T }");
        GameState state = State.read("./src/test/resources/start.json");
        ABehaviourTree.Status priorityTreeStatus = priorityTree.process(state);
        Integer priorityTreeLanes = state.selectedLanes.size();
        assertEquals(ABehaviourTree.Status.Failure, priorityTreeStatus);
        assertEquals(0, priorityTreeLanes.intValue());
    }
}