package TangoCompiler;

import java.util.*;

/**
 * Created by azegiest on 10/13/15.
 */

public class Token {
    public String value;
    public int type;
    public int lineNumber;

    //Token constructor
    public Token (String val, int t, int ln) {
        value = val;
        type = t;
        lineNumber = ln;
    }

    //Token types
    public static final int INT = 0;
    public static final int OP = 1;
    public static final int ASSIGN = 2;
    public static final int SEMI = 3;
    public static final int VAR = 4;
    public static final int OPEN_PAREN = 5;
    public static final int CLOSE_PAREN = 6;
    public static final int OPEN_CB = 7;
    public static final int CLOSE_CB = 8;
    public static final int DB_QUOTE = 9;
    public static final int S_QUOTE = 10;
    public static final int KEYWORD = 11;
    public static final int ID = 12;
    public static final int STRING = 13;

    public static final String [] typename = {
      "INT", "OP", "ASSIGN", "SEMI", "VAR", "OPEN_PAREN", "CLOSE_PAREN", "OPEN_CB", "CLOSE_CB",
      "DB_QUOTE", "S_QUOTE", "KEYWORD", "ID", "STRING"
    };

    //print function
    public String toString() {
        return "Token: " + value + " Type: " + typename[type];
    }

    public static final String[] keywords = {"clase", "func$", "principal", "imprimirln"};

    public Boolean isKeyword(String t) {
        ArrayList<String> keywordList = new ArrayList<String>(Arrays.asList(keywords));
        //returns true if keyword is in list, returns false if keyword is not in list
        return keywordList.contains(t);
    }

    //TODO: separate Keywords into keywords and library calls

    public Boolean isClase(String t) {return t.equals("clase");}
    public Boolean isFunc$(String t) {return t.equals("func$");}
    public Boolean isPrincipal(String t) {return t.equals("principal");}
    public Boolean isImprimirln(String t) {return t.equals("imprimirln");}



}
