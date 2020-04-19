/*
 * Copyright (C) 2020 Asconn
 *
 * This file is part of FindMathFunction.
 * FindMathFunction is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * FindMathFunction is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>
 */
package br.com.samuka.findmathfunction.model;

import br.com.samuka.findmathfunction.controller.PrincipalController;
import br.com.samuka.findmathfunction.util.Genetica;
import br.com.samuka.findmathfunction.util.Uteis;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class Tree {

    /**
     * Raiz da arvore
     */
    private Node root;

    private String lastFunction = "";

    DecimalFormat format = new DecimalFormat("0.##");

    /**
     * Armazena o resultado de cada calculo.
     */
    public double[] resultsCalc;

    /**
     * Armazena o tamanho da arvore.
     */
    private int height;

    public Tree() {
        root = null;
    }

    /**
     * Inicializa a arvore já com uma raiz.
     *
     * @param root Node raiz.
     */
    public Tree(Node root) {
        this.root = root;
    }

    /**
     * Insere um Nó à arvore
     *
     * @param pai Nó Pai
     * @param filho No Filho
     */
    public void insere(Node pai, Node filho) {

        if (root == null) {
            root = pai;
            insere(pai, filho);
        } else {

            filho.root = pai;

            if (null == filho.root.sonRight) { //caso o pai seja maior
                filho.root.sonRight = filho;
            } else if (null == filho.root.sonLeft) { //caso o filho seja menor.
                filho.root.sonLeft = filho;
            } else {
                insere(filho.root.sonRight, filho);
            }
        }
    }

    /**
     * Efetua a troca de node.
     *
     * @param nodeToChange Node local a ser trocado.
     * @param nodeNew Novo node.
     */
    public void change(Node nodeToChange, Node nodeNew) throws CloneNotSupportedException {

        nodeNew = nodeNew.clone();

        if (nodeToChange.root.sonLeft.equals(nodeToChange)) {
            nodeToChange.root.sonLeft = nodeNew;
        } else {
            nodeToChange.root.sonRight = nodeNew;
        }
        encontraElemento(nodeToChange, root);
        nodeNew.root = nodeToChange.root;
        String function = getFunctionAsString();
        lastFunction = function;
    }

    /**
     * @return Retorna a root da arvore
     */
    public Node root() {
        return this.root;
    }

    /**
     * @param node Nó a ser verificado
     * @return retorna o pai do nó
     */
    public Node getFather(Node node) {
        return node.root;
    }

    /**
     * Encontra os filhos do nó
     *
     * @param n Nó a ser verificado
     * @return ArrayList com os nós filhos encontrados
     */
    public ArrayList<Node> childrens(Node n) {

        ArrayList<Node> childrens = new ArrayList<>();

        if (n.sonRight != null) {
            childrens.add(n.sonRight);
        }

        if (n.sonLeft != null) {
            childrens.add(n.sonLeft);
        }

        return childrens;
    }

    /**
     * Verifica se um nó é interno. Nó interno são aqueles que possuem algum
     * filho
     *
     * @param node Nó a ser verificado
     * @return True para interno false caso externo.
     */
    public boolean verificaInterno(Node node) {
        return childrens(node).size() > 0;
    }

    /**
     * Verifica se o node é uma root
     *
     * @param node Nó a ser verificado
     * @return True caso seja root, false caso contrario
     */
    public boolean verificaRoot(Node node) {
        return node.root.value == root.value;
    }

    /**
     * Obtém todos os nós da arvore.
     *
     * @param nodeInicio Nó inicial, passar null para inicial da raiz.
     * @return ArrayList<Node> com todos os nós encontrados.
     */
    public ArrayList<Node> elementos(Node nodeInicio) {
        
        ArrayList<Node> lista = new ArrayList<Node>();
        lista.add(root);
        
        int i = 0;
        height = 1;
        while (i < lista.size()) {
            Node atual = lista.get(i);

            if (atual.sonRight != null) {
                lista.add(atual.sonRight);
            }

            if (atual.sonLeft != null) {
                lista.add(atual.sonLeft);
                height++;
            }

            i++;
        }

        return lista;
    }

    /**
     * Retorna o filho da esquerda do value em questão
     *
     * @param find nó a ser encontrado
     * @return Valor do nó da esquerda
     */
    public Node getFilhoEsquerda(Node find) {

        Node node = encontraElemento(find, null);

        if (null != node) {

            if (verificaInterno(node)) {
                if (null != node.sonRight) {
                    return node.sonRight;
                } else {
                    msg("Erro, Valor [" + find + "] não possui um filho à esquerda");
                }
            } else {
                msg("Erro, Valor [" + find + "] não está em um nó interno");
            }
        } else {
            msg("Erro, Valor [" + find + "] não encontrado");
        }

        return null;
    }

    /**
     * Retorna o filho da direita do value em questão
     *
     * @param find nó a ser encontrado
     * @return Valor do nó da esquerda
     */
    public Node getFilhoDireita(Node find) {

        Node node = encontraElemento(find, null);

        if (null != node) {

            if (verificaInterno(node)) {
                if (null != node.sonLeft) {
                    return node.sonLeft;
                } else {
                    msg("Erro, Valor [" + find + "] não possui um filho à direitra");
                }
            } else {
                msg("Erro, Valor [" + find + "] não está em um nó interno");
            }
        } else {
            msg("Erro, Valor [" + find + "] não encontrado");
        }

        return null;
    }

    public int getIrmao(Node find) {

        if (root == find) {
            msg("Erro, Valor [" + find + "] é a root, não possui irmão");
            return -1;
        }

        Node node = encontraElemento(find, null);

        if (null != node) {

            if (node.root.sonRight == node) {
                return node.root.sonLeft.value;
            } else {
                return node.root.sonRight.value;
            }

        } else {
            msg("Erro, Valor [" + find + "] não encontrado");
        }

        return -1;

    }

    /**
     * Encontra um elemento nó por seu value
     *
     * @return Nó encontrado
     */
    private Node encontraElemento(Node find, Node node) {

        if (node == null) {
            node = root;
        }

        if (node == find) {
            return node;
        } else if (verificaInterno(node)) {

            encontraElemento(find, node.sonLeft);
            encontraElemento(find, node.sonRight);
        }

        return null;

    }

    /**
     * Mensagem ao usuário.
     *
     * @param msg Mensagem a ser exibida
     */
    private void msg(String msg) {
        System.err.println(msg);
    }

    /**
     * Obtém a função da arvore.
     *
     * @return
     */
    private String getFunctionAsString() {

        ArrayList<Node> nodes = elementos(null);

        String rightOperations = getElementString(root.sonRight);//root.sonRight.toString();

        String midOperation = root.toString();

        String leftOperations = getElementString(root.sonLeft);//root.sonRight.toString();

        if (nodes.size() > 3) {

            String adicional = " (";
            if (leftOperations.charAt(0) != ' ') {
                adicional += " ";
            }
            leftOperations = adicional + leftOperations;

            if (rightOperations.contains(")")) {
                rightOperations = " (" + rightOperations + " ";
            }

        }
        String retorno = rightOperations + midOperation + leftOperations;

        retorno = Uteis.ltrim(retorno);
        retorno = Uteis.rtrim(retorno);

        return retorno;

    }

    private String getElementString(Node node) {

        String r = "";
        String m = "";
        String l = "";

        if (node.sonRight != null) {
            if (node.sonRight.numberOperation == Node.IND_OPERATION) {
                r = "( " + getElementString(node.sonRight) + " ";
            } else {

                r += " " + node.sonRight.toString() + " ";
            }
        } else {
            m += " ";
        }

        m += node.toString() + " ";

        if (node.sonLeft != null) {
            if (node.sonLeft.numberOperation == Node.IND_OPERATION) {
                l = "(" + getElementString(node.sonLeft) + " )";
            } else {
                l = node.sonLeft.toString() + " )";
            }
        }

        if (r.contains("  ")) {
            r = r.replace("  ", " ");
        }

        String retorno = r + m + l;

        if (retorno.contains("((")) {
            retorno = retorno.replace("((", "( (");
        }

        return retorno;
    }

    /**
     * O fenotipo é calculado pela função RMSE entre os resultados objetivos e o
     * resultado da arvore. Quanto menor o resultado mais próximo do objetivo.
     *
     * @return Valor do fenotipo.
     */
    public double getFenotipo() {

        double retorno = 0;

        String function = getFunctionAsString();
        //function = "x * ((  1 - 5 ) + ( 5 * ( -4 / -6 ) ) )";
        lastFunction = function;

        double results[] = new double[PrincipalController.OBJETIVO.length];
        resultsCalc = new double[PrincipalController.OBJETIVO.length];

        try {
            for (int i = 0; i < PrincipalController.OBJETIVO.length; i++) {

                resultsCalc[i] = executeFunction(function, i + 1);

                results[i] = Math.sqrt(Math.abs(resultsCalc[i] - PrincipalController.OBJETIVO[i]));
                //depois de usar já guarda formatado.
                resultsCalc[i] = format.parse(format.format(resultsCalc[i])).doubleValue();
            }

            double sum = 0;

            for (double result : results) {
                sum += result;
            }

            double finalSum = Math.pow((sum / PrincipalController.OBJETIVO.length), 2);

            if (finalSum == Double.NaN) {
                finalSum = 0;
            }

            retorno = format.parse(format.format(finalSum)).doubleValue();

            if (height > Genetica.QTD_NIVEL) {
                retorno = retorno * ((double) height / (Genetica.QTD_NIVEL));
            }

        } catch (ParseException e) {
            System.err.println("Função que retornou erro: " + function);
            System.exit(0);
        }

        return retorno;

    }

    private double executeFunction(String function, int xValue) throws ParseException {

        function = function.replace("x", String.valueOf(xValue));

        String[] replaced = function.split(" ");

        double retorno = execute(replaced);

        retorno = format.parse(format.format(retorno)).doubleValue();
        return retorno;
    }

    private double execute(String[] replaced) {

        double retorno = 0;

        int i = 0;

        double value1 = -500;
        double value2 = -500;
        String operation = "";

        for (String str : replaced) {

            if (isOperation(str)) {
                operation = str;
            } else if (str.equals("(")) {
                String[] novoArray = new String[replaced.length - i - 2];//tirar ( e )
                System.arraycopy(replaced, i + 1, novoArray, 0, replaced.length - i - 2);
                value2 = execute(novoArray);
            } else {
                if (value1 == -500) {
                    value1 = Integer.parseInt(str);
                } else {
                    value2 = Integer.parseInt(str);
                }
            }

            if (value1 != -500 && value2 != -500 && !operation.equals("")) {
                retorno = executeOperation(value1, value2, operation);
                break;
            }

            i++;
        }

        return retorno;
    }

    /**
     * Verifica se é uma operação.
     *
     * @param str
     * @return
     */
    private boolean isOperation(String str) {
        return str.equals("+")
                || str.equals("-")
                || str.equals("*")
                || str.equals("/")
                || str.equals("^");
    }

    /**
     * Executa a operação a partir dos números informados.
     *
     * @param value1 Valor 1
     * @param value2 Valor 2
     * @param operation Operação a ser calculada.
     * @return
     */
    private double executeOperation(double value1, double value2, String operation) {
        double retorno = 0;

        try {
            switch (operation) {
                case "+":
                    retorno = value1 + value2;
                    break;
                case "-":
                    retorno = value1 - value2;
                    break;
                case "*":
                    retorno = value1 * value2;
                    break;
                case "/":
                    retorno = value1 / value2;
                    break;
                case "^":
                    retorno = Math.pow(value1, value2);
                    break;
            }
        } catch (Exception e) {
            System.err.println(value1 + " " + operation + " " + value2);
        }

        return retorno;
    }

    @Override
    public String toString() {
        if (lastFunction.isEmpty()) {
            return getFunctionAsString();
        } else {
            return lastFunction;
        }

    }

    @Override
    public Tree clone() throws CloneNotSupportedException {

        Node atualRoot = new Node(this.root.numberOperation, this.root.value);

        Tree retorno = new Tree(atualRoot);

        ArrayList<Node> elementos = elementos(root);

        String str = getFunctionAsString();
        List<Node> roots = new ArrayList();
        roots.add(atualRoot);
        int count = 0;
        for (int i = 1; i < elementos.size(); i++) {

            if (count == 2) {
                roots.remove(0);
                count = 0;
            }

            Node node = elementos.get(i).clone();
            node.sonLeft = null;
            node.sonRight = null;
            if (node.numberOperation == Node.IND_OPERATION) {
                retorno.insere(roots.get(0), node);
                roots.add(node);
            } else {
                node.root = null;
                retorno.insere(roots.get(0), node);
            }
            count++;
        }

        lastFunction = "";

        return retorno;

    }

}
