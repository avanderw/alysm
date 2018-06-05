package avdw.java.entelect.codexai;

import avdw.java.entelect.core.state.State;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BotAiTest {
    @Test
    private void testBasic() {
        BotAi botAi = new BotAi(State.read("./src/test/resources/start.json"));
        assertNotEquals("", botAi.run());
    }
}