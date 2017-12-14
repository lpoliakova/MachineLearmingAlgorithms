package algorithms;

import org.junit.Assert;
import org.junit.Test;

public class ForwardBackwardTests {
    @Test
    public void computeOsTest() {
        int sizeS = 2;
        double[][] B = {{0.9, 0.1}, {0.2, 0.8}};
        int[] input = {0, 0, 1, 0, 0};
        double[][][] actual = ForwardBackward.computeOs(sizeS, B, input);

        double[][][] Os = {
                {{0.9, 0.0}, {0.0, 0.2}},
                {{0.9, 0.0}, {0.0, 0.2}},
                {{0.1, 0.0}, {0.0, 0.8}},
                {{0.9, 0.0}, {0.0, 0.2}},
                {{0.9, 0.0}, {0.0, 0.2}}};

        for (int i = 0; i < actual.length; i++) {
            for (int j = 0; j < actual[0].length; j++) {
                Assert.assertArrayEquals(Os[i][j], actual[i][j], 0.0001);
            }
        }
    }

    @Test
    public void computeForwardTest() {
        int sizeS = 2;
        double[] P = {0.4, 0.6};
        double[][] A = {{0.4, 0.6}, {0.8, 0.2}};
        double[][] B = {{0.4, 0.3, 0.3}, {0.1, 0.5, 0.4}};
        int[] input = {1, 0, 1, 1, 2};
        double[][][] Os = ForwardBackward.computeOs(sizeS, B, input);

        double[][] actual = ForwardBackward.computeForward(P, A, Os);

        double[][] Fs = {
                {0.12, 0.3},
                {0.1152, 0.0132},
                {0.017, 0.0359},
                {0.0107, 0.0087},
                {0.0034, 0.0033}
        };

        for (int i = 0; i < actual.length; i++) {
            Assert.assertArrayEquals(Fs[i], actual[i], 0.0001);
        }
    }

    @Test
    public void computeBackwardTest() {
        int sizeS = 2;
        double[][] A = {{0.4, 0.6}, {0.8, 0.2}};
        double[][] B = {{0.4, 0.3, 0.3}, {0.1, 0.5, 0.4}};
        int[] input = {1, 0, 1, 1, 2};
        double[][][] Os = ForwardBackward.computeOs(sizeS, B, input);

        double[][] actual = ForwardBackward.computeBackward(A, Os);

        double[][] Bs = {
                {0.0111, 0.0176},
                {0.0522, 0.0452},
                {0.1392, 0.1184},
                {0.36, 0.32},
                {1.0, 1.0}
        };

        for (int i = 0; i < actual.length; i++) {
            Assert.assertArrayEquals(Bs[i], actual[i], 0.0001);
        }
    }

    @Test
    public void computeProbabilitiesTest() {
        int sizeS = 2;
        double[] P = {0.4, 0.6};
        double[][] A = {{0.4, 0.6}, {0.8, 0.2}};
        double[][] B = {{0.4, 0.3, 0.3}, {0.1, 0.5, 0.4}};
        int[] input = {1, 0, 1, 1, 2};
        double[][][] Os = ForwardBackward.computeOs(sizeS, B, input);
        double[][] Fs = ForwardBackward.computeForward(P, A, Os);
        double[][] Bs = ForwardBackward.computeBackward(A, Os);

        double[][] actual = ForwardBackward.computeProbabilities(Fs, Bs);

        double[][] sP = {
                {0.0013, 0.0053},
                {0.0060, 0.0006},
                {0.0024, 0.0042},
                {0.0038, 0.0028},
                {0.0034, 0.0033}
        };

        for (int i = 0; i < actual.length; i++) {
            Assert.assertArrayEquals(sP[i], actual[i], 0.0001);
        }
    }

    @Test
    public void computeSmoothingTest() {
        int sizeS = 2;
        double[] P = {0.4, 0.6};
        double[][] A = {{0.4, 0.6}, {0.8, 0.2}};
        double[][] B = {{0.4, 0.3, 0.3}, {0.1, 0.5, 0.4}};
        int[] input = {1, 0, 1, 1, 2};
        double[][][] Os = ForwardBackward.computeOs(sizeS, B, input);
        double[][] Fs = ForwardBackward.computeForward(P, A, Os);
        double[][] Bs = ForwardBackward.computeBackward(A, Os);
        double[][] sP = ForwardBackward.computeProbabilities(Fs, Bs);

        double[][] actual = ForwardBackward.computeSmoothing(sP);

        double[][] pr = {
                {0.2009, 0.7991},
                {0.9097, 0.0903},
                {0.3576, 0.6424},
                {0.5798, 0.4202},
                {0.5084, 0.4916}
        };

        for (int i = 0; i < actual.length; i++) {
            Assert.assertArrayEquals(pr[i], actual[i], 0.0001);
        }
    }

    @Test
    public void computeTest() {
        int sizeO = 3;
        int sizeS = 2;
        double[] P = {0.4, 0.6};
        double[][] A = {{0.4, 0.6}, {0.8, 0.2}};
        double[][] B = {{0.4, 0.3, 0.3}, {0.1, 0.5, 0.4}};
        int[] input = {1, 0, 1, 1, 2};

        int[] actual = ForwardBackward.compute(sizeO, sizeS, P, A, B, input);

        int[] sequence = {1, 0, 1, 0, 0};

        Assert.assertArrayEquals(sequence, actual);
    }
}
