// Class for storing a number
public class NumberNode extends Node {
    private final double value;

    public NumberNode(double value) {
        this.value = value;
    }

    @Override
    public double evaluate(double xValue) {
        return value;
    }
}