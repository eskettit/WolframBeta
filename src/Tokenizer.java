import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private final String expression;
    private int pos;


    public Tokenizer(String expression) {
        this.expression = expression.replaceAll("\\s+", ""); // remove whitespace
        this.pos = 0;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (pos < expression.length()) {
            char currentChar = expression.charAt(pos);

            if (Character.isDigit(currentChar) || currentChar == '.') {
                tokens.add(tokenizeNumber());
            } else if ("+-*/^".indexOf(currentChar) != -1) {
                tokens.add(tokenizeOperator());
            } else if (currentChar == '(') {
                tokens.add(new Token(Token.TokenType.PARENTHESIS_O, "("));
                pos++;
            } else if (currentChar == ')') {
                tokens.add(new Token(Token.TokenType.PARENTHESIS_C, ")"));
                pos++;
            } else if (Character.isLetter(currentChar)) {
                tokens.add(tokenizeVariableOrFunction());
            } else {
                throw new IllegalArgumentException("Invalid character: " + currentChar);
            }
        }
        tokens.add(new Token(Token.TokenType.END_OF_EXPRESSION, ""));
        return tokens;
    }

    // Handles numbers, operators, and variable names
    private Token tokenizeNumber() {
        int startPos = pos;
        while (pos < expression.length() && (Character.isDigit(expression.charAt(pos)) || expression.charAt(pos) == '.')) {
            pos++;
        }
        String value = expression.substring(startPos, pos);
        return new Token(Token.TokenType.NUMBER, value);
    }

    // Handles operators, including parentheses
    private Token tokenizeOperator() {
        String value = String.valueOf(expression.charAt(pos));
        pos++;
        return new Token(Token.TokenType.OPERATOR, value);
    }

    // Handles variable names and functions
    private Token tokenizeVariableOrFunction() {
        int startPos = pos;
        while (pos < expression.length() && Character.isLetter(expression.charAt(pos))) {
            pos++;
        }
        String name = expression.substring(startPos, pos);
        if ("sin".equals(name) || "cos".equals(name) || "tan".equals(name)) {
            return new Token(Token.TokenType.FUNCTION, name);
        } else if ("x".equals(name)) {
            return new Token(Token.TokenType.VARIABLE, name);
        } else {
            throw new IllegalArgumentException("Unknown variable or function: " + name);
        }
    }

}
