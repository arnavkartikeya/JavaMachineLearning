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

    public double[][] getArray(){
        double[][] newArray = new double[this.getHeight()][this.getWidth()];
        for(int row = 0; row < this.getHeight(); row++){
            for(int col = 0; col < this.getWidth(); col++){
                newArray[row][col] = this.matrix[row][col];
            }
        }
        return newArray;
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

    public double[][] getList() { return this.matrix; }

    public int getWidth() {
        return width;
    }

    //creates random matrix between start and end values inclusively. If isInt is true, this will generate only integer values
    public static Matrix randomizeMatrix(int height, int width, int start, int end, boolean isInt){
        Matrix ans;
        double[][] vals = new double[height][width];
        Random r = new Random();
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                //only integers
                if(isInt == true){
                   vals[row][col] = r.nextInt((end - start) + 1 ) + start;
                }else{
                    vals[row][col] = start + (end - start) * r.nextDouble();
                }
            }
        }
        ans = new Matrix(vals);
        return ans;
    }
    //create a matrix of 1 given a specified dimension
    public static Matrix makeOnes(int height, int width){
        Matrix ans;
        double[][] vals = new double[height][width];
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                vals[row][col] = 1;
            }
        }
        ans = new Matrix(vals);
        return ans;
    }

    public static Matrix makeZeros(int height, int width){
        double[][] vals = new double[height][width];
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                vals[row][col] = 0;
            }
        }
        return new Matrix(vals);
    }

    public static Matrix randomizeMatrix(int height, int width, int start, int end){
        Matrix ans;
        double[][] vals = new double[height][width];
        Random r = new Random();
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                vals[row][col] = (double)(end - start) * r.nextDouble() + (double)start;
            }
        }
        ans = new Matrix(vals);
        return ans;
    }

    public static Matrix randomizeMatrix(int height, int width, double start, double end){
        Matrix ans;
        double[][] vals = new double[height][width];
        Random r = new Random();
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                vals[row][col] = (double)(end - start) * r.nextDouble() + (double)start;
            }
        }
        ans = new Matrix(vals);
        return ans;
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
    //adds a vector of either height or width that is equal to the height or width of the matrix
    public Matrix addVect(Matrix vect){

        Matrix ans;
        double[][] vals = new double[this.getHeight()][this.getWidth()];
        if(vect.getHeight() == this.getHeight()){
           //add each column with the vector
           for(int row = 0; row < this.getHeight(); row++){
               for(int col = 0; col < this.getWidth(); col++){
                   vals[row][col] = this.getElement(row, col) + vect.getElement(col, 0);
               }
           }
           ans = new Matrix(vals);
           return ans;
        }else if(vect.getWidth() == this.getWidth()){
            for(int row = 0; row < this.getHeight(); row++){
                for(int col = 0; col < this.getWidth(); col++){
                    vals[row][col] = this.getElement(row, col) + vect.getElement(0, col);
                }
            }
            return new Matrix(vals);
        }
        return null;
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

    /*if matrix is a vector, will divide each row with that row's vector value. if matrix is not a vector,
    it must be the same dims as the original matrix and it is now an element wise division
     */
    public Matrix matrixDivision(Matrix div){
        if(div.getHeight() == 1){
           div = div.transpose();
        }
        //vector division
        if(div.getWidth() == 1){
            double[][] vals = new double[this.getHeight()][this.getWidth()];
            for(int row = 0; row < this.getHeight(); row++){
                for(int col = 0; col < this.getWidth(); col++){
                    vals[row][col] = this.getElement(row, col) / div.getElement(row, 0);
                }
            }
            return new Matrix(vals);
        }

        //element wise division, assumes that dims are the same as the current matrix
        double[][] vals = new double[this.getHeight()][this.getWidth()];
        for(int row = 0; row < this.getHeight(); row++){
            for(int col = 0; col < this.getWidth(); col++){
                vals[row][col] = this.getElement(row, col) / div.getElement(row, col);
            }
        }

        return new Matrix(vals);

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
            double c = a.getElement(row, i);
            double d = b.getElement(i, col);
            double e = c * d;
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
    public static Matrix readTxt(File f, int height, int width, String delimiter) throws FileNotFoundException {
        Scanner sc = new Scanner(f);
        sc.useDelimiter(delimiter);
        double[][] arr = new double[height][width];
        for (int row = 0; row < height; row++) {
            Scanner tempSC = new Scanner(sc.nextLine());
            tempSC.useDelimiter(delimiter);
            for(int col = 0; col < width; col++){
                arr[row][col] = Double.parseDouble(tempSC.next());
            }
        }
        Matrix ans = new Matrix(arr);
        return ans;
    }

    //method which returns a new matrix from the index of start and end columns
    public Matrix splitCol(int start, int end) {
        int colIndex = 0;
        double[][] arr = new double[this.getHeight()][end - start + 1];
        for (int row = 0; row < this.getHeight(); row++) {
            for (int col = start; col <= end; col++) {
                arr[row][colIndex] = this.getElement(row, col);
                colIndex++;
            }
            colIndex = 0;
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

    //sums all columns in a Matrix and returns the new Matrix
    public Matrix sumColumns(){
        double[][] vals = new double[1][this.getWidth()];
        for(int col = 0; col < this.getWidth(); col++){
            double sum = 0;
            for(int row = 0; row < this.getHeight(); row++){
                sum += this.getElement(row, col);
            }
            vals[0][col] = sum;
        }
        return new Matrix(vals);
    }


    public Matrix sumRows(){
        double[][] vals = new double[this.getHeight()][1];
        for(int row = 0; row < this.getHeight(); row++){
            double sum = 0;
            for(int col = 0; col < this.getWidth(); col++){
                sum += this.getElement(row, col);
            }
            vals[row][0] = sum;
        }
        return new Matrix(vals);
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

    //sets all elements of this certain value to another value
    public Matrix setAll(double element, double replacement){
        double[][] val = new double[this.getHeight()][this.getWidth()];
        Matrix ans;
        for(int row = 0; row < this.getHeight(); row++){
            for(int col = 0; col < this.getWidth(); col++){
                if(this.getElement(row, col) == element){
                    val[row][col] = replacement;
                }else{
                    val[row][col] = this.getElement(row, col);
                }
            }
        }
        ans = new Matrix(val);
        return ans;
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
    //returns the sum of all elements in the matrix
    public double sum(){
        double ans = 0;
        for(int row = 0; row < this.getHeight(); row++){
            for(int col = 0; col < this.getWidth(); col++){
                ans += this.getElement(row, col);
            }
        }
        return ans;
    }
    //returns arithmetic mean of all values in the sum (must be a vector)
    public double mean(){
        return this.getWidth() == 1 ? this.sum()/this.getHeight() : this.sum()/this.getWidth();
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

    //returns one hot encoded vector from sparse vector (0 must be a class)
    public Matrix sparseToEncoded(){
        //find max value (highest class)
        if(this.getWidth() == 1){
            this.transpose();
        }
        int max = 0;
        for(int col = 0; col < this.getWidth(); col++){
            if(this.getElement(0, col) > max){
                max = (int)(this.getElement(0, col));
            }
        }
        int index = 0;
        double[][] vals = new double[this.getWidth()][max + 1];
        for(int row = 0; row < vals.length; row++){
            for(int col = 0; col < vals[0].length; col++){
                if(col == this.getElement(0, index)){
                    vals[row][col] = 1;
                    col = vals[0].length;
                    index++;
                }else{
                    vals[row][col] = 0;
                }
            }
        }
        return new Matrix(vals);
    }
    //returns sparse vector from one hot encoded vector
    public Matrix encodedToSparse(){
        /*
        [[0, 1, 0]
         [1, 0, 0]
         [0, 0, 1]]

         [1, 0, 2]
         */
        double[][] vals = new double[1][this.getHeight()];
        int index = 0;
        for(int row = 0; row < this.getHeight(); row++){
            for(int col = 0; col < this.getWidth(); col++){
                if (this.getElement(row, col) == 1) {
                    vals[0][index] = col;
                    index++;
                    col = this.getWidth();
                }
            }
        }
        return new Matrix(vals);
    }

    //returns a vector of the max values in each row
    public Matrix maxRow(){
        double[][] vals = new double[this.getHeight()][1];
        for(int row = 0; row < this.getHeight(); row++){
            double max = -1;
            double maxIndex = 0;
            for(int col = 0; col < this.getWidth(); col++){
                if(max < this.getElement(row, col)){
                    //need index
                    max = this.getElement(row, col);
                    maxIndex = col;
                }
            }
            vals[row][0] = maxIndex;
            max = -1;
        }
        return new Matrix(vals);
    }

    public Matrix copy(){
        return new Matrix(matrix);
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
