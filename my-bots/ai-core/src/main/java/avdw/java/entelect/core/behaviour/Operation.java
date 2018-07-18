package avdw.java.entelect.core.behaviour;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Operation {
    GREATER_THAN(">"), EQUALS("="), LESS_THAN_EQUAL("<="), LESS_THAN("<"), GREATER_THAN_EQUAL(">="), ANY("*");

    private static Map<String, Operation> mappings = new HashMap();
    public String key;

    Operation(String key) {
        this.key = key;

    }

    static {
        EnumSet.allOf(Operation.class).stream().forEach(operation -> {
            mappings.put(operation.key, operation);
        });

    }

    public static Operation map(String key) {
        return mappings.get(key);
    }
}
