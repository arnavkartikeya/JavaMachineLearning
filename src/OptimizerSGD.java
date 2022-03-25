public class OptimizerSGD {
    double learningRate;
    Matrix newWeights;
    Matrix newBias;
    Matrix dWeights;
    Matrix dBias;
    Matrix weightMomentum;
    Matrix biasMomentum;
    double momentum;
    int numIterations = 0;
    double currRate;
    double decay = 0;
    public OptimizerSGD(double lr, double decay, double momentum, NetworkLayer l){
        this.learningRate = lr;
        this.decay = decay;
        this.currRate = learningRate;
        weightMomentum = Matrix.makeZeros(l.getWeights().getHeight(), l.getWeights().getWidth());
        biasMomentum = Matrix.makeZeros(l.getBias().getHeight(), l.getBias().getWidth());
        this.momentum = momentum;
    }
    public void update(NetworkLayer layer){

        Matrix weightUpdates = weightMomentum.scalarMultiplication(momentum).subtractMatrix(layer.getDWeights().scalarMultiplication(currRate));
        Matrix biasUpdates = biasMomentum.scalarMultiplication(momentum).subtractMatrix(layer.getDBias().scalarMultiplication(currRate));

        weightMomentum = weightUpdates;
        biasMomentum = biasUpdates;

        Matrix n = layer.getWeights();
        Matrix b = layer.getBias();

        n = n.addMatrix(weightUpdates);
        b = b.addMatrix(biasUpdates);

        layer.setWeights(n);
        layer.setBias(b);
        numIterations++;
        updateLearningRate();
    }

    public void updateLearningRate(){
        this.currRate = this.learningRate * (1.0/(1.0 + this.decay * this.numIterations));
    }

    public Matrix getWeights(){
        return newWeights;
    }

    public Matrix getBias(){
        return newBias;
    }
}
