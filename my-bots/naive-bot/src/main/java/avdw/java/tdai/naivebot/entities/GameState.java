package avdw.java.tdai.naivebot.entities;

import avdw.java.tdai.naivebot.BotBehaviourTree;
import avdw.java.tdai.naivebot.enums.PlayerType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameState {
    public GameDetails gameDetails;
    protected Player[] players;
    protected CellStateContainer[][] gameMap;

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
}
