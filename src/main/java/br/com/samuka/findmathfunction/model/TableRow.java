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

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class TableRow {
    
    private SimpleIntegerProperty numGeracao;
    
    private SimpleStringProperty funcao;
    
    private SimpleDoubleProperty fitness;

    public TableRow(int numGeracao, String funcao, double fitness) {
        this.numGeracao = new SimpleIntegerProperty(numGeracao);
        this.funcao = new SimpleStringProperty(funcao);
        this.fitness = new SimpleDoubleProperty(fitness);
    }

    /**
     * @return the numGeracao
     */
    public Integer getNumGeracao() {
        return numGeracao.getValue();
    }

    /**
     * @param numGeracao the numGeracao to set
     */
    public void setNumGeracao(SimpleIntegerProperty numGeracao) {
        this.numGeracao = numGeracao;
    }

    /**
     * @return the funcao
     */
    public String getFuncao() {
        return funcao.getValue();
    }

    /**
     * @param funcao the funcao to set
     */
    public void setFuncao(SimpleStringProperty funcao) {
        this.funcao = funcao;
    }

    /**
     * @return the fitness
     */
    public Double getFitness() {
        return fitness.getValue();
    }

    /**
     * @param fitness the fitness to set
     */
    public void setFitness(SimpleDoubleProperty fitness) {
        this.fitness = fitness;
    }
    
    
    
}
