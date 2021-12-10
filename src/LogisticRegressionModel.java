import java.util.*;
import java.util.LinkedHashSet;
public class LogisticRegressionModel {
    public Matrix inputs;
    public Matrix outputs;
    public Matrix weights;
    public Matrix testInputs;
    public Matrix testOutputs;
    public Matrix trainInputs;
    public Matrix trainOutputs;
    public LogisticRegressionModel(Matrix x, Matrix y){
        this.outputs = y;
        this.inputs = x.addOnes();
        testInputs = this.inputs;
        testOutputs = this.outputs;
        trainInputs = this.inputs;
        trainOutputs = this.outputs;
//        this.inputs.printMatrix();
    }

//incase the fit method doesnt work with accuracy metrics
//    public Matrix fit(int numIterations, double alpha, boolean doPrintLoss){
//        Matrix hyp;
//        Matrix err;
//        Matrix temp;
//        Matrix temp2;
//        Matrix temp3;
//        double adj_alpha = 0.0005/
//                (double)(this.trainInputs.height);
//        Matrix w = initializeWeights();
//        for(int i = 0; i < numIterations; i++){
//            hyp = trainInputs.multiplyMatrix(w);
//            hyp = this.sigmoid(hyp);
//            err = hyp.subtractMatrix(trainOutputs);
//
//
//            temp = trainInputs.transpose();
//            temp3 = temp.multiplyMatrix(err);
//            temp2 = temp3.scalarDivision(this.trainInputs.getHeight());
//            temp2 = temp2.scalarMultiplication((double)(alpha));
//            w = w.subtractMatrix(temp2);
//            this.weights = w;
//            if(i%100 == 0 && doPrintLoss){
//                System.out.println("Accuracy at " + i + "th iteration: " + accuracy(hyp));
//            }
//        }
//        return weights;
//    }
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
            hyp = sigmoid(hyp);
            //error
            err = hyp.subtractMatrix(trainOutputs);
            //this is the actual derivatives of theta
            gradients = xNew.transpose().multiplyMatrix(xNew.multiplyMatrix(w).subtractMatrix(trainOutputs));
            //gradients * 1/m * alpha
            Matrix gradientTemp = gradients.scalarMultiplication((alpha/this.trainInputs.getHeight()));
            //w = weight matrix
            w = w.subtractMatrix(gradientTemp);
            this.weights = w;
            test.setHypothesis(round(hyp));
            if(i%test.getIncrements() == 0){
                System.out.println("Metric: " + test.compute());
            }
        }

        return w;
    }


    //one vs all logistic regression
    /*
    First: Create a duplicate of matrix data. Find the number of different clusters using a hashtable
    Second: make a loop that iterates through the size of the hashtable
    Third: create a matrix method which sets all values to another [setAll(double element, double replacment)].
    Fourth: Set all output values besides a one chosen one to 1. The chosen one should be set to 0.
    Fifth: Do logistic regression
    Sixth: Repease the last few steps for each otehr cluster
    Seventh: When given an input, use all models found and return the answer of the model with the highest confidence 

     */

    public double cost(Matrix hyp){
        double sum = 0;
        for(int row = 0; row < hyp.getHeight(); row++){
            sum += this.testOutputs.getElement(row, 0) * Math.log(hyp.getElement(row, 0)) + (double)(1 - this.testOutputs.getElement(row, 0)) * Math.log(1 - hyp.getElement(row, 0));
        }
        sum *= -1;

        return sum;
    }
    //this will return the function that needs to be put inside sigmoid
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
        return "sigmoid(" + ans + ")";
    }
    public Matrix predict(Matrix val){
        val = val.addOnes();
        Matrix ans = val.multiplyMatrix(weights);
        ans = sigmoid(ans);
        for(int row = 0; row < ans.getHeight(); row++){
            for(int col = 0; col < ans.getWidth(); col++){
                if(ans.getElement(row, col) < 0.5){
                    ans.setElement(0, row, col);
                }else{
                    ans.setElement(1, row, col);
                }
            }
        }
        return ans;
    }
    //creates an initialize weight matrix
    public Matrix initializeWeights(){
        int numWeights = inputs.getWidth();
        double[][] ans = new double[numWeights][1];
        for(int row = 0; row < numWeights; row++){
            ans[row][0] = 0.0;
        }
        Matrix a = new Matrix(ans);
        return a;
    }
    //changes all values below or equal to 0.5 to 0 and values above 0.5 to 1
    public Matrix round(Matrix a){
        Matrix ans = a;
        for(int row = 0; row < a.getHeight(); row++){
            for(int col = 0; col < a.getWidth(); col++){
                if(ans.getElement(row, col) <= 0.5){
                    ans.setElement(0, row, col);
                }else{
                    ans.setElement(1, row, col);
                }
            }
        }
        return ans;
    }



    //method which randomly selects values for training and test dataset
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
    //take the hypothesis and apply sigmoid function to it
    public Matrix sigmoid(Matrix hypothesis){
        Matrix negs = hypothesis.scalarMultiplication(-1);
        Matrix powerNegs = negs.raiseE(Math.exp(1));
        powerNegs = powerNegs.addAll(1);
        Matrix rec = powerNegs.reciprocal();
        return rec;
    }
    public double accuracy(Matrix hyp){
        double accSum = 0;
        for(int row = 0; row < hyp.getHeight(); row++){
            double element = 1;
            if(hyp.getElement(row , 0) < 0.5){
                element = 0;
            }
            if(element == this.testOutputs.getElement(row, 0)){
                accSum++;
            }
        }
        return (accSum/(double)(hyp.getHeight()));
    }

}
