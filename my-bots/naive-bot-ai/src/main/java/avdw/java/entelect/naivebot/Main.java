package avdw.java.entelect.naivebot;

import avdw.java.entelect.core.state.State;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    private static final String COMMAND_FILE_NAME = "command.txt";
    private static final String STATE_FILE_NAME = "state.json";

    public static void main(String[] args) {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} [{thread}] {class}.{method}() {level}: {message}")
                .level(Level.TRACE)
                .activate();
        BotAi naiveBot = new BotAi(State.read(STATE_FILE_NAME));
        writeBotResponseToFile(naiveBot.run());
    }

    private static void writeBotResponseToFile(String command) {
        try {
            Logger.debug("writing command: " + command);
            Files.write(Paths.get(COMMAND_FILE_NAME), command.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
