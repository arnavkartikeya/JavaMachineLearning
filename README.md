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
Metric: 809.0
Metric: 3.403854252358098
Metric: 2.145630842610738
Metric: 1.3525055338586076
Metric: 0.8525563590856811
Metric: 0.537411734903422
Metric: 0.3387592734885464
Metric: 0.2135380341017495
Metric: 0.13460440961059672
Metric: 0.08484833703200481
Equation: 4.519556907674327 + 2.0352109239044593x_1
```
 
