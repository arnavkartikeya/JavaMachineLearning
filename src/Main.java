import java.awt.image.CropImageFilter;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        double[][] vals = {{0,1}, {2,3}, {4,5}};
//        double[][] output = {{0},{1},{2}};
//        Matrix X = new Matrix(vals);
//        Matrix y = new Matrix(output);
//
//        NetworkLayer dense3 = new NetworkLayer(2, 3);
//        double[][] weightsVals = {{0.01, .0024, 0.0034}, {0.002, 0.0017, 0.002}};
//        Matrix weights1 = new Matrix(weightsVals);
//        dense3.setWeights(weights1);
//
//        NetworkLayer dense4 = new NetworkLayer(3, 3);
//        double[][] weightsVals1 = {{0.01, .0024, 0.0034}, {0.002, 0.0017, 0.002}, {0.002, 0.0017, 0.002}};
//        Matrix weights2 = new Matrix(weightsVals1);
//        dense4.setWeights(weights2);
//
//        Activation activation4 = new Relu();
//        SoftMax m = new SoftMax();
//        CategoricalCrossEntropy c = new CategoricalCrossEntropy();
//        SoftmaxAndCross loss_activation1 = new SoftmaxAndCross(m, c);
//
//        Matrix out = dense3.feedForward(X);
//        Matrix out1 = activation4.activate(out);
//        Matrix out2 = dense4.feedForward(out1);
//        loss_activation1.forward(out2, y);
//        Matrix out3 = loss_activation1.getOutput();
//
//        System.out.println("Forwards");
//        out3.printMatrix();
//
//
//        //#TODO back prop needs to be fixed
//        System.out.println("Backwards");
//
//        Matrix dValue = loss_activation1.backwards(new Matrix(out3.getArray()), y);
//        Matrix d = dense4.backwards(dValue);
//        Matrix d1 = activation4.backwards(d);
//        Matrix d2 = dense3.backwards(d1);
//
//        d2.printMatrix();






        Matrix data = Matrix.readTxt(new File("src/test.txt"), 300, 3);
        Matrix x = data.splitCol(0, 1);
        Matrix y = data.getCol(2);


        Activation relu = new Relu();
        SoftMax m = new SoftMax();
        CategoricalCrossEntropy c = new CategoricalCrossEntropy();
        NetworkLayer first = new NetworkLayer(2, 64);
        Matrix forward = first.feedForward(x);
        forward = relu.activate(forward);
        //forward.printMatrix();
        NetworkLayer second = new NetworkLayer(64 , 3);
        Matrix nextForward = second.feedForward(forward);
        SoftmaxAndCross loss_activation = new SoftmaxAndCross(m, c);
        loss_activation.forward(nextForward, y);
        Matrix out = loss_activation.getOutput();

        Matrix dValue = loss_activation.backwards(new Matrix(out.getArray()), y);
        dValue = second.backwards(dValue);
        dValue = first.backwards(dValue);
        //dValue = relu.backwards(dValue);
        System.out.println(loss_activation.getAccuracy());
        OptimizerSGD s = new OptimizerSGD( 1, 0.01, 0.9, first);
        OptimizerSGD s2 = new OptimizerSGD(1, 0.01, 0.9, second);

        for(int epoch = 0; epoch < 10001 ; epoch++){
            forward = first.feedForward(x);
            Matrix f = relu.activate(forward);
            nextForward = second.feedForward(f);
            loss_activation.forward(nextForward, y);
            out = loss_activation.getOutput();

            dValue = loss_activation.backwards(new Matrix(out.getArray()), y);
            Matrix d = second.backwards(dValue);
            Matrix d1 = relu.backwards(d);
            Matrix d2 = first.backwards(d1);

            s.update(first);
            s2.update(second);

            if(epoch % 1000 == 0){
                System.out.println("Accuracy: " + loss_activation.getAccuracy() + " Loss: " + loss_activation.getLoss());
                //first.getWeights().printMatrix();
            }
        }





//        //reading data
//        Matrix data = Matrix.readTxt(new File ("src/test.txt"),4, 2 );
//        Matrix x = data.getCol(0);
//        Matrix y = data.getCol(1);
//        //fitting model
//        LinearRegressionModel l = new LinearRegressionModel(x, y);
//        Metrics test = new MeanSquaredLoss(10000, null, y );
//        Matrix weights =  l.fit(50000,0.5 , test);
//        //outputs
//        double[][] testValues = {{0.0163}};
//        Matrix values = new Matrix(testValues);
//        Matrix answers = l.predict(values);
//
//        System.out.println("Equation: " + l.getEquation());
//        System.out.println("Answer: " + answers.getElement(0, 0));




//        //#TODO test if backpropagation works with the same spiral dataset they use
//        double[][] test = {{0, 0, 1}, {0, 1, 0}, {1, 0, 0}};
//        Matrix encodedVec = new Matrix(test);
//        encodedVec.encodedToSparse().printMatrix();
//
//
//
//
//        Activation a = new Relu();
//        Activation two = new Relu();
//        Activation three = new SoftMax();
//
////        NetworkLayer n = new NetworkLayer(10, 2, a);
//        double[][] testInput = {{1, 2, 3, 2.5}, {2., 5, -1, 2}, {-1.5, 2.7, 3.3, -0.8}};
//        double[][] testWeights1 = {{0.2, 0.8, -0.5, 1}, {0.5, -0.91, 0.26, -0.5}, {-0.26, -0.27, 0.17, 0.87}};
//        double[][] biases1 = {{2},{3},{0.5}};
//
//        double[][] testWeights2 = {{0.1, -0.14, 0.5}, {-0.5, 0.12, -0.33}, {-0.44, 0.73, -0.13}};
//        double[][] biases2 = {{-1}, {2}, {-0.5}};
//
//        NetworkLayer m = new NetworkLayer(4, testInput, testWeights1, biases1, a);
//        Matrix output = m.feedForward(new Matrix(testInput));
//        NetworkLayer mNext = new NetworkLayer(3, output.getList(), testWeights2, biases2, three);
//        //mNext.feedForward(new Matrix(output.getList())).printMatrix();
//
//
//        NeuralNetwork testNetwork = new NeuralNetwork();
//        testNetwork.addLayer(new NetworkLayer(4, 3, a));
//        testNetwork.addLayer(new NetworkLayer(3, three));
//        testNetwork.feedForward(new Matrix(testInput)).printMatrix();


//        //reading data
//        Matrix data = Matrix.readTxt(new File ("src/test.txt"),150, 3);
//        Matrix x = data.splitCol(0, 1);
//        Matrix y = data.getCol(2);
//        //Defining which metric to use (accuracy in this case)
//        Metrics test = new Accuracy(500, null, y );
//        //Creating model
//        LogisticRegressionModel l = new LogisticRegressionModel(x,y);
//        //Fitting model
//        Matrix weights = l.fit(5000, 0.01, test);
//        //Printing the equation and decision boundary for the model
//        System.out.println("Equation: " + l.getEquation());
//        System.out.println("Decision boundary: " + l.decisionBoundary());
//        //Predictions
//        double[][] ans = {{5.1, 3.5}};
//        Matrix vals = new Matrix(ans);
//        System.out.println("Prediction output: " + l.predict(vals));

    }
}
