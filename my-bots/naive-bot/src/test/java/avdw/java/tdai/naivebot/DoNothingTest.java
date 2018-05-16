package avdw.java.tdai.naivebot;

import avdw.java.tdai.naivebot.entities.GameState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoNothingTest {

    @Test
    void process() {

        BotResponse botResponse = new BotResponse();
        DoNothing doNothing = new DoNothing(botResponse);
        doNothing.process(new GameState());
        assertEquals("", botResponse.response);
    }
}