public class variableNode extends Node{
    private final String name;

    public variableNode(String name) {
        this.name = name;
    }

    @Override
    public double evaluate(double xValue) {
        if (name.equals("x")) {
            return xValue;
        } else {
            throw new IllegalArgumentException("Unknown variable: " + name);
        }
    }
}
