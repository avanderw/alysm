package avdw.java.tdai.naivebot.enums;

public enum BuildingType {
    DEFENSE("0"),
    ATTACK("1"),
    ENERGY("2"), EMPTY("-1");

    private final String commandCode;

    BuildingType(String commandCode) {
        this.commandCode = commandCode;
    }

    public String getCommandCode() {
        return commandCode;
    }
}
