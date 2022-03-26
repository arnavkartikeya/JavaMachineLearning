# JavaMachineLearning
In progress library for machine learning in Java aimed for easy and beginner friendly use. Currently has multivariable linear regression, logistic regression, and neural networks (more optimizers to come). 

## Linear Regression: 
Linear Regression's goal is to take some set of data and predict a continous output value. An example of this is predicting the price of a house (which can be an value from 0 to infinity, a continous value) given the square footing of a house. In the code example given below, I created a data set with inputs 1 through 20 and outputs that model the equation 2x+5 with some random variation. This means the output for linear regression should be near 2x+5. 

This first step is to read the data, which is done through the matrix class with the `Matrix.readTxt()` method, which takes a file, the amount of samples in the data, and the width of the data (number of features + output width (usually 1)). In this case the number of features is 1 x value so the width of the data is 2. Next, the data needs to be split into x (input features) and y (output) using the getCol() method which returns the specified columm. The full code for this would be: 

```
Matrix data = Matrix.readTxt(new File("src/test.txt"), 20, 2); 
Matrix x = data.getCol(0);
Matrix y = data.getCol(1); 
```

If x has more than 1 feature the `splitCol()` method can be used which returns a new matrix of columns between two values. 

To train the model, the `LinearRegressionModel` class is used, where the constructor takes the input and output data. Next, a metric is defined as to measure how well the model is doing, an example is `MeanSquaredLoss`. This metric requires however many iterations should be taken before it is computed. So for example putting 500 means every 500 iterations the metric is computed. A hypothesis matrix can be given in the constructor, although not neccesary. Lastly the output vector y is needed. Other metrics include accuracy. Finally, the `LinearRegresssionModel` method `fit()` is called, with parameters of numbers of iterations, learning rate, and a Metric. This method will print out the Metric computation into the console. Code shown below: 

```
MeanSquaredLoss loss = new MeanSquaredLoss(500, null, y); 
LinearRegressionModel model = new LinearRegressionModel(5000, 0.01, loss);
model.fit(5000, 0.01, loss);
```

The last part of linear regression is to make predictions with the model. This can be done with the `predict()` method, which takes a Matrix that it predicts the output of. The method `getEquation()` gives the line of best fit that the model predicts. 

```
//making a matrix
double[][] testValues = {{7}}; 
Matrix values = new Matrix(testValues); 
//finding output
Matrix answers = model.predict(values); 
//printing model predictions
answers.printMatrix();
//printing line of best fit
System.out.println("Equation: " + l.getEquation());
```

### Putting it all together: 


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
Logistic Regression is similar to linear regression, however rather than outputing a continuous value, it outputs a discrete one. In most cases it is typcially predicting a class (cat vs dog or daisy vs rose vs tulip). Logistic regression is implemented almost exactly as linear regression. 

The main differences include a different Metrics class, `Accuracy`, instead of `MeanSquaredLoss`, and using the `LogisticRegressionModel` instead of the LinearRegressionModel. LogisticRegressionModel has an included method `decisionBoundary()` which gives the decision boundary that splits two classes. Multivariable Logistic Regression is also implemented. The code example below is multivariate. 


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

Neural Networks are now implemented in JavaMachineLearning, and more ease of use applications will come. For now, it operates much like python libraries with Dense layers. The included classes are `NetworkLayers`, `Activation`, and `OptimizerSGD`. 

Neural networks are built rather simply. The first step is to read data much like linear or logistic regression and splitting the data between input and output. The second step is to build the activation functions. Typically layers that are not the last output layer have activations of `Relu` or `Sigmoid`, although Relu is used more commonly. `SoftMax` and `CategoricalCrossEntropy` are created to later create a `SoftmaxAndCross` optimizer, which is a more effecient implementation than having both activations seperately. 

```
//code for creating activations
Activation relu = new Activation();
SoftmaxAndCross loss_activation = new SoftmaxAndCross(new CategoricalCrossEntropy(), new Softmax()); 
```

After creating the activations, the next step is to build the architecture of the network. This is done by creating a `NetworkLayer` which takes the nubmer of neurons in the current layer and the number of neurons in the next layer. It can also take the activation as part of the initialization, but it is not neccesary. If you choose not to include the activation inside the network layer, each activation has it's own `activate()` and `backward()` method which will be used during forward and back propagation. Future implentations will standarize everything to `forward()`. 

```
//code for building network architecture (in this case a network that takes 2 inputs, has a single hidden layer of 64 neurons, and an output of 3 neurons, which indicated 3 classes).
NetworkLayer first = new NetworkLayer(2, 64); 
NetworkLayer second = new NetworkLayer(64, 3); 
```

Finally, forward propagation, backwards propagation and optimization are needed. Forward and backwards propagation are needed to create a Matrix of dValues, which are used by the `OptimizerSGD` class to optimize the weights and biases of each NetworkLayer. Forward propagation works by using the `feedForward()` or `forward()` method for each part of the network. Backwards propagation works in the same way, but using `backwards()` and in the reverse order of forward. Finally the optimizers are initizalized. They require a learning rate, decay rate, momentum, and NetworkLayer. If you are unsure about these hyperparameters, later implentations will have different optimizers which require less. OptimizerSGD will use the `update()` method for the NetworkLayer associated with it. 

```
//creating optimizers and implementing forward and backwards propagation
OptimizerSGD s = new OptimizerSGD( 1, 0.01, 0.9, first);
OptimizerSGD s2 = new OptimizerSGD(1, 0.01, 0.9, second);

//forward propagation
forward = first.feedForward(x);
Matrix f = relu.activate(forward);
nextForward = second.feedForward(f);
loss_activation.forward(nextForward, y);
//forward propogation output and backward propagation input 
out = loss_activation.getOutput();

//backward propagation
dValue = loss_activation.backwards(new Matrix(out.getArray()), y);
 Matrix d = second.backwards(dValue);
Matrix d1 = relu.backwards(d);
first.backwards(d1);

//updating using update() method from optimizers, taking a parameter of the NetworkLayer. 
s.update(first);
s2.update(second);
```

The forward and backward propagation, as well as the update() method can be put in a for loop for any amount of iterations for better training. In terms of metrics, the SoftmaxAndCross class has a `getAccuracy()` method which gives accuracy from Network to dataset, and a `getLoss()` which returns cross entropy loss. 


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
