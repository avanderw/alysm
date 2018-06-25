package avdw.java.entelect.core.state;

import java.util.*;

public class GameState {
    public String command = "";
    public GameDetails gameDetails;
    protected Player[] players;
    protected CellStateContainer[][] gameMap;
    public Set<Integer> selectedLanes = new HashSet();

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

    public Long getEnergyFor(PlayerType playerType){
        return (long) getPlayers().stream()
                .filter(p -> p.playerType == playerType)
                .mapToInt(p -> p.energy)
                .sum();
    }

    public Long getEnergyGenerationFor(PlayerType playerType) {
        return (long) gameDetails.roundIncomeEnergy + getGameMap().stream()
                .filter(cell-> cell.cellOwner == playerType)
                .map(cell->cell.buildings.stream().findFirst())
                .filter(building->building.isPresent())
                .filter(building->building.get().buildingType == BuildingType.ENERGY)
                .mapToInt(building->building.get().energyGeneratedPerTurn)
                .sum();
    }

    public Long getBuildingPrice(BuildingType buildingType) {
        return (long) gameDetails.buildingPrices.get(buildingType);
    }

    public Long getMostExpensiveBuildingPrice(){
        int buildingPrice = 0;
        for (Integer value : gameDetails.buildingPrices.values()){
            if (value > buildingPrice){
                buildingPrice = value;
            }
        }
        return (long) buildingPrice;
    }

    public long countBuildingsFor(PlayerType playerType, BuildingType buildingType) {
        return getGameMap().stream()
                .filter(c->c.cellOwner == playerType)
                .filter(c->c.getBuildings().stream().anyMatch(building->building.buildingType == buildingType))
                .count();
    }
}
