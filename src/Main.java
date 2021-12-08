import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //reading data
        Matrix data = Matrix.readTxt(new File ("src/test.txt"),11, 3 );
        Matrix x = data.splitCol(0, 1);
        Matrix y = data.getCol(2);
        //fitting model
        LogisticRegressionModel l = new LogisticRegressionModel(x, y);
        Metrics test = new Accuracy(100, null, y );
        Matrix weights =  l.fit(1000,0.01 , test);
        //outputs
        double[][] testValues = {{1}};
        Matrix values = new Matrix(testValues);
        Matrix answers = l.predict(values);
        answers.printMatrix();
        System.out.println("Equation: " + l.getEquation());

    }
}
