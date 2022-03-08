package edu.ntnu.dockerwebcompiler.service;

import java.io.*;

public class FileHandler {
    public void writeToFile(File file, String input){
        String[] lines = input.split("\"\\r?\\n|\\r");

        for(int i = 0; i < lines.length; i++){
            System.out.println(lines[i]);
        }

        try(OutputStream os = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(os))){
                for(int i = 0; i < lines.length; i++){
                    pw.write(lines[i] + "\n");
                }
            }catch (Exception e ){
            e.printStackTrace();
        }
    }
}
