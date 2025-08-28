// Class for binary operations
public class BinaryOpNode extends Node {
    private final Node left;
    private final Node right;
    private final String operator;

    public BinaryOpNode(Node left, String operator, Node right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public double evaluate(double xValue) {
        double leftValue = left.evaluate(xValue);
        double rightValue = right.evaluate(xValue);

        switch (operator) {
            case "+":
                return leftValue + rightValue;
            case "-":
                return leftValue - rightValue;
            case "*":
                return leftValue * rightValue;
            case "/":
                return leftValue / rightValue;
            case "^":
                return Math.pow(leftValue, rightValue);
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }
}