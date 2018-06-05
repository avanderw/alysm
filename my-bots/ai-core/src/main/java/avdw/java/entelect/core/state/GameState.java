package avdw.java.entelect.core.state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class GameState {
    public String command = "";
    public GameDetails gameDetails;
    protected Player[] players;
    protected CellStateContainer[][] gameMap;
    public Set<Integer> selectedLanes;

    public List<Player> getPlayers(){
        return new ArrayList<Player>(Arrays.asList(players));
    }

    public List<CellStateContainer> getGameMap(){
        ArrayList<CellStateContainer> list = new ArrayList<CellStateContainer>();

        for (int i = 0; i < gameMap.length; i++){
            for (int j = 0; j < gameMap[i].length; j ++){
                list.add(gameMap[i][j]);
            }
        }

        return list;
    }

    public int getEnergyFor(PlayerType playerType){
        return getPlayers().stream()
                .filter(p -> p.playerType == playerType)
                .mapToInt(p -> p.energy)
                .sum();
    }

    public int getEnergyGenerationFor(PlayerType playerType) {
        return gameDetails.roundIncomeEnergy + getGameMap().stream()
                .filter(cell-> cell.cellOwner == playerType)
                .map(cell->cell.buildings.stream().findFirst())
                .filter(building->building.isPresent())
                .filter(building->building.get().buildingType == BuildingType.ENERGY)
                .mapToInt(building->building.get().energyGeneratedPerTurn)
                .sum();
    }

    public int getBuildingPrice(BuildingType buildingType) {
        return gameDetails.buildingPrices.get(buildingType);
    }

    public int getMostExpensiveBuildingPrice(){
        int buildingPrice = 0;
        for (Integer value : gameDetails.buildingPrices.values()){
            if (value > buildingPrice){
                buildingPrice = value;
            }
        }
        return buildingPrice;
    }

    public long countBuildingsFor(PlayerType playerType, BuildingType buildingType) {
        return getGameMap().stream()
                .filter(c->c.cellOwner == playerType)
                .filter(c->c.getBuildings().stream().anyMatch(building->building.buildingType == buildingType))
                .count();
    }
}
