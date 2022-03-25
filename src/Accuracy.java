public class Accuracy extends Metrics {
    public int increments;
    Matrix outputs;
    public Matrix hypothesis;
    public Accuracy(int increments, Matrix hypothesis, Matrix output){
        this.increments = increments;
        this.outputs = output;
        this.hypothesis = hypothesis;
    }

    public Accuracy(){

    }

    @Override
    public double compute() {
        double acc = 0;
        for(int row = 0; row < hypothesis.getHeight(); row++){
            for(int col = 0; col < hypothesis.getWidth(); col++){
                if(hypothesis.getElement(row, col) == outputs.getElement(row, col)){
                    acc++;
                }
            }
        }
        return (acc/(double)(hypothesis.getHeight()));
    }

    //not finished
    public double compute(Matrix input, Matrix target_classes){
        double[][] correctVect = new double[input.getHeight()][1];
        double correctSum = 0;
        //a vector containing correct (1) and incorrect (0)
        //assuming sparse target_class vector
        for(int row = 0; row < input.getHeight(); row++){
            for(int col = 0; col < input.getWidth(); col++){
                if(input.getElement(row, col) >= 0.5){
                    if(target_classes.getElement(row, col) == col){
                        correctSum++;
                    }
                }
                col = input.getWidth();
            }
        }
        //assuming one hot encoded vector
        return 0;
    }


    @Override
    public int getIncrements() {
        return increments;
    }

    @Override
    public void setOutputs(Matrix outputs) {
        this.outputs = outputs;
    }

    @Override
    public void setHypothesis(Matrix hypothesis) {
        this.hypothesis = hypothesis;
    }
}
