/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Analizador.semantic;

import helpers.Table;
import helpers.TableQuad;
import helpers.TableRow;
import helpers.TreeNode;
import java.util.ArrayList;
import java.util.List;
import java_cup.runtime.Symbol;

/**
 *
 * @author Desarrollo
 */
public class AnalizadorSemantico {

    public static List<String> errors = new ArrayList<>();

    public static void semantico(TreeNode parent_node, Table table) {
        ArrayList<TreeNode> childs = parent_node.getChilds();
        for (TreeNode child : childs) {
            if (child.getValue().value.toString().equals("declaration")
                    || child.getValue().value.equals("parameter_declaration")) {
                String type = child.getChilds().get(0).getValue().value.toString();
                if (parent_node.valueIsString("iteration_statement")) {
                    Table child_table = new Table(table);
                    table.addChild(child_table);
                    getDeclarations(child.getChilds().get(1), type, child_table);
                } else {
                    getDeclarations(child.getChilds().get(1), type, table);
                }
            } else if (child.getValue().value.equals("=")) {
                Asignacion(child, table);

            } else if (child.getValue().value.equals("function_definition")) {
                Table child_table = new Table(table);
                table.addChild(child_table);
                ArrayList<TreeNode> function_definition_childs = child.getChilds();
                for (TreeNode function_child : function_definition_childs) {
                    semantico(function_child, child_table);
                }
            } else if (child.getValue().value.equals("decl_stmnt_list") && !parent_node.getValue().value.equals("function_definition")) {
                Table child_table = new Table(table);
                table.addChild(child_table);
                semantico(child, child_table);
            } else {
                semantico(child, table);
            }
        }
    }

    public static void Asignacion(TreeNode node, Table table) {
        if (node.getChilds().size() == 2) {
            TreeNode first = node.getChilds().get(0);
            TreeNode second = node.getChilds().get(1);
            if (first.getValue().sym == symSemantic.IDENTIFIER) {
                TableRow firstResult = table.search(first.getValue().value.toString());
                if (firstResult != null) {
                    if (second.getValue().value.equals("unary_expression")) {
                        if (second.getChilds().get(0).getValue().sym == 71) {
                            if (!firstResult.type.contains("Pointer")) {
                                System.err.println("Error en la linea " + (first.getValue().right + 1) + ", columna " + first.getValue().left + " en el token " + first.getValue().value + ": Varibales son de diferente tipo");
                                errors.add("Error en la linea " + (first.getValue().right + 1) + ", columna " + first.getValue().left + " en el token " + first.getValue().value + ": Varibales son de diferente tipo");
                            }
                        }
                    }
                    switch (second.getValue().sym) {
                        case symSemantic.IDENTIFIER:
                            TableRow secondResult = table.search(second.getValue().value.toString());
                            if (secondResult != null) {
                                if (firstResult.type.equals(secondResult.type)) {
                                    firstResult.value = secondResult.value;
                                } else {
                                    System.err.println("Error en la linea " + (first.getValue().right + 1) + ", columna " + first.getValue().left + " en el token " + first.getValue().value + ": Variables son de diferente tipo");
                                    errors.add("Error en la linea " + (first.getValue().right + 1) + ", columna " + first.getValue().left + " en el token " + first.getValue().value + ": Variables son de diferente tipo");
                                }
                            } else {
                                System.err.println("Error en la linea " + (second.getValue().right + 1) + ", columna " + second.getValue().left + " en el token " + second.getValue().value + ": Variable no ha sido declarada");
                                errors.add("Error en la linea " + (second.getValue().right + 1) + ", columna " + second.getValue().left + " en el token " + second.getValue().value + ": Variable no ha sido declarada");
                            }
                            break;
                        case symSemantic.CONSTANT:
                        case symSemantic.STRING_LITERAL:
                        case -1:
                            if (second.getChilds().size() > 0 && second.getChilds().get(0).getValue().sym == 71) {

                            } else if (checkValueType(second, firstResult.type)) {
                                firstResult.value = second.getValue().value;
                            } else if (second.getChilds().size() > 0 && second.getChilds().get(0).getValue().sym != 71 || second.getChilds().size() == 0) {
                                System.err.println("Error en la linea " + (first.getValue().right + 1) + ", columna " + first.getValue().left + " en el token " + first.getValue().value + ": Varibales son de diferente tipo");
                                errors.add("Error en la linea " + (first.getValue().right + 1) + ", columna " + first.getValue().left + " en el token " + first.getValue().value + ": Varibales son de diferente tipo");
                            }
                            break;
                        case symSemantic.PLUS:
                        case symSemantic.MINUS:
                        case symSemantic.DIVIDE:
                        case symSemantic.MUL:
                            if (firstResult.type.equals("int")
                                    || firstResult.type.equals("char")
                                    || firstResult.type.equals("double")
                                    || firstResult.type.equals("float")
                                    || firstResult.type.equals("long")) {
                                aritmetica(node, firstResult.type);
                            } else {
                                System.err.println("Error en la linea " + (first.getValue().right + 1) + ", columna " + first.getValue().left + " en el token " + first.getValue().value + ": No se puede asignar expresion aritmetica");
                                errors.add("Error en la linea " + (first.getValue().right + 1) + ", columna " + first.getValue().left + " en el token " + first.getValue().value + ": No se puede asignar expresion aritmetica");
                            }
                            break;
                    }

                } else {
                    System.err.println("Error en la linea " + (first.getValue().right + 1) + ", columna " + first.getValue().left + " en el token " + first.getValue().value + ": Variable no ha sido declarada");
                    errors.add("Error en la linea " + (first.getValue().right + 1) + ", columna " + first.getValue().left + " en el token " + first.getValue().value + ": Variable no ha sido declarada");
                }
            } else if (first.getValue().sym == -1 && !first.valueIsString("ERROR")) {
                System.err.println(first.getValue().value);
                TreeNode firstchild = first.getChilds().get(0);
                if (firstchild.getValue().sym == -1) {
                    TreeNode secondchild = first.getChilds().get(1);
                    TableRow firstResult = table.search(secondchild.getValue().value.toString());
                    if (firstResult != null) {
                        TreeNode thirdchild = first.getChilds().get(2);
                        String properties[] = arrayParse(firstResult);
                        String sizeOfArray = properties[0];
                        String type = properties[1];
                        if (Integer.parseInt(sizeOfArray) - 1 >= Integer.parseInt(thirdchild.getValue().value.toString())) {

                            switch (second.getValue().sym) {
                                case symSemantic.CONSTANT:
                                    if (type.equals("int")
                                            || firstResult.type.equals("char")
                                            || firstResult.type.equals("double")
                                            || firstResult.type.equals("float")
                                            || firstResult.type.equals("long")) {
                                        setValidNumber(second.getValue().value.toString(), second, type, false);
                                    } else {
                                        System.err.println("Error en la linea " + (thirdchild.getValue().right + 1) + ", columna " + thirdchild.getValue().left + " en el token " + thirdchild.getValue().value + ": Asignacion no es del tipo " + type);
                                        errors.add("Error en la linea " + (thirdchild.getValue().right + 1) + ", columna " + thirdchild.getValue().left + " en el token " + thirdchild.getValue().value + ": Asignacion no es del tipo " + type);
                                    }
                                    break;
                                case symSemantic.STRING_LITERAL:
                                    if (type.contains("Pointer")) {
                                        setValidNumber(second.getValue().value.toString(), second, type, false);
                                    } else {
                                        System.err.println("Error en la linea " + (thirdchild.getValue().right + 1) + ", columna " + thirdchild.getValue().left + " en el token " + thirdchild.getValue().value + ": Asignacion no es del tipo " + type);
                                        errors.add("Error en la linea " + (thirdchild.getValue().right + 1) + ", columna " + thirdchild.getValue().left + " en el token " + thirdchild.getValue().value + ": Asignacion no es del tipo " + type);
                                    }
                                    break;
                            }
                        } else {
                            System.err.println("Error en la linea " + (thirdchild.getValue().right + 1) + ", columna " + thirdchild.getValue().left + " en el token " + thirdchild.getValue().value + ": Asignacion del arreglo fuera del limite");
                            errors.add("Error en la linea " + (thirdchild.getValue().right + 1) + ", columna " + thirdchild.getValue().left + " en el token " + thirdchild.getValue().value + ": Asignacion del arreglo fuera del limite");
                        }

                    }

                }
            }
        }
    }

    public static String[] arrayParse(TableRow row) {
        String type = "";
        type = row.type.replace("array(", "");
        type = type.replace(")", "");
        String response[] = type.split(", ");
        return response;
    }

    public static void getDeclarations(TreeNode node, String type, Table table) {
        String id = node.getValue().value.toString();
        ArrayList<TreeNode> node_childs = node.getChilds();
        TreeNode child_id, child_value;
        switch (id) {
            case "declaration_list":
                break;
            case "parameter_list":
            case "init_declarator_list":
                for (TreeNode child : node_childs) {
                    getDeclarations(child, type, table);
                }
                break;
            case "init_declarator":
                child_id = node_childs.get(0);
                child_value = node_childs.get(1);
                if (child_id.getValue().value.equals("declarator")) {
                    child_id = child_id.getChilds().get(1);
                    if (checkValueType(child_value, "Pointer(" + type + ")")) {
                        int offset = table.getActualOffset();
                        table.addTableRow(child_id.getValue().value.toString(), child_value.getValue().value, "Pointer(" + type + ")", offset);
                    } else {
                        System.err.println("Error en linea " + (child_id.getValue().right + 1) + ", columna " + child_id.getValue().left + " en el token " + child_id.getValue().value + ", asignacion no es de tipo " + type + "*");
                        errors.add("Error en linea " + (child_id.getValue().right + 1) + ", columna " + child_id.getValue().left + " en el token " + child_id.getValue().value + ", asignacion no es de tipo " + type + "*");
                    }
                } else if (child_id.getValue().value.equals("direct_declarator") && child_id.getChilds().get(0).getValue().value == "array_declarator") {
                    int offset = table.getActualOffset();

                    if (child_id.getChilds().size() == 3) {
                        if (child_id.getChilds().get(2).getValue().sym == symSemantic.CONSTANT) {
                            if (setValidNumber(child_id.getChilds().get(2).getValue().value.toString(), child_id.getChilds().get(2), "int", false)) {
                                table.addTableRow(child_id.getChilds().get(1).getValue().value.toString(), null, "array(" + child_id.getChilds().get(2).getValue().value + ", " + type + ")", offset);
                            }

                        } else {
                            System.err.println("Error en linea " + (child_id.getChilds().get(1).getValue().right + 1) + ", columna " + child_id.getChilds().get(1).getValue().left + " en el token " + child_id.getChilds().get(1).getValue().value + ": asignacion no es de tipo arreglo " + type);
                            errors.add("Error en linea " + (child_id.getChilds().get(1).getValue().right + 1) + ", columna " + child_id.getChilds().get(1).getValue().left + " en el token " + child_id.getChilds().get(1).getValue().value + ": asignacion no es de tipo arreglo " + type);
                        }
                    } else if (child_id.getChilds().size() == 2) {
                        table.addTableRow(child_id.getChilds().get(1).getValue().value.toString(), null, "array(0, " + type + ")", offset);
                    }

                    System.err.println("Advertencia en la linea " + (child_id.getChilds().get(1).getValue().right + 1) + ", columna " + child_id.getChilds().get(1).getValue().left + " en el token " + child_id.getChilds().get(1).getValue().value + ": no se acepta incializacion de arreglos");
                    errors.add("Advertencia en la linea " + (child_id.getChilds().get(1).getValue().right + 1) + ", columna " + child_id.getChilds().get(1).getValue().left + " en el token " + child_id.getChilds().get(1).getValue().value + ": no se acepta incializacion de arreglos");
                } else if (checkValueType(child_value, type)) {
                    int offset = table.getActualOffset();
                    if (child_value.getValue().sym != symSemantic.PLUS
                            && child_value.getValue().sym != symSemantic.MUL
                            && child_value.getValue().sym != symSemantic.DIVIDE
                            && child_value.getValue().sym != symSemantic.MINUS) {
                        table.addTableRow(child_id.getValue().value.toString(), child_value.getValue().value, type, offset);
                    } else {
                        table.addTableRow(child_id.getValue().value.toString(), null, type, offset);
                    }
                } else {
                    System.err.println("Error en linea " + (child_id.getValue().right + 1) + ", columna " + child_id.getValue().left + " en el token " + child_id.getValue().value + ": asignacion no es de tipo " + type);
                    errors.add("Error en linea " + (child_id.getValue().right + 1) + ", columna " + child_id.getValue().left + " en el token " + child_id.getValue().value + ": asignacion no es de tipo " + type);
                }
                break;
            case "direct_declarator": {
                String declaration_type = node_childs.get(0).getValue().value.toString();
                if (declaration_type.equals("function_declarator")) {
                    child_id = node_childs.get(1);
                    String parameter_types = "";
                    if (node_childs.size() > 2) {
                        parameter_types = node_childs.get(2).getValue().value.toString();
                        if (parameter_types.equals("parameter_list")) {
                            ArrayList<String> params = new ArrayList();
                            ArrayList<TreeNode> param_childs = node_childs.get(2).getChilds();
                            for (TreeNode param : param_childs) {
                                params.add(param.getValue().value.toString());
                            }
                            parameter_types = String.join(" x ", params);
                        }
                    }
                    parameter_types = type + " -> " + parameter_types;
                    int offset = table.getActualOffset();
                    table.addTableRow(child_id.getValue().value.toString(), null, parameter_types, offset);
                } else if (declaration_type.equals("array_declarator")) {
                    child_id = node_childs.get(1);
                    int offset = table.getActualOffset();
                    if (node_childs.size() == 3) {
                        child_value = node_childs.get(2);
                        if (child_value.getValue().sym == 3) {
                            if (setValidNumber(child_value.getValue().value.toString(), child_value, "int", false)) {
                                table.addTableRow(child_id.getValue().value.toString(), null, "array(" + child_value.getValue().value + ", " + type + ")", offset);
                            }
                        } else {
                            System.err.println("Error en linea " + (child_id.getValue().right + 1) + ", columna " + child_id.getValue().left + " en el token " + child_id.getValue().value + ": asignacion no es de tipo arreglo " + type);
                            errors.add("Error en linea " + (child_id.getValue().right + 1) + ", columna " + child_id.getValue().left + " en el token " + child_id.getValue().value + ": asignacion no es de tipo arreglo " + type);
                        }
                    } else {
                        table.addTableRow(child_id.getValue().value.toString(), null, "array(0, " + type + ")", offset);
                    }
                }
                break;
            }
            case "declarator":
                child_id = node_childs.get(1);
                int offset = table.getActualOffset();
                table.addTableRow(child_id.getValue().value.toString(), null, "Pointer(" + type + ")", offset);
                break;
            case "parameter_declaration":
                type = node_childs.get(0).getValue().value.toString();
                child_id = node_childs.get(1);
                getDeclarations(child_id, type, table);
                break;
            default:
                offset = table.getActualOffset();
                table.addTableRow(id, null, type, offset);
        }
    }

    public static boolean checkValueType(TreeNode node, String type) {
        Symbol value = node.getValue();
        switch (type) {
            case "int":
            case "short":
            case "long":
            case "float":
            case "double":
            case "char":
                if (value.sym == 3) {
                    return setValidNumber(value.value.toString(), node, type, false);
                } else if (value.value.toString().equals("unary_expression")) {
                    return setValidNumber(node.getChilds().get(1).getValue().value.toString(), node, type, true);
                } else if (value.sym >= 74 && value.sym <= 77) {
                    aritmetica(node, type);
                    return true;
                }
                break;
            case "Pointer(char)":
                return value.sym == 4;
        }
        return false;
    }

    public static void aritmetica(TreeNode node, String type) {
        int symbol = node.getValue().sym;

        if (node.getChilds().size() == 2) {

            int firstChild = node.getChilds().get(0).getValue().sym;
            Object a = null;
            boolean AisNegative = false;

            switch (firstChild) {
                case symSemantic.CONSTANT:
                    setValidNumber(node.getChilds().get(0).getValue().value.toString(), node.getChilds().get(0), type, false);
                    a = node.getChilds().get(0).getValue().value;
                    break;
                case symSemantic.PLUS:
                case symSemantic.MUL:
                case symSemantic.DIVIDE:
                case symSemantic.MINUS:
                    if (node.getChilds().get(0).getChilds().size() > 0) {
                        aritmetica(node.getChilds().get(0), type);
                        if (node.getChilds().get(0).getValue().sym == symSemantic.CONSTANT) {
                            a = node.getChilds().get(0).getValue().value;
                        }
                    } else {
                        System.err.println("Error en la linea " + (node.getChilds().get(0).getValue().right + 1) + ", columna " + node.getChilds().get(0).getValue().left + " en el token " + node.getChilds().get(0).getValue().value + ": Valor en expresion aritmetica no valida");
                        errors.add("Error en la linea " + (node.getChilds().get(0).getValue().right + 1) + ", columna " + node.getChilds().get(0).getValue().left + " en el token " + node.getChilds().get(0).getValue().value + ": Valor en expresion aritmetica no valida");
                    }
                    break;
                case -1:
                    if (node.getChilds().get(0).getChilds().get(0).equals("-")) {
                        setValidNumber(node.getChilds().get(0).getChilds().get(1).getValue().value.toString(), node.getChilds().get(0), type, false);
                        node.getChilds().get(0).setSym(symSemantic.CONSTANT);
                        node.getChilds().get(0).deleteChilds();
                        a = node.getChilds().get(0).getValue().value;
                        AisNegative = true;
                    } else {
                        System.err.println("Error en la linea " + (node.getChilds().get(0).getChilds().get(0).getValue().right + 1) + ", columna " + node.getChilds().get(0).getChilds().get(0).getValue().left + " en el token " + node.getChilds().get(0).getChilds().get(0).getValue().value + ": Valor en expresion aritmetica no valida");
                        errors.add("Error en la linea " + (node.getChilds().get(0).getChilds().get(0).getValue().right + 1) + ", columna " + node.getChilds().get(0).getChilds().get(0).getValue().left + " en el token " + node.getChilds().get(0).getChilds().get(0).getValue().value + ": Valor en expresion aritmetica no valida");
                    }
                    break;
                case symSemantic.IDENTIFIER:
                    break;
                default:
                    System.err.println("Error en la linea " + (node.getChilds().get(0).getValue().right + 1) + ", columna " + node.getChilds().get(0).getValue().left + " en el token " + node.getChilds().get(0).getValue().value + ": Valor en expresion aritmetica no valida");
                    errors.add("Error en la linea " + (node.getChilds().get(0).getValue().right + 1) + ", columna " + node.getChilds().get(0).getValue().left + " en el token " + node.getChilds().get(0).getValue().value + ": Valor en expresion aritmetica no valida");
                    if (node.getValue().sym == symSemantic.PLUS
                            || node.getValue().sym == symSemantic.MINUS) {
                        setValidNumber("0", node.getChilds().get(0), type, false);
                    } else {
                        setValidNumber("1", node.getChilds().get(0), type, false);
                    }
                    node.getChilds().get(0).setSym(symSemantic.CONSTANT);
                    node.getChilds().get(0).deleteChilds();
                    a = node.getChilds().get(0).getValue().value;
                    break;
            }

            int secondChild = node.getChilds().get(1).getValue().sym;
            Object b = null;
            boolean BisNegative = false;

            switch (secondChild) {
                case symSemantic.CONSTANT:
                    setValidNumber(node.getChilds().get(1).getValue().value.toString(), node.getChilds().get(1), type, false);
                    b = node.getChilds().get(1).getValue().value;
                    break;
                case symSemantic.PLUS:
                case symSemantic.MUL:
                case symSemantic.DIVIDE:
                case symSemantic.MINUS:
                    if (node.getChilds().get(1).getChilds().size() > 0) {
                        aritmetica(node.getChilds().get(1), type);
                        if (node.getChilds().get(1).getValue().sym == symSemantic.CONSTANT) {
                            b = node.getChilds().get(1).getValue().value;
                        }
                    } else {
                        System.err.println("Error en la linea " + (node.getChilds().get(1).getValue().right + 1) + ", columna " + node.getChilds().get(1).getValue().left + " en el token " + node.getChilds().get(1).getValue().value + ": Valor en expresion aritmetica no valida");
                        errors.add("Error en la linea " + (node.getChilds().get(1).getValue().right + 1) + ", columna " + node.getChilds().get(1).getValue().left + " en el token " + node.getChilds().get(1).getValue().value + ": Valor en expresion aritmetica no valida");
                    }
                    break;
                case -1:
                    if (node.getChilds().get(1).getChilds().get(0).equals("-")) {
                        setValidNumber(node.getChilds().get(1).getChilds().get(1).getValue().value.toString(), node.getChilds().get(1), type, false);
                        node.getChilds().get(1).setSym(symSemantic.CONSTANT);
                        node.getChilds().get(1).deleteChilds();
                        b = node.getChilds().get(1).getValue().value;
                        BisNegative = true;
                    } else {
                        System.err.println("Error en la linea " + (node.getChilds().get(1).getChilds().get(0).getValue().right + 1) + ", columna " + node.getChilds().get(1).getChilds().get(0).getValue().left + " en el token " + node.getChilds().get(1).getChilds().get(0).getValue().value + ": Valor en expresion aritmetica no valida");
                        errors.add("Error en la linea " + (node.getChilds().get(1).getChilds().get(0).getValue().right + 1) + ", columna " + node.getChilds().get(1).getChilds().get(0).getValue().left + " en el token " + node.getChilds().get(1).getChilds().get(0).getValue().value + ": Valor en expresion aritmetica no valida");
                    }
                    break;
                case symSemantic.IDENTIFIER:
                    break;
                default:
                    System.err.println("Error en la linea " + (node.getChilds().get(1).getValue().right + 1) + ", columna " + node.getChilds().get(1).getValue().left + " en el token " + node.getChilds().get(1).getValue().value + ": Valor en expresion aritmetica no valida");
                    errors.add("Error en la linea " + (node.getChilds().get(1).getValue().right + 1) + ", columna " + node.getChilds().get(1).getValue().left + " en el token " + node.getChilds().get(1).getValue().value + ": Valor en expresion aritmetica no valida");
                    if (node.getValue().sym == symSemantic.PLUS
                            || node.getValue().sym == symSemantic.MINUS) {
                        setValidNumber("0", node.getChilds().get(1), type, false);
                    } else {
                        setValidNumber("1", node.getChilds().get(1), type, false);
                    }
                    node.getChilds().get(1).setSym(symSemantic.CONSTANT);
                    node.getChilds().get(1).deleteChilds();
                    b = node.getChilds().get(1).getValue().value;
                    break;
            }

            if (a != null && b != null) {
                Double A = Double.parseDouble(a.toString());
                if (AisNegative) {
                    A = A * -1;
                }
                Double B = Double.parseDouble(b.toString());
                if (BisNegative) {
                    B = B * -1;
                }

                String res = "";
                switch (symbol) {
                    case symSemantic.PLUS:
                        res = Double.toString(A + B);
                        break;
                    case symSemantic.MUL:
                        res = Double.toString(A * B);
                        break;
                    case symSemantic.DIVIDE:
                        res = Double.toString(A / B);
                        break;
                    case symSemantic.MINUS:
                        res = Double.toString(A - B);
                        break;
                }
                if (res.contains("-")) {
                    res = res.replace("-", "");
                    if (setValidNumber(res, node, type, true)) {
                        node.setSym(symSemantic.CONSTANT);
                        node.deleteChilds();
                    }
                } else if (setValidNumber(res, node, type, false)) {
                    node.setSym(symSemantic.CONSTANT);
                    node.deleteChilds();
                }
            }
        }
    }

    public static boolean setValidNumber(String value, TreeNode node, String type, boolean negative) {
        Double ret;
        if (value.matches("[0-9.]*")) {
            ret = Double.parseDouble(value);
        } else {
            Integer ascii = (int) value.replace("\'", "").charAt(0);
            ret = ascii.doubleValue();
        }
        node.deleteChilds();
        if (negative) {
            ret = ret * -1;
        }
        switch (type) {
            case "short":
                if (ret.shortValue() >= -32768 && ret.shortValue() <= 32767) {
                    node.setValue(ret.shortValue());
                    return true;
                }
                break;
            case "long":
                node.setValue(ret.longValue());
                return true;
            case "int":
            case "char":
                node.setValue(ret.intValue());
                return true;
            case "float":
                node.setValue(ret.floatValue());
                return true;
            case "double":
                node.setValue(ret);
                return true;
        }
        return false;
    }

    public static TableQuad cuadruplos(TreeNode ast, Table symbols, int scope) {
        if(true)return null;
        TableQuad cuadr = new TableQuad();
        try {
            ArrayList<TreeNode> childs = ast.getChilds();
            String op;
            String arg1;
            String arg2;
            String res = "null";
            /*if(ast.getValue().value.equals("postfix_expression")){
            System.out.println(ast);
        }*/
            if (ast.valueIsString("function_definition") && ast.getParent() == null) {
                cuadr.addRow("gen_sym", "" + scope);
            }
            for (TreeNode child : childs) {
                if (child.getValue().value.equals("postfix_expression")) {
                    //GENERATE IR POSTFIX EXPRESSION
                    ArrayList<TreeNode> postfix_childs = child.getChilds();
                    if (postfix_childs.size() >= 2) {
                        //Si tiene 3 hijos

                        //Primer hijo me dice que operador es
                        op = postfix_childs.get(0).getValue().value.toString();
                        //Obtengo el segundo hijo, normalmente no tiene hijos
                        Symbol arg1Sym = postfix_childs.get(1).getValue();

                        //Si el operador es una function_call entonces
                        if (op.equals("function_call") && postfix_childs.size() == 3) {
                            //Si es funcion y tiene parametros
                            //obtengo el tercer hijo, puede que tenga hijos
                            TreeNode params = postfix_childs.get(2);
                            if (arg1Sym.sym == symSemantic.SCANF) {
                                res = params.getValue().value.toString();
                                arg1 = symbols.search(res).type;
                                cuadr.addRow("scanf", arg1, res);
                            } else {
                                cuadr.concat(getParams(params));
                            }
                        }
                        //Si el segundo hijo es un operador ++ o --
                        if (arg1Sym.sym == symSemantic.INC_OP || arg1Sym.sym == symSemantic.DEC_OP) {
                            cuadr.addRow(arg1Sym.value.toString().substring(0, 1), op, "1", op);
                        } else if (arg1Sym.sym != symSemantic.SCANF) {
                            cuadr.addRow(op, "_" + arg1Sym.value.toString());
                        }
                        if (op.equals("function_call") && arg1Sym.sym != symSemantic.SCANF) {
                            cuadr.addRow("=", "RET", "t0");
                        }
                    }
                } else if (child.getValue().value.equals("=")) {
                    ArrayList<TreeNode> equal_childs = child.getChilds();
                    if (equal_childs.size() == 2) {
                        //Obtengo al que se le asigna
                        TreeNode resNode = equal_childs.get(0);
                        Symbol resSymbol = resNode.getValue();
                        if (resSymbol.sym != -1) {
                            res = resSymbol.value.toString();
                        } else if (resNode.valueIsString("unary_expression")) {
                            ArrayList<TreeNode> unary_childs = resNode.getChilds();
                            if (unary_childs.size() == 2) {
                                Symbol unary_symbol = unary_childs.get(0).getValue();
                                String id = unary_symbol.value.toString();
                                id += unary_childs.get(1).getValue().value.toString();
                                //Aqui la validacion si es un puntero o una direccion
                                if (unary_symbol.sym == symSemantic.ADRESS || unary_symbol.sym == symSemantic.MUL) {
                                    res = id;
                                }
                            }
                        }
                        //Lo que se le va a asignar
                        Symbol valueSym = equal_childs.get(1).getValue();
                        if (valueSym.sym == symSemantic.IDENTIFIER || valueSym.sym == symSemantic.CONSTANT || valueSym.sym == symSemantic.STRING_LITERAL) {
                            cuadr.addRow("=", valueSym.value.toString(), res);
                        } else {
                            cuadr.concat(cuadruplos(child, symbols, scope));
                            //RET contiene el valor de la aritmetica
                            cuadr.addRow("=", "t0", res);
                        }
                    }
                } else if (child.valueIsString("function_definition")) {
                    cuadr.addRow("gen_sym", "" + scope);
                    Table scope_table = symbols.getChilds().get(scope++);
                    cuadr.concat(cuadruplos(child, scope_table, 0));
                } else if (child.valueIsString("direct_declarator") && ast.valueIsString("function_definition")) {
                    ArrayList<TreeNode> direct_declarator_childs = child.getChilds();
                    if (direct_declarator_childs.get(0).getValue().value.equals("function_declarator")) {
                        arg1 = direct_declarator_childs.get(1).getValue().value.toString();
                        if (!arg1.equals("main")) {
                            arg1 = "_" + arg1;
                        }
                        arg1 = arg1 + ":";
                        cuadr.addRow("genfunc", arg1);
                    }
                } else if (child.getValue().value.equals("decl_stmnt_list") && !ast.getValue().value.equals("function_definition")) {
                    cuadr.concat(cuadruplos(child, symbols, 0));
                } else if (child.getValue().sym == symSemantic.MUL || child.getValue().sym == symSemantic.DIVIDE
                        || child.getValue().sym == symSemantic.PLUS || child.getValue().sym == symSemantic.MINUS) {
                    //Haciendo los calculos de aritmetica
                    Symbol left_op = child.getChilds().get(0).getValue();
                    Symbol right_op = child.getChilds().get(1).getValue();
                    cuadr.concat(cuadruplos(child, symbols, scope));
                    op = child.getValue().value.toString();
                    if (left_op.sym != symSemantic.CONSTANT && left_op.sym != symSemantic.IDENTIFIER) {
                        arg1 = "t0";
                    } else {
                        arg1 = left_op.value.toString();
                    }
                    arg2 = right_op.value.toString();
                    res = "t0";
                    cuadr.addRow(op, arg1, arg2, res);
                } else {
                    cuadr.concat(cuadruplos(child, symbols, scope));
                }
            }
            /*int t=0;
        cuadr.addRow("=", "&vb", "aaaa");
        cuadr.addRow("=", "vb", "main");
        cuadr.addRow("if=", "1", "1", "etiq1");
        cuadr.addRow("goto", "etiq2");
        cuadr.addRow("genetiq", "etiq1");
        cuadr.addRow("*", "10", "2", "t" + t++);
        cuadr.addRow("+", "t" + (t - 1), "x", "t" + t++);
        cuadr.addRow("+", "t" + (t - 1), "10", "t" + t++);
        cuadr.addRow("=", "t" + (t - 1), "y");
        t = 0;//LIMPIAR TEMPORALES
        cuadr.addRow("genetiq", "etiq2");
        cuadr.addRow("+", "10", "7", "x"); //ver si usar o no temporal
        cuadr.addRow("=", "10", "y");
        cuadr.addRow("=", "10", "z");

        arg1 = "" + (int) 'c';
        cuadr.addRow("=", arg1, "y");
        arg1 = "" + 1 * 4;
        cuadr.addRow("[]=", arg1, "3", "a");
        //GENERATE FUNCTION
        //PARAM 0
        arg1 = "" + 0 * 4;
        param1 = "t" + t++;
        cuadr.addRow("=[]", "a", arg1, param1);
        cuadr.addRow("param", "0", param1);
        //PARAM 1
        arg1 = "" + 0 * 4;
        param1 = "t" + t++;
        cuadr.addRow("=[]", "a", arg1, param1);
        cuadr.addRow("param", "1", param1);

        t = 0;//LIMPIAR TEMPORALES
        //Call funcition
        cuadr.addRow("call", "somefunc2");
        cuadr.addRow("=", "RET", "t"+t++);
             */
            //cuadr.print();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return cuadr;
        }
    }

    private static TableQuad getParams(TreeNode params) {
        ArrayList<TreeNode> paramChilds = params.getChilds();
        TableQuad cuadr = new TableQuad();
        for (TreeNode child : paramChilds) {
            Symbol child_value = child.getValue();
            if (child.getChilds().isEmpty() && params.valueIsString("expression")) {
                //Si no tiene hijos entonces agrego el parametro
                if (child_value.sym == symSemantic.STRING_LITERAL) {
                    cuadr.addMessage(child_value.value.toString());
                    cuadr.addRow("param", "_msgstring");
                } else {
                    cuadr.addRow("param", child_value.value.toString());
                }
            } else if (child.valueIsString("unary_expression")) {
                //Si es un puntero o una direccion
                ArrayList<TreeNode> unary_childs = child.getChilds();
                if (unary_childs.size() == 2) {
                    Symbol unary_symbol = unary_childs.get(0).getValue();
                    String param = unary_symbol.value.toString();
                    param += unary_childs.get(1).getValue().value.toString();
                    //Aqui la validacion si es un puntero o una direccion
                    if (unary_symbol.sym == symSemantic.ADRESS || unary_symbol.sym == symSemantic.MUL) {
                        cuadr.addRow("param", param);
                    }
                }
            } else {
                getParams(child);
            }
        }
        if (params.getParent().getValue().value.equals("postfix_expression") && params.getChilds().isEmpty()) {
            if (params.getValue().sym == symSemantic.STRING_LITERAL) {
                cuadr.addMessage(params.getValue().value.toString());
                cuadr.addRow("param", "_msgstring");
            } else {
                cuadr.addRow("param", params.getValue().value.toString());
            }
        }
        return cuadr;
    }
}
