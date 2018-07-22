package avdw.java.entelect.animusai;

import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.PlayerType;
import avdw.java.entelect.core.state.State;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String COMMAND_FILE_NAME = "command.txt";
    private static final String STATE_FILE_NAME = "state.json";

    public static void main(String[] args) {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} [{thread}] {class}.{method}() {level}: {message}")
                .level(Level.DEBUG)
                .activate();


        Path replaysDir = Paths.get("C:\\Users\\van der Westhuizen\\Documents\\GitHub\\alysm\\my-bots\\runner\\replays");
        Arrays.stream(replaysDir.toFile().listFiles()).forEach(replayDir -> {
            List<Attributes> attributesList = new ArrayList();
            Arrays.stream(replayDir.listFiles()).forEach(roundDir -> {
                Arrays.stream(roundDir.listFiles())
                        .filter(playerDir -> playerDir.getName().startsWith("A"))
                        .forEach(playerDir -> {
                            Arrays.stream(playerDir.listFiles((dir, name) -> name.equals("JsonMap.json"))).forEach(stateFile -> {
                                GameState state = State.read(stateFile.getAbsolutePath());
                                attributesList.add(new Attributes(state));
                            });
                            Arrays.stream(playerDir.listFiles((dir, name) -> name.equals("PlayerCommand.txt"))).forEach(stateFile -> {
                                try {
                                    Scanner scanner = new Scanner(stateFile);
                                    String line = scanner.nextLine();
                                    String building = null;
                                    switch (line.substring(line.length()-1)) {
                                        case "0": building = "Defense"; break;
                                        case "1": building = "Attack"; break;
                                        case "2": building = "Energy"; break;
                                    }

                                    attributesList.get(attributesList.size()-1).built = building;
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            });
                        });
            });

            Long aSTotal = 0L;
            Long bSTotal = 0L;
            for (int i = 0; i < attributesList.size(); i++) {
                Attributes prevAttributes;
                if (i == 0) {
                    prevAttributes = attributesList.get(0);
                } else {
                    prevAttributes = attributesList.get(i - 1);
                }

                Attributes attributes = attributesList.get(i);
                attributes.playerATotalIncome = aSTotal += attributes.playerAIncome;
                attributes.playerBTotalIncome = bSTotal += attributes.playerBIncome;
                attributes.energyRoundDiff = attributes.playerAEnergy - prevAttributes.playerAEnergy;
                attributes.attackRoundDiff = attributes.playerAAttack - prevAttributes.playerAAttack;
            }

            System.out.println();
            System.out.println(replayDir.getName());
            Integer pagingSize = 15;
            Integer index = 0;
            while (index < attributesList.size()) {
                System.out.println(String.format("%10s | %10s | %10s | %10s | %10s | %10s | %10s | %10s | %10s",
                        "Round", "Income", "ATT Diff", "NRG Diff", "ATT Delta", "NRG Delta", "A Health", "B Health", "Built"));
                attributesList.subList(index, Math.min(index+=pagingSize,attributesList.size())).forEach(System.out::println);
            }
        });
    }
}

class Attributes {
    final Integer round;
    final Integer playerAStorage;
    final Integer playerBStorage;
    final Integer playerAAttack;
    final Integer playerBAttack;
    final Integer playerAEnergy;
    final Integer playerBEnergy;
    final Integer playerAHealth;
    final Integer playerBHealth;
    final Long playerAIncome;
    final Long playerBIncome;
    Long playerATotalIncome;
    Long playerBTotalIncome;
    String built;
    Integer energyRoundDiff;
    Integer attackRoundDiff;

    public Attributes(GameState state) {
        round = state.gameDetails.round;

        playerAStorage = state.getPlayers().stream()
                .filter(player -> player.playerType == PlayerType.A)
                .mapToInt(player -> player.energy)
                .sum();
        playerBStorage = state.getPlayers().stream()
                .filter(player -> player.playerType == PlayerType.B)
                .mapToInt(player -> player.energy)
                .sum();

        playerAIncome = state.getPlayers().stream()
                .filter(player -> player.playerType == PlayerType.A)
                .mapToLong(player -> state.getEnergyGenerationFor(PlayerType.A))
                .sum();
        playerBIncome = state.getPlayers().stream()
                .filter(player -> player.playerType == PlayerType.B)
                .mapToLong(player -> state.getEnergyGenerationFor(PlayerType.B))
                .sum();

        playerAHealth = state.getPlayers().stream()
                .filter(player -> player.playerType == PlayerType.A)
                .mapToInt(player -> player.health)
                .sum();
        playerBHealth = state.getPlayers().stream()
                .filter(player -> player.playerType == PlayerType.B)
                .mapToInt(player ->  player.health)
                .sum();

        playerAAttack = state.getGameMap().stream()
                .filter(cell -> cell.cellOwner == PlayerType.A)
                .map(cell -> cell.getBuildings().stream().findFirst())
                .filter(building -> building.isPresent())
                .mapToInt(building -> building.get().buildingType == BuildingType.ATTACK ? 1 : 0)
                .sum();

        playerBAttack = state.getGameMap().stream()
                .filter(cell -> cell.cellOwner == PlayerType.B)
                .map(cell -> cell.getBuildings().stream().findFirst())
                .filter(building -> building.isPresent())
                .mapToInt(building -> building.get().buildingType == BuildingType.ATTACK ? 1 : 0)
                .sum();

        playerAEnergy = state.getGameMap().stream()
                .filter(cell -> cell.cellOwner == PlayerType.A)
                .map(cell -> cell.getBuildings().stream().findFirst())
                .filter(building -> building.isPresent())
                .mapToInt(building -> building.get().buildingType == BuildingType.ENERGY ? 1 : 0)
                .sum();

        playerBEnergy = state.getGameMap().stream()
                .filter(cell -> cell.cellOwner == PlayerType.B)
                .map(cell -> cell.getBuildings().stream().findFirst())
                .filter(building -> building.isPresent())
                .mapToInt(building -> building.get().buildingType == BuildingType.ENERGY ? 1 : 0)
                .sum();
    }

    @Override
    public String toString() {
        return String.format("%10s | %10s | %10s | %10s | %10s | %10s | %10s | %10s | %10s",
                round, playerATotalIncome - playerBTotalIncome,
                playerAAttack - playerBAttack, playerAEnergy - playerBEnergy, attackRoundDiff,
                energyRoundDiff, playerAHealth, playerBHealth,built);
    }
}
