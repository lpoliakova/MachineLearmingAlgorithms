package output;

import utils.MatrixOperations;

public class ForwardBackwardOut {
    public static void printOs(double[][][] Os) {
        System.out.println("Ймовірності переходів на кожному кроці");
        for (int number = 1; number <= Os.length; number++) {
            System.out.print("O_" + number + " = ");
            Common.printMatrix(Os[number - 1]);
            System.out.println();
        }
        System.out.println();
    }

    public static void printForward(double[] P, double[][][] Os, double[][] A, double[][] Fs) {
        System.out.println("Вперед");
        System.out.print("fwd[t=1] = O_1 * P = ");
        Common.printMatrix(Os[0]);
        Common.printVector(P, false);
        System.out.print(" * ");
        Common.printVector(Fs[0], false);
        System.out.println();
        for (int number = 1; number < Fs.length; number++) {
            System.out.print("fwd[t=" + (number + 1) + "] = ");
            System.out.print("O_" + (number + 1) + " * A^T * fwd[t=" + number + "] = ");
            Common.printMatrix(Os[number]);
            System.out.print(" * ");
            Common.printMatrix(MatrixOperations.transpose(A));
            System.out.print(" * ");
            Common.printVector(Fs[number - 1], false);
            System.out.print(" = ");
            Common.printVector(Fs[number], false);
            System.out.println();
        }
        System.out.println();
    }

    public static void printBackward(double[][][] Os, double[][] A, double[][] Bs) {
        System.out.println("Назад");
        System.out.print("bwd[t=" + Bs.length + "] = ");
        Common.printVector(Bs[Bs.length - 1], false);
        System.out.println();
        for (int number = Bs.length - 2; number >= 0; number--) {
            System.out.print("bwd[t=" + (number + 1) + "] = ");
            System.out.print("A * O_" + (number + 2) + " * bwd[t=" + (number + 2) + "] = ");
            Common.printMatrix(A);
            System.out.print(" * ");
            Common.printMatrix(Os[number + 1]);
            System.out.print(" * ");
            Common.printVector(Bs[number + 1], false);
            System.out.print(" = ");
            Common.printVector(Bs[number], false);
            System.out.println();
        }
        System.out.println();
    }

    public static void printProbabilities(double[][] sP, double[][] Fs, double[][] Bs) {
        System.out.println("Ймовірності без згладжування");
        for (int number = 0; number < sP.length; number++) {
            System.out.print("s_pr[t=" + (number + 1) + "] = ");
            System.out.print("fwd[t=" + (number + 1) + "] * ");
            System.out.print("bkw[t=" + (number + 1) + "] = ");
            Common.printVector(Fs[number], false);
            System.out.print(" * ");
            Common.printVector(Bs[number], false);
            System.out.print(" = ");
            Common.printVector(sP[number], false);
            System.out.println();
        }
        System.out.println();
    }

    public static void printSmoothing(double[][] pr) {
        System.out.println("Згладжування");
        System.out.print("pr[t][s] = s_pr[t][s] / (Sum with j from S of s_pr[t][j])");
        for (int number = 0; number < pr.length; number++) {
            System.out.print("pr[t=" + (number + 1) + "] = ");
            Common.printVector(pr[number], false);
            System.out.println();
        }
        System.out.println();
    }

    public static void printSequence(int[] sequence) {
        Common.printSequence(sequence);
    }
}
