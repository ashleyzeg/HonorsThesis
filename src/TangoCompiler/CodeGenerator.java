package TangoCompiler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by azegiest on 11/10/15.
 * Creates an output file and then generates the corresponding java code
 */
public class CodeGenerator {
    public static FileWriter fw;
    public static File file;
    private static Hashtable<String, String> dictionary = new Hashtable<String, String>();

    //constructor
    public CodeGenerator() throws IOException {
        System.out.println("*** Welcome to the Code Generator!!! ***");
        buildDictionary();
    }

    //helper functions
    public static void createFileWriter(String filename) throws IOException {
        file = new File(filename + ".java");
        fw = new FileWriter(file);
    }

    public static void closeFileWriter() throws IOException {
        fw.flush();
        fw.close();
    }

    //key = spanish, value = english
    public static void buildDictionary() {
        //preload keyword values
        dictionary.put("públic@", "public");
        dictionary.put("ent", "int");
        dictionary.put("dec", "double");
        dictionary.put("cadena", "String");
        dictionary.put("bool", "Boolean");
        dictionary.put("ciert@", "true");
        dictionary.put("fals@", "false");
        dictionary.put("nuev@", "new");
        dictionary.put("si", "if");
        dictionary.put("sino", "else");
        dictionary.put("para", "for");
        dictionary.put("mientras", "while");
        dictionary.put("hacer", "do");
        dictionary.put("clase", "class");
        dictionary.put("estátic@", "static");
        dictionary.put("vaci@", "void");
        dictionary.put("nul@", "null");
        dictionary.put("regresar", "return");
        dictionary.put("escáner", "Scanner");
        dictionary.put("sigEnt", "nextInt");
    }

    //translate/convert spanish keyword to english equivalent
    public static String translate(String keyword) {
        return dictionary.get(keyword);
    }

    public static void writeClassStruct(String classId, String accessMod) throws IOException {
        accessMod = translate(accessMod);
        fw.write(accessMod + " class " + classId + " {");
    }

    public static void writeMainStruct() throws IOException {
        fw.write("\npublic static void main(String [] args) {");
    }

    public static void writePrintStmt(String s) throws IOException {
        fw.write("\nSystem.out.println(\"" + s + "\");");
    }

    //variable declaration writer helpers
    public static void writeDataTypeAndId(String dataType, String id) throws IOException {
        dataType = translate(dataType);
        fw.write(dataType + " " + id + " ");
    }

    public static void writeDecTail() throws IOException {
        fw.write("=");
    }

    public static void writeBoolVal(String boolVal) throws IOException {
        fw.write(translate(boolVal) + " ");
    }

    public static void writeSemiColon() throws IOException {
        fw.write(";");
    }
}
