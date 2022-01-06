import java.io.File;
import java.io.FileNotFoundException;

public class Main {
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
}
