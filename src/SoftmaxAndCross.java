public class SoftmaxAndCross {
    SoftMax activation;
    CategoricalCrossEntropy loss;
    public Matrix output;
    public Matrix yTrue;

    public SoftmaxAndCross(SoftMax m, CategoricalCrossEntropy l){
        this.activation = m;
        this.loss = l;
    }

    public Matrix getOutput(){
        return this.output;
    }


    //#TODO create the forwards method and decide if this should extend Activation or Metric class
    //returns the loss as well as does forward activation for Softmax
    public Matrix forward(Matrix input, Matrix yTrue){
        Matrix output = this.activation.activate(input);
        this.yTrue = yTrue;
        this.output = output;
        return this.loss.compute(output, yTrue);

    }


    public Matrix dInputs;
    //dValues is just the output of the forward metric
    public Matrix backwards(Matrix dValues, Matrix yTrue){
        //Convert yTrue to discrete if it is 1-hot right now
        if(yTrue.getWidth() != 1 && yTrue.getHeight() != 1){
            yTrue.encodedToSparse();
        }
        //subtract 1 (since that is always going to be the true y value from whichever index yTrue specifies through the whole dValues array
        //#TODO figure out if i need to copy the dValues matrix into dInputs more carefully
        dInputs = dValues;
        for(int row = 0; row < dValues.getHeight(); row++){
            double setElement = dInputs.getElement(row, (int)yTrue.getElement(row, 0))  - 1;
            dInputs.setElement(setElement, row, (int)yTrue.getElement(row, 0));
        }
        //normalize by dividing by the length of samples in the dValues array
        dInputs = dInputs.scalarDivision(dInputs.getHeight());
        return dInputs;
    }

    public double getAccuracy(){
        int num = 0;
        Matrix out = output.maxRow();
        for(int row = 0; row < out.getHeight(); row++){
            if(out.getElement(row, 0) == yTrue.getElement(row, 0)){
                num++;
            }
        }
        return (double)(num)/out.getHeight();
    }

    public double getLoss(){
        double mean = 0;
        Matrix takeMean = this.loss.compute(output, yTrue);
        for(int row = 0; row < takeMean.getHeight(); row++){
            for(int col = 0; col < takeMean.getWidth(); col++){
                mean += takeMean.getElement(row, col);
            }
        }
        return (mean/(double)(takeMean.getHeight() * takeMean.getWidth()));
    }
}
