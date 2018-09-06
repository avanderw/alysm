package avdw.java.entelect.core.state;

public enum BuildingType {
    EMPTY("-1"),
    DEFENSE("0"),
    ATTACK("1"),
    ENERGY("2"),
    DECONSTRUCT("3"),
    TESLA("4"),
    IRON_CURTAIN("5");

    private final String commandCode;

    BuildingType(String commandCode) {
        this.commandCode = commandCode;
    }

    public String getCommandCode() {
        return commandCode;
    }
}
