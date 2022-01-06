import java.util.*;
import java.util.LinkedHashSet;
public class LinearRegressionModel {
    public Matrix inputs;
    public Matrix outputs;
    public Matrix weights;
    public double[][] weightList = new double[100][2];
    public Matrix testInputs;
    public Matrix testOutputs;
    public Matrix trainInputs;
    public Matrix trainOutputs;
    public Matrix dTheta;
    public double[][] dApprox;
    public LinearRegressionModel(Matrix x, Matrix y){
        this.outputs = y;
        this.inputs = x.addOnes();
        testInputs = this.inputs;
        testOutputs = this.outputs;
        trainInputs = this.inputs;
        trainOutputs = this.outputs;
//        this.inputs.printMatrix();
    }
    public Matrix fit(int numIterations, double alpha, Metrics test){
        Matrix hyp;
        Matrix err;
        Matrix xNew = this.trainInputs;
        Matrix temp2;
        Matrix gradients;
        Matrix w = initializeWeights();
        test.setOutputs(trainOutputs);
        for(int i = 0; i < numIterations; i++){
            //hypothesis
            hyp = trainInputs.multiplyMatrix(w);
            //error
            err = hyp.subtractMatrix(trainOutputs);
            //this is the actual derivatives of theta
            gradients = xNew.transpose().multiplyMatrix(xNew.multiplyMatrix(w).subtractMatrix(trainOutputs));
            //settings dTheta to gradients for gradient checking
            dTheta = gradients;
            //gradients * 1/m * alpha
            Matrix gradientTemp = gradients.scalarMultiplication((alpha/this.trainInputs.getHeight()));
            //w = weight matrix
            w = w.subtractMatrix(gradientTemp);
            this.weights = w;
            test.setHypothesis(hyp);
            if(i%test.getIncrements() == 0){
                System.out.println("Metric at " + i + "th iteration: " + test.compute());
            }
        }

        return w;
    }
    //fit w/ regularization
    public Matrix fit(int numIterations, double alpha, double lambda, Metrics test){
        Matrix hyp;
        Matrix err;
        Matrix xNew = this.trainInputs;
        Matrix temp2;
        Matrix gradients;
        Matrix w = initializeWeights();
        test.setOutputs(trainOutputs);
        for(int i = 0; i < numIterations; i++){
            //hypothesis
            hyp = trainInputs.multiplyMatrix(w);
            //error
            err = hyp.subtractMatrix(trainOutputs);
            //this is the actual derivatives of theta
            gradients = xNew.transpose().multiplyMatrix(xNew.multiplyMatrix(w).subtractMatrix(trainOutputs));
            //settings dTheta to gradients for gradient checking
            dTheta = gradients;
            //gradients * 1/m * alpha
            Matrix gradientTemp = gradients.scalarMultiplication((alpha/this.trainInputs.getHeight()));
            //w = weight matrix
            w = w.scalarMultiplication((double)(1 + (alpha * lambda)/this.trainInputs.getHeight())).subtractMatrix(gradientTemp);
            this.weights = w;
            test.setHypothesis(hyp);
            if(i%test.getIncrements() == 0){
                System.out.println("Metric at " + i + "th iteration: " + test.compute(lambda, this.weights));
            }
        }

        return w;
    }

    //mean square error cost
    public double cost(Matrix err){
        double sumSquared = 0;
        for(int row = 0; row < err.getHeight(); row++){
            sumSquared += Math.pow(err.getElement(row, 0), 2);
        }
        return (double)(sumSquared/(err.getHeight()));
    }


    //returns a string with the equation in a + bx1 + cx2 form
    public String getEquation(){
        String ans = "";
        for(int row = 0; row < weights.getHeight(); row++){
            Double d = weights.getElement(row, 0);
            if(row == 0){
                ans += d.toString() + " + ";
            }else if(row != weights.getHeight() - 1){
                ans += d.toString() + "x_" + row + " + ";
            }else{
                ans += d.toString() + "x_" + row;
            }
        }
        return ans;
    }

    //returns matrix of predicted values
    public Matrix predict(Matrix val){
        val = val.addOnes();
        Matrix ans = val.multiplyMatrix(weights);
        return ans;
    }

    //creates an initialize weight matrix
    public Matrix initializeWeights(){
        int numWeights = inputs.getWidth();
        double[][] ans = new double[numWeights][1];
        for(int row = 0; row < numWeights; row++){
            ans[row][0] = 0;
        }
        Matrix a = new Matrix(ans);
        return a;
    }

    //splits test set and train set given a ratio for how much should be in test set
    public void makeTestSet(double testRatio){
        //creating the randomly chosen rows (no duplicates using hashset)
        Set<Integer> set = new LinkedHashSet<Integer>();

        int amountTest = (int)(testRatio * this.inputs.getHeight());
        Random r = new Random();
        while(set.size() < amountTest){
            set.add(r.nextInt(this.inputs.getHeight()));
        }
        Integer[] dataPointsTest = set.toArray(new Integer[set.size()]);

        Collections.sort(Arrays.asList(dataPointsTest));

        double[][] test = new double[1][this.inputs.getWidth()];
        this.testInputs = new Matrix(test);
        this.trainInputs = this.inputs;
        for(int row = 0; row < dataPointsTest.length; row++){
            testInputs.addRow(this.inputs.getRow(dataPointsTest[row] - row));
            trainInputs.removeRow(dataPointsTest[row] - row);
        }

        double[][] t = new double[1][1];
        this.testOutputs = new Matrix(t);
        this.trainOutputs = this.outputs;
        for(int row = 0; row < dataPointsTest.length; row++){
            testOutputs.addRow(this.outputs.getRow(dataPointsTest[row] - row));
            trainOutputs.removeRow(dataPointsTest[row] - row);
        }
        this.testInputs.removeRow(0);
        this.testOutputs.removeRow(0);
    }

    //under developement - currently doesn't work
    public double gradientCheck(double epsilon){
        dApprox = new double[this.weights.getHeight()][this.weights.getWidth()];
        //create a cost vector which has each theta have +epsilon and the cost divided by 2*epsilon
        for(int row = 0; row < this.weights.height; row++){
            Matrix tempWeight = this.weights;
            //sets the theta value to itself + epsilon
            tempWeight.setElement(this.weights.getElement(row, 0) + epsilon, row, 0);
            Matrix hyp = trainInputs.multiplyMatrix(tempWeight);
            Matrix err = hyp.subtractMatrix(trainOutputs);
            //compute the cost of this new vector
            double val = cost(err);

            //do the same thing but with theta-epsilon
            tempWeight = this.weights;
            //sets the theta value to itself + epsilon
            tempWeight.setElement(this.weights.getElement(row, 0) - epsilon, row, 0);
            hyp = trainInputs.multiplyMatrix(tempWeight);
            err = hyp.subtractMatrix(trainOutputs);
            //compute the cost of this new vector
            double newVal = cost(err);

            double finalVal = (val - newVal)/(2.0 * epsilon);
            //vector which has the final dApprox
            dApprox[row][0] = finalVal;
        }
        Matrix approx = new Matrix(dApprox);
        double numerator = Matrix.euclideanLength(approx.subtractMatrix(dTheta));
        double denominator = Matrix.euclideanLength(approx.addMatrix(dTheta));
        //debugging for now
        //double numerator = 10;
        //double denominator = 10;
        return (numerator);


    }


}
