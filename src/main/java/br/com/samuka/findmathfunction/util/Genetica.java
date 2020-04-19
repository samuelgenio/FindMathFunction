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

import br.com.samuka.findmathfunction.controller.PrincipalController;
import static br.com.samuka.findmathfunction.controller.PrincipalController.plot;
import br.com.samuka.findmathfunction.model.Node;
import br.com.samuka.findmathfunction.model.TableRow;
import br.com.samuka.findmathfunction.model.Tree;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class Genetica extends Thread {

    private final int QTD_CELULA = 8;

    public static int QTD_NIVEL = 4;

    /**
     * Quantidade de gerações.
     */
    private int qtdGeracao;

    /**
     * Quantidade de individuos da população.
     */
    private int qtdGenotipos;

    /**
     * Genotipos utilizados.
     */
    Tree[] genotipos;

    /**
     * Fenotipos dos genotipos atuais.
     */
    Double[] fenotipos;

    File file = new File("result.txt");

    FileWriter writer;

    private int geracaoAtual;

    private boolean isElitismo;

    List<Integer> listIndexesRoleta;

    private double qtdIndividuosMutacao;

    private double perMutacao;

    DecimalFormat format = new DecimalFormat("0.##");

    private LineChart<String, Number> lncGrafico;

    private Text txQtdIndividuos, txPerMutacao, txQtdGeracoes;

    private TableView tbResult;

    /**
     *
     * @param qtdGenotipos Quantidade de indivíduos que a população terá.
     * @param qtdGeracao Quantidade de derações serão processadas.
     * @param isElitismo Caso true, método aplicado será Elitismo, false para
     * Proporcial Aptidão
     * @param perMutacao Percentual de mutação nos indivíduos de cada geração
     */
    public Genetica(int qtdGenotipos, int qtdGeracao, boolean isElitismo, double perMutacao) {
        this.qtdGenotipos = qtdGenotipos;
        this.isElitismo = isElitismo;
        this.perMutacao = perMutacao;
        this.qtdIndividuosMutacao = qtdGenotipos * perMutacao / 100;
        if (qtdGeracao != -1) {
            this.qtdGeracao = qtdGeracao;
        }
        try {
            writer = new FileWriter(file);
        } catch (IOException ex) {
        }

    }

    public void setChat(LineChart chart) {
        this.lncGrafico = chart;
    }

    public void setTextIndividuos(Text text) {
        this.txQtdIndividuos = text;
    }

    public void setTextGeracao(Text text) {
        this.txQtdGeracoes = text;
    }

    public void setTextMutacao(Text text) {
        this.txPerMutacao = text;
    }

    public void setTable(TableView table) {
        this.tbResult = table;
    }

    @Override
    public void run() {
        execute();
    }

    /**
     * Executa o processo da criação da geração.
     */
    private void execute() {

        generatePopulation();

        geracaoAtual = 0;

        while (geracaoAtual < qtdGeracao - 1) {

            calculateFenotipos();

            boolean isResult = false;
            int i = 0;
            while (i < fenotipos.length) {
                if (fenotipos[i] == 0) {
                    System.err.println("resposta encontrada na geração[" + (geracaoAtual + 1) + "]: " + i);
                    isResult = true;
                }
                i++;
            }

            if (isResult) {
                break;
            }

            nextGeneration();

            geracaoAtual++;
        }

        //calcula os fenotipos após ultima geração processada.
        calculateFenotipos();

        QuickSort.genotipo = genotipos;

        QuickSort.order(fenotipos, 0, fenotipos.length - 1);

        genotipos = (Tree[]) QuickSort.genotipo;

        updateChart(genotipos[0].resultsCalc, geracaoAtual + 1);

        int i = 0;

        try {

            String resultLine = "";
            String genotipoLine = "";

            for (Tree genotipo : genotipos) {
                genotipoLine += "[" + genotipo + "]";
                resultLine += fenotipos[i];

                if (i + 1 < genotipos.length) {
                    resultLine += " - ";
                    genotipoLine += " - ";
                }

                i++;
            }

            writer.write((geracaoAtual + 1) + "º Geração[G] -> ");
            writer.write(genotipoLine + "\r\n");
            writer.write((geracaoAtual + 1) + "º Geração[F] -> ");
            writer.write(resultLine + "\r\n");
            writer.flush();
        } catch (IOException ex) {
        }
    }

    private void nextGeneration() {

        QuickSort.genotipo = genotipos;

        QuickSort.order(fenotipos, 0, fenotipos.length - 1);

        genotipos = (Tree[]) QuickSort.genotipo;

        int i = 0;

        try {

            String resultLine = "";
            String genotipoLine = "";

            for (Tree genotipo : genotipos) {
                genotipoLine += "[" + genotipo + "]";
                resultLine += fenotipos[i];

                if (i + 1 < genotipos.length) {
                    resultLine += " - ";
                    genotipoLine += " - ";
                }

                i++;
            }

            writer.write((geracaoAtual + 1) + "º Geração[G] -> ");
            writer.write(genotipoLine + "\r\n");
            writer.write((geracaoAtual + 1) + "º Geração[F] -> ");
            writer.write(resultLine + "\r\n");
            writer.flush();
        } catch (IOException ex) {
        }

        Tree[] nextGeneration = new Tree[qtdGenotipos / 2];

        updateChart(genotipos[0].resultsCalc, geracaoAtual + 1);

        getGenotiposElitismo(nextGeneration);

        genotipos = new Tree[genotipos.length];

        double qtdMutacaoAtual = qtdIndividuosMutacao;

        i = 0;
        int indexGenotiposAdded = 0;
        while (i < nextGeneration.length) {

            boolean isMutacao = false;

            if (qtdMutacaoAtual > 0) {

                if (qtdMutacaoAtual < 1) {
                    isMutacao = (int) (Math.random() * 1) < qtdMutacaoAtual;
                    qtdMutacaoAtual = isMutacao ? - 1 : -0;
                } else {
                    if ((int) (Math.random() * 1) > 0.5) {
                        isMutacao = true;
                        qtdMutacaoAtual--;
                    }
                }

            }

            Tree son2 = null;
            if (i + 1 == nextGeneration.length) {
                son2 = nextGeneration[i];
            } else {
                son2 = nextGeneration[i + 1];
            }
            
            Tree[] sons = getSon(nextGeneration[i], son2, isMutacao);

            genotipos[indexGenotiposAdded++] = nextGeneration[i];
            genotipos[indexGenotiposAdded++] = son2;
            if (i + 1 < nextGeneration.length) { 
                genotipos[indexGenotiposAdded++] = sons[0];
            }
            if (i + 1 < nextGeneration.length) { 
                genotipos[indexGenotiposAdded++] = sons[1];
            }
            i = i + 2;
        }
    }

    /**
     * Produz a próxima geração.
     *
     * @param ancient1 Ancestral 1
     * @param ancient2 Ancestral 2
     * @param isMutacao Indica se os filhos sofreram mutação.
     * @return Tree[] com os dois filhos gerados
     */
    private Tree[] getSon(Tree ancient1, Tree ancient2, boolean isMutacao) {

        Tree son1 = null;
        Tree son2 = null;

        try {

            Tree anc1 = ancient1.clone();
            Tree anc2 = ancient2.clone();

            //obtendo filho 1
            Node nodeAncient1 = getNodeSelected(anc1, true);
            Node nodeAncient2 = null;
            if (nodeAncient1 != null) {
                //obtendo filho 2
                nodeAncient2 = getNodeSelected(anc2, true);
            }

            if (nodeAncient2 != null) {
                anc1.change(nodeAncient1, nodeAncient2);
                anc2.change(nodeAncient2, nodeAncient1);
                son1 = ancient1;
                son2 = ancient2;
            } else {
                // gera um filho aleatório.
                int qtdNiveis = (int) (Math.random() * QTD_NIVEL + 1);
                son1 = getGenotipo(qtdNiveis);
            }

            if (son2 == null) {

                anc1 = ancient1.clone();
                anc2 = ancient2.clone();

                //obtendo filho 2
                nodeAncient1 = getNodeSelected(anc1, true);
                nodeAncient2 = null;
                if (nodeAncient1 != null) {
                    //obtendo filho 2
                    nodeAncient2 = getNodeSelected(anc2, true);
                }

                if (nodeAncient2 != null) {
                    ancient1.change(nodeAncient1, nodeAncient2);
                    //ancient2.change(nodeAncient2, nodeAncient1);
                    son2 = ancient1;
                } else {
                    // gera um filho aleatório.
                    int qtdNiveis = (int) (Math.random() * QTD_NIVEL + 1);
                    son2 = getGenotipo(qtdNiveis);
                }
            }

            if (isMutacao) {
                int qtdNiveis = (int) (Math.random() * QTD_NIVEL - 1);
                if (qtdNiveis == 0) {
                    qtdNiveis++;
                }
                Tree individuoMutacao = getGenotipo(qtdNiveis);
                boolean isSon1 = (int) (Math.random() * 5) > 3;

                if (isSon1) {
                    Node node = getNodeSelected(son1, false);
                    son1.change(node, individuoMutacao.root().sonLeft);
                } else {
                    Node node = getNodeSelected(son2, false);
                    son2.change(node, individuoMutacao.root().sonLeft);
                }

            }
        } catch (CloneNotSupportedException | ParseException e) {
        }

        return new Tree[]{son1, son2};
    }

    /**
     * Obtém o Node de seleção para aplicar o crossover
     *
     * @param ancient Arvore a ser varrida.
     * @return
     */
    private Node getNodeSelected(Tree ancient, boolean returnNull) {
        Node retorno = null;

        ArrayList<Node> nodesAncient = ancient.elementos(null);

        //não passa pela raiz.
        for (int i = 1; i < nodesAncient.size(); i++) {
            boolean isSelected = (int) (Math.random() * 5) > 3;
            if (isSelected) {
                return nodesAncient.get(i);
            }
        }

        if (!returnNull) {
            return getNodeSelected(ancient, returnNull);
        }

        return retorno;
    }

    /**
     * Obtém somente os genotipos mais fortes.
     */
    private void getGenotiposElitismo(Tree[] nextGeneration) {

        int half = genotipos.length / 2;

        int count = 0;
        while (half > 0) {
            nextGeneration[count] = genotipos[--half];
            count++;
        }
    }

    /**
     * Calcula os fenotipos dos genotipos. O fenotipo é a quantidade é a
     * distancia entre os resultados esperados e o resultado alcançado pela
     * função. Quanto menor o resultado melhor.
     *
     */
    private void calculateFenotipos() {

        fenotipos = new Double[genotipos.length];

        int i = 0;
        for (Tree genotipo : genotipos) {
            fenotipos[i] = genotipo.getFenotipo();
            i++;
        }
    }

    /**
     * Cria a população.
     */
    private void generatePopulation() {

        genotipos = new Tree[qtdGenotipos];

        try {
            int i = 0;
            while (i < qtdGenotipos) {

                int qtdNiveis = (int) (Math.random() * QTD_NIVEL + 1);
                //int qtdNiveis = 4;

                genotipos[i] = getGenotipo(qtdNiveis);

                i++;
            }
        } catch (ParseException e) {
        }
    }

    /**
     * Obtém a arvore que representa um genótipo.
     *
     * @param qtdNiveis Quantidade de raizes existentes (Operações)
     * @return
     * @throws ParseException
     */
    private Tree getGenotipo(int qtdNiveis) throws ParseException {

        Node root = Node.getNode(Node.IND_OPERATION);
        Node son = null;

        Tree tree = new Tree(root);

        int i = 1;

        int qtdX = (qtdNiveis > 1) ? (int) (Math.random() * qtdNiveis) - 1 : 1;

        if (qtdX < 1) {
            qtdX = 1;
        }

        while (i <= qtdNiveis) {

            son = Node.getNode(Node.IND_NUMBER);

            if (qtdX > 0) {
                son.value = Node.VALUE_X;
                qtdX--;
            }

            tree.insere(root, son);

            if (i == qtdNiveis) {
                son = Node.getNode(Node.IND_NUMBER);
                tree.insere(root, son);
            } else {

                son = Node.getNode(Node.IND_OPERATION);
                tree.insere(root, son);
                root = son;// nova raiz pois é operação.
            }
            i++;
        }

        return tree;
    }

    /**
     * Atualiza o grafico com o objeto mais próximo do alvo.
     */
    private void updateChart(double[] values, int geracao) {

        Platform.runLater(() -> {
            try {
                txQtdIndividuos.setText(String.valueOf(genotipos.length));
                txQtdGeracoes.setText(String.valueOf(geracao));
                txPerMutacao.setText(String.valueOf(perMutacao) + "%");
            } catch (Exception e) {
            }
            
            try {
                
                double val = fenotipos[0].isNaN() || fenotipos[0].isInfinite() ? 0 : fenotipos[0];
                
                try {
                    val = format.parse(format.format(val)).doubleValue();
                } catch (Exception e) {
                    val = 0;
                }
                
                tbResult.getItems().add(new TableRow(geracao, genotipos[0].toString(), val));
            } catch (Exception e) {
            }
            
            try {
                lncGrafico.getData().remove(1);
            } catch (Exception e) {
            }
            
            lncGrafico.getData().add(new XYChart.Series(
                    "Ger. " + (geracaoAtual + 1),
                    FXCollections.observableArrayList(
                            plot(PrincipalController.X_NAMES, values)
                    )
            ));
        });
    }

}
