package org.art.java_core.collections;

import java.util.*;

/**
 * Custom set collection implementation (via delegate pattern)
 * extended with the ability to get i-th min/max order statistic.
 *
 * NOTE: the algorithm of the order statistic definition is
 * not efficient because of intermediate sort operation.
 */
public class OrderStatisticSet {

    private Set<Integer> set;

    public OrderStatisticSet(List<Integer> list) {
        this.set = new TreeSet<>(list);
    }

    public void add(Integer val) {
        set.add(val);
    }

    public Integer getMin(int minOrder) {
        if(minOrder > set.size()) {
            System.out.println("Incorrect 'minOrder' value!");
            return null;
        }
        Iterator<Integer> iter = set.iterator();
        for(int i = 0; i < minOrder - 1; i++) {
            if(iter.hasNext()) {
                iter.next();
            }
        }
        return iter.next();
    }

    public Integer getMax(int maxOrder) {
        Set<Integer> reversedSet = new TreeSet<>(Comparator.reverseOrder());
        reversedSet.addAll(set);
        if(maxOrder > reversedSet.size()) {
            System.out.println("Incorrect 'maxOrder' value!");
            return null;
        }
        Iterator<Integer> iter = reversedSet.iterator();
        for(int i = 0; i < maxOrder - 1; i++) {
            if(iter.hasNext()) {
                iter.next();
            }
        }
        return iter.next();
    }

    @Override
    public String toString() {
        return set.toString();
    }
}
