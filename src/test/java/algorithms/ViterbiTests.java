package algorithms;

import org.junit.Assert;
import org.junit.Test;

public class ViterbiTests {
    @Test
    public void getTStateSTest() {
        int sizeS = 2;
        double[] TState = {0.12, 0.3};
        double[][] A = {{0.3, 0.7}, {0.6, 0.4}};
        double[][] B = {{0.3, 0.3, 0.4}, {0.5, 0.2, 0.3}};
        int s = 0;
        int input = 2;

        double[] actual = Viterbi.getTStateS(sizeS, TState, A, B, s, input);

        double[] tStateS = { 0.0144, 0.072};

        Assert.assertArrayEquals(tStateS, actual, 0.0001);
    }

    @Test
    public void getTStateAndTIndexTest() {
        int sizeS = 2;
        double[] P = {0.4, 0.6};
        double[][] A = {{0.4, 0.6}, {0.8, 0.2}};
        double[][] B = {{0.4, 0.3, 0.3}, {0.1, 0.5, 0.4}};
        int input[] = {1, 0, 1, 1, 2};

        double[][] actualTState = new double[input.length][];
        int[][] actualTIndex = new int[input.length][];
        Viterbi.getTStateAndTIndex(actualTState, actualTIndex, sizeS, P, A, B, input);

        double[][] TState = {
                {0.12, 0.3},
                {0.096, 0.0072},
                {0.01152, 0.0288},
                {0.006912, 0.003456},
                {0.00082944, 0.00165888}
        };
        int[][] TIndex = {
                {-1, -1},
                {1, 0},
                {0, 0},
                {1, 0},
                {0, 0}
        };

        for (int i = 0; i < actualTState.length; i++) {
            Assert.assertArrayEquals(TState[i], actualTState[i], 0.0001);
        }

        for (int i = 0; i < actualTIndex.length; i++) {
            Assert.assertArrayEquals(TIndex[i], actualTIndex[i]);
        }
    }

    @Test
    public void calculateTest() {
        int sizeO = 3;
        int sizeS = 2;
        double[] P = {0.4, 0.6};
        double[][] A = {{0.4, 0.6}, {0.8, 0.2}};
        double[][] B = {{0.4, 0.3, 0.3}, {0.1, 0.5, 0.4}};
        int input[] = {1, 0, 1, 1, 2};

        int[] actual = Viterbi.calculate(sizeO, sizeS, P, A, B, input);

        int[] sequence = {1, 0, 1, 0, 1};

        Assert.assertArrayEquals(sequence, actual);
    }
}
