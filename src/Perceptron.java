public class Perceptron {
    //these are the weights that all connect to this perceptron
    //dimension is n*1; n = number of input features
    Matrix weights;
    Perceptron(int height, int width){
        this.weights = Matrix.randomizeMatrix(height, width, 0, 1);
    }
    //compute a weighted sum and apply activation function
    public double feedForward(Matrix inputs){
        double weightedSum = weights.multiplyMatrix(inputs.transpose()).getElement(0,0);
        double ans = activiation(weightedSum);
        return ans;
    }
    //sigmoid activation function
    public double activiation(double sum){
        double ans = 0;
        ans = 1.0/(1.0 + Math.exp(-1.0 * sum));
        return ans;
    }

    //training
    public void train(Matrix inputs, double answer){
        double alpha = 0.01;
        //gradient descent: weights -= alpha * 1/m * error * inputs
        //or weights = weights - ∆weights
        double guess = feedForward(inputs);
        double error = guess - answer;
        //gradient dimensions are also n*1; n = number of input features
        Matrix gradient = inputs.scalarMultiplication(error * alpha);
        weights = weights.subtractMatrix(gradient);
    }
}