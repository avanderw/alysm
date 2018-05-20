package avdw.java.tdai.naivebot;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

import static org.junit.jupiter.api.Assertions.*;

class BotBehaviourTreeTest {
    @BeforeAll
    static void beforeAll() {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} [{thread}] {class}.{method}() {level}: {message}")
                .level(Level.TRACE)
                .activate();
    }

    @Test
    void bugNotBuilding() {
        BotBehaviourTree tree = new BotBehaviourTree(State.read("src/test/resources/bug/not-building.json"));
        String command = tree.run();
        assertNotEquals("", command);
    }
}