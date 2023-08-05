package org.firstinspires.ftc.teamcode.NetworkCode;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.concurrent.ArrayBlockingQueue;
import java.io.*;


public class Network_Transition {

    public static int[] predict(double weights[][][], double biases[][][], double x[][]){




        double[][] a = x;
        for (int i = 0; i < weights.length;i++){
            a = sigmoid(add(dot(weights[i], a),biases[i]));
        }


        //for (int i = 0; i < a.length; i++){
        //    System.out.println(a[i][0]);
        //}


        int[] prediction = new int[a.length];


        for (int i = 0; i < a.length; i++){
            if (a[i][0] >= 0.5){
                prediction[i] = 1;
            }
            else{
                prediction[i] = 0;
            }
            //System.out.println(prediction[i]);
        }

        return prediction;

    }


    public static double[][] dot(double matrix1[][], double matrix2[][]){


        int row1 = matrix1.length;
        int col1 = matrix1[0].length;


        int row2 = matrix2.length;
        int col2 = matrix2[0].length;




        //System.out.println(row1);
        //System.out.println(col1);


        //System.out.println(row2);
        //System.out.println(col2);


        //System.out.println(matrix1[0].length);


        if (row2 != col1) {
            col1 = 1;
        }



        double matrixF[][] = new double[row1][col2];




        if (row2 == col1) {
            for (int i = 0; i < row1; i++) {
                for (int j = 0; j < col2; j++) {
                    for (int k = 0; k < row2; k++)
                        matrixF[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        else{
            double matrixI[][] = new double[row1][matrix1[0].length];



            for (int i = 0; i < row1; i++){

                for (int j = 0; j < matrix1[0].length; j++){
                    matrixI[i][j] = matrix1[i][j] * matrix2[j][0];
                }
            }



            for (int i = 0; i < row1; i++){
                for (int j = 0; j < matrix1[0].length; j++){
                    matrixF[i][0] += matrixI[i][j];
                }
            }
        }
        return matrixF;
    }


    public static double[][] add(double matrix1[][], double matrix2[][]){


        double[][] matrixF = new double[matrix1.length][matrix1[0].length];


        for (int i = 0; i < matrix1.length; i++){
            for (int j = 0; j < matrix1[i].length; j++){
                matrixF[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }


        return matrixF;
    }


    public static double[][] exponential(double matrix1[][]){


        double[][] matrixF = new double[matrix1.length][matrix1[0].length];


        for (int i = 0; i < matrix1.length; i++){
            for (int j = 0; j < matrix1[i].length; j++){
                matrixF[i][j] = Math.exp(matrix1[i][j]);
            }
        }


        return matrixF;
    }


    public static double[][] sigmoid(double matrix1[][]){


        double[][] matrixF = new double[matrix1.length][matrix1[0].length];


        for (int i = 0; i < matrix1.length; i++){
            for (int j = 0; j < matrix1[i].length; j++){
                matrixF[i][j] = 1.0/(1.0+Math.exp(-matrix1[i][j]));
            }
        }

        return matrixF;
    }


/*

    static void printMatrix(int M[][],
                            int rowSize,
                            int colSize)
    {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++)
                System.out.print(M[i][j] + " ");

            System.out.println();
        }
    }

    // Function to multiply
    // two matrices A[][] and B[][]
    static void multiplyMatrix(
            int row1, int col1, int A[][],
            int row2, int col2, int B[][])
    {
        int i, j, k;

        // Print the matrices A and B
        System.out.println("\nMatrix A:");
        printMatrix(A, row1, col1);
        System.out.println("\nMatrix B:");
        printMatrix(B, row2, col2);

        // Check if multiplication is Possible
        if (row2 != col1) {

            System.out.println(
                    "\nMultiplication Not Possible");
            return;
        }

        // Matrix to store the result
        // The product matrix will
        // be of size row1 x col2
        int C[][] = new int[row1][col2];

        // Multiply the two matrices
        for (i = 0; i < row1; i++) {
            for (j = 0; j < col2; j++) {
                for (k = 0; k < row2; k++)
                    C[i][j] += A[i][k] * B[k][j];
            }
        }

        // Print the result
        System.out.println("\nResultant Matrix:");
        printMatrix(C, row1, col2);
    }

    // Driver code
    public static void main(String[] args)
    {

        int row1 = 4, col1 = 3, row2 = 3, col2 = 4;

        int A[][] = { { 1, 1, 1 },
                { 2, 2, 2 },
                { 3, 3, 3 },
                { 4, 4, 4 } };

        int B[][] = { { 1, 1, 1, 1 },
                { 2, 2, 2, 2 },
                { 3, 3, 3, 3 } };

        multiplyMatrix(row1, col1, A,
                row2, col2, B);
    }


*/

}



