package TangoCompiler;


import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by azegiest on 11/4/15.
 */
public class TangoParser {
    private CodeGenerator codeGen = new CodeGenerator();

    private Token[] tokens;
    private Token token = new Token("", 0, 0);
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

        codeGen.closeFileWriter();

    }

    public void displayError (Position tpos, String errorMsg) {
        System.out.print("**Parse Error (Line: " + tokens[tpos.x].lineNumber + "), " + errorMsg);
        System.exit(0);
    }

    //PRODUCTION: program --> clase id accessMod { classContents }
    public void parseProgram (Position tpos, int lt) throws IOException {
        String classId = "";
        String accessModVal = "";

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

        parseAccessMod(tpos, lt);
        accessModVal = tokens[tpos.x-1].value;

        if(tokens[tpos.x].type == Token.OPEN_CB && tpos.x != lt) {
            tpos.x++;
        } else {
            displayError(tpos, "Expected open curly brace");
        }

        //should create file
        codeGen.createFileWriter(classId);

        //should generate following java code: public class claseId {
        codeGen.writeClassStruct(classId, accessModVal);

        parseClassContents(tpos, lt);

        if(tokens[tpos.x].type == Token.CLOSE_CB && tpos.x == lt) {
            System.out.println("Program Successfully Parsed!");
        } else {
            displayError(tpos, "Expected end of program");
        }

        codeGen.fw.write("\n}");
    }

    public void parseAccessMod(Position tpos, int lt) throws IOException {
        if(tokens[tpos.x].type == Token.KEYWORD && token.isPública(tokens[tpos.x].value) && tpos.x != lt) {
            tpos.x++;
        } else {
            displayError(tpos, "Access modifier expected");
        }
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

        //token array index is increased by 1 in the parseStmtList function once stmtList --> E occurs and '}' is found

        codeGen.fw.write("\n\t}");
    }

    public void parseStmtList(Position tpos, int lt) throws IOException {
        //stmt --> stmt stmtList
        if (tokens[tpos.x].type == Token.KEYWORD
                && (token.isImprimirln(tokens[tpos.x].value) || token.isDataType(tokens[tpos.x].value))
                && tpos.x != lt) {
            parseStmt(tpos, lt);
            parseStmtList(tpos, lt);
        }

        //stmt --> E
        else if (tokens[tpos.x].type == Token.CLOSE_CB && tpos.x != lt) {
            tpos.x++;
        } else {
            displayError(tpos, "stmtList error");
        }

    }

    public void parseStmt(Position tpos, int lt) throws IOException {
        //stmt --> imprimirln ( printContent );
        if (tokens[tpos.x].type == Token.KEYWORD && token.isImprimirln(tokens[tpos.x].value) && tpos.x != lt) {
            tpos.x++;

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

        //stmt --> dataType id decTail;
        else if (tokens[tpos.x].type == Token.KEYWORD && token.isDataType(tokens[tpos.x].value) && tpos.x != lt) {

            parseDataType(tpos, lt);

            if (tokens[tpos.x].type == Token.ID && tpos.x != lt) {
                //store id name and value
                tpos.x++;
                System.out.println("need to store id and value");
            } else {
                displayError(tpos, "Expected id");
            }

            parseDecTail(tpos, lt);

            if (tokens[tpos.x].type == Token.SEMI) {
                tpos.x++;
            } else {
                displayError(tpos, "Expected semi-colon");
            }
        }
    }

    public void parseDataType(Position tpos, int lt) throws IOException {
        //dataType --> ent | dec | cadena | bool
        if (token.isDataType(tokens[tpos.x].value)) {
            tpos.x++;
        }
    }

    public void parseDecTail(Position tpos, int lt) throws IOException {
        //decTail --> = expr
        if (tokens[tpos.x].type == Token.ASSIGN && tpos.x != lt) {
            tpos.x++;
        }

        parseExpr(tpos, lt);
    }

    public void parseExpr(Position tpos, int lt) throws  IOException {
        //expr --> boolOp
        if (tokens[tpos.x].type == Token.KEYWORD && token.isBool(tokens[tpos.x].value)) {
            tpos.x++;
        }
    }

    public void parsePrintContent(Position tpos, int lt) throws IOException {
        //switch statement handles different productions
        String stringCons = "";
        switch (tokens[tpos.x].type) {
            //handles production printContent -> " string "
            case Token.DB_QUOTE: {
                if (tokens[tpos.x].type == Token.DB_QUOTE && tpos.x != lt) {
                    tpos.x++;
                } else {
                    displayError(tpos, "Expected double quotation");
                }

                if (tokens[tpos.x].type == Token.STRING && tpos.x != lt) {
                    stringCons = tokens[tpos.x].value;
                    tpos.x++;
                } else {
                    displayError(tpos, "Expected string value");
                }

                if (tokens[tpos.x].type == Token.DB_QUOTE && tpos.x != lt) {
                    tpos.x++;
                } else {
                    displayError(tpos, "Expected double quotation");
                }

                codeGen.writePrintStmt(stringCons);
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
