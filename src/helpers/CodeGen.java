/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;

public class CodeGen {

    private File file;
    private String mipsHeader = "";

    public CodeGen(File file) {
        this.file = file;
    }

    //icr = intermediate code representation
    public void generateCode(TableQuad icr, Table symbolsTable) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(file);
        fileOut.write(mipsHeader.getBytes());
        icr.print();
        symbolsTable.print();
        ArrayList<TableRowQuad> tr = icr.getRows();
        ArrayList<TableRowQuad> hr = icr.getHeaderRows();
        String toWrite;
        int paramCount = 0;
        int msgCount = 0;
        int fixedStack = 0;
        int tempCount = 0;
        //HEADER
        fileOut.write(".data\n".getBytes());
        //MESSAGES
        for (int i = 0; i < hr.size(); i++) {
            TableRowQuad row = hr.get(i);
            switch (row.op) {
                case "newmsg": {
                    toWrite = "_msg" + i + ": " + row.arg1 + "\n";
                    fileOut.write(toWrite.getBytes());
                    break;
                }
            }
        }
        toWrite = ".text\n.globl main\n";
        fileOut.write(toWrite.getBytes());
        //CODE
        for (TableRowQuad row : tr) {
            toWrite = "";
            switch (row.op) {
                case "*":
                    if (toWrite.isEmpty()) {
                        toWrite = "mul ";
                    }
                case "/":
                    if (toWrite.isEmpty()) {
                        toWrite = "div ";
                    }
                case "+":
                    if (toWrite.isEmpty()) {
                        toWrite = "add ";
                    }
                case "-":{
                    if (toWrite.isEmpty()) {
                        toWrite = "sub ";
                    }
                    if (row.arg1.equals("t0")) {
                        row.arg1 = "$" + row.arg1;
                    }
                    if (row.res.equals("t0")) {
                        row.res = "$" + row.res;
                    }
                    toWrite += row.res + ", ";
                    TableRow search = symbolsTable.search(row.arg1);
                    if(search != null){
                        int offset = fixedStack - search.offset -Table.getTypeSize(search.type);
                        String load = "lw $t0, "+offset+"($fp)\n";
                        fileOut.write(load.getBytes());
                        toWrite += "$t0, ";
                    }else{
                        toWrite += row.arg1 + ", ";
                    }
                    toWrite += row.arg2 + "\n";
                    fileOut.write(toWrite.getBytes());
                    break;
                }
                case "=": {
                    if (row.arg1.equals("t0")) {
                        row.arg1 = "$" + row.arg1;
                    }else if (row.arg1.equals("RET")){
                        row.arg1 = "$v0";
                    }
                    if (row.res.equals("t0")) {
                        row.res = "$" + row.res;
                        toWrite = "move " + row.res + ", " + row.arg1+"\n";
                    }else{
                        TableRow search = symbolsTable.search(row.res);
                        System.out.println(row.res);
                        int offset = fixedStack - search.offset - Table.getTypeSize(search.type);
                        toWrite = "sw " + row.arg1 + ", " + offset + "($fp)\n";
                    }
                    fileOut.write(toWrite.getBytes());
                    break;
                }
                case "genfunc": {
                    fixedStack = 0;
                    fileOut.write((row.arg1 + "\n").getBytes());
                    if (row.arg1.equals("main:")) {
                        toWrite = "move $fp, $sp\nsw $fp, -4($sp)\nsub $sp, $sp, 4\n";
                        fixedStack -= 4;
                        fileOut.write(toWrite.getBytes());
                    }
                    int stackSize = 0;
                    for (TableRow symRow : symbolsTable.rows) {
                        stackSize += Table.getTypeSize(symRow.type);
                    }
                    if (stackSize > 0) {
                        toWrite = "sub $sp, $sp, " + stackSize + "\n";
                        fileOut.write(toWrite.getBytes());
                    }
                    break;
                }
                case "param": {
                    String param = "$a" + paramCount;
                    if (row.arg1.equals("_msgstring")) {
                        toWrite = "la " + param + ", _msg" + msgCount;
                        msgCount++;
                    } else {
                        TableRow search;
                        search = symbolsTable.search(row.arg1);
                        if (row.arg1.charAt(0) == '&' || row.arg1.charAt(0) == '*') {
                            search = symbolsTable.search(row.arg1.substring(1));
                        }
                        if (search != null) {
                            int offset = fixedStack - search.offset - Table.getTypeSize(search.type);
                            if (search.type.contains("Pointer")) {
                                toWrite = "lw $t0,  " + offset + "($fp)\n";
                                toWrite += "move $t1, 0($t0)\n";
                            } else {
                                toWrite = "lw $t0, " + offset + "($fp)\n";
                            }
                            toWrite += "move " + param + ", $t" + tempCount;
                        }
                    }
                    toWrite += "\n";
                    fileOut.write(toWrite.getBytes());
                    paramCount++;
                    break;
                }
                case "function_call": {
                    if (row.arg1.equals("_printf")) {
                        toWrite = "li $v0, 4\nsyscall\n";
                        if (paramCount == 2) {
                            toWrite += "move $a0, $a1\nli $v0, 1\nsyscall\n";
                        }
                        fileOut.write(toWrite.getBytes());
                    } else {
                        toWrite = "jal " + row.arg1 + "\n";
                        fileOut.write(toWrite.getBytes());
                    }
                    paramCount = 0;
                    break;
                }
                case "scanf": {
                    switch (row.arg1) {
                        case "int":
                        case "long": {
                            TableRow search = symbolsTable.search(row.res);
                            System.out.println(fixedStack);
                            int offset = fixedStack - search.offset - Table.getTypeSize(search.type);
                            toWrite = "li $v0, 5\nsyscall\nsw $v0, " + offset + "($fp)\n";
                            fileOut.write(toWrite.getBytes());
                            break;
                        }
                    }
                    break;
                }
                default:

            }
        }
        fileOut.close();
    }

    public void setMipsHeader(String append) {
        mipsHeader += append;
    }
}
