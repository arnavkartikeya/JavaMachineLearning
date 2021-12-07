# JavaMachineLearning
In progress library for machine learning in Java. Currently has linear and logistic regression. 

## Sample use: 

```
public static void main(String[] args) throws FileNotFoundException {
        //reading data 
        Matrix data = Matrix.readTxt(new File ("src/test.txt"),20, 2 );
        Matrix x = data.getCol(0);
        Matrix y = data.getCol(1);
        //fitting model
        LinearRegressionModel l = new LinearRegressionModel(x, y);
        Metrics test = new MeanSquaredLoss(100, null, y );
        Matrix weights =  l.fit(1000,0.01 , test);
        //outputs
        double[][] testValues = {{7}};
        Matrix values = new Matrix(testValues);
        Matrix answers = l.predict(values);

        System.out.println("Equation: " + l.getEquation());

}
```

## Output: 
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
 
