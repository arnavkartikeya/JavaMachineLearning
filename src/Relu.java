public class Relu extends Activation {
    Matrix input;
    public Matrix activate(Matrix input){
        this.input = input;
        Matrix ans;
        double[][] vals = new double[input.getHeight()][input.getWidth()];
        for(int row = 0; row < input.getHeight(); row++){
            for(int col = 0; col < input.getWidth(); col++){
                vals[row][col] = Math.max(0 , input.getElement(row, col));
            }
        }
        ans = new Matrix(vals);
        return ans;
    }
    //derivative of relu
    public Matrix backwards(Matrix dValues){
        double[][] vals = new double[dValues.getHeight()][dValues.getWidth()];
        for(int row = 0; row < this.input.getHeight(); row++){
            for(int col = 0; col < this.input.getWidth(); col++){
                if(this.input.getElement(row, col) <= 0){
                    vals[row][col] = 0;
                }else{
                    vals[row][col] = dValues.getElement(row, col);
                }
            }
        }
        return new Matrix(vals);
    }
    public void setInput(Matrix input){
       this.input = input;
    }
}
