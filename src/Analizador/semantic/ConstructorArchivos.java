/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Analizador.semantic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Desarrollo
 */
public class ConstructorArchivos {

    public static void main(String[] args) {
        try {
            String camino = "./src/Analizador/semantic/c.flex";
            String[] caminoS = {"-parser", "Sintaxis", "./src/Analizador/semantic/sintactico.cup"};
            generarArchivos(camino, caminoS);
        } catch (Exception ex) {
            Logger.getLogger(ConstructorArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void generarArchivos(String camino, String[] caminoS) throws IOException, Exception {
        File arc;
        arc = new File(camino);
        JFlex.Main.generate(arc);
        java_cup.Main.main(caminoS);
        if(true) return;

        Path caminoSym = Paths.get("./src/Analizador/sym.java");
        if (Files.exists(caminoSym)) {
            Files.delete(caminoSym);
        }
        Files.move(
                Paths.get("./sym.java"),
                Paths.get("./src/Analizador/sym.java")
        );
        Path caminoSint = Paths.get("./src/Analizador/Sintaxis.java");
        if (Files.exists(caminoSint)) {
            Files.delete(caminoSint);
        }
        Files.move(
                Paths.get("./Sintaxis.java"),
                Paths.get("./src/Analizador/Sintaxis.java")
        );
    }
}
