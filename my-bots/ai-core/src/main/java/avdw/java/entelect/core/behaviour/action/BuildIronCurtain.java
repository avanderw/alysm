package avdw.java.entelect.core.behaviour.action;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.PlayerType;
import org.pmw.tinylog.Logger;

public class BuildIronCurtain extends ABehaviourTree<GameState> {
    @Override
    public Status process(GameState state) {
        if (state.getEnergyFor(PlayerType.A) < state.getIronCurtainPrice()) {
            Logger.debug(String.format("[FAILURE] Not enough energy to build IRON_CURTAIN"));
            return Status.Failure;
        }


        state.command = String.format("0,0,%s", BuildingType.IRON_CURTAIN.getCommandCode());
        Logger.debug(String.format("[SUCCESS] Building IRON_CURTAIN"));
        return Status.Success;
    }
}
