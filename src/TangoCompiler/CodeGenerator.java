package TangoCompiler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by azegiest on 11/10/15.
 * Creates an output file and then generates the corresponding java code
 */
public class CodeGenerator {

    public CodeGenerator() throws IOException {
        System.out.println("*** Welcome to the Code Generator!!! ***");

        //create output text file
        File file = new File("test.java");
        file.createNewFile();

        //creates FileWriter Object
        FileWriter fw = new FileWriter(file);

        //writes content to file
        fw.write("something");
        fw.flush();
        fw.close();

    }

    //methods to write specific code chunks to file where do these methods go?
    public  String getFileName(String filename) {
        return filename;
    }
}
