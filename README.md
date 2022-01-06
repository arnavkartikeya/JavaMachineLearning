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

## What's to come: 
* Gradient Checking (in progress)
* Nueral Networks 
* More Metrics (cross entropy, etc.)
* Matrices will hold generic values rather than just doubles

