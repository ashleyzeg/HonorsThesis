package TangoCompiler;


import java.util.ArrayList;

/**
 * Created by azegiest on 11/4/15.
 */
public class TangoParser {
    public Token[] tokens;
    public Token token = new Token("", 0, 0);
    private class Position {
        public int x;
    }

    Position tpos = new Position();


    //TODO: Create funciton that displays lineNumber, error message, and exits program
    public TangoParser(ArrayList<Token> tokenList) {
        System.out.println("*** Welcome to the Tango Parser!!! ***");
        //converts arraylist into array
        tokens = new Token[tokenList.size()];
        tokens = tokenList.toArray(tokens);
        tpos.x = 0;

        //position of lastToken in array of tokens
        int lastToken = tokens.length - 1;

        parseProgram(tpos, lastToken);

    }

    public void parseProgram (Position tpos, int lt) {
        if(tokens[tpos.x].type == Token.KEYWORD && token.isClase(tokens[tpos.x].value) && tpos.x != lt) {
            tpos.x++;
        } else {
            System.out.print("**Parse Error (Line: " + tokens[tpos.x].lineNumber + "), Expected keyword 'clase'");
            System.exit(0);
        }

        if(tokens[tpos.x].type == Token.ID && tpos.x != lt) {
            tpos.x++;
        } else {
            System.out.print("**Parse Error (Line: " + tokens[tpos.x].lineNumber + "), Expected class identifier");
            System.exit(0);
        }

        if(tokens[tpos.x].type == Token.OPEN_CB && tpos.x != lt) {
            tpos.x++;
        } else {
            System.out.print("**Parse Error (Line: " + tokens[tpos.x].lineNumber + "), Expected open curly brace");
            System.exit(0);
        }

        parseClassContents(tpos, lt);

        if(tokens[tpos.x].type == Token.CLOSE_CB && tpos.x == lt) {
            System.out.println("Program Successfully Parsed!");
        } else {
            System.out.print("**Parse Error (Line: " + tokens[tpos.x].lineNumber + "), Expected end of program");
            System.exit(0);
        }
    }

    public void parseClassContents(Position tpos, int lt) {
        if(tokens[tpos.x].type == Token.KEYWORD && token.isFunc$(tokens[tpos.x].value) && tpos.x != lt) {
            tpos.x++;
            parseFuncMain(tpos, lt);
        } else {
            System.out.println("**Parse Error (Line: " + tokens[tpos.x].lineNumber + "), Expected keyword func$");
            System.exit(0);
        }
    }

    public void parseFuncMain(Position tpos, int lt) {
        if (tokens[tpos.x].type == Token.KEYWORD && token.isPrincipal(tokens[tpos.x].value) && tpos.x != lt) {
            tpos.x++;
        } else {
            System.out.println("**Parse Error (Line: " + tokens[tpos.x].lineNumber + "), Expected keyword principal");
            System.exit(0);
        }

        if (tokens[tpos.x].type == Token.OPEN_PAREN && tpos.x != lt) {
            tpos.x++;
        } else {
            System.out.println("**Parse Error (Line: " + tokens[tpos.x].lineNumber + "), Expected open paren");
            System.exit(0);
        }

        if (tokens[tpos.x].type == Token.CLOSE_PAREN && tpos.x != lt) {
            tpos.x++;
        } else {
            System.out.println("**Parse Error (Line: " + tokens[tpos.x].lineNumber + "), Expected close paren");
            System.exit(0);
        }

        if (tokens[tpos.x].type == Token.OPEN_CB && tpos.x != lt) {
            tpos.x++;
        } else {
            System.out.println("**Parse Error (Line: " + tokens[tpos.x].lineNumber + "), Expected open curly brace");
            System.exit(0);
        }

        parseStmtList(tpos, lt);

        if (tokens[tpos.x].type == Token.CLOSE_CB && tpos.x != lt) {
            tpos.x++;
        } else {
            System.out.println("**Parse Error (Line: " + tokens[tpos.x].lineNumber + "), Expected close curly brace");
            System.exit(0);
        }
    }

    public void parseStmtList(Position tpos, int lt) {
        if (tokens[tpos.x].type == Token.KEYWORD && token.isImprimirln(tokens[tpos.x].value) && tpos.x != lt) {
            tpos.x++;
        } else {
            System.out.println("**Parse Error (Line: " + tokens[tpos.x].lineNumber + "), Expected keyword imprimirln");
            System.exit(0);
        }

        if (tokens[tpos.x].type == Token.OPEN_PAREN && tpos.x != lt) {
            tpos.x++;
        } else {
            System.out.println("**Parse Error (Line: " + tokens[tpos.x].lineNumber + "), Expected open paren");
            System.exit(0);
        }

        parsePrintContent(tpos, lt);

        if (tokens[tpos.x].type == Token.CLOSE_PAREN && tpos.x != lt) {
            tpos.x++;
        } else {
            System.out.println("**Parse Error (Line: " + tokens[tpos.x].lineNumber + "), Expected close paren");
            System.exit(0);
        }

        if (tokens[tpos.x].type == Token.SEMI && tpos.x != lt) {
            tpos.x++;
        } else {
            System.out.println("**Parse Error (Line: " + tokens[tpos.x].lineNumber + "), Expected semicolon");
            System.exit(0);
        }

    }

    //only handles production printContent -> " stringValue "
    //TODO: add production printContent -> id
    public void parsePrintContent(Position tpos, int lt) {
        if (tokens[tpos.x].type == Token.DB_QUOTE && tpos.x != lt) {
            tpos.x++;
        } else {
            System.out.println("**Parse Error (Line: " + tokens[tpos.x].lineNumber + "), Expected double quotation");
            System.exit(0);
        }

        if (tokens[tpos.x].type == Token.ID && tpos.x != lt) {
            tpos.x++;
        } else {
            System.out.println("**Parse Error (Line: " + tokens[tpos.x].lineNumber + "), Expected string value");
            System.exit(0);
        }

        if (tokens[tpos.x].type == Token.DB_QUOTE && tpos.x != lt) {
            tpos.x++;
        } else {
            System.out.println("**Parse Error (Line: " + tokens[tpos.x].lineNumber + "), Expected double quotation");
            System.exit(0);
        }
    }
}
