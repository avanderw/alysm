package avdw.java.entelect.core.behaviour.guard;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.state.BuildingType;
import avdw.java.entelect.core.state.GameState;
import avdw.java.entelect.core.state.PlayerType;
import org.pmw.tinylog.Logger;

public class Guard extends ABehaviourTree<GameState> {
    private String expression;
    private Boolean evaluation;
    private String description;

    public Guard(String expression, Object... values) {
        this.expression = expression;

        for (Object value : values) {
            this.expression = expression.replaceFirst("\\$", value.toString());
        }
    }

    public Guard(Boolean evaluation, String description) {
        this.evaluation = evaluation;
        this.description = description;
    }

    @Override
    public Status process(GameState state) {
        if (evaluation == null) {
            String[] tokens = expression.split(" ");
            Long left = evaluate(tokens[0], state);
            Long right = evaluate(tokens[2], state);

            switch (tokens[1]) {
                case "<":
                    evaluation = left < right;
                    break;
                case ">":
                    evaluation = left > right;
                    break;
                case "==":
                    evaluation = left == right;
                    break;
                case "<=":
                    evaluation = left <= right;
                    break;
                case ">=":
                    evaluation = left >= right;
                    break;
                default:
                    throw new UnsupportedOperationException(String.format("not implemented [%s]", expression));
            }
        }

        if (evaluation) {
            Logger.debug(String.format("[SUCCESS] Guard passed [%s]", expression == null ? description : expression));
            return Status.Success;
        } else {
            Logger.debug(String.format("[FAILURE] Guard failed [%s]", expression == null ? description : expression));
            return Status.Failure;
        }
    }

    private Long evaluate(String token, GameState state) {
        PlayerType player;
        if (token.startsWith("A")) {
            player = PlayerType.A;
        } else if (token.startsWith("B")) {
            player = PlayerType.B;
        } else {
            return Long.parseLong(token);
        }

        Long evaluation = -1L;
        switch (token.substring(token.indexOf("{") + 1, token.indexOf("}"))) {
            case "T":
                evaluation = state.countBuildingsFor(player, BuildingType.TESLA);
                break;
            case "E":
                evaluation = state.getEnergyFor(player);
                break;
            case "A":
                evaluation = state.countBuildingsFor(player, BuildingType.ATTACK);
                break;
            case "G":
                evaluation = state.getEnergyGenerationFor(player);
                break;
            default:
                throw new UnsupportedOperationException(String.format("not implemented [%s]", token));
        }

        return evaluation;
    }
}
