package algorithms;

import output.ViterbiOut;

import java.util.Arrays;

public class Viterbi {
    public static int[] calculate(int sizeO, int sizeS, double[] P, double[][] A, double[][] B, int[] input) {
        //TODO: checks

        double[][] TState = new double[input.length][];
        int[][] TIndex = new int[input.length][];

        getTStateAndTIndex(TState, TIndex, sizeS, P, A, B, input);

        return getSequence(TState, TIndex);
    }

    static void getTStateAndTIndex(double[][] TState, int[][] TIndex, int sizeS, double[] P, double[][] A, double[][] B, int[] input) {
        TState[0] = getFirstTState(P, B, input[0]);
        TIndex[0] = getFirstTIndex(sizeS);

        ViterbiOut.printFormulas();

        for (int t = 1; t < input.length; t++) {
            double[] tStateT = new double[sizeS];
            int[] tIndexT = new int[sizeS];
            for (int s = 0; s < sizeS; s++) {
                double[] tStateS = getTStateS(sizeS, TState[t - 1], A, B, s, input[t]);
                tStateT[s] = getMax(tStateS);
                tIndexT[s] = getMaxIndex(tStateS);
                ViterbiOut.printTStateS(A, B, input[t], TState[t - 1], tStateS, tStateT[s], t, s);
            }
            TState[t] = tStateT;
            TIndex[t] = tIndexT;
        }

        ViterbiOut.printTStateAndTSequence(TState, TIndex);
    }

    static double[] getTStateS(int sizeS, double[] TState, double[][] A, double[][] B, int s, int input) {
        double[] tStateS = new double[sizeS];
        for (int fromS = 0; fromS < sizeS; fromS++) {
            tStateS[fromS] = TState[fromS] * A[fromS][s] * B[s][input];
        }
        return tStateS;
    }

    private static int[] getSequence(double[][] TState, int[][] TIndex) {
        int[] sequence = new int[TIndex.length];
        sequence[TIndex.length - 1] = getMaxIndex(TState[TState.length - 1]);
        for (int t = TIndex.length - 1; t > 0; t--) {
            sequence[t - 1] = TIndex[t][sequence[t]];
        }
        ViterbiOut.printSequence(sequence);
        return sequence;
    }

    private static double[] getFirstTState(double[] P, double[][] B, int input) {
        double[] tState = new double[P.length];
        for (int i = 0; i < tState.length; i++) {
            tState[i] = B[i][input] * P[i];
        }
        ViterbiOut.printFirstTState(P, B, input, tState);
        return tState;
    }

    private static int[] getFirstTIndex(int sizeS) {
        int[] tIndex = new int[sizeS];
        for (int i = 0; i < sizeS; i++) {
            tIndex[i] = -1;
        }
        return tIndex;
    }

    private static double getMax(double[] array) {
        return Arrays.stream(array).max().orElse(0);
    }

    private static int getMaxIndex(double[] array) {
        int index = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[index]) {
                index = i;
            }
        }
        return index;
    }
}
