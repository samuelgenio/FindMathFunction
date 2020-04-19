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

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class Node {
    
    /**
     * Operação de adição
     */
    public static final int OPE_ADICAO = 0;
    
    /**
     * Operação de subtração
     */
    public static final int OPE_SUBTRACAO = 1;
    
    /**
     * Operação de multiplicação
     */
    public static final int OPE_MULTIPLICACAO = 2;
    
    /**
     * Operação de divisão
     */
    public static final int OPE_DIVISAO = 3;
    
    /**
     * Operação de potencialização
     */
    public static final int OPE_POTENCIALIZACAO = 4;
    
    public static final int IND_NUMBER = 0;
    
    public static final int IND_OPERATION = 1;
    
    public static final int VALUE_X = 1000;
    
    /**
     * Valor mínimo a ser gerado.
     */
    private static final int VALUE_MIN = -20;
    
    /**
     * Valor máximo a ser gerado.
     */
    private static final int VALUE_MAX = 20;
    
    /**
     * Raiz do nó
     */
    public Node root;
    
    public Node sonLeft;
    
    public Node sonRight;
    
    public int numberOperation;

    public int value;
    
    /**
     * 
     * 
     * @param numberOperation Define se o nó é uma operação ou um valor.
     * @param value
     * @see Node.IND_NUMBER ou Node.IND_OPERATION
     */
    public Node(int numberOperation, int value) {
        this.numberOperation = numberOperation;
        this.value = value;
    }
    
    public int getNodePosition(int indexPosition) {
    
        int retorno = -1;
        
        return retorno;
        
    }
    
    public static Node getNode(int numberOperation) {
    
        int value = -1;
        
        if(numberOperation == Node.IND_OPERATION) {
            value = (int) ((Math.random() * 4) + 1);
        } else {           
            boolean isNegative = ((int) (Math.random() * 2)) >= 1;
            value = (int) (Math.random() * VALUE_MAX) * (isNegative ? -1 : 1);
        }
        
        Node node = new Node(numberOperation, value);
        
        return node;
        
    }

    @Override
    public String toString() {
        
        String retorno = "";
        
        if(this.numberOperation == IND_NUMBER) {
            retorno = value == VALUE_X ? "x" : String.valueOf(value);
        } else {
            switch(value) {
                case OPE_ADICAO:
                    retorno = "+";
                    break;
                case OPE_SUBTRACAO:
                    retorno = "-";
                    break;
                case OPE_MULTIPLICACAO:
                    retorno = "*";
                    break;
                case OPE_DIVISAO:
                    retorno = "/";
                    break;
                case OPE_POTENCIALIZACAO:
                    retorno = "^";
                    break;
            }
        }
        
        return retorno;
        
    }

    @Override
    public Node clone() throws CloneNotSupportedException {
        Node node = new Node(numberOperation, value);
        node.root = (this.root != null) ? new Node(this.root.numberOperation, this.root.value) : null;
        node.sonLeft = (this.sonLeft != null) ? new Node(this.sonLeft.numberOperation, this.sonLeft.value) : null;
        node.sonRight = (this.sonRight != null) ? new Node(this.sonRight.numberOperation, this.sonRight.value) : null;
        
        return node;
    }

    public boolean equals(Node node) {
        
        boolean retorno = false;
        
        if(node.numberOperation == this.numberOperation && node.value == this.value) {
            retorno = true;
        }
        
        return retorno;
        
    }
    
    
    
}
