package org.art.java_core.fork_join_pool;

import java.util.concurrent.CountedCompleter;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * Finds the max element in the array. MapReduce algorithm is
 * implemented (map function can be applied to array values
 * before results reduction).
 */
public class MapReducer extends CountedCompleter<Integer> {

    private int[] array;
    private int lo;
    private int hi;
    private Function<Integer, Integer> mapper;                  //Some map function for values transformation
    private BinaryOperator<Integer> reducer = Math::max;        //Reduce function for finding the max element in the pair
    private MapReducer sibling;
    private Integer result;

    public MapReducer(CountedCompleter<Integer> completer, int[] array, Function<Integer, Integer> mapper, int lo, int hi) {
        super(completer);
        this.array = array;
        this.mapper = mapper;
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    public void compute() {
        if (hi - lo >= 2) {
            int mid = (lo + hi) >> 1;
            MapReducer left = new MapReducer(this, array, mapper, lo, mid);
            MapReducer right = new MapReducer(this, array, mapper, mid, hi);
            left.sibling = right;
            right.sibling = left;
            setPendingCount(1);         //only right is pending
            right.fork();
            left.compute();
        } else {
            if (hi > lo) {
                result = mapper.apply(findMax(lo, hi, array));
            }
            tryComplete();
        }
    }

    @Override
    public void onCompletion(CountedCompleter<?> caller) {
        if (caller != this) {
            MapReducer child = (MapReducer) caller;
            MapReducer sib = child.sibling;
            if (sib == null || sib.result == 0) {
                result = child.result;
            } else {
                result = reducer.apply(child.result, sib.result);
            }
        }
    }

    @Override
    public Integer getRawResult() {
        return this.result;
    }

    /**
     * Finds the max element on the specified interval (from 'lo' to 'hi') in the array.
     */
    private static int findMax(int lo, int hi, int[] array) {
        int elem;
        int max = array[lo];
        if (hi > lo) {
            for (int i = lo; i < hi - 1; i++) {
                if ((elem = array[lo + 1]) > max) {
                    max = elem;
                }
            }
        }
        return max;
    }
}

