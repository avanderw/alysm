package avdw.java.entelect.core.behaviour;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Operation {
    GREATER_THAN(">"), EQUALS("="), LESS_THAN_OR_EQUALS("<="), LESS_THAN("<");

    private static Map<String, Operation> mappings = new HashMap();
    private String key;

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
