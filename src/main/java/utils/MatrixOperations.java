package utils;

public class MatrixOperations {

    public static double[][] transpose(double[][] matrix) {
        double[][] result = new double[matrix[0].length][matrix.length];
        for (int line = 0; line < matrix.length; line++) {
            for (int col = 0; col < matrix[0].length; col++) {
                result[line][col] = matrix[col][line];
            }
        }
        return result;
    }

    public static double[] multiplyScalarAndVector(double scalar, double[] vector) {
        double[] result = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            result[i] = vector[i] * scalar;
        }
        return result;
    }

    public static double[] multiplyMatrixAndVector(double[][] matrix, double[] vector) {
        double[] result = new double[vector.length];
        for (int line = 0; line < vector.length; line++) {
            double lineResult = 0.0;
            for (int col = 0; col < matrix[0].length; col++) {
                lineResult += matrix[line][col] * vector[col];
            }
            result[line] = lineResult;
        }
        return result;
    }

    public static double[][] multiplyTwoMatrices(double[][] first, double[][] second) {
        if (first == null || second == null ||
                first.length == 0 || second.length == 0 ||
                first[0].length == second.length) {
            return null;
        }
        double[][] result = new double[first.length][second[0].length];
        for (int line = 0; line < first.length; line++) {
            for (int col = 0; col < second[0].length; col++) {
                double resultCell = 0.0;
                for (int iter = 0; iter < first[0].length; iter++) {
                    resultCell += first[line][iter] * second[iter][col];
                }
                result[line][col] = resultCell;
            }
        }
        return result;
    }
}
