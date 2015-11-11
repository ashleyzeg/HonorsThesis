package TangoCompiler;


import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by azegiest on 11/4/15.
 */
public class TangoParser {
    public CodeGenerator codeGen = new CodeGenerator();
    public String classId;

    public Token[] tokens;
    public Token token = new Token("", 0, 0);
    private class Position {
        public int x;
    }

    Position tpos = new Position();


    //TODO: Create funciton that displays lineNumber, error message, and exits program
    public TangoParser(ArrayList<Token> tokenList) throws IOException {
        System.out.println("*** Welcome to the Tango Parser!!! ***");
        //converts arraylist into array
        tokens = new Token[tokenList.size()];
        tokens = tokenList.toArray(tokens);
        tpos.x = 0;

        //position of lastToken in array of tokens
        int lastToken = tokens.length - 1;

        parseProgram(tpos, lastToken);

        codeGen.fw.flush();
        codeGen.fw.close();

    }

    public void displayError (Position tpos, String errorMsg) {
        System.out.print("**Parse Error (Line: " + tokens[tpos.x].lineNumber + "), " + errorMsg);
        System.exit(0);
    }

    public void parseProgram (Position tpos, int lt) throws IOException {
        if(tokens[tpos.x].type == Token.KEYWORD && token.isClase(tokens[tpos.x].value) && tpos.x != lt) {
            tpos.x++;
        } else {
            displayError(tpos, "Expected keyword 'clase'");
        }

        if(tokens[tpos.x].type == Token.ID && tpos.x != lt) {
            classId = tokens[tpos.x].value;
            tpos.x++;
        } else {
            displayError(tpos, "Expected class identifier");
        }

        if(tokens[tpos.x].type == Token.OPEN_CB && tpos.x != lt) {
            tpos.x++;
        } else {
            displayError(tpos, "Expected open curly brace");
        }

        //should generate following java code: public class claseId {
        codeGen.writeClassStruct(classId);

        parseClassContents(tpos, lt);

        if(tokens[tpos.x].type == Token.CLOSE_CB && tpos.x == lt) {
            System.out.println("Program Successfully Parsed!");
        } else {
            displayError(tpos, "Expected end of program");
        }

        codeGen.fw.write("\n}");
    }

    public void parseClassContents(Position tpos, int lt) throws IOException{
        if(tokens[tpos.x].type == Token.KEYWORD && token.isFunc$(tokens[tpos.x].value) && tpos.x != lt) {
            tpos.x++;
            parseFuncMain(tpos, lt);
        } else {
            displayError(tpos, "Expected keyword func$");
        }
    }

    public void parseFuncMain(Position tpos, int lt) throws IOException {
        if (tokens[tpos.x].type == Token.KEYWORD && token.isPrincipal(tokens[tpos.x].value) && tpos.x != lt) {
            tpos.x++;
        } else {
            displayError(tpos, "Expected keyword principal");
        }

        if (tokens[tpos.x].type == Token.OPEN_PAREN && tpos.x != lt) {
            tpos.x++;
        } else {
            displayError(tpos, "Expected open paren");
        }

        if (tokens[tpos.x].type == Token.CLOSE_PAREN && tpos.x != lt) {
            tpos.x++;
        } else {
            displayError(tpos, "Expected close paren");
        }

        if (tokens[tpos.x].type == Token.OPEN_CB && tpos.x != lt) {
            tpos.x++;
        } else {
            displayError(tpos, "Expected open curly brace");
        }

        codeGen.writeMainStruct();

        parseStmtList(tpos, lt);

        if (tokens[tpos.x].type == Token.CLOSE_CB && tpos.x != lt) {
            tpos.x++;
        } else {
            displayError(tpos, "Expected close curly brace");
        }

        codeGen.fw.write("\n\t}");
    }

    public void parseStmtList(Position tpos, int lt) {
        if (tokens[tpos.x].type == Token.KEYWORD && token.isImprimirln(tokens[tpos.x].value) && tpos.x != lt) {
            tpos.x++;
        } else {
            displayError(tpos, "Expected keyword imprimirln");
        }

        if (tokens[tpos.x].type == Token.OPEN_PAREN && tpos.x != lt) {
            tpos.x++;
        } else {
            displayError(tpos, "Expected open paren");
        }

        parsePrintContent(tpos, lt);

        if (tokens[tpos.x].type == Token.CLOSE_PAREN && tpos.x != lt) {
            tpos.x++;
        } else {
            displayError(tpos, "Expected close paren");
        }

        if (tokens[tpos.x].type == Token.SEMI && tpos.x != lt) {
            tpos.x++;
        } else {
            displayError(tpos, "Expected semicolon");
        }

    }

    public void parsePrintContent(Position tpos, int lt) {
        //switch statement handles different productions
        switch (tokens[tpos.x].type) {
            //handles production printContent -> " string "
            case Token.DB_QUOTE: {
                if (tokens[tpos.x].type == Token.DB_QUOTE && tpos.x != lt) {
                    tpos.x++;
                } else {
                    displayError(tpos, "Expected double quotation");
                }

                if (tokens[tpos.x].type == Token.STRING && tpos.x != lt) {
                    tpos.x++;
                } else {
                    displayError(tpos, "Expected string value");
                }

                if (tokens[tpos.x].type == Token.DB_QUOTE && tpos.x != lt) {
                    tpos.x++;
                } else {
                    displayError(tpos, "Expected double quotation");
                }
                break;
            }

            //handles production printContent -> id
            case Token.ID: {
                if (tokens[tpos.x].type == Token.ID && tpos.x != lt) {
                    tpos.x++;
                } else {
                    displayError(tpos, "Expected id string or int value");
                }
                break;
            }

            default:
                System.out.println("**Parse Error ...");
        }
    }
}
