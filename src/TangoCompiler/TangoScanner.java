package TangoCompiler;

import java.io.*;
import java.util.*;

/**
 * Created by azegiest on 10/13/15.
 */
public class TangoScanner {
    public ArrayList<Token> tokens = new ArrayList<>();
    public Token tokenObj = new Token("", 0, 0);

    public TangoScanner(File input) throws FileNotFoundException {
        System.out.println("*** Welcome to the Tango Scanner!!! ***");

        Scanner program = new Scanner(new FileReader(input));
        String line;
        int lineNumber = 0;
        while(program.hasNextLine()) {
            line = program.nextLine().trim();
            lineNumber = lineNumber + 1;
            scan(line, lineNumber);
        }
        for (Token t: tokens)
            if (t != null) System.out.println(t);
    }

    //scans for valid tokens and appends them to the tokens array list
    //when the end of a line is reached, a new line character is placed into the arraylist
    public void scan(String line, int lineNumber) {
        for(int i=0; i<line.length(); i++) {
            char c = line.charAt(i);

            //id, keyword, or library call
            if ( c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
                int j;
                for( j=i; j<line.length() && (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '$'); j++) {
                    c=line.charAt(j);
                }
                if (tokenObj.isKeyword(line.substring(i,j-1)))
                    tokens.add(new Token(line.substring(i,j-1), Token.KEYWORD, lineNumber));
                else
                    tokens.add(new Token(line.substring(i,j-1), Token.ID, lineNumber));
                i=j-2;
            }
            //open curly brace
            else if (c == '{' )
                tokens.add(new Token(""+c, Token.OPEN_CB, lineNumber));
            //close curly brace
            else if (c == '}' )
                tokens.add(new Token(""+c, Token.CLOSE_CB, lineNumber));
            //open parenthesis
            else if (c == '(' )
                tokens.add(new Token(""+c, Token.OPEN_PAREN, lineNumber));
            //close parenthesis
            else if (c == ')' )
                tokens.add(new Token(""+c, Token.CLOSE_PAREN, lineNumber));
            //semi colon
            else if (c == ';')
                tokens.add(new Token(""+c, Token.SEMI, lineNumber));
            //assignment operator
            else if (c == '=')
                tokens.add(new Token(""+c, Token.ASSIGN, lineNumber));
            //operators
            else if (c == '+' || c == '-' || c == '*' || c == '/')
                tokens.add(new Token(""+c, Token.OP, lineNumber));
            //double quote
            else if (c == '"') {
                tokens.add(new Token("" + c, Token.DB_QUOTE, lineNumber));
                int k;
                int prevNumTokens = tokens.size();
                for (k = i+1; k < line.length(); k++) {
                    c = line.charAt(k);
                    if (c == '"') {
                        tokens.add(new Token(line.substring(i+1, k), Token.STRING, lineNumber));
                        tokens.add(new Token(""+c, Token.DB_QUOTE, lineNumber));
                        break;
                    }
                }
                int curNumTokens = tokens.size();
                if (prevNumTokens == curNumTokens) {
                    System.out.println("**ERROR (Line: " + lineNumber + "), Missing closing double quote \n" + line);
                    System.exit(0);
                }
                i=k;
            }
            //single quote
            else if (c == '\'')
                tokens.add(new Token(""+c, Token.S_QUOTE, lineNumber));
            //continue if blank space or tab
            else if (c == ' ' || c == '\t')
                continue;
            //invalid token
            else {
                System.out.println("**ERROR (Line: " + lineNumber + "), Invalid Token: '" + c + "' \n" + line);
                System.exit(0);
            }
        }

    }
}
