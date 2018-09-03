package avdw.java.entelect.jarvisai;

import avdw.java.entelect.core.state.State;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

import static org.junit.jupiter.api.Assertions.*;

@Ignore
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
//        assertEquals("6,4,1", botAi.run());
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
        assertEquals("6,0,1", botAi.run());
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
        assertEquals("7,0,1", botAi.run());
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
        assertEquals("7,0,1", botAi.run());
    }


    @Test
    public void test2018071820330763() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018071820330763.json"));
        assertTrue(botAi.run().endsWith("1"));
    }

    @Test
    public void test2018071920123212() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018071920123212.json"));
        assertNotEquals("2,0,2",botAi.run());
    }

    @Test
    public void test2018071920123215() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018071920123215.json"));
        assertEquals("7,3,1",botAi.run());
    }

    @Test
    public void test2018071920123216() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018071920123216.json"));
        assertEquals("6,3,1",botAi.run());
    }


    @Test
    public void test2018071920123227() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018071920123227.json"));
        assertEquals("7,1,1",botAi.run());
    }
    @Test
    public void test2018071920123233() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018071920123233.json"));
        assertEquals("7,2,1",botAi.run());
    }
    @Test
    public void test2018071920123234() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018071920123234.json"));
        assertEquals("6,2,1",botAi.run());
    }
    @Test
    public void test2018071920123238() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018071920123238.json"));
        assertEquals("7,2,1",botAi.run());
    }
    @Test
    public void test2018071920123247() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018071920123247.json"));
        assertEquals("7,7,1",botAi.run());
    }
    @Test
    public void test2018071921145042() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018071921145042.json"));
        assertEquals("7,0,0",botAi.run());
    }
    @Test
    public void test2018072017394539() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018072017394539.json"));
        assertEquals("7,3,1",botAi.run());
    }
    @Test
    public void test2018072017394540() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018072017394540.json"));
        assertEquals("6,3,1",botAi.run());
    }
    @Test
    public void test2018072017394547() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018072017394547.json"));
        assertEquals("2,0,2",botAi.run());
    }
    @Test
    public void test2018072017394552() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018072017394552.json"));
        assertNotEquals("0,1,2",botAi.run());
    }
    @Test
    public void test2018072118355721() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018072118355721.json"));
        assertEquals("7,3,0",botAi.run());
    }
    @Test
    public void test2018072118355732() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018072118355732.json"));
        assertEquals("6,3,1",botAi.run());
    }
    @Test
    public void test2018072118355735() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018072118355735.json"));
        assertEquals("7,3,0",botAi.run());
    }
    @Test
    public void test2018072118355737() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018072118355737.json"));
        assertNotEquals("7,5,0",botAi.run());
    }
    @Test
    public void test2018072119215329() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018072119215329.json"));
        assertEquals("2,2,2",botAi.run());
    }
    @Test
    public void test2018072119215331() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018072119215331.json"));
        assertEquals("2,0,2",botAi.run());
    }
    @Test
    public void test2018072119215332() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018072119215332.json"));
        assertEquals("7,1,1",botAi.run());
    }
    @Test
    public void test2018072119215334() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018072119215334.json"));
        assertEquals("2,0,2",botAi.run());
    }
    @Test
    public void test2018072120355615() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018072120355615.json"));
        assertEquals("7,1,1",botAi.run());
    }
    @Test
    public void test2018072208484215() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018072208484215.json"));
        assertEquals("7,0,1",botAi.run());
    }
    @Test
    public void test2018072210035415() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018072210035415.json"));
        assertEquals("7,0,1",botAi.run());
    }
    @Test
    public void test2018072210160111() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018072210160111.json"));
        assertNotEquals("2,2,2",botAi.run());
    }
    @Test
    public void test2018072210234421() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/2018072210234421.json"));
        assertEquals("7,2,1",botAi.run());
    }
}