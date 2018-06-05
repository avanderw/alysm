package avdw.java.entelect.codexai;

import avdw.java.entelect.core.state.State;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BotAiTest {
    @Test
    void testBasic() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/start.json"));
        assertNotEquals("", botAi.run());
    }

    @Test
    void testEnergy() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/round-19-energy.json"));
        assertNotEquals("2,4,2", botAi.run());
        assertEquals("1,6,2", botAi.run());
    }
}