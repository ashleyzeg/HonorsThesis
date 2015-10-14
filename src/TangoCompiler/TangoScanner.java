package TangoCompiler;

import java.io.*;
import java.util.*;

/**
 * Created by azegiest on 10/13/15.
 */
public class TangoScanner {
    //public Token [] tokens;
    public ArrayList<Token> tokens = new ArrayList<>();

    public TangoScanner(File input) throws FileNotFoundException {
        System.out.println("*** Welcome to the Tango Scanner!!! ***");

        Scanner program = new Scanner(new FileReader(input));
        String line;
        while(program.hasNextLine()) {
            line = program.nextLine().trim();
            scan(line);
            //append end of line character to array
            tokens.add(new Token("*****", "END_LINE"));
//            for (Token t: tokens)
//                if (t != null) System.out.println(t);
        }
        for (Token t: tokens)
            if (t != null) System.out.println(t);
    }

    //scans for valid tokens and appends them to the tokens array list
    //when the end of a line is reached, a new line character is placed into the arraylist
    public void scan(String line) {
        for(int i=0; i<line.length(); i++) {
            char c = line.charAt(i);

            //open curly brace
            if (c == '{' )
                tokens.add(new Token(""+c, "OPEN_CB"));
            //close curly brace
            else if (c == '}' )
                tokens.add(new Token(""+c, "CLOSE_CB"));
            //open parenthesis
            else if (c == '(' )
                tokens.add(new Token(""+c, "OPEN_PAREN"));
            //close parenthesis
            else if (c == ')' )
                tokens.add(new Token(""+c, "CLOSE_PAREN"));
            //semi colon
            else if (c == ';')
                tokens.add(new Token(""+c, "SEMI"));
            //assignment operator
            else if (c == '=')
                tokens.add(new Token(""+c, "ASSIGN"));
            //operators
            else if (c == '+' || c == '-' || c == '*' || c == '/')
                tokens.add(new Token(""+c, "OP"));
            //continue if blank space or tab
            else if (c == ' ' || c == '\t')
                continue;
            //invalid token
            else
                System.out.println("**ERROR: Invalid Token: '" + c + "' Position " + i + "\n" +line);


        }

    }
}
