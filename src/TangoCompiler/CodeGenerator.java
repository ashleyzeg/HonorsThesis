package TangoCompiler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by azegiest on 11/10/15.
 * Creates an output file and then generates the corresponding java code
 */
public class CodeGenerator {
    public FileWriter fw;
    File file;

    public CodeGenerator() throws IOException {
        System.out.println("*** Welcome to the Code Generator!!! ***");

        //TODO: figure out how to pass in claseId as filename
        //create output text file
        file = new File("test.java");
        file.createNewFile();

        //creates FileWriter Object
        fw = new FileWriter(file);

    }

    //methods to write specific code chunks to file where do these methods go?
//    public File createFile(String filename) throws IOException {
//        file = new File(filename + ".java");
//        file.createNewFile();
//        return file;
//    }

    public void writeClassStruct(String classId) throws IOException {
        fw.write("public class " + classId + " {");
    }

    public void writeMainStruct() throws IOException {
        fw.write("\n\tpublic static void main(String [] args) {");
    }
}
