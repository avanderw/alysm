package avdw.java.entelect.animusai;

import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.PlayerType;
import avdw.java.entelect.core.state.State;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
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
            final List<String> winnerText = new ArrayList();
            System.out.println("processing " + replayDir);
            List<Attributes> attributesList = new ArrayList();
            Arrays.stream(replayDir.listFiles()).forEach(roundDir -> {
                Arrays.stream(roundDir.listFiles())
                        .filter(playerDir -> playerDir.getName().startsWith("endGameState"))
                        .forEach(file -> {
                            try {
                                winnerText.addAll(Files.readAllLines(Paths.get(file.getPath())));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
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
                                    switch (line.substring(line.length() - 1)) {
                                        case "0":
                                            building = "Defense";
                                            break;
                                        case "1":
                                            building = "Attack";
                                            break;
                                        case "2":
                                            building = "Energy";
                                            break;
                                        case "3":
                                            building = "Deconstruct";
                                            break;
                                        case "4":
                                            building = "Tesla";
                                            break;
                                        case "5":
                                            building = "Iron Curtain";
                                            break;
                                    }

                                    attributesList.get(attributesList.size() - 1).aBuilt = building;
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            });
                        });
                Arrays.stream(roundDir.listFiles())
                        .filter(playerDir -> playerDir.getName().startsWith("B"))
                        .forEach(playerDir -> {
                            Arrays.stream(playerDir.listFiles((dir, name) -> name.equals("PlayerCommand.txt"))).forEach(stateFile -> {
                                try {
                                    Scanner scanner = new Scanner(stateFile);
                                    String line = scanner.nextLine();
                                    String building = null;
                                    switch (line.substring(line.length() - 1)) {
                                        case "0":
                                            building = "Defense";
                                            break;
                                        case "1":
                                            building = "Attack";
                                            break;
                                        case "2":
                                            building = "Energy";
                                            break;
                                        case "3":
                                            building = "Deconstruct";
                                            break;
                                        case "4":
                                            building = "Tesla";
                                            break;
                                        case "5":
                                            building = "Iron Curtain";
                                            break;
                                    }

                                    attributesList.get(attributesList.size() - 1).bBuilt = building;
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

            Path path = replaysDir.resolve(replayDir.getName() + ".csv");
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
                bufferedWriter.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s%n",
                        "Round", "Accumulated(Energy)", "Round(Energy)", "History(Energy)", "Round(Attack)", "History(Attack)", "A(Health)", "B(Health)", "A(Built)", "B(Built)", "A(Energy Build)", "B(Energy Build)", "A(Energy)", "B(Energy)", "A(Attack)", "B(Attack)"));
                attributesList.forEach(a -> {
                    try {
                        bufferedWriter.write(a.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                bufferedWriter.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s%n",
                        "", "", attributesList.stream().mapToInt(a -> a.playerAEnergy - a.playerBEnergy).sum(), "", attributesList.stream().mapToInt(a -> a.playerAAttack - a.playerBAttack).sum(),
                        "", "", "",
                        String.format("Energy(%s)",attributesList.stream().filter(a-> a.aBuilt!=null && a.aBuilt.equals("Energy")).count()),
                        String.format("Energy(%s)",attributesList.stream().filter(a-> a.bBuilt!=null && a.bBuilt.equals("Energy")).count())));
                bufferedWriter.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s%n",
                        "", "", "", "", "", "", "", "",
                        String.format("Attack(%s)",attributesList.stream().filter(a-> a.aBuilt!=null && a.aBuilt.equals("Attack")).count()),
                        String.format("Attack(%s)",attributesList.stream().filter(a-> a.bBuilt!=null && a.bBuilt.equals("Attack")).count())));
                bufferedWriter.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s%n",
                        "", "", "", "", "", "", "", "",
                        String.format("Total(%s)",attributesList.stream().filter(a-> a.aBuilt!=null).count()),
                        String.format("Total(%s)",attributesList.stream().filter(a-> a.bBuilt!=null).count())));
                winnerText.forEach(s -> {
                    try {
                        bufferedWriter.write(s + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
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
    String aBuilt;
    String bBuilt;
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
                .mapToInt(player -> player.health)
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
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s%n",
                round, playerATotalIncome - playerBTotalIncome,
                playerAEnergy - playerBEnergy, energyRoundDiff, playerAAttack - playerBAttack, attackRoundDiff,
                playerAHealth, playerBHealth, aBuilt, bBuilt, playerAEnergy, playerBEnergy, playerAStorage, playerBStorage,
                playerAAttack, playerBAttack);
    }
}
