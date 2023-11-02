package com.bo.upb.algoritmica1.ui;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ArchivoPruebas {

    public static String readContent(String pathFile) {
        String content= null;
        try {
            content = FileUtils.readFileToString(new File(pathFile), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content;
    }
    public static List<String> readContentLines(String pathFile) {
        List<String> lines= null;
        try {
            lines = FileUtils.readLines(new File(pathFile), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static boolean writeContent(String pathFile, String content){
        try {
            FileUtils.writeStringToFile(new File(pathFile), content, StandardCharsets.UTF_8);
        return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean writeContentLines(String pathFile, List<String> contentLines){
        try {
            FileUtils.writeLines(new File(pathFile), StandardCharsets.UTF_8.name(), contentLines);//El name devuelve el contenido del UTF8
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //TODO TAREA,


    public static void main(String[] args) {
        String pathFile = "C:\\Users\\saulm\\Downloads\\arbol-datos.txt";
        System.out.println(readContent(pathFile));
        System.out.println();
//        System.out.println(readContentLines(pathFile));

        //TODO Guardar contenido

//        String content= readContent(pathFile);
//        writeContent(pathFile, content + "\nFin");

//        List<String> contentLines= readContentLines(pathFile);
//        contentLines.add("Fin2");
//        writeContentLines(pathFile, contentLines);


        //TODO --------------------------------------------

//        File file= new File(pathFile);
//        if (!file.exists()){
//            file.mkdirs();
//        }
//        System.out.println("Existe: " + file.exists());
    }
}
