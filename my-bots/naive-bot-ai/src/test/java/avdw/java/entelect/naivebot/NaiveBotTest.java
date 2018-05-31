package avdw.java.entelect.naivebot;

import avdw.java.entelect.core.state.State;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

import static org.junit.jupiter.api.Assertions.*;

class NaiveBotTest {
    @BeforeAll
    static void beforeAll() {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} [{thread}] {class}.{method}() {level}: {message}")
                .level(Level.TRACE)
                .activate();
    }

    @Test
    void bugNotBuilding() {
        NaiveBotAi tree = new NaiveBotAi(State.read("src/test/resources/bug/not-building.json"));
        String command = tree.run();
        assertNotNull(command);
        assertNotEquals("", command);
    }

    @Test
    void bugShouldBuildAttack() {
        NaiveBotAi tree = new NaiveBotAi(State.read("src/test/resources/bug/should-build-attack.json"));
        String command = tree.run();
        assertNotNull(command);
        assertNotEquals("", command);
    }


    @Test
    void reinforceAttack() {
        NaiveBotAi tree = new NaiveBotAi(State.read("src/test/resources/reinforce-attack.json"));
        String command = tree.run();
        assertNotNull(command);
        assertNotEquals("", command);
    }

    @Test
    void defendAttackedLane() {
        NaiveBotAi tree = new NaiveBotAi(State.read("src/test/resources/defend-attacked-lane.json"));
        String command = tree.run();
        assertNotNull(command);
        assertNotEquals("", command);
    }

    @Test
    void bugNotBuilding1() {
        NaiveBotAi tree = new NaiveBotAi(State.read("src/test/resources/bug/not-building.json"));
        String command = tree.run();
        assertNotNull(command);
        assertNotEquals("", command);
    }


    @Test
    void replaceEnergyBuilding() {
        NaiveBotAi tree = new NaiveBotAi(State.read("src/test/resources/replace-energy-building.json"));
        String command = tree.run();
        assertNotNull(command);
        assertEquals("0,1,1", command);
    }
}