import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Matrix data = Matrix.readTxt(new File ("src/test.txt"),20, 2 );
        Matrix x = data.getCol(0);
        Matrix y = data.getCol(1);

        LinearRegressionModel l = new LinearRegressionModel(x, y);

        Metrics test = new MeanSquaredLoss(100, null, y );


        Matrix weights =  l.fit(1000,0.01 , test);

        double[][] testValues = {{7}};
        Matrix values = new Matrix(testValues);
        Matrix answers = l.predict(values);

        System.out.println("Equation: " + l.getEquation());

    }
}
