package TangoCompiler;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by azegiest on 10/13/15.
 */
public class tangoCompiler {
    public static void main(String [] args) throws IOException {

        System.out.println("*** Welcome to the Tango Compiler!!! ***");
        System.out.println("Enter the file you would like to compile and run:");

        Scanner in = new Scanner(System.in);
        File inFile = new File("");
        File completePath = new File(inFile.getAbsolutePath() + "/src/TangoCompiler", in.nextLine());

        if (completePath.exists() && !completePath.isDirectory()) {
            inFile = completePath;
        } else {
            System.out.println("Invalid file name, or file does not exist!");
            System.exit(0);
        }

        TangoScanner tScanner = new TangoScanner(inFile);

        TangoParser tParser = new TangoParser(tScanner.tokens);

        //CodeGenerator codeGen = new CodeGenerator(tParser.classId);
    }
}
