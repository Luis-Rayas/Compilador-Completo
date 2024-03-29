package Analizador;

import Analizador.semantic.AnalizadorSemantico;
import Analizador.semantic.LexerSemantic;
import Analizador.semantic.Parser;
import helpers.Table;
import helpers.TableQuad;
import helpers.TreeNode;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.runtime.Symbol;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmAnalizador extends javax.swing.JFrame {

    private File srcFile;
    private Script script;
    private ArrayList<String>  identificadores;
    private ArrayList<String>  warnings;
    

    public FrmAnalizador() {
        initComponents();
                
        deshabilitarSintactico();
        deshabilitarSemantico();
        bloquarEjecutable();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BotonAnalisis = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Resultado = new javax.swing.JTextArea();
        BotonSintactico = new javax.swing.JButton();
        btnArchivo = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAnalizarSin = new javax.swing.JTextArea();
        Borrar1 = new javax.swing.JButton();
        btnBorrarSemantico = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnEjecutar = new javax.swing.JButton();
        btnCompilar = new javax.swing.JButton();
        jBSave = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tb_tokens = new javax.swing.JTable();
        btnSemantico = new javax.swing.JButton();
        btnSintacticoBorrar = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtSemantico = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Luis Roberto Carlos Reyes Rayas");

        BotonAnalisis.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        BotonAnalisis.setText("Análisis Léxico");
        BotonAnalisis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAnalisisActionPerformed(evt);
            }
        });

        Resultado.setColumns(20);
        Resultado.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        Resultado.setRows(5);
        jScrollPane1.setViewportView(Resultado);

        BotonSintactico.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        BotonSintactico.setText("Análisis Sintático");
        BotonSintactico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonSintacticoActionPerformed(evt);
            }
        });

        btnArchivo.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnArchivo.setText("Abrir Fuente");
        btnArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArchivoActionPerformed(evt);
            }
        });

        txtAnalizarSin.setEditable(false);
        txtAnalizarSin.setColumns(20);
        txtAnalizarSin.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtAnalizarSin.setRows(5);
        jScrollPane3.setViewportView(txtAnalizarSin);

        Borrar1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        Borrar1.setText("Borrar");
        Borrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Borrar1ActionPerformed(evt);
            }
        });

        btnBorrarSemantico.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnBorrarSemantico.setText("Borrar");
        btnBorrarSemantico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarSemanticoActionPerformed(evt);
            }
        });

        btnEjecutar.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnEjecutar.setText("Ejecutar");
        btnEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEjecutarActionPerformed(evt);
            }
        });

        btnCompilar.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnCompilar.setText("Compilar");
        btnCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompilarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(187, 187, 187)
                .addComponent(btnEjecutar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addComponent(btnCompilar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(869, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEjecutar)
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btnCompilar)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jBSave.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jBSave.setText("Guardar");
        jBSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSaveActionPerformed(evt);
            }
        });

        tb_tokens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Linea", "Token", "Linea"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tb_tokens);

        btnSemantico.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnSemantico.setText("Análisis Semantico");
        btnSemantico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSemanticoActionPerformed(evt);
            }
        });

        btnSintacticoBorrar.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnSintacticoBorrar.setText("Borrar");
        btnSintacticoBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSintacticoBorrarActionPerformed(evt);
            }
        });

        txtSemantico.setEditable(false);
        txtSemantico.setColumns(20);
        txtSemantico.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtSemantico.setRows(5);
        jScrollPane5.setViewportView(txtSemantico);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BotonAnalisis)
                        .addGap(18, 18, 18)
                        .addComponent(Borrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67)
                        .addComponent(BotonSintactico)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSintacticoBorrar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnArchivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBSave, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSemantico)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBorrarSemantico))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(btnArchivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonSintactico, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBorrarSemantico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BotonAnalisis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Borrar1)
                    .addComponent(btnSemantico, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSintacticoBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(jScrollPane5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void analizarLexico() throws IOException {
        int cont = 1;
        String expr = (String) Resultado.getText();
        Lexico lexicos = new Lexico(new StringReader(expr));
        DefaultTableModel model = (DefaultTableModel)tb_tokens.getModel();
        model.setRowCount(0);
        String resultado = "NO. LINEA \t\tSIMBOLO\nLINEA " + cont + "\n";
        while (true) {
            Tokens token = lexicos.yylex();            
            if (token == null) {
                //txtAnalizarLex.setText(resultado);
                habilitarSintactico();
                return;
            }
            
            switch (token) {
                case Linea:
                    cont++;
                    resultado += "LINEA " + cont + "\n";
                    continue;
                    //break;
                /*case STRING_LITERAL:
                    resultado += "  <Cadena>\t\t" + lexicos.lexemas + "\n";
                    break;
                case Comillas:
                    resultado += "  <Comillas>\t\t" + lexicos.lexemas + "\n";
                    break;
                case Comilla_simple:
                    resultado += "  <Comilla simple>\t" + lexicos.lexemas + "\n";
                    break;
                case _String:
                    resultado += "  <Tipo de dato String>\t" + lexicos.lexemas + "\n";
                    break;
                case If:
                    resultado += "  <Reservada if>\t" + lexicos.lexemas + "\n";
                    break;
                case Int:
                    resultado += "  <Tipo de dato int>\t" + lexicos.lexemas + "\n";
                    break;
                case Double:
                    resultado += "  <Tipo de dato double>\t" + lexicos.lexemas + "\n";
                    break;
                case Float:
                    resultado += "  <Tipo de dato float>\t" + lexicos.lexemas + "\n";
                    break;
                case Bool:
                    resultado += "  <Tipo de dato bool>\t" + lexicos.lexemas + "\n";
                    break;
                case Byte:
                    resultado += "  <Tipo de dato byte>\t" + lexicos.lexemas + "\n";
                    break;
                case Long:
                    resultado += "  <Tipo de dato long>\t" + lexicos.lexemas + "\n";
                    break;
                case Scanf:
                    resultado += "  <Reservado Scanf>\t" + lexicos.lexemas + "\n";
                    break;
                case Printf:
                    resultado += "  <Reservado Printf>\t" + lexicos.lexemas + "\n";
                    break;
                case Short:
                    resultado += "  <Tipo de dato short>\t" + lexicos.lexemas + "\n";
                    break;
                case Char:
                    resultado += "  <Tipo de dato char>\t" + lexicos.lexemas + "\n";
                    break;
                case Incremento:
                    resultado += "  <Incremento>\t\t" + lexicos.lexemas + "\n";
                    break;
                case Decremento:
                    resultado += "  <Decremento>\t\t" + lexicos.lexemas + "\n";
                    break;
                case Else:
                    resultado += "  <Reservada else>\t" + lexicos.lexemas + "\n";
                    break;
                case Endl:
                    resultado += "  <Salto de linea>\t" + lexicos.lexemas + "\n";
                    break;
                case Return:
                    resultado += "  <Reservada return>\t" + lexicos.lexemas + "\n";
                    break;
                case Void:
                    resultado += "  <Reservada switch>\t" + lexicos.lexemas + "\n";
                    break;
                case Typedef:
                    resultado += "  <Reservada switch>\t" + lexicos.lexemas + "\n";
                    break;
                case Switch:
                    resultado += "  <Reservada switch>\t" + lexicos.lexemas + "\n";
                    break;
                case Do:
                    resultado += "  <Reservada do>\t" + lexicos.lexemas + "\n";
                    break;
                case For:
                    resultado += "  <Reservada for>\t" + lexicos.lexemas + "\n";
                    break;
                case Igual:
                    resultado += "  <Operador igual>\t" + lexicos.lexemas + "\n";
                    break;
                case O_logico:
                    resultado += "  <Operador o logico>\t" + lexicos.lexemas + "\n";
                    break;
                case BitOr:
                    resultado += "  <Operador bitor>\t" + lexicos.lexemas + "\n";
                    break;
                case Y_logico:
                    resultado += "  <Operador y logico>\t" + lexicos.lexemas + "\n";
                    break;
                case BitAnd:
                    resultado += "  <Operador bitand>\t" + lexicos.lexemas + "\n";
                    break;
                case Suma:
                    resultado += "  <Operador suma>\t" + lexicos.lexemas + "\n";
                    break;
                case Resta:
                    resultado += "  <Operador resta>\t" + lexicos.lexemas + "\n";
                    break;
                case Multiplicacion:
                    resultado += "  <Operador multiplicacion>\t" + lexicos.lexemas + "\n";
                    break;
                case Division:
                    resultado += "  <Operador division>\t" + lexicos.lexemas + "\n";
                    break;
                case ModuloIgual:
                    resultado += "  <Operador modulo igual>\t" + lexicos.lexemas + "\n";
                    break;
                case Modulo:
                    resultado += "  <Operador modulo>\t" + lexicos.lexemas + "\n";
                    break;
                case Diferente:
                    resultado += "  <Comparador diferente>\t" + lexicos.lexemas + "\n";
                    break;
                case Negador:
                    resultado += "  <Operador de negacion>\t" + lexicos.lexemas + "\n";
                    break;
                case DobleMayor:
                    resultado += "  <Doble mayor>\t" + lexicos.lexemas + "\n";
                    break;
                case DobleMenor:
                    resultado += "  <Doble menor>\t" + lexicos.lexemas + "\n";
                    break;
                case MayorQue:
                    resultado += "  <Simbolo Mayor que>\t" + lexicos.lexemas + "\n";
                    break;
                case MenorQue:
                    resultado += "  <Simbolo Menor que>\t" + lexicos.lexemas + "\n";
                    break;
                case MayorIgual:
                    resultado += "  <Mayor igual>\t\t" + lexicos.lexemas + "\n";
                    break;
                case MenorIgual:
                    resultado += "  <Menor igual>\t\t" + lexicos.lexemas + "\n";
                    break;
                case MasIgual:
                    resultado += "  <Mas igual>\t\t" + lexicos.lexemas + "\n";
                    break;
                case MenosIgual:
                    resultado += "  <Menos igual>\t\t" + lexicos.lexemas + "\n";
                    break;
                case MultiplicacionIgual:
                    resultado += "  <Multiplica igual>\t" + lexicos.lexemas + "\n";
                    break;
                case DivisionIgual:
                    resultado += "  <Division igual>\t" + lexicos.lexemas + "\n";
                    break;
                case Parent_a:
                    resultado += "  <Parentesis de apertura>\t" + lexicos.lexemas + "\n";
                    break;
                case Parent_c:
                    resultado += "  <Parentesis de cierre>\t" + lexicos.lexemas + "\n";
                    break;
                case Llave_a:
                    resultado += "  <Llave de apertura>\t" + lexicos.lexemas + "\n";
                    break;
                case Llave_c:
                    resultado += "  <Llave de cierre>\t" + lexicos.lexemas + "\n";
                    break;
                case Corchete_a:
                    resultado += "  <Corchete de apertura>\t" + lexicos.lexemas + "\n";
                    break;
                case Corchete_c:
                    resultado += "  <Corchete de cierre>\t" + lexicos.lexemas + "\n";
                    break;
                case Main:
                    resultado += "  <Reservada main>\t" + lexicos.lexemas + "\n";
                    break;
                case While:
                    resultado += "  <Reservada while>\t" + lexicos.lexemas + "\n";
                    break;
                case Cin:
                    resultado += "  <Entrada por consola>\t" + lexicos.lexemas + "\n";
                    break;
                case Cout:
                    resultado += "  <Salida por consola>\t" + lexicos.lexemas + "\n";
                    break;
                case P_coma:
                    resultado += "  <Punto y coma>\t" + lexicos.lexemas + "\n";
                    break;
                case Punto:
                    resultado += "  <Punto>\t\t" + lexicos.lexemas + "\n";
                    break;
                case Coma:
                    resultado += "  <Coma>\t\t" + lexicos.lexemas + "\n";
                    break;
                case DosPuntos:
                    resultado += "  <Dos puntos>\t\t" + lexicos.lexemas + "\n";
                    break;
                case Include:
                    resultado += "  <Reservado include>\t" + lexicos.lexemas + "\n";
                    break;
                case Std:
                    resultado += "  <Reservado std>\t" + lexicos.lexemas + "\n";
                    break;
                case Namespace:
                    resultado += "  <Reservado namespace>\t" + lexicos.lexemas + "\n";
                    break;
                case Case:
                    resultado += "  <Reservado case>\t" + lexicos.lexemas + "\n";
                    break;
                case Continue:
                    resultado += "  <Reservado continue>\t" + lexicos.lexemas + "\n";
                    break;
                case Break:
                    resultado += "  <Reservado break>\t" + lexicos.lexemas + "\n";
                    break;
                case Iostream:
                    resultado += "  <Reservado iostream>\t" + lexicos.lexemas + "\n";
                    break;
                case Using:
                    resultado += "  <Reservado using>\t" + lexicos.lexemas + "\n";
                    break;
                case Const:
                    resultado += "  <Reservado using>\t" + lexicos.lexemas + "\n";
                    break;
                case Default:
                    resultado += "  <Reservado default>\t" + lexicos.lexemas + "\n";
                    break;
                case Define:
                    resultado += "  <Reservado define>\t" + lexicos.lexemas + "\n";
                    break;
                case Unsigned:
                    resultado += "  <Reservado unsigned>\t" + lexicos.lexemas + "\n";
                    break;
                case Register:
                    resultado += "  <Reservado register>\t" + lexicos.lexemas + "\n";
                    break;
                case Struct:
                    resultado += "  <Reservado struct>\t" + lexicos.lexemas + "\n";
                    break;
                case Numeral:
                    resultado += "  <Reservado numeral>\t" + lexicos.lexemas + "\n";
                    break;
                case Identificador:
                    resultado += "  <Identificador>\t\t" + lexicos.lexemas + "\n";
                    if(identificadores.contains(lexicos.lexemas)){
                        warnings.add(lexicos.lexemas);
                    }
                    identificadores.add(lexicos.lexemas);
                    break;
                case Numero:
                    resultado += "  <Numero>\t\t" + lexicos.lexemas + "\n";
                    break;
                case ERROR:
                    resultado += "  <Simbolo no definido>\n";
                    break;
                default:
                    resultado += "  < " + lexicos.lexemas + " >\n";
                    break;*/
            }
            model.addRow(new Object[]{
                cont,
                lexicos.yytext(),
                token
            });
        }

    }

    private void analizarSintactico(){
        String ST = Resultado.getText();
        //Sintaxis s = new Sintaxis(new LexicoCup(new StringReader(ST)));
        Parser s = new Parser(new LexerSemantic((Reader)(new StringReader(ST))));
        try {
            //s.parse();
            if(s.error_sym!=null){
                Symbol sym = s.error_sym;
                txtAnalizarSin.setText("Error de sintaxis. Linea: " + (sym.right+1) + " Columna: " + (sym.left+1) + ", Texto: \"" + sym.value.toString() + "\"");
                txtAnalizarSin.setForeground(Color.red);
                deshabilitarSemantico();
            } else {
                txtAnalizarSin.setText("Analisis realizado correctamente");
                txtAnalizarSin.setForeground(new Color(25, 111, 61));
                habilitarSemantico();            
            }
        } catch (Exception ex) {
            
            s.report_error("Error!", ex);
            txtAnalizarSin.setText(s.report_error(ex));
            txtAnalizarSin.setForeground(Color.red);
            deshabilitarSemantico();
            /*
            Symbol sym = s.getErrorSymbol();
            txtAnalizarSin.setText("Error de sintaxis. Linea: " + (sym.right+1) + " Columna: " + (sym.left+1) + ", Texto: \"" + sym.value + "\"");
            txtAnalizarSin.setForeground(Color.red);
            deshabilitarSemantico();
            */
        }
    }
    
    private void analizarSemantico(){
        txtSemantico.setText(null);
        AnalizadorSemantico.errors.clear();
        System.out.println("\n---------------------------------------------------------------");
        try {
            Parser cupParser = new Parser(new StringReader(Resultado.getText()));
            TreeNode AST = (TreeNode) cupParser.parse().value;
            AST.reduceTreeNode();
            //AST.saveTreeToFile("treeASTFile");
            Table table = new Table();
            AnalizadorSemantico.semantico(AST, table);
            AnalizadorSemantico.cuadruplos(AST, table, 0);
            TableQuad quad = AnalizadorSemantico.cuadruplos(AST, table, 0);
            System.out.println(AST.toString());
        } catch (Exception ex) {
            Logger.getLogger(FrmAnalizador.class.getName()).log(Level.SEVERE, null, ex);
        }    
        if(!AnalizadorSemantico.errors.isEmpty()){
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < AnalizadorSemantico.errors.size(); i++) {
                str.append(AnalizadorSemantico.errors.get(i)).append("\n");
            }
            System.out.println(AnalizadorSemantico.errors.size());
            txtSemantico.setText(str.toString());
            txtSemantico.setForeground(Color.RED);
        } else {
            txtSemantico.setText("Analisis realizado correctamente");
            txtSemantico.setForeground(new Color(25, 111, 61));
        }
    }
    
    private boolean generacionCodigo(){        
        Script s = new Script(this.srcFile);
        return s.ejecutable();                
    }
    
    private void BotonAnalisisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonAnalisisActionPerformed
        try {
            analizarLexico();
        } catch (IOException ex) {
            Logger.getLogger(FrmAnalizador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BotonAnalisisActionPerformed

    private void btnBorrarSemanticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarSemanticoActionPerformed
        txtSemantico.setText(null);
        deshabilitarSemantico();
    }//GEN-LAST:event_btnBorrarSemanticoActionPerformed

    private void btnArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchivoActionPerformed
        JFileChooser directory = new JFileChooser();
        directory.setCurrentDirectory(new File("."));
        directory.setDialogTitle("DIRECTORIO");
        directory.setAcceptAllFileFilterUsed(false);

        if (directory.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                this.srcFile = directory.getSelectedFile();
                String ST = new String(Files.readAllBytes(srcFile.toPath()));
                Resultado.setText(ST);
                this.setTitle(this.srcFile.getName());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FrmAnalizador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FrmAnalizador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(FrmAnalizador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnArchivoActionPerformed

    private void BotonSintacticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonSintacticoActionPerformed
        analizarSintactico();
    }//GEN-LAST:event_BotonSintacticoActionPerformed

    private void Borrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Borrar1ActionPerformed
        //txtAnalizarLex.setText(null);
        DefaultTableModel model = (DefaultTableModel)tb_tokens.getModel();
        model.setRowCount(0);
        deshabilitarSintactico();
        deshabilitarSemantico();
    }//GEN-LAST:event_Borrar1ActionPerformed

    private void jBSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSaveActionPerformed
        try {
            JFileChooser escoger = new JFileChooser();
            escoger.setCurrentDirectory(new File("."));
            if (escoger.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                FileWriter fw = new FileWriter(escoger.getSelectedFile()+".c");
                fw.flush();
                fw.write(Resultado.getText());                 
                fw.close();
                JOptionPane.showMessageDialog(this,"Archivo guardado con exito");
                this.srcFile = new File(escoger.getSelectedFile().getAbsoluteFile()+".c");
                this.setTitle(srcFile.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"No se pudo guardar el archivo: \n"
                    + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jBSaveActionPerformed

    private void btnSemanticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSemanticoActionPerformed
        analizarSemantico();
    }//GEN-LAST:event_btnSemanticoActionPerformed

    private void btnSintacticoBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSintacticoBorrarActionPerformed
        txtAnalizarSin.setText(null);
        deshabilitarSintactico();
        deshabilitarSemantico();
    }//GEN-LAST:event_btnSintacticoBorrarActionPerformed

    private void btnEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEjecutarActionPerformed
        Script s = new Script(srcFile);
        s.start();
    }//GEN-LAST:event_btnEjecutarActionPerformed

    private void btnCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompilarActionPerformed
        if(this.srcFile == null){
            if(JOptionPane.showConfirmDialog(this, "No hay archivo guardado, desea guardarlo?")==JOptionPane.YES_OPTION){
                jBSave.doClick();
            } else {
                return;
            }
        }
        if(generacionCodigo()){
            desbloquearEjecutable();
        } else {
            bloquarEjecutable();
        }
    }//GEN-LAST:event_btnCompilarActionPerformed

    private boolean saveFile(File file, String doc) {
        try {
            FileOutputStream out = new FileOutputStream(file);
            byte[] bytxt = doc.getBytes();
            out.write(bytxt);
            this.srcFile = file;
            return true;
        } catch (Exception e) {
            this.srcFile = null;
            return false;
        }
    }
    
    private void habilitarSintactico(){
        BotonSintactico.setEnabled(true);
        btnSintacticoBorrar.setEnabled(true);
    }
    
    private void deshabilitarSintactico(){
        BotonSintactico.setEnabled(false);
        btnSintacticoBorrar.setEnabled(false);
    }
    
    private void habilitarSemantico(){
        btnSemantico.setEnabled(true);
        btnBorrarSemantico.setEnabled(true);
    }
    
    private void deshabilitarSemantico(){
        btnSemantico.setEnabled(false);
        btnBorrarSemantico.setEnabled(false);
    }
    
    private void desbloquearEjecutable() {
        btnEjecutar.setEnabled(true);
    }

    private void bloquarEjecutable() {
        btnEjecutar.setEnabled(false);
    }
    
    public static void main(String args[]) throws Exception {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmAnalizador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmAnalizador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmAnalizador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmAnalizador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAnalizador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Borrar1;
    private javax.swing.JButton BotonAnalisis;
    private javax.swing.JButton BotonSintactico;
    private javax.swing.JTextArea Resultado;
    private javax.swing.JButton btnArchivo;
    private javax.swing.JButton btnBorrarSemantico;
    private javax.swing.JButton btnCompilar;
    private javax.swing.JButton btnEjecutar;
    private javax.swing.JButton btnSemantico;
    private javax.swing.JButton btnSintacticoBorrar;
    private javax.swing.JButton jBSave;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable tb_tokens;
    private javax.swing.JTextArea txtAnalizarSin;
    private javax.swing.JTextArea txtSemantico;
    // End of variables declaration//GEN-END:variables
}
