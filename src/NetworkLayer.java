/*this class has the weights defined as the weights that are after the current neuron layer, not before, thus
the dimensions of the weight matrix is next layer * current layer, where each row is a single neurons set of weights
to each of the next neurons.
 */
public class NetworkLayer {
    //a vector
    Matrix layer;
    Matrix weights;
    Matrix bias;

    public Matrix getDWeights() {
        return dWeights;
    }

    public void setDWeights(Matrix dWeights) {
        this.dWeights = dWeights;
    }

    public Matrix getDBias() {
        return dBias;
    }

    public void setWeight(Matrix weight){
        this.weights = weight;
    }

    Activation activation;
    Matrix inputs;
    boolean hasActivation;
    Matrix dWeights;
    Matrix dBias;
    //size of the vector
    int size;
    //#TODO check if its better to scalar multiply the weights by 0.01 like the book does
    //this constructor is for the last layer in the network, and thus doesn't need weights
    public NetworkLayer(int size,  Activation a){
        this.size = size;
        layer = Matrix.randomizeMatrix(this.size, 1, 0, 1, false);
        this.activation = a;

    }

    public Matrix getWeights(){
        return this.weights;
    }
    public Matrix getBias(){
        return this.bias;
    }
    public void setWeights(Matrix weights){
        this.weights = weights;
    }
    public void setBias(Matrix bias){
        this.bias = bias;
    }


    public NetworkLayer(int size, int nextSize){
        hasActivation = false;
        this.size = size;
        weights = Matrix.randomizeMatrix(this.size, nextSize, 0, 1, false).scalarMultiplication(0.01);
        layer = Matrix.randomizeMatrix(this.size, 1, 0, 1, false);
        this.bias = Matrix.makeZeros(1, nextSize);
    }

    public NetworkLayer(int size, int nextSize, Activation activation){
        this.size = size;
        layer = Matrix.randomizeMatrix(this.size, 1, 0, 1, false);
        //System.out.println("Layer matrix: ");
        //layer.printMatrix();
        //System.out.println("Weight matrix: ");
        weights = Matrix.randomizeMatrix(this.size, nextSize, 0, 1, false).scalarMultiplication(0.01);
        //weights.printMatrix();
        //creates a matrix of 1s for bias vector
        this.bias = Matrix.makeOnes(1, nextSize);
        this.activation = activation;
    }
    public NetworkLayer(int size, double[][] input, double[][] weight, double[][] biases, Activation a){
        this.size = size;
        layer = Matrix.randomizeMatrix(this.size, 1, 0, 1, false);
        this.weights = new Matrix(weight);
        this.bias = new Matrix(biases);
        this.activation = a;
//        System.out.println("Layer matrix: ");
//        layer.printMatrix();
//        System.out.println("Weight matrix: ");
//        weights = Matrix.randomizeMatrix(this.size, prevSize, 0, 1, false);
//        weights.printMatrix();
    }
    //feed forward, currently does not have activation on purpose
    //inputs is defined as a matrix with 1 feature set per row, so in an n*m matrix, n is the size of the dataset and m is the number of features in a single dataset
    public Matrix feedForward(Matrix inputs){
        this.inputs = inputs;
        //weights transpose times inputs = output
        Matrix forward = inputs.multiplyMatrix(weights);

        forward = forward.addVect(bias);
        //activate
        if(hasActivation) {
            forward = activation.activate(forward);
        }
       // forward.printMatrix();
        return forward;
    }

    public Matrix backwards(Matrix dValues){
        //return derivative with respect to inputs
        this.dWeights = this.inputs.transpose().multiplyMatrix(dValues);
        Matrix dInputs = dValues.multiplyMatrix(this.weights.transpose());
        this.dBias = dValues.sumColumns();
        //need to add activation derivative

        return dInputs;
    }

    //sigmoid activation, takes every input value and applies sigmoid function to them
    public Matrix sigmoidActivation(Matrix input){
        Matrix negs = input.scalarMultiplication(-1);
        Matrix powerNegs = negs.raiseE(Math.exp(1));
        powerNegs = powerNegs.addAll(1);
        Matrix rec = powerNegs.reciprocal();
        return rec;
    }

}
