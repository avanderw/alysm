package avdw.java.tdai.naivebot.entities;

import avdw.java.tdai.naivebot.State;
import avdw.java.tdai.naivebot.enums.BuildingType;
import avdw.java.tdai.naivebot.enums.PlayerType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {
    static GameState state;

    @BeforeAll
    static void setUp() {
        state = State.read("./src/test/resources/basic/less-energy.json");
    }

    @Test
    void getEnergyFor() {
        assertEquals(50, state.getEnergyFor(PlayerType.A));
    }

    @Test
    void getEnergyGenerationFor() {
        assertEquals(5, state.getEnergyGenerationFor(PlayerType.A));
        assertEquals(8, State.read("./src/test/resources/basic/built-energy.json").getEnergyGenerationFor(PlayerType.A));
    }


    @Test
    void getBuildingPrice() {
        assertEquals(20, state.getBuildingPrice(BuildingType.ENERGY));
    }

    @Test
    void getMostExpensiveBuildingPrice() {
        assertEquals(30, state.getMostExpensiveBuildingPrice());
    }
}