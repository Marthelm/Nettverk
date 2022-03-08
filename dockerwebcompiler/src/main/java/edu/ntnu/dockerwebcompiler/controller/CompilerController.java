package edu.ntnu.dockerwebcompiler.controller;
import edu.ntnu.dockerwebcompiler.service.FileHandler;

import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;


import java.io.*;
import java.util.Map;

@RestController
@CrossOrigin
public class CompilerController {

    @PostMapping("/compile")
    public String run(@RequestBody Map<String, String> codeinput) throws IOException {
        FileHandler fileHandler = new FileHandler();
        File file = new File("C:/Users/motoc/nettverk/dockerweb/dockerwebcompiler/src/main/java/edu/ntnu/dockerwebcompiler/docker/tempFolder");
        try{
            file.mkdir();
        } catch (Exception e ){
            e.printStackTrace();
        }
        File codeFile = new File("C:/Users/motoc/nettverk/dockerweb/dockerwebcompiler/src/main/java/edu/ntnu/dockerwebcompiler/docker/tempFolder/temp.py");
        fileHandler.writeToFile(codeFile, codeinput.get("codeinput"));



        Runtime.getRuntime().exec("docker rmi dockerwebcompiler");
        Process p = Runtime.getRuntime().exec("docker build C:\\Users\\motoc\\nettverk\\dockerweb\\dockerwebcompiler\\src\\main\\java\\edu\\ntnu\\dockerwebcompiler\\docker\\ -t dockerwebcompiler");
        BufferedReader buildInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader buildError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        String b;
        while((b = buildInput.readLine())!= null){
            System.out.println(b);
        }
        while((b = buildError.readLine()) != null){
            System.out.println(b);
        }

        FileSystemUtils.deleteRecursively(file);

        Process pro = Runtime.getRuntime().exec("docker run --rm dockerwebcompiler");

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(pro.getInputStream()));
        BufferedReader errInput = new BufferedReader(new InputStreamReader(pro.getErrorStream()));

        StringBuilder output = new StringBuilder();
        StringBuilder outputError = new StringBuilder();

        //Read output from the command
        String s;
        while ((s = stdInput.readLine()) != null){
            System.out.println(s);
            output.append(s).append("\n");
        }

        //Read any errors from attempted command
        while((s = errInput.readLine()) != null){
            System.out.println(s);
            outputError.append(s);
        }

        String outputString = output.toString();
        String outputErrorString = outputError.toString();

        if(outputErrorString.length() != 0) return outputErrorString;
        return outputString;
    }
    @GetMapping("/Hello")
    public String Hello(){
        return "hello";
    }

}


