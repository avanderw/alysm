package avdw.java.tdai.naivebot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoNothingTest {

    @Test
    void process() {

        BotResponse botResponse = new BotResponse();
        DoNothing doNothing = new DoNothing(botResponse);
        doNothing.process();
        assertEquals("", botResponse.response);
    }
}