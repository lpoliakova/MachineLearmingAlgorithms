package viterbi;

class ViterbiOut {
    static void printFormulas() {
        System.out.println("Формули");
        System.out.println("TSate[s,t] = max with sFrom from S from TState(sFrom, t - 1) * A(sFrom, s) * B(s, input(t)) )");
        System.out.println("TIndex[s,t] = arg max with sFrom from S from TState(sFrom, t - 1) * A(sFrom, s) * B(s, input(t)) )");
        System.out.println();
    }

    static void printFirstTState(double[] P, double[][] B, int input, double[] TState) {
        System.out.println("Початковий стан");
        for (int s = 0; s < P.length; s++) {
            System.out.print("TState[s=" + (s + 1) + ", t=1] = P(1) * B(" + (s + 1) + ", input(" + (input + 1) + ")) = ");
            printScalar(P[s]);
            System.out.print(" * ");
            printScalar(B[s][input]);
            System.out.print(" = ");
            printScalar(TState[s]);
            System.out.println();
        }
        System.out.println();
    }

    static void printTStateS(double[][] A, double[][] B, int input, double[] TState, double[] TStateS, double tState, int step, int state) {
        System.out.print("TState[s=" + (state + 1) + ", t=" + (step + 1) + "] = max( ");
        for (int s = 0; s < TStateS.length; s++) {
            System.out.print("TState(" + (s + 1) + ", " + step + ") * ");
            System.out.print("A(" + (s + 1) + ", " + (state + 1) + ") * ");
            System.out.print("B(" + (state + 1) + ", input(" + (input + 1) + "))");
            if (s != TStateS.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(" ) =");

        System.out.print("\t= max ( ");
        for (int s = 0; s < TStateS.length; s++) {
            printScalar(TState[s]);
            System.out.print(" * ");
            printScalar(A[s][state]);
            System.out.print(" * ");
            printScalar(B[state][input]);
            if (s != TStateS.length - 1) {
                System.out.print(", ");
            }
        }

        System.out.print(" ) = max ( ");
        for (int s = 0; s < TStateS.length; s++) {
            printScalar(TStateS[s]);
            if (s != TStateS.length - 1) {
                System.out.print(", ");
            }
        }

        System.out.print(" ) = ");
        printScalar(tState);
        System.out.println();
    }

    static void printTStateAndTSequence(double[][] TState, int[][] TIndex) {
        System.out.println();
        System.out.print("TState = (");
        for (int s = 0; s < TState[0].length; s++) {
            for (double[] tState : TState) {
                printScalar(tState[s]);
                System.out.print(" ");
            }
            if (s != TState[0].length - 1) {
                System.out.print("| ");
            }
        }
        System.out.println(")");
        System.out.print("TIndex = (");
        for (int s = 0; s < TIndex[0].length; s++) {
            for (int[] tIndex : TIndex) {
                System.out.print(tIndex[s] + " ");
            }
            if (s != TIndex[0].length - 1) {
                System.out.print("| ");
            }
        }
        System.out.println(")");
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

    private static void printScalar(double scalar) {
        System.out.format("%.4f", scalar);
    }
}
