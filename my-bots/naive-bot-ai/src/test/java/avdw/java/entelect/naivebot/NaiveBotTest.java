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
        BotAi tree = new BotAi(State.read("src/test/resources/bug/not-building.json"));
        String command = tree.run();
        assertNotNull(command);
        assertNotEquals("", command);
    }

    @Test
    void bugShouldBuildAttack() {
        BotAi tree = new BotAi(State.read("src/test/resources/bug/should-build-attack.json"));
        String command = tree.run();
        assertNotNull(command);
        assertNotEquals("", command);
    }


    @Test
    void reinforceAttack() {
        BotAi tree = new BotAi(State.read("src/test/resources/reinforce-attack.json"));
        String command = tree.run();
        assertNotNull(command);
        assertNotEquals("", command);
    }

    @Test
    void defendAttackedLane() {
        BotAi tree = new BotAi(State.read("src/test/resources/defend-attacked-lane.json"));
        String command = tree.run();
        assertNotNull(command);
        assertNotEquals("", command);
    }

    @Test
    void bugNotBuilding1() {
        BotAi tree = new BotAi(State.read("src/test/resources/bug/not-building.json"));
        String command = tree.run();
        assertNotNull(command);
        assertNotEquals("", command);
    }


    @Test
    void replaceEnergyBuilding() {
        BotAi tree = new BotAi(State.read("src/test/resources/replace-energy-building.json"));
        String command = tree.run();
        assertNotNull(command);
        assertEquals("0,1,1", command);
    }
}