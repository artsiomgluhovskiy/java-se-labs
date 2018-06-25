package org.art.java_core.algorithms;

/**
 * The longest common sequence finder (from T. Cormen).
 */
public class LCSFinder {

    public static int computeLCS(String seq1, String seq2, int[][] lengthTable, int[][] solutionTable) {
        int m = seq1.length() + 1;
        int n = seq2.length() + 1;
        for (int i = 0; i < m; i++) {
            lengthTable[i][0] = 0;
        }
        for (int j = 0; j < n; j++) {
            lengthTable[0][j] = 0;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (seq1.charAt(i - 1) == seq2.charAt(j - 1)) {
                    lengthTable[i][j] = lengthTable[i - 1][j - 1] + 1;
                    solutionTable[i][j] = 1;        //arrow from bottom right side to upper left
                } else if (lengthTable[i - 1][j] >= lengthTable[i][j - 1]) {
                    lengthTable[i][j] = lengthTable[i - 1][j];
                    solutionTable[i][j] = 2;        //up arrow
                } else {
                    lengthTable[i][j] = lengthTable[i][j - 1];
                    solutionTable[i][j] = 3;        //left arrow
                }
            }
        }
        return lengthTable[m - 1][n - 1];
    }

    public static void printLCS(int[][] solutionTable, String seq1, int i, int j) {
        if (i == 0 || j == 0) {
            return;
        }
        if (solutionTable[i][j] == 1) {
            printLCS(solutionTable, seq1, i - 1, j - 1);
            System.out.print(seq1.charAt(i - 1));
        } else if (solutionTable[i][j] == 2) {
            printLCS(solutionTable, seq1, i - 1, j);
        } else {
            printLCS(solutionTable, seq1, i, j - 1);
        }
    }

    public static void main(String[] args) {
        String seq1 = "ABCBDAB";
        String seq2 = "BDCABA";
        int m = seq1.length() + 1;
        int n = seq2.length() + 1;
        int[][] lengthTable = new int[m][n];
        int[][] solutionTable = new int[m][n];
        int lCSLength = computeLCS(seq1, seq2, lengthTable, solutionTable);
        System.out.println("The longest common sequence length: " + lCSLength);
        //Printing the solution (the longest common sequence)
        printLCS(solutionTable, seq1, seq1.length(), seq2.length());
    }
}
