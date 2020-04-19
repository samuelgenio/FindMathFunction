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
package br.com.samuka.findmathfunction.util;

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class QuickSort {

    public static Object[] genotipo;
    
    public static void order(Double[] vetor, int inicio, int fim) {
        if (inicio < fim) {
            int posicaoPivo = separar(vetor, inicio, fim);
            order(vetor, inicio, posicaoPivo - 1);
            order(vetor, posicaoPivo + 1, fim);
        }
    }

    private static int separar(Double[] vetor, int inicio, int fim) {
        double pivo = vetor[inicio];
        Object pivoGenotipo = genotipo[inicio];
        
        int i = inicio + 1, f = fim;
        while (i <= f) {
            if (vetor[i] <= pivo) {
                i++;
            } else if (pivo < vetor[f]) {
                f--;
            } else {
                double troca = vetor[i];
                vetor[i] = vetor[f];
                vetor[f] = troca;
                
                Object trocaGenotipo = genotipo[i];
                genotipo[i] = genotipo[f];
                genotipo[f] = trocaGenotipo;
                
                i++;
                f--;
            }
        }
        vetor[inicio] = vetor[f];
        vetor[f] = pivo;
        
        genotipo[inicio] = genotipo[f];
        genotipo[f] = pivoGenotipo;
        
        return f;
    }

}
