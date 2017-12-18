package forwardBackward;

import java.util.Arrays;

public class ForwardBackward {
    public static int[] compute(int sizeO, int sizeS, double[] P, double[][] A, double[][] B, int[] input) {
        //TODO: checks
        double[][][] Os = computeOs(sizeS, B, input);
        double[][] Fs = computeForward(P, A, Os);
        double[][] Bs = computeBackward(A, Os);
        double[][] sP = computeProbabilities(Fs, Bs);
        double[][] pr = computeSmoothing(sP);
        return findSequence(pr);
    }

    static double[][][] computeOs(int sizeS, double[][] B, int[] input) {
        double[][][] Os = new double[input.length][][];
        for (int i = 0; i < input.length; i++) {
            Os[i] = computeO(sizeS, B, input[i]);
        }
        ForwardBackwardOut.printOs(Os);
        return Os;
    }

    static double[][] computeForward(double[] P, double[][] A, double[][][] Os) {
        double[][] Fs = new double[Os.length][];
        Fs[0] = firstF(P, Os[0]);
        for (int i = 1; i < Os.length; i++) {
            Fs[i] = computeF(Fs[i - 1], A, Os[i]);
        }
        ForwardBackwardOut.printForward(P, Os, A, Fs);
        return Fs;
    }

    static double[][] computeBackward(double[][] A, double[][][] Os) {
        double[][] Bs = new double[Os.length][];
        Bs[Os.length - 1] = firstB(A.length);
        for (int i = Os.length - 2; i >= 0; i--) {
            Bs[i] = computeB(Bs[i + 1], A, Os[i + 1]);
        }
        ForwardBackwardOut.printBackward(Os, A, Bs);
        return Bs;
    }

    static double[][] computeProbabilities(double[][] Fs, double[][] Bs) {
        double[][] sProbability = new double[Fs.length][Fs[0].length];
        for (int t = 0; t < Fs.length; t++) {
            for (int s = 0; s < Fs[0].length; s++) {
                sProbability[t][s] = Fs[t][s] * Bs[t][s];
            }
        }
        ForwardBackwardOut.printProbabilities(sProbability, Fs, Bs);
        return sProbability;
    }

    static double[][] computeSmoothing(double[][] probability) {
        double[][] smoothed = new double[probability.length][probability[0].length];
        for (int t = 0; t < probability.length; t++) {
            double coefficient = Arrays.stream(probability[t]).sum();
            smoothed[t] = MatrixOperations.multiplyScalarAndVector(1.0 / coefficient, probability[t]);
        }
        ForwardBackwardOut.printSmoothing(probability);
        return smoothed;
    }

    private static int[] findSequence(double[][] probabilities) {
        int[] sequence = new int[probabilities.length];
        for (int i = 0; i < probabilities.length; i++) {
            sequence[i] = getMaxIndex(probabilities[i]);
        }
        ForwardBackwardOut.printSequence(sequence);
        return sequence;
    }

    private static double[][] computeO(int sizeS, double[][] B, int input) {
        double[][] O = new double[sizeS][sizeS];
        for (int line = 0; line < sizeS; line++) {
            for (int col = 0; col < sizeS; col++) {
                if (line == col) {
                    O[line][col] = B[line][input];
                }
            }
        }
        return O;
    }

    private static double[] firstF(double[] P, double[][] O1) {
        double[] F = new double[P.length];
        for (int i = 0; i < P.length; i++) {
            F[i] = P[i] * O1[i][i];
        }
        return F;
    }

    private static double[] computeF(double[] previousF, double[][] A, double[][] O) {
        double[][] At = MatrixOperations.transpose(A);
        double[] AtF = MatrixOperations.multiplyMatrixAndVector(At, previousF);
        return MatrixOperations.multiplyMatrixAndVector(O, AtF);
    }

    private static double[] firstB(int sizeS) {
        double[] F = new double[sizeS];
        for (int i = 0; i < sizeS; i++) {
            F[i] = 1.0;
        }
        return F;
    }

    private static double[] computeB(double[] previousB, double[][] A, double[][] O) {
        double[] OB = MatrixOperations.multiplyMatrixAndVector(O, previousB);
        return MatrixOperations.multiplyMatrixAndVector(A, OB);
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
