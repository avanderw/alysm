package avdw.java.entelect.core.behaviour.filter;

import avdw.java.entelect.core.behaviour.ABehaviourTree;
import avdw.java.entelect.core.state.GameState;

import java.util.*;
import java.util.stream.Collectors;

public class SortedLaneSelector extends ABehaviourTree<GameState> {
    static Map<String, Comparator<LaneParameter>> comparators = new HashMap();

    static {
        comparators.put("AA", Comparator.comparing(o -> o.aAttack));
        comparators.put("AD", Comparator.comparing(o -> o.aDefense));
        comparators.put("AE", Comparator.comparing(o -> o.aEnergy));
        comparators.put("BA", Comparator.comparing(o -> o.bAttack));
        comparators.put("BD", Comparator.comparing(o -> o.bDefense));
        comparators.put("BE", Comparator.comparing(o -> o.bEnergy));
    }

    final ABehaviourTree<GameState> laneSelectionBehaviourTree = new ABehaviourTree.Selector();

    public SortedLaneSelector(String shorthand) {
        final Map<String, GenSort> genSortMap = new HashMap();
        final PriorityQueue<String> sortOrder = new PriorityQueue();

        Arrays.stream(shorthand.split(";")).forEach(player -> {
            player = player.trim();
            char playerType = player.charAt(0);

            String[] buildings = player.substring(2, player.length() - 1).split(",");
            Arrays.stream(buildings).forEach(building -> {
                building = building.trim();
                char buildingType = building.charAt(1);
                String key = playerType + "" + buildingType;
                Boolean toSort = building.charAt(0) != '*';
                String generate = building.length() > 5 ? building.substring(3, 5) : "";
                genSortMap.put(key, new GenSort(generate));
                if (toSort) {
                    sortOrder.add(building.charAt(0) + key);
                }
            });
        });

        List<LaneParameter> list = generateLaneSelectorParameters(genSortMap.get("AA"), genSortMap.get("AD"), genSortMap.get("AE"),
                genSortMap.get("BA"), genSortMap.get("BD"), genSortMap.get("BE"));

        List<String> orderList = new ArrayList();
        while (!sortOrder.isEmpty()) {
            orderList.add(sortOrder.remove().substring(1));
        }

        createLaneSelectors(list, orderList, genSortMap).stream()
                .forEach(laneSelectionBehaviourTree::add);
    }

    @Override
    public Status process(GameState state) {
        return laneSelectionBehaviourTree.process(state);
    }

    private List<LaneSelector> createLaneSelectors(List<LaneParameter> list, List<String> sortOrder, Map<String, GenSort> genSortMap) {
        Comparator comparator;
        if (genSortMap.get(sortOrder.get(0)).asc) {
            comparator = comparators.get(sortOrder.get(0));
        } else {
            comparator = comparators.get(sortOrder.get(0)).reversed();
        }
        for (String sort : sortOrder.subList(1, sortOrder.size())) {
            if (genSortMap.get(sort).asc) {
                comparator = comparator.thenComparing(comparators.get(sort));
            } else {
                comparator = comparator.thenComparing(comparators.get(sort).reversed());
            }
        }

        return (List<LaneSelector>) list.stream().sorted(comparator).map(laneParameters -> new LaneSelector(laneParameters.toString())).collect(Collectors.toList());
    }

    private List<LaneParameter> generateLaneSelectorParameters(GenSort aAttack, GenSort aDefense, GenSort aEnergy,
                                                               GenSort bAttack, GenSort bDefense, GenSort bEnergy) {
        Set<LaneParameter> set = new HashSet();
        for (int param1 = aAttack.lower; param1 <= aAttack.upper; param1++) {
            for (int param2 = aDefense.lower; param2 <= aDefense.upper; param2++) {
                for (int param3 = aEnergy.lower; param3 <= aEnergy.upper; param3++) {
                    for (int param4 = bAttack.lower; param4 <= bAttack.upper; param4++) {
                        for (int param5 = bDefense.lower; param5 <= bDefense.upper; param5++) {
                            for (int param6 = bEnergy.lower; param6 <= bEnergy.upper; param6++) {
                                set.add(new LaneParameter(param1, param2, param3, param4, param5, param6));
                            }
                        }
                    }
                }
            }
        }

        List<LaneParameter> list = new ArrayList();
        list.addAll(set);
        return list;
    }
}

class LaneParameter {
    public Integer aAttack;
    public Integer aDefense;
    public Integer aEnergy;
    public Integer bAttack;
    public Integer bDefense;
    public Integer bEnergy;

    public LaneParameter(Integer aAttack, Integer aDefense, Integer aEnergy,
                         Integer bAttack, Integer bDefense, Integer bEnergy) {
        this.aAttack = aAttack;
        this.aDefense = aDefense;
        this.aEnergy = aEnergy;
        this.bAttack = bAttack;
        this.bDefense = bDefense;
        this.bEnergy = bEnergy;
    }

    @Override
    public String toString() {
        return String.format("A{ A = %d, D = %d, E = %d }; B{ A = %d, D = %d, E = %d }", aAttack, aDefense, aEnergy, bAttack, bDefense, bEnergy);
    }

    @Override
    public boolean equals(Object obj) {
        if (!LaneParameter.class.isInstance(obj)) {
            return false;
        }

        LaneParameter that = (LaneParameter) obj;
        return this.aAttack == that.aAttack &&
                this.aDefense == that.aDefense &&
                this.aEnergy == that.aEnergy &&
                this.bAttack == that.bAttack &&
                this.bDefense == that.bDefense &&
                this.bEnergy == that.bEnergy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(aAttack, aDefense, aEnergy, bAttack, bDefense, bEnergy);
    }
}

class GenSort {
    public Boolean asc;
    public Integer lower;
    public Integer upper;

    GenSort(String generate) {
        if (generate.length() > 0) {
            Integer first, last;
            switch (generate.charAt(0)) {
                case 'A':
                case 'D':
                    asc = generate.charAt(0) == 'A';
                    first = asc ? 0 : Character.getNumericValue(generate.charAt(1));
                    last = asc ? Character.getNumericValue(generate.charAt(1)) : 0;
                    break;
                default:
                    first = Character.getNumericValue(generate.charAt(0));
                    last = Character.getNumericValue(generate.charAt(1));
                    asc = first < last;
                    break;
            }
            lower = Math.min(first, last);
            upper = Math.max(first, last);

        } else {
            lower = upper = 0;
        }
    }
}
