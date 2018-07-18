package avdw.java.entelect.gladosai;

import avdw.java.entelect.core.state.State;
import org.junit.BeforeClass;
import org.junit.Ignore;
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

    @Test
    public void test2018070320500014() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018070320500014.json"));
        assertEquals("7,0,1", botAi.run());
    }

    @Test
    public void test2018070320500016() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018070320500016.json"));
        assertEquals("7,1,1", botAi.run());
    }

    @Test
    public void test2018070321240015() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018070321240015.json"));
        assertEquals("7,1,1", botAi.run());
    }

    @Test
    public void test2018070323140616() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018070323140616.json"));
        assertEquals("6,0,1", botAi.run());
    }

    @Test
    public void test2018070323263420() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018070323263420.json"));
        assertEquals("6,0,1", botAi.run());
    }

    @Test
    public void test2018070323263441() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018070323263441.json"));
        assertEquals("7,0,0", botAi.run());
    }

    @Test
    public void test2018070323444640() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018070323444640.json"));
        assertEquals("7,7,0", botAi.run());
    }

    @Test
    public void test2018070323444621() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018070323444621.json"));
        assertEquals("7,0,1", botAi.run());
    }

    @Test
    public void test2018070323444636() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018070323444636.json"));
        assertEquals("7,0,0", botAi.run());
    }

    @Test
    public void test2018070323444645() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018070323444645.json"));
        assertNotEquals("", botAi.run());
    }

    @Test
    public void test2018071818232709() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018071818232709.json"));
        assertEquals("7,5,1", botAi.run());
    }


    @Test
    public void test2018071818582760() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018071818582760.json"));
        String output = botAi.run();
        assertFalse(output.endsWith("2"));
        assertEquals("6,7,1", output);
    }

    @Test
    public void test2018071818575535() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018071818575535.json"));
        assertEquals("7,0,0", botAi.run());
    }

    @Test
    public void test2018071818591823() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018071818591823.json"));
        assertEquals("6,4,1", botAi.run());
    }

    @Test
    public void test2018071818591824() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018071818591824.json"));
        assertEquals("7,0,0", botAi.run());
    }

    @Test
    public void test2018071820033424() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018071820033424.json"));
        assertEquals("7,3,1", botAi.run());
    }

    @Test
    public void test2018071820040318() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018071820040318.json"));
        assertEquals("7,0,1", botAi.run());
    }

    @Test
    public void test2018071820040334() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018071820040334.json"));
        assertEquals("7,5,1", botAi.run());
    }


    @Test
    public void test2018071820330763() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018071820330763.json"));
        assertTrue(botAi.run().endsWith("1"));
    }
}