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
    public File file;

    //constructor
    public CodeGenerator() throws IOException {
        System.out.println("*** Welcome to the Code Generator!!! ***");
    }

    //helper functions
    public void createFileWriter(String filename) throws IOException {
        file = new File(filename + ".java");
        fw = new FileWriter(file);
    }

    public void closeFileWriter() throws IOException {
        fw.flush();
        fw.close();
    }

    public void writeClassStruct(String classId) throws IOException {
        fw.write("public class " + classId + " {");
    }

    public void writeMainStruct() throws IOException {
        fw.write("\npublic static void main(String [] args) {");
    }

    public void writePrintStmt(String s) throws IOException {
        fw.write("\nSystem.out.println(\"" + s + "\");");
    }
}
