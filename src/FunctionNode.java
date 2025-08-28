public class FunctionNode extends Node{
    private String funcName;
    private Node arg;

    public FunctionNode(String funcName, Node arg) {
        this.funcName = funcName;
        this.arg = arg;
    }
    @Override
    public double evaluate(double xValue) {
        switch (funcName) {
            case "sin":
                return Math.sin(arg.evaluate(xValue));
            case "cos":
                return Math.cos(arg.evaluate(xValue));
            case "tan":
                return Math.tan(arg.evaluate(xValue));
            default :
                throw new IllegalArgumentException("Unknown function: " + funcName);
        }
    }
}
