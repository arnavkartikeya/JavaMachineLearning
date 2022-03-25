import java.util.ArrayList;

public class NeuralNetwork {
    ArrayList<NetworkLayer> layers = new ArrayList<>();
    //iterates through the layers and feed forward
    public Matrix feedForward(Matrix originalInput){
        Matrix next;
        next = layers.get(0).feedForward(originalInput);
        for(int i = 0; i < layers.size() - 2; i++){
            next = layers.get(i).feedForward(next);
            //activate this layer

        }
        next = layers.get(layers.size() -1).activation.activate(next);
        return next;

    }

    public void addLayer(NetworkLayer n){
        layers.add(n);
    }
}
