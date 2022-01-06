import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        //reading data
        Matrix data = Matrix.readTxt(new File ("src/test.txt"),20, 2);
        Matrix x = data.getCol(0);
        Matrix y = data.getCol(1);
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

        LinearRegressionModel l = new LinearRegressionModel(x,y);

        Matrix weights = l.fit(5000, 0.005, 0, test);
//        for(int i = 0; i < weights.length; i++){
//            System.out.println(l.getEquation(weights[i]));
//        }
        System.out.println(l.getEquation());
        double[][] ans = {{1, 1}};
        Matrix vals = new Matrix(ans);


        //debugging, finding each logistic regression line
    }
}
