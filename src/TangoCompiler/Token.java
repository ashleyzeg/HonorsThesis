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

    //TODO: refactor token types and toString function
    //Token types

    //MISC TOKENS
    public static final int OPEN_PAREN = 5;
    public static final int CLOSE_PAREN = 6;
    public static final int OPEN_CB = 7;
    public static final int CLOSE_CB = 8;
    public static final int OPEN_BR = 16;
    public static final int CLOSE_BR = 17;
    public static final int S_QUOTE = 10;
    public static final int DB_QUOTE = 9;
    public static final int SEMI = 3;


    //EXPRESSION & COMPARISON TOKENS
    public static final int VAR = 4; //var and id may be able to be combined?
    public static final int ID = 12;
    public static final int OP = 1; //+, -, *, /, %
    public static final int ASSIGN = 2; //=
    public static final int COMP_OP = 18; // &&, ||, ==, !=, <, >, <=, >=

    //KEYWORD TOKENS
    public static final int KEYWORD = 11;

    //DATA TYPE TOKENS
    public static final int INT = 0;
    public static final int DOUBLE = 14;
    public static final int STRING = 13;


    //IF STATEMENT & LOOP TOKENS

    //CLASS & FUNCTION TOKENS

    //BASIC I/0 TOKENS

    //

    public static final String [] typename = {
      "INT", "OP", "ASSIGN", "SEMI", "VAR", "OPEN_PAREN", "CLOSE_PAREN", "OPEN_CB", "CLOSE_CB",
      "DB_QUOTE", "S_QUOTE", "KEYWORD", "ID", "STRING", "DOULBE", "?", "OPEN_BR", "CLOSE_BR", "COMP_OP"
    };

    //print function
    public String toString() {
        return "Token: " + value + " Type: " + typename[type];
    }

    public static final String[] keywords = {
            //class and function keywords
            "clase", "func$", "principal", "imprimirln",

            //data type keywords
            "ent", "dec", "cadena", "bool", "cierto", "falso", "nuevo"
    };

    private ArrayList<String> keywordList = new ArrayList<String>(Arrays.asList(keywords));

    public Boolean isKeyword(String t) {
        //returns true if keyword is in list, returns false if keyword is not in list
        return keywordList.contains(t);
    }

    //TODO: separate Keywords into keywords and library calls

    public Boolean isClase(String t) {return t.equals("clase");}
    public Boolean isFunc$(String t) {return t.equals("func$");}
    public Boolean isPrincipal(String t) {return t.equals("principal");}
    public Boolean isImprimirln(String t) {return t.equals("imprimirln");}



}
