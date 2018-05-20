package avdw.java.tdai.naivebot;

import com.google.gson.Gson;
import avdw.java.tdai.naivebot.entities.GameState;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    private static final String COMMAND_FILE_NAME = "command.txt";
    private static final String STATE_FILE_NAME = "state.json";

    public static void main(String[] args) {
        BotBehaviourTree botBehaviourTree = new BotBehaviourTree(State.read(STATE_FILE_NAME));
        writeBotResponseToFile(botBehaviourTree.run());
    }

    private static void writeBotResponseToFile(String command) {
        try {
            Files.write(Paths.get(COMMAND_FILE_NAME), command.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
