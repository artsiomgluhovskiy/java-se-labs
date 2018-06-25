package org.art.java_core.algorithms;

/**
 * 'Towers of Hanoi' algorithm implementation (from Stolarov book).
 */
public class HanoiTowers {

    /**
     * Prints the solution of the quiz.
     * @param source - the number of the source rod
     * @param target - the number of the target rod
     * @param transition - the number of the transition rod
     * @param n - the number of discs
     */
    public static void solve(int source, int target, int transition, int n) {
        if (n == 0) {
            return;
        }
        solve(source, transition, target, n - 1);
        System.out.println("Disc: " + n + ". Source rod: " + source + " -> Target rod: " + target);
        solve(transition, target, source, n - 1);
    }

    public static void main(String[] args) {
        solve(1, 2, 3, 3);
    }
}
