package avdw.java.entelect.gladosai;

import avdw.java.entelect.core.state.State;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

import static org.junit.jupiter.api.Assertions.*;

public class BotAiTest {
    @BeforeClass
    public static void beforeClass() {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} [{thread}] {class}.{method}() {level}: {message}")
                .level(Level.DEBUG)
                .activate();
    }
    @Test
    public void testStart() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/start.json"));
        assertNotEquals("", botAi.run());
    }
}