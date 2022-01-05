import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        //reading data
        Matrix data = Matrix.readTxt(new File ("src/test.txt"),150, 3);
        Matrix x = data.splitCol(0, 1);
        Matrix y = data.getCol(2);
//        //fitting model
//        LogisticRegressionModel l = new LogisticRegressionModel(x, y);
        Metrics test = new Accuracy(100, null, y );
//        Matrix weights =  l.fit(5000,0.01 , test);
//        //outputs
//        double[][] testValues = {{1,6}};
//        Matrix values = new Matrix(testValues);
//        Matrix answers = l.predict(values);
//        answers.printMatrix();
//        System.out.println("Equation: " + l.getEquation());

        LogisticRegressionModel l = new LogisticRegressionModel(x,y);

        Matrix weights = l.fit(5000, 0.01, test);

        System.out.println(l.decisionBoundary());


//        Matrix[] weights = l.fitOneVsAll(1000, 0.01, data, test);
//        for(int i = 0; i < weights.length; i++){
//            System.out.println(l.getEquation(weights[i]));
//        }


        //debugging, finding each logistic regression line
    }
}
