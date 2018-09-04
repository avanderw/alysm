package avdw.java.entelect.jarvisai;

import avdw.java.entelect.core.api.BotBehaviourTree;
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
                .level(Level.DEBUG)
                .activate();

        Logger.info("started");
        try {
            BotBehaviourTree ai = new BotAi(State.read(STATE_FILE_NAME));
            writeBotResponseToFile(ai.run());
        } catch (Exception e) {
            Logger.error(e);
            writeBotResponseToFile("0,0,0");
        }
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
