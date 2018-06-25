package org.art.java_core.collections;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

/**
 * Physical phases and phase transition simulation
 * (EnumMap code example)
 */
public enum Phase {
    SOLID, LIQUID, GAS;

    public enum Transition {
        MELT(SOLID, LIQUID),
        FREEZE(LIQUID, SOLID),
        BOIL(LIQUID, GAS),
        CONDENSE(GAS, LIQUID),
        SUBLIME(SOLID, GAS),
        DEPOSIT(GAS, SOLID);

        private final Phase from;
        private final Phase to;

        Transition(Phase from, Phase to) {
            this.from = from;
            this.to = to;
        }

        //Initialize the phase transition map
        private static final Map<Phase, Map<Phase, Transition>> transMap =
                Stream.of(values()).collect(groupingBy(trans -> trans.from,
                        () -> new EnumMap<>(Phase.class), toMap(trans -> trans.to,
                                trans -> trans, (trans1, trans2) -> trans2, () -> new EnumMap<>(Phase.class))));

        public static Transition from(Phase from, Phase to) {
            return transMap.get(from).get(to);
        }
    }
}
