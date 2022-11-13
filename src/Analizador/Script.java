/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Analizador;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;


//path :::  D:\Programas\MinGW\bin
public class Script {
    private File cppFile;
    private String compilerPath;
    private String projectPath;
    private String path = "D:\\Programas\\MinGW\\bin";
    private String fileName;
     
    public Script(File srcFile){
        this.compilerPath = "D:\\Programas\\MinGW\\bin";     
        this.projectPath = srcFile.getParent();
        this.cppFile = srcFile;
        this.fileName = cppFile.getName().split("\\.")[0];
    }
    
    private boolean bat(String script){
        StringBuilder bat = new StringBuilder();
        bat.append("cmd /C "); //abrir cmd        
        bat.append("start cmd.exe ");
        bat.append("/k \"cd " + projectPath + " && ");
        bat.append(script + " && exit \"");
        System.out.println(bat);
        try {
            Runtime.getRuntime().exec(bat.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    public boolean addPath(){
        if(this.path.equals("")||this.path == null){
            return false;
        }
        String bat = "";
        bat += "cmd /c start cmd.exe "; //abrir cmd
        bat += "/K \" set path=%path%;"+this.path+ " && exit";
        System.out.println(bat);
        try {
            Runtime.getRuntime().exec(bat);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /*
    public boolean intermedio(){
        String script = "cpp "+ fileName +".cpp > "+ fileName+".i";
        //+programaCppPath+ " > "+ programaIPath;
        //String script = "cpp Programa.cpp > Programa.i";
        return bat(script);
    }
    
    public boolean assambler(){
        String script = "g++ -Wall -S "+ fileName + ".i";
        //String script = "g++ -Wall -S "+programaIPath;
        return bat(script);
    }
*/
    
   /* public boolean objeto(){
        String script = "as "+ fileName +".s -o "+ fileName + ".o";
        //String script = "as -Wall -S " + ProgramaIPath;
        return bat(script);
    }*/
    
    public boolean ejecutable(){
        StringBuilder script = new StringBuilder();
        script.append("gcc -c " + cppFile.getPath() + " -o "+fileName+".o");
        script.append(" &&  gcc " + fileName + ".o -o " + fileName + ".exe");
        return bat(script.toString());
    }
    
    public boolean start(){
        String script = "start "+ fileName +".exe";
        return bat(script);        
    }
}
