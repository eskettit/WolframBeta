import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Node parse() {
        return parseExpression();
    }

    private Token peek() {
        if (pos < tokens.size()) {
            return tokens.get(pos);
        }
        return tokens.get(tokens.size() - 1);
    }

    private Token consume() {
        return tokens.get(pos++);
    }

    // handles '+' and '-' operators
    private Node parseExpression() {
        Node left = parseTerm();
        while (peek().getType() == Token.TokenType.OPERATOR && (peek().getValue().equals("+") || peek().getValue().equals("-"))) {
            Token operator = consume();
            Node right = parseTerm();
            left = new BinaryOpNode(left, operator.getValue(), right);
        }
        return left;
    }

    // handles '*' and '/' operators
    private Node parseTerm() {
        Node left = parsePower();
        while (peek().getType() == Token.TokenType.OPERATOR && (peek().getValue().equals("*") || peek().getValue().equals("/"))) {
            Token operator = consume();
            Node right = parsePower();
            left = new BinaryOpNode(left, operator.getValue(), right);
        }
        return left;
    }

    // handles '^' operator
    private Node parsePower() {
        Node left = parseFactor();

        if (peek().getType() == Token.TokenType.OPERATOR && peek().getValue().equals("^")) {
            Token operator = consume();
            Node right = parsePower();
            return new BinaryOpNode(left, operator.getValue(), right);
        }
        return left;
    }

    private Node parseFactor() {
        Token token = peek();

        // handles unary minus
        if (token.getType() == Token.TokenType.OPERATOR && token.getValue().equals("-")) {
            consume(); // unary minus
            Node right = parseFactor();
            return new BinaryOpNode(new NumberNode(0), "-", right);
        }
        // handes unary plus
        if (token.getType() == Token.TokenType.OPERATOR && token.getValue().equals("+")) {
            consume(); // unary plus
            return parseFactor();
        }

        token = consume();
        switch (token.getType()) {
            case NUMBER:
                return new NumberNode(Double.parseDouble(token.getValue()));
            case VARIABLE:
                return new variableNode(token.getValue());
            case PARENTHESIS_O:
                Node node = parseExpression();
                if (peek().getType() != Token.TokenType.PARENTHESIS_C) {
                    throw new IllegalArgumentException("Mismatched parentheses");
                }
                consume(); // closing parenthesis
                return node;
            case FUNCTION:
                if (peek().getType() != Token.TokenType.PARENTHESIS_O) {
                    throw new IllegalArgumentException("Function must be followed by parentheses");
                }
                consume(); // open parenthesis
                Node arg = parseExpression();
                if (peek().getType() != Token.TokenType.PARENTHESIS_C) {
                    throw new IllegalArgumentException("Mismatched parentheses");
                }
                consume(); // closing parenthesis
                return new FunctionNode(token.getValue(), arg);
            default:
                throw new IllegalArgumentException("Unexpected token: " + token.getValue());
        }
    }
}