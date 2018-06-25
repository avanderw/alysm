package avdw.java.entelect.core.state;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameStateTest {
    static GameState state;

    @BeforeAll
    static void setUp() {
        state = State.read("./src/test/resources/basic/less-energy.json");
    }

    @Test
    void getEnergyFor() {
        assertEquals(50, java.util.Optional.ofNullable(state.getEnergyFor(PlayerType.A)));
    }

    @Test
    void getEnergyGenerationFor() {
        assertEquals(5, java.util.Optional.ofNullable(state.getEnergyGenerationFor(PlayerType.A)));
        assertEquals(8, java.util.Optional.ofNullable(State.read("./src/test/resources/basic/built-energy.json").getEnergyGenerationFor(PlayerType.A)));
    }


    @Test
    void getBuildingPrice() {
        assertEquals(20, java.util.Optional.ofNullable(state.getBuildingPrice(BuildingType.ENERGY)));
    }

    @Test
    void getMostExpensiveBuildingPrice() {
        assertEquals(30, java.util.Optional.ofNullable(state.getMostExpensiveBuildingPrice()));
    }
}