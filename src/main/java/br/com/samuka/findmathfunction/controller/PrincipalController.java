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
package br.com.samuka.findmathfunction.controller;

import br.com.samuka.findmathfunction.util.Genetica;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class PrincipalController implements Initializable {

    @FXML
    private TextField tfQtdIndividuos;

    @FXML
    private Text txQtdIndividuos;

    @FXML
    public LineChart<String, Number> lncGrafico;

    @FXML
    private Pane pnOpitions;

    @FXML
    private Pane pnGraphic;

    @FXML
    private TextField tfQtdGeracoes;

    @FXML
    private Button btExecutar;

    @FXML
    private Text txQtdGeracoes;

    @FXML
    private TextField tfPerMutacao;

    @FXML
    private Text txPerMutacao;
    
    private XYChart.Series serieObjetivo;

    @FXML
    private TableView tbResult;
    
    public static final String[] X_NAMES = new String[] {
            "1","2","3","4","5","6","7","8","9","10"
        };
    
    /**
     * Objetivo da aplicação
     */
    public static final double[] OBJETIVO = {
        0.67,
        2,
        4,
        6.67,
        10,
        14,
        18.67,
        24,
        30,
        36.67
    };

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        CategoryAxis eixoX = new CategoryAxis();
        NumberAxis eixoY = new NumberAxis();
        lncGrafico.setTitle("Função alvo");
        eixoX.setLabel("Valor de X");
        eixoY.setLabel("Resultado");

        lncGrafico.setData(FXCollections.observableArrayList(
                new XYChart.Series(
                        "Alvo",
                        FXCollections.observableArrayList(
                                plot(X_NAMES, 0.67,
                                        2,
                                        4,
                                        6.67,
                                        10,
                                        14,
                                        18.67,
                                        24,
                                        30,
                                        36.67)
                        )
                )
        ));
 
        ((TableColumn)tbResult.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<>("numGeracao"));
        ((TableColumn)tbResult.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<>("funcao"));
        ((TableColumn)tbResult.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<>("fitness"));
    }

    public static ObservableList<XYChart.Data<String, Number>> plot(String[] names, double... y) {
        final ObservableList<XYChart.Data<String, Number>> dataset = FXCollections.observableArrayList();
        int i = 0;
        while (i < y.length) {
            final XYChart.Data<String, Number> data = new XYChart.Data<>(names[i], y[i]);
            data.setNode(
                    new HoverNode(
                            y[i]
                    )
            );

            dataset.add(data);
            i++;
        }

        return dataset;
    }

    @FXML
    void onActionBtExecutar(ActionEvent ev) {

        int qtdIndividuos = 0;
        int qtdGeracao = 0;
        int perMutacao = 0;

        if (tfQtdIndividuos.getText().isEmpty()) {
            showAlert(AlertType.WARNING, "Campo vazio", "Informe a quantidade de indivíduos");
            return;
        }

        if (tfQtdGeracoes.getText().isEmpty()) {
            showAlert(AlertType.WARNING, "Campo vazio", "Informe a quantidade de gerações");
            return;
        }

        qtdIndividuos = Integer.parseInt(tfQtdIndividuos.getText());
        qtdGeracao = Integer.parseInt(tfQtdGeracoes.getText());

        try {
            if (!tfPerMutacao.getText().isEmpty()) {
                perMutacao = Integer.parseInt(tfPerMutacao.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            tbResult.getItems().clear();
            Genetica genetica = new Genetica(qtdIndividuos, qtdGeracao, true, perMutacao);
            genetica.setChat(lncGrafico);
            genetica.setTextIndividuos(txQtdIndividuos);
            genetica.setTextGeracao(txQtdGeracoes);
            genetica.setTextMutacao(txPerMutacao);
            genetica.setTable(tbResult);
            genetica.start();

            txQtdIndividuos.setText(String.valueOf(qtdIndividuos));
            txQtdGeracoes.setText(String.valueOf(1));
            txPerMutacao.setText(String.valueOf(perMutacao));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showAlert(AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
