public class Accuracy extends Metrics {
    public int increments;
    Matrix outputs;
    public Matrix hypothesis;
    public Accuracy(int increments, Matrix hypothesis, Matrix output){
        this.increments = increments;
        this.outputs = output;
        this.hypothesis = hypothesis;
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
