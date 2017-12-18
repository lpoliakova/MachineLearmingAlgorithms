package forwardBackward;

class ForwardBackwardOut {
    static void printOs(double[][][] Os) {
        System.out.println("Ймовірності переходів на кожному кроці");
        for (int number = 1; number <= Os.length; number++) {
            System.out.print("O_" + number + " = ");
            NumericOut.printMatrix(Os[number - 1]);
            System.out.println();
        }
        System.out.println();
    }

    static void printForward(double[] P, double[][][] Os, double[][] A, double[][] Fs) {
        System.out.println("Вперед");
        System.out.print("fwd[t=1] = O_1 * P = ");
        NumericOut.printMatrix(Os[0]);
        NumericOut.printVector(P, false);
        System.out.print(" * ");
        NumericOut.printVector(Fs[0], false);
        System.out.println();
        for (int number = 1; number < Fs.length; number++) {
            System.out.print("fwd[t=" + (number + 1) + "] = ");
            System.out.print("O_" + (number + 1) + " * A^T * fwd[t=" + number + "] = ");
            NumericOut.printMatrix(Os[number]);
            System.out.print(" * ");
            NumericOut.printMatrix(MatrixOperations.transpose(A));
            System.out.print(" * ");
            NumericOut.printVector(Fs[number - 1], false);
            System.out.print(" = ");
            NumericOut.printVector(Fs[number], false);
            System.out.println();
        }
        System.out.println();
    }

    static void printBackward(double[][][] Os, double[][] A, double[][] Bs) {
        System.out.println("Назад");
        System.out.print("bwd[t=" + Bs.length + "] = ");
        NumericOut.printVector(Bs[Bs.length - 1], false);
        System.out.println();
        for (int number = Bs.length - 2; number >= 0; number--) {
            System.out.print("bwd[t=" + (number + 1) + "] = ");
            System.out.print("A * O_" + (number + 2) + " * bwd[t=" + (number + 2) + "] = ");
            NumericOut.printMatrix(A);
            System.out.print(" * ");
            NumericOut.printMatrix(Os[number + 1]);
            System.out.print(" * ");
            NumericOut.printVector(Bs[number + 1], false);
            System.out.print(" = ");
            NumericOut.printVector(Bs[number], false);
            System.out.println();
        }
        System.out.println();
    }

    static void printProbabilities(double[][] sP, double[][] Fs, double[][] Bs) {
        System.out.println("Ймовірності без згладжування");
        for (int number = 0; number < sP.length; number++) {
            System.out.print("s_pr[t=" + (number + 1) + "] = ");
            System.out.print("fwd[t=" + (number + 1) + "] * ");
            System.out.print("bkw[t=" + (number + 1) + "] = ");
            NumericOut.printVector(Fs[number], false);
            System.out.print(" * ");
            NumericOut.printVector(Bs[number], false);
            System.out.print(" = ");
            NumericOut.printVector(sP[number], false);
            System.out.println();
        }
        System.out.println();
    }

    static void printSmoothing(double[][] pr) {
        System.out.println("Згладжування");
        System.out.print("pr[t][s] = s_pr[t][s] / (Sum with j from S of s_pr[t][j])");
        for (int number = 0; number < pr.length; number++) {
            System.out.print("pr[t=" + (number + 1) + "] = ");
            NumericOut.printVector(pr[number], false);
            System.out.println();
        }
        System.out.println();
    }

    static void printSequence(int[] sequence) {
        System.out.println("Відповідь");
        System.out.print("{");
        for (int i = 0; i < sequence.length - 1; i++) {
            System.out.print("S_" + (sequence[i] + 1) + ", ");
        }
        System.out.print("S_" + (sequence[sequence.length - 1] + 1) + "}");
        System.out.println();
    }
}
