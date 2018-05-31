package avdw.java.entelect.core.state;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class State {
    public static GameState read(String statePath) {
        String state = null;
        try {
            state = new String(Files.readAllBytes(Paths.get(statePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        return gson.fromJson(state, GameState.class);
    }
}
