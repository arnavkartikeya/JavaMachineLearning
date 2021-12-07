import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;
public class Matrix {
    private double[][] matrix;
    int height;
    int width;

    public Matrix(double[][] arr) {
        matrix = arr;
        height = arr.length;
        width = arr[0].length;
    }

    public Matrix(int row, int column) {
        //make a 2 dimensional array with row and column
        matrix = new double[row][column];
        height = row;
        width = column;
    }

    public void printMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    //adding elements to matrix (using coordinates)
    public void addElement(double element, int i, int j) {
        matrix[i][j] = element;
    }

    public double getElement(int i, int j) {
        return matrix[i][j];
    }
    public void setElement(double element, int i, int j){
        matrix[i][j] = element;
    }
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    //a method which takes a matrix and adds to to the current matrix
    public Matrix addMatrix(Matrix b) {
        if (b.getWidth() != this.getWidth() || b.getHeight() != this.getHeight()) {
            System.out.println("Matrices are not the same dimensions");
            return null;
        }
        double[][] arr = new double[this.getHeight()][this.getWidth()];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = this.getElement(i, j) + b.getElement(i, j);
            }
        }
        Matrix ans = new Matrix(arr);
        return ans;
    }
    //add the value to each element in the matrix
    public Matrix addAll(double value){
        double[][] vals = new double[this.getHeight()][this.getWidth()];
        Matrix ans;
        for(int row = 0; row < this.getHeight(); row++){
            for(int col = 0; col < this.getWidth(); col++){
                vals[row][col] = this.getElement(row, col) + value;
            }
        }
        ans = new Matrix(vals);
        return ans;
    }
    public Matrix subtractMatrix(Matrix b) {
        if (b.getWidth() != this.getWidth() || b.getHeight() != this.getHeight()) {
            System.out.println("Matrices are not the same dimensions");
            return null;
        }
        double[][] arr = new double[this.getHeight()][this.getWidth()];
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getWidth(); j++) {
                arr[i][j] = (double)this.getElement(i, j) - (double)b.getElement(i, j);
            }
        }
        Matrix ans = new Matrix(arr);
        return ans;
    }

    public Matrix scalarMultiplication(double scalar) {
        double[][] arr = new double[this.getHeight()][this.getWidth()];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = (double)(this.getElement(i, j) * scalar);
            }
        }


        Matrix ans = new Matrix(arr);
        return ans;
    }

    public Matrix scalarDivision(double scalar) {
        double[][] arr = new double[this.getHeight()][this.getWidth()];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = (double) (this.getElement(i, j) / scalar);
            }
        }


        Matrix ans = new Matrix(arr);
        return ans;
    }

    //
    public Matrix multiplyMatrix(Matrix b) {
        double[][] arr = new double[this.getHeight()][b.getWidth()];
        for (int row = 0; row < this.getHeight(); row++) {
            for (int col = 0; col < b.getWidth(); col++) {
                //multiply rowth row with colth column
                arr[row][col] = multiplyCells(this, b, row, col);
            }
        }
        Matrix ans = new Matrix(arr);
        return ans;
    }

    public double multiplyCells(Matrix a, Matrix b, int row, int col) {
        double val = 0;
        for (int i = 0; i < a.getWidth(); i++) {
            val += a.getElement(row, i) * b.getElement(i, col);
        }
        return val;
    }

    public Matrix transpose() {
        double[][] arr = new double[this.getWidth()][this.getHeight()];
        for (int row = 0; row < this.getHeight(); row++) {
            for (int col = 0; col < this.getWidth(); col++) {
                arr[col][row] = this.getElement(row, col);
            }
        }
        Matrix ans = new Matrix(arr);
        return ans;
    }

    //add a column of ones before the matrix
    public Matrix addOnes() {
        Matrix ans;
        double[][] arr = new double[this.getHeight()][this.getWidth() + 1];
        for (int row = 0; row < this.getHeight(); row++) {
            arr[row][0] = 1;
            for (int col = 1; col < this.getWidth() + 1; col++) {
                arr[row][col] = this.getElement(row, col - 1);
            }
        }
        ans = new Matrix(arr);
        return ans;
    }

    //method which takes a .txt file and makes it into a matrix
    public static Matrix readTxt(File f, int height, int width) throws FileNotFoundException {
        Scanner sc = new Scanner(f);
        double[][] arr = new double[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                arr[row][col] = sc.nextDouble();
            }
        }
        Matrix ans = new Matrix(arr);
        return ans;
    }

    //method which returns a new matrix from the index of start and end columns
    public Matrix splitCol(int start, int end) {
        double[][] arr = new double[this.getHeight()][end - start + 1];
        for (int row = 0; row < this.getHeight(); row++) {
            for (int col = start; col <= end; col++) {
                arr[row][col] = this.getElement(row, col);
            }
        }
        Matrix ans = new Matrix(arr);
        return ans;
    }

    public Matrix getCol(int colIndex) {
        double[][] arr = new double[this.getHeight()][1];
        for (int row = 0; row < this.getHeight(); row++) {
            arr[row][0] = this.getElement(row, colIndex);
        }
        Matrix ans = new Matrix(arr);
        return ans;
    }

    //Things to do:
    /*
    Make a matrix class using 2d arrays
    Have methods for making a new matrix, smth like add(element, row or column), or something like add(2d array)
     */
    @Override
    public String toString() {
        String s = "";
        for (int row = 0; row < this.getHeight(); row++) {
            for (int col = 0; col < this.getWidth(); col++) {
                Double d = this.getElement(row, col);
                String temp = d.toString();
                s += temp;
            }
            s += "\n";
        }
        return s;
    }

    public double[] getRow(int rowIndex){
        double[] ans = new double[this.getWidth()];
        for(int col = 0; col < this.getWidth(); col++){
            ans[col] = this.getElement(rowIndex, col);
        }
        return ans;
    }
    //adds row to the bottom of the matrix
    public void addRow(double[] rowVals){
        double[][] vals = new double[this.getHeight() + 1][this.getWidth()];
        for(int row = 0; row < vals.length; row++){
            for(int col = 0; col < this.getWidth(); col++){
                if(vals.length - 1 != row) {
                    vals[row][col] = this.getElement(row, col);
                }else{
                    vals[row][col] = rowVals[col];
                }
            }
        }
        this.matrix = vals;
        this.height = vals.length;
        this.width = vals[0].length;
    }
    public static Matrix addRow(double[] rowVals, Matrix b){
        Matrix ans;
        double[][] vals = new double[b.getHeight() + 1][b.getWidth()];
        for(int row = 0; row < vals.length; row++){
            for(int col = 0; col < b.getWidth(); col++){
                if(vals.length - 1 != row) {
                    vals[row][col] = b.getElement(row, col);
                }else{
                    vals[row][col] = rowVals[col];
                }
            }
        }
        ans = new Matrix(vals);
        return b;
    }
    public void removeRow(int rowIndex){
        int rowCount = 0;
        double[][] ans =  new double[this.getHeight() - 1][this.getWidth()];
        for(int row = 0; row < this.getHeight(); row++){
            if(row == rowIndex){
                continue;
            }else{
                for(int col = 0; col < this.getWidth(); col++){
                            ans[rowCount][col] = this.getElement(row, col);
                }
                rowCount++;
            }

        }
        this.matrix = ans;
        this.height = ans.length;
        this.width = ans[0].length;
    }

    //the following two method are used in the logistic regression class to apply sigmoid function to the hypthosesis
    //take each element in the matrix and have it raised pow^element
    public Matrix raiseE(double pow){
        Matrix ans;
        double[][] vals = new double[this.getHeight()][this.getWidth()];
        for(int row = 0; row < this.height; row++){
            for(int col = 0; col < this.width; col++){
                vals[row][col] = Math.exp(this.getElement(row, col));
            }
        }
        ans = new Matrix(vals);
        return ans;
    }

    //take each element in the matrix and replace with 1/value
    public Matrix reciprocal(){
        Matrix ans;
        double[][] vals = new double[this.getHeight()][this.getWidth()];
        for(int row = 0; row < this.height; row++){
            for(int col = 0; col < this.width; col++){
                vals[row][col] = (double)(1.0)/this.getElement(row, col);
            }
        }
        ans = new Matrix(vals);
        return ans;
    }
    //for gradient checking
    //For matrix a and b, they must be vectors
    //finds the difference of each value in the two vectors and squares them then sums all and takes the square root of that result
    public static double euclideanDistance(Matrix a, Matrix b){
        double distance = 0;
        for(int row = 0; row < a.getHeight(); row++){
            for(int col = 0; col < a.getWidth(); col++){
                distance += Math.pow(a.getElement(row, col) - b.getElement(row,  col), 2);
            }
        }
        return Math.sqrt(distance);
    }
    //takes the square root of all the values in the vector squared
    public static double euclideanLength(Matrix a){
        double distance = 0;
        for(int row = 0; row < a.getHeight(); row++){
            for(int col = 0; col < a.getWidth(); col++){
                distance += Math.pow(a.getElement(row, col), 2);
            }
        }
        return Math.sqrt(distance);
    }

    //for debugging
    public void printVector(){
        //assuming only 1 column
        for(int row = 0; row < this.getHeight(); row++){
            if(row != this.getHeight() - 1){
                System.out.print(this.getElement(row, 0 ) + ", ");
            }else{
                System.out.println(this.getElement(row, 0));
            }
        }
    }

}
