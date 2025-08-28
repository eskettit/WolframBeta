public class Token {

    public enum TokenType {
        NUMBER,
        OPERATOR,
        FUNCTION,
        VARIABLE,
        PARENTHESIS_O,
        PARENTHESIS_C,
        END_OF_EXPRESSION
    }

    private final TokenType type;
    private final String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {

        return value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }

}
