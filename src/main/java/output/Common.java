package output;

public class Common {
    static void printMatrix(double[][] matrix) {
        System.out.print("(");
        for (int line = 0; line < matrix.length; line++) {
            for (int col = 0; col < matrix[line].length; col++) {
                System.out.format("%.4f ", matrix[line][col]);
            }
            if (line != matrix.length - 1) {
                System.out.print("| ");
            }
        }
        System.out.print(")");
    }

    static void printVector(double[] vector, boolean inLine) {
        System.out.print("(");
        for (int i = 0; i < vector.length; i++) {
            System.out.format("%.4f ", vector[i]);
        }
        System.out.print(")");
        if (!inLine) {
            System.out.print("T");
        }
    }

    static void printScalar(double scalar) {
        System.out.format("%.4f", scalar);
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
