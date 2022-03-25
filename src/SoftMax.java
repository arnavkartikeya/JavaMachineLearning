public class SoftMax extends Activation{
    public Matrix activate(Matrix input){
        double[][] maxCols = new double[1][input.getHeight()];
        for(int row  = 0; row < input.getHeight(); row++){
            double max = Double.MIN_VALUE;
            for(int col = 0; col < input.getWidth(); col++){
                if(input.getElement(row, col) > max){
                    max = input.getElement(row, col);
                }
            }
            maxCols[0][row] = max;
        }

        Matrix ans;
        double[][] vals = new double[input.getHeight()][input.getWidth()];
        ans = new Matrix(vals);
        double[][] divide = new double[input.getHeight()][1];
        Matrix divideMatrix = new Matrix(divide);

        double sumExp = 0;
        for(int row = 0; row < input.getHeight(); row++){
            for(int col = 0; col < input.getWidth(); col++){
                //#TODO check if the indexing for maxCols works in this case
                ans.setElement(Math.exp(input.getElement(row, col) - maxCols[0][row]), row, col);
//                sumExp += Math.exp(input.getElement(row, col));
            }
//            divideMatrix.setElement(sumExp, row, 0);
//            sumExp = 0;
        }

        Matrix exp_values = ans.sumRows();


        //ans = new Matrix(vals);
        return ans.matrixDivision(exp_values);
    }
    //#TODO make this method
    public Matrix backwards(Matrix input){
        //make input a height * 1 vector
        if(input.getHeight() == 1){
            input = input.transpose();
        }
        //implementing Softmax * krockener delta function
        double[][] vals = new double[input.getHeight()][input.getHeight()];
        int index = 0;

        for(int row = 0; row < vals.length; row++){
            for(int col = 0; col < vals[0].length; col++){
                if(row == col){
                    vals[row][col] = input.getElement(index ,0 );
                    index++;
                }else{
                    vals[row][col] = 0;
                }
            }
        }
        Matrix first = new Matrix(vals);
        
        return first;
    }
}
