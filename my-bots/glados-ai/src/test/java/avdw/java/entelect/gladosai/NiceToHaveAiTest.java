package avdw.java.entelect.gladosai;

import avdw.java.entelect.core.state.State;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class NiceToHaveAiTest {
    @BeforeClass
    public static void beforeClass() {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} [{thread}] {class}.{method}() {level}: {message}")
                .level(Level.DEBUG)
                .activate();
    }

    @Test
    @Ignore
    public void test2018070400052444() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018070400052444.json"));
        assertEquals("0,0,0", botAi.run());
    }
}