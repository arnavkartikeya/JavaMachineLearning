public class MeanSquaredLoss extends Metrics {
    public int increments;
    Matrix outputs;
    public Matrix hypothesis;
    public MeanSquaredLoss(int increments, Matrix hypothesis, Matrix output){
        this.increments = increments;
        this.outputs = output;
        this.hypothesis = hypothesis;
    }
    @Override
    public double compute() {
        Matrix err = hypothesis.subtractMatrix(outputs);
        double sumSquared = 0;
        for(int row = 0; row < err.getHeight(); row++){
            sumSquared += Math.pow(err.getElement(row, 0), 2);
//            sumSquared += Math.abs(err.getElement(row, 0));
        }
        return (double)(sumSquared/(err.getHeight()));
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
