package TangoCompiler;

/**
 * Created by azegiest on 10/13/15.
 */
public class Token {
    public String value, type;

    //Token constructor
    public Token (String val, String t) {
        value = val;
        type = t;
    }

    //Token types
    public static final String INVALID = "INVALID";

    public static final String INT = "INT";
    public static final String OP = "OP";
    public static final String ASSIGN = "ASSIGN";
    public static final String SEMI = "SEMI";
    public static final String VAR = "VAR";
    public static final String OPEN_PAREN = "OPEN_PAREN";
    public static final String CLOSE_PAREN = "CLOSE_PAREN";
    public static final String OPEN_CB = "OPEN_CB";
    public static final String CLOSE_CB = "CLOSE_CB";
    public static final String END_LINE = "END_LINE";

    //print function
    public String toString() {
        return "Token: " + value + " Type: " + type;
    }

}
