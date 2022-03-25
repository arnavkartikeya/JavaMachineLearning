public class CategoricalCrossEntropy extends Metrics {
    //hotVector can either be sparse (a vector with class targets 1...n)
    //or one hot encoded matrix

    //#TODO check dimensions of the output here because it should match the dimensions of dValues in the backwards method
    public Matrix compute(Matrix output, Matrix targetClass){
        clipOutput(output);
        //for sparse vectors including each target class
        if(targetClass.getHeight() == 1 || targetClass.getWidth() == 1) {
            double[][] vals = new double[output.getHeight()][1];
            if (targetClass.getWidth() == output.getHeight()) {
                targetClass = targetClass.transpose();
            }
            for (int row = 0; row < targetClass.getHeight(); row++) {
                vals[row][0] = output.getElement(row, (int) targetClass.getElement(row, 0));
            }
            Matrix ans = new Matrix(vals);
            for (int row = 0; row < ans.getHeight(); row++) {
                ans.setElement(-1 * Math.log(ans.getElement(row, 0)), row, 0);
            }
            return ans;
        }
        //for one hot encoded vectors
        else{
            Matrix ans;
            double[][] vals = new double[output.getHeight()][1];
            for(int row = 0; row < targetClass.getHeight(); row++){
                for(int col = 0; col < targetClass.getWidth(); col++){
                    if(targetClass.getElement(row, col) == 1){
                        vals[row][0] = output.getElement(row, col);
                        col = targetClass.getWidth();
                    }
                }
            }
            ans = new Matrix(vals);
            for(int row = 0; row < ans.getHeight(); row++){
                for(int col = 0; col < ans.getWidth(); col++){
                    ans.setElement(-1 * Math.log(ans.getElement(row, 0)), row, 0);
                }
            }
            return ans;
        }
    }

    //need to clip the values to be between 1e^-7 because log(0) is infinite
    public void clipOutput(Matrix output){
        for(int row = 0; row < output.getHeight(); row++){
            for(int col = 0; col < output.getWidth(); col++){
                if(output.getElement(row, col) < 1e-7){
                    output.setElement(1e-7, row, col);
                }
            }
        }
    }

    //dValues in this case is the output vector of this function
    public Matrix backwards(Matrix yTrue, Matrix dValues){
        double[][] vals = new double[yTrue.getHeight()][yTrue.getWidth()];
        //is sparse vector
        if(yTrue.getWidth() == 1 || yTrue.getHeight() == 1){
           yTrue = yTrue.sparseToEncoded();
        }
        for(int row = 0; row < yTrue.getHeight(); row++){
            for(int col = 0; col < yTrue.getWidth(); col++){
                if(dValues.getElement(row, col) != 0){
                    vals[row][col] = -1 * yTrue.getElement(row, col)/dValues.getElement(row, col);
                }else{
                    vals[row][col] = 0;
                }
            }
        }
        //normalizing gradients
        Matrix ans = new Matrix(vals);
        ans.scalarDivision(dValues.getHeight());
        return new Matrix(vals);
    }

}
