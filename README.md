# JavaMachineLearning
In progress library for machine learning in Java aimed for easy and beginner friendly use. Currently has multivariable linear regression and logistic regression (softmax regression still to come, however includes one vs all regression). 

## Linear Regression: 

```
public static void main(String[] args) throws FileNotFoundException {
        //reading data 
        Matrix data = Matrix.readTxt(new File ("src/test.txt"),20, 2 );
        Matrix x = data.getCol(0);
        Matrix y = data.getCol(1);
        //fitting model
        LinearRegressionModel l = new LinearRegressionModel(x, y);
        Metrics test = new MeanSquaredLoss(500, null, y );
        Matrix weights =  l.fit(5000,0.01 , test);
        //outputs
        double[][] testValues = {{7}};
        Matrix values = new Matrix(testValues);
        Matrix answers = l.predict(values);

        System.out.println("Equation: " + l.getEquation());

}
```

### Output: 
```
Metric at 0th iteration: 809.0
Metric at 500th iteration: 0.537411734903422
Metric at 1000th iteration: 0.05348443128961202
Metric at 1500th iteration: 0.005322891564486252
Metric at 2000th iteration: 5.297462069636101E-4
Metric at 2500th iteration: 5.272154061232164E-5
Metric at 3000th iteration: 5.246966958911073E-6
Metric at 3500th iteration: 5.221900185002276E-7
Metric at 4000th iteration: 5.196953164613539E-8
Metric at 4500th iteration: 5.172125325498477E-9
Equation: 4.999952867236871 + 2.0000034542866003x_1
```


## Logistic Regression:
```
public static void main(String[] args) throws FileNotFoundException {
        //reading data
        Matrix data = Matrix.readTxt(new File ("src/test.txt"),150, 3);
        Matrix x = data.splitCol(0, 1);
        Matrix y = data.getCol(2);
        //Defining which metric to use (accuracy in this case)
        Metrics test = new Accuracy(500, null, y );
        //Creating model
        LogisticRegressionModel l = new LogisticRegressionModel(x,y);
        //Fitting model
        Matrix weights = l.fit(5000, 0.01, test);
        //Printing the equation and decision boundary for the model
        System.out.println("Equation: " + l.getEquation());
        System.out.println("Decision boundary: " + l.decisionBoundary());
        //Predictions
        double[][] ans = {{5.1, 3.5}};
        Matrix vals = new Matrix(ans);
        System.out.println("Prediction output: " + l.predict(vals));
    }
```
### Output (On Iris dataset)
```
Metric: 0.3333333333333333
Metric: 0.78
Metric: 0.9533333333333334
Metric: 0.9866666666666667
Metric: 0.9866666666666667
Metric: 0.9933333333333333
Metric: 0.9933333333333333
Metric: 0.9933333333333333
Metric: 0.9933333333333333
Metric: 0.9933333333333333
Equation: sigmoid(-0.5358341227663898 + 2.242814233803413x_1 + -3.6471986763872404x_2)
Decision boundary: -((-0.5358341227663898 + 2.242814233803413x_1) / -3.6471986763872404)
Prediction output: 0.0
```

# Update (3/24/2022): Neural Networks
* Added basic neural networks
* Customizable dense layers and activations (Relu, Cross Entropy, Softmax, Sigmoid)
* Optimized activations with Cross Entropy and Softmax combined
* Parameters update with momentum and 1/t learning rate decay


## Code example (Spiral Dataset):
```
        Matrix data = Matrix.readTxt(new File("src/test.txt"), 300, 3);
        Matrix x = data.splitCol(0, 1);
        Matrix y = data.getCol(2);

        //creating activations and dense layers
        Activation relu = new Relu();
        SoftMax m = new SoftMax();
        CategoricalCrossEntropy c = new CategoricalCrossEntropy();
        NetworkLayer first = new NetworkLayer(2, 64);
        NetworkLayer second = new NetworkLayer(64 , 3);
        Matrix nextForward = second.feedForward(forward);
        SoftmaxAndCross loss_activation = new SoftmaxAndCross(m, c);

        //creating optimizers with decay and momentum 
        OptimizerSGD s = new OptimizerSGD( 1, 0.01, 0.9, first);
        OptimizerSGD s2 = new OptimizerSGD(1, 0.01, 0.9, second);


        //forward and backward propagation
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
```

## Output
```
Accuracy: 0.30666666666666664 Loss: 1.098613999008802
Accuracy: 0.6 Loss: 0.7834813071122058
Accuracy: 0.6766666666666666 Loss: 0.6941580582381379
Accuracy: 0.71 Loss: 0.6554447386154415
Accuracy: 0.7466666666666667 Loss: 0.6227082533674139
Accuracy: 0.78 Loss: 0.6002097748068823
Accuracy: 0.79 Loss: 0.5808453125800811
Accuracy: 0.8066666666666666 Loss: 0.5652433231196666
Accuracy: 0.8033333333333333 Loss: 0.5527555188170457
Accuracy: 0.7966666666666666 Loss: 0.5423047872577976
Accuracy: 0.8033333333333333 Loss: 0.5329574453342741
```

### What's to come:
* Further optimizers
* Code clean up 
* Documentation
* Increasing ease of use for clients  
