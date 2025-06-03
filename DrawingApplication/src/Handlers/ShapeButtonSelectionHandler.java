/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handlers;

import Command.Command;
import Command.DrawingReceiver;
import Command.InsertShapeCommand;
import Shapes.IrregularPolygonShape;
import Shapes.Shape;
import Shapes.ShapeFactory;
import Shapes.ShapeType;
import Shapes.TextShape;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author ciroc
 */
public class ShapeButtonSelectionHandler {

    private ShapeType selectedShapeType;
    private IrregularPolygonShape currentPolygon;
    private boolean isDrawingPolygon;
    private Tooltip polygonTooltip;
    private final List<Circle> polygonSidesPreviewDots;
    private final PreviewHandler previewHandler;
    private final ColorSelectionHandler colorHandler;
    private final ShapeFactory shapeFactory = new ShapeFactory();
    private DrawingReceiver receiver;
    
        public ShapeButtonSelectionHandler(DrawingReceiver receiver, ColorSelectionHandler colorHandler, PreviewHandler previewHandler){
            this.receiver = receiver;
        this.colorHandler = colorHandler;
        this.previewHandler = previewHandler;
        this.polygonSidesPreviewDots = new ArrayList<>();
    }

    public void selectShape(ShapeType type) {
        selectedShapeType = type;
        if (type == ShapeType.POLYGON) {
            isDrawingPolygon = true;
            polygonTooltip = new Tooltip("Clicca per aggiungere punti. Clicca vicino al punto iniziale per chiudere.");
            Tooltip.install(receiver.getDrawingPane(), polygonTooltip);

            currentPolygon = (IrregularPolygonShape) shapeFactory.createShape(
                ShapeType.POLYGON.name(), 0, 0, 0, 0,
                colorHandler.getPerimetralColor(),
                colorHandler.getFillingColor()
            );

            polygonSidesPreviewDots.clear();
            receiver.getDrawingPane().setCursor(Cursor.CROSSHAIR);
        } else {
            receiver.getDrawingPane().setCursor(Cursor.DEFAULT);
        }
    }

    public void clearSelection() {
        cancelPolygonDrawing();
        selectedShapeType = null;
    }

    private void cancelPolygonDrawing() {
        if (isDrawingPolygon) {
            receiver.getDrawingPane().getChildren().removeAll(polygonSidesPreviewDots);
            Tooltip.uninstall(receiver.getDrawingPane(), polygonTooltip);
            receiver.getDrawingPane().setCursor(Cursor.DEFAULT);

            isDrawingPolygon = false;
            currentPolygon = null;
            polygonSidesPreviewDots.clear();
        }
    }

    public void handleMousePressed(MouseEvent e, DrawingStateHistory commandHistory) {
        if (selectedShapeType == ShapeType.TEXT) {
            Optional<TextShape> result = StringDialog(e);
            result.ifPresent(selectedShape -> {
                Command insertCmd = new InsertShapeCommand(selectedShape, receiver);
                commandHistory.executeCommand(insertCmd);            
            });

        } else if (selectedShapeType == ShapeType.POLYGON && isDrawingPolygon) {
            //e.consume();

            if (e.getButton() == MouseButton.PRIMARY) {
                double x = e.getX();
                double y = e.getY();

                currentPolygon.addPoint(x, y);

                Circle dot = new Circle(x, y, 3, Color.BLACK);
                receiver.getDrawingPane().getChildren().add(dot);
                polygonSidesPreviewDots.add(dot);

                if (currentPolygon.getPolygonPoints().size() > 2 && currentPolygon.isClosed()) {
                    receiver.getDrawingPane().setCursor(Cursor.DEFAULT);
                    Tooltip.uninstall(receiver.getDrawingPane(), polygonTooltip);
                    receiver.getDrawingPane().getChildren().removeAll(polygonSidesPreviewDots);

                    currentPolygon.computeBoundingBox();

                    Command insertCmd = new InsertShapeCommand(currentPolygon, receiver);
                    commandHistory.executeCommand(insertCmd);
                    // Reset
                    isDrawingPolygon = false;
                    selectedShapeType = null;
                    currentPolygon = null;
                    polygonSidesPreviewDots.clear();
                }
            }
            e.consume();
        } else {
            previewHandler.handleMousePressed(e);
        }
    }
    
    public void handleMouseDragged(MouseEvent e) {
        if (selectedShapeType != null && selectedShapeType != ShapeType.TEXT && selectedShapeType != ShapeType.POLYGON) {
            previewHandler.handleMouseDragged(e, selectedShapeType.name(), receiver.getDrawingPane());
        }
    }
    
    public void handleMouseReleased(MouseEvent e, DrawingStateHistory commandHistory) {
        // Ignora se non è selezionato nessuno strumento oppure se è TEXT/POLYGON (che non usano previewHandler)
        if (selectedShapeType == null || selectedShapeType == ShapeType.TEXT || selectedShapeType == ShapeType.POLYGON) {
            return;
        }

        // Esegui il disegno finale della forma con PreviewHandler
        Shape selectedShape = previewHandler.handleMouseReleased(
            e, selectedShapeType.name(), receiver.getDrawingPane(),
            colorHandler.getPerimetralColor(),
            colorHandler.getFillingColor()
        );

        if (selectedShape != null) {
            Command insertCmd = new InsertShapeCommand(selectedShape, receiver);
            insertCmd.execute();
            commandHistory.push(insertCmd);
        }

        // Torna automaticamente in modalità selezione
        clearSelection();
    }

    private Optional<TextShape> StringDialog(MouseEvent e){
        Dialog<TextShape> dialog = new Dialog<>();
        dialog.setTitle("Inserisci testo");
        dialog.setHeaderText("Inserisci il testo e la dimensione del carattere");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        TextField textField = new TextField("Testo");
        TextField fontSizeField = new TextField("20");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Testo:"), 0, 0);
        grid.add(textField, 1, 0);
        grid.add(new Label("Dimensione carattere:"), 0, 1);
        grid.add(fontSizeField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                try {
                    String content = textField.getText();
                    double fontSize = Double.parseDouble(fontSizeField.getText());
                    if (fontSize <= 0 || fontSize > 200) {
                        showError("La dimensione del font deve essere tra 1 e 200.");
                        return null;
                    }

                    TextShape s = new TextShape(content, e.getX(), e.getY());
                    s.setFontSize(fontSize);
                    s.setPerimetralColor(colorHandler.getPerimetralColor());
                    s.setInternalColor(colorHandler.getFillingColor());
                    s.setFXShape(s.toFXShape());
                    s.checkHeight();

                    return s;
                } catch (NumberFormatException ex) {
                    return null;
                }
            }
            return null;
        });

        return dialog.showAndWait();
    }

    public void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public ShapeType getSelectedShapeType() {
        return selectedShapeType;
    }
    
    public boolean isShapeSelected() {
        return selectedShapeType != null;
    }
}
