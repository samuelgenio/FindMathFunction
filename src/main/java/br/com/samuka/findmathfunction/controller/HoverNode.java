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

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Samuel
 */
public class HoverNode extends StackPane {

    HoverNode(double value) {
        setPrefSize(15, 15);

        final Label label = createDataThresholdLabel(value);

        setOnMouseEntered((MouseEvent mouseEvent) -> {
            getChildren().setAll(label);
            setCursor(Cursor.NONE);
            toFront();
        });
        setOnMouseExited((MouseEvent mouseEvent) -> {
            getChildren().clear();
            setCursor(Cursor.CROSSHAIR);
        });
    }

    private Label createDataThresholdLabel(double value) {
        final Label label = new Label(value + "");
        label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
        label.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");

        label.setTextFill(Color.FIREBRICK);

        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        return label;
    }
}
