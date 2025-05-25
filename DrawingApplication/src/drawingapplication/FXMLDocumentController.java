/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package drawingapplication;

import Command.BringForwardCommand;
import Command.BringToFrontCommand;
import Command.ChangeColorCommand;
import Command.Clipboard;
import Command.Command;
import Command.CutCommand;
import Command.DeleteCommand;
import Command.InsertShapeCommand;
import Command.PasteCommand;
import Command.SendBackwardCommand;
import Command.SendToBackCommand;
import Handlers.ColorSelectionHandler;
import Handlers.DrawingStateHistory;
import Handlers.PreviewHandler;
import Handlers.ShapeIOManager;
import Handlers.ShapeSelectionHandler;
import Shapes.Shape;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 *
 * @author genna
 */
public class FXMLDocumentController implements Initializable {

    //FXML View
    @FXML
    private Pane drawingPane;
    @FXML
    private HBox toolbar;
    @FXML
    private RadioButton perimeterRadio, fillRadio;
    @FXML
    private Button purpleButton, blackButton, pinkButton, yellowButton, greenButton, blueButton, redButton, whiteButton, cyanButton;
    @FXML
    private HBox colorToolbarSection;
    @FXML
    private ToggleGroup shapeToggleGroup;
    @FXML
    private ToggleButton lineButton, rectangleButton, ellipseButton;
    @FXML
    private ToggleGroup radioColorButtonToggleGroup;
    @FXML
    private Button salvaButton;
    @FXML
    private Button CaricaButton;
    /*
    @FXML
    private Button cutButton;
    */
    
    // Handler per la selezione colori
    private final ColorSelectionHandler colorHandler = new ColorSelectionHandler();
    
    //Handler per il salvataggio e caricamento da file
    private final ShapeIOManager shapeIOManager = new ShapeIOManager();
    
    //Handler per preview della forma
    private final PreviewHandler previewHandler = new PreviewHandler();
    
    private final DrawingStateHistory commandHistory = new DrawingStateHistory();
    //Handler per la selezione della forma
    private final ShapeSelectionHandler selectionHandler = new ShapeSelectionHandler(commandHistory);
    
    //Clipboard per copiare le forme
    private Clipboard clipboard = new Clipboard();
        
    private List<Shape> drawShapes = new ArrayList<>();
    private ContextMenu shapeMenu;
    private double lastContextX;
    private double lastContextY;
    private ContextMenu canvasMenu;
    private MenuItem pasteMenuItem;
    private String selectedShapeType = null;
    @FXML
    private Button undoButton;
    MenuItem bringToFront;
    MenuItem bringForward;
    MenuItem sendBackward;
    MenuItem sendToBack;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ComboBox<String> zoomComboBox;

    private final DoubleProperty zoomProperty = new SimpleDoubleProperty(1.0);
    @FXML
    private Group zoomGroup;
    @FXML
    private Group contentGroup;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        createShapeMenu();
        createCanvasMenu();
        
        // Sezione di controlli per far funzionare bene lo scrolling
        scrollPane.setPannable(true);
        scrollPane.setFitToWidth(false);
        scrollPane.setFitToHeight(false);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        scrollPane.addEventFilter(MouseEvent.ANY, event -> {
            if (event.getTarget() == scrollPane) {
                event.consume();
            }
        });
        scrollPane.setPannable(false);

        // Sezione di controlli per la dimensione del riquadro di disegno
        AnchorPane.setBottomAnchor(drawingPane, 0.0);
        AnchorPane.setLeftAnchor(drawingPane, 0.0);
        AnchorPane.setRightAnchor(drawingPane, 0.0);
        VBox.setVgrow(drawingPane, Priority.ALWAYS);

        // Sezione di metodi utili alla visualizzazione dell'icona del tasto di undo
        ImageView icon = new ImageView(getClass().getResource("/icons/undo_icon.png").toExternalForm());
        undoButton.setGraphic(icon);
        icon.setFitWidth(30);
        icon.setFitHeight(30);
        colorHandler.init(perimeterRadio, fillRadio);

        //Registra i pulsanti per la selezione dei colori
        colorHandler.registerColorButtons(Arrays.asList(
            purpleButton, blackButton, pinkButton, yellowButton,
            greenButton, blueButton, redButton, whiteButton, cyanButton
        ));

        //Evita che la forma vada oltre l'area di disegno
        //Imposta tooltip e immagine dei pulsanti delle forme
        setTooltipAndImage(rectangleButton, "Rectangle", "rectangle_icon.png");
        setTooltipAndImage(ellipseButton, "Ellipse", "ellipse_icon.png");
        setTooltipAndImage(lineButton, "Line", "line_icon.png");

        //Pressione del mouse
        drawingPane.setOnMousePressed(e -> {
            if (selectedShapeType != null) {
                previewHandler.handleMousePressed(e);
            }
        });

        //Trascinamento mouse
        drawingPane.setOnMouseDragged(e -> {
            if (selectedShapeType != null) {
                previewHandler.handleMouseDragged(e, selectedShapeType, drawingPane);
                drawingPaneSizeDynamicUpdate(drawingPane);
            }
        });

        //Rilascio del mouse
        drawingPane.setOnMouseReleased(e -> {
            if (selectedShapeType != null) {
                Shape s = previewHandler.handleMouseReleased(e, selectedShapeType, drawingPane,
                        colorHandler.getPerimetralColor(), colorHandler.getFillingColor());

                Command insertCmd = new InsertShapeCommand(s, drawShapes, drawingPane);
                insertCmd.execute();
                commandHistory.push(insertCmd);
            }
        });

        /**
         * Metodo per gestire il click del mouse sul riquadro di disegno
         * 
         * @author ciroc
         */
        drawingPane.setOnMouseClicked(event -> {
            shapeMenu.hide();
            canvasMenu.hide();

            // Gestione selezione
            selectionHandler.handleSelection(event, drawShapes, drawingPane);

            // Dopo la selezione, se è tasto destro...
            if (event.getButton() == MouseButton.SECONDARY) {
                if (selectionHandler.getSelectedShape() != null) {
                    updateLayerMenuItems();
                    shapeMenu.show(drawingPane, event.getScreenX(), event.getScreenY());
                } else {
                    lastContextX = event.getX();
                    lastContextY = event.getY();
                    canvasMenu.show(drawingPane, event.getScreenX(), event.getScreenY());
                }
            }
        });
        
        zoomComboBox.getItems().addAll(
            Arrays.asList("100 %", "200 %", "300 %", "400 %")
        );
        zoomComboBox.getSelectionModel().select("100 %");

        zoomGroup.scaleXProperty().bind(zoomProperty);
        zoomGroup.scaleYProperty().bind(zoomProperty);
        zoomProperty.set(1.0);

        /* Facoltativo: permette panning con drag del mouse */
        scrollPane.setPannable(false);
        zoomGroup.scaleXProperty().bind(zoomProperty);
        zoomGroup.scaleYProperty().bind(zoomProperty);
        
        // dimensione di partenza della "tela"
        drawingPane.setPrefSize(880, 504);
        drawingPane.setMinSize(880, 504);
    }

    
    @FXML
    private void selectRectangle(ActionEvent event) {
        if (rectangleButton.isSelected()) {
            selectedShapeType = "RECTANGLE";
        } else {
            selectedShapeType = null;
        }
    }

    @FXML
    private void selectEllipse(ActionEvent event) {
        if (ellipseButton.isSelected()) {
            selectedShapeType = "ELLIPSE";
        } else {
            selectedShapeType = null;
        }
    }

    @FXML
    private void selectLine(ActionEvent event) {
        if (lineButton.isSelected()) {
            selectedShapeType = "LINE";
        } else {
            selectedShapeType = null;
        }
    }

    private void setTooltipAndImage(ToggleButton button, String tooltip, String imageFile) {
        button.setTooltip(new Tooltip(tooltip));
        setImage(button, imageFile, 22);
    }

    private void setImage(Labeled node, String imageFile, int dimension) {
        Image imageOk = new Image(getClass().getResourceAsStream("/icons/" + imageFile));
        //Image imageOk = new Image("file:src/icons/" + imageFile);
        ImageView img = new ImageView(imageOk);
        img.setPreserveRatio(true);
        img.setFitHeight(dimension);
        img.setFitWidth(dimension);
        node.setGraphic(img);
    }

    /**
     * Metodo dedito alla gestione della selezione delle forme
     * 
     * @param event evento di click sul riquadro di disegno che può scatenare l'azione di selezione
     * 
     * @author ciroc
     */
    @FXML
    private void shapeSelectionHandler(javafx.scene.input.MouseEvent event) {
        // Vengono salvate le coordinate del click sul riquadro di disegno
        double x = event.getX();
        double y = event.getY();
        
        Shape newSelectedShape = null;

        // Viene scorso l'intorno del punto cliccato per verificare se ricade in una forma
        for (int i = drawShapes.size() - 1; i >= 0; i--) {
            Shape shape = drawShapes.get(i);

            if (shape.toFXShape().contains(x, y)) {
                // All'occorrenza del click entro la forma viene selezionata la forma stessa
                newSelectedShape = shape;
                break;
            }
        }

        selectionHandler.applyVisualSelection(newSelectedShape); // Viene richiamato il metodo che gestisce la componente visiva della selezione
    }
    

    /**
     * Metodo dedito alla creazione di un menù contestuale generico per il riquadro di disegno
     * 
     * @author ciroc
     */
    private void createCanvasMenu() {
        canvasMenu = new ContextMenu();
        pasteMenuItem = new MenuItem("Incolla");
        pasteMenuItem.setDisable(true);
        pasteMenuItem.setOnAction(e -> {
            Command paste = new PasteCommand(clipboard, drawingPane, drawShapes, lastContextX, lastContextY);
            paste.execute();
            commandHistory.push(paste);
            drawingPaneSizeDynamicUpdate(drawingPane);
        });
        canvasMenu.getItems().add(pasteMenuItem);
    }

    private void createShapeMenu() {
        shapeMenu = new ContextMenu();

        MenuItem deletion = new MenuItem("Elimina");
        MenuItem copy = new MenuItem("Copia");
        MenuItem cut = new MenuItem("Taglia");
        MenuItem changeColor = new MenuItem("Cambia Colore");
        
        //Menu per il cambio livello
        Menu layerMenu = new Menu("Cambia livello");
        bringToFront = new MenuItem("Porta in primo piano");
        bringForward = new MenuItem("Porta avanti");
        sendBackward = new MenuItem("Porta indietro");
        sendToBack = new MenuItem("Manda in ultimo piano");

        deletion.setOnAction(e -> {
            Shape selectedShape = selectionHandler.getSelectedShape();
            if (selectedShape != null) {
                Command delete = new DeleteCommand(drawingPane, drawShapes, selectedShape);
                delete.execute();
                commandHistory.push(delete);
                selectionHandler.setSelectedShape(null);
            }
        });

        copy.setOnAction(e -> {
            Shape selectedShape = selectionHandler.getSelectedShape();
            if (selectedShape != null) {
                clipboard.setContents(Collections.singletonList(selectedShape));
                pasteMenuItem.setDisable(false);
            }
        });

        cut.setOnAction(e -> {
            Shape selectedShape = selectionHandler.getSelectedShape();
            if (selectedShape != null) {
                Command cutC = new CutCommand(Collections.singletonList(selectedShape), clipboard, drawShapes, drawingPane);
                cutC.execute();
                commandHistory.push(cutC);
                selectionHandler.setSelectedShape(null);
                pasteMenuItem.setDisable(false);
                shapeMenu.hide();
            }
        });

        // Cambia colore alla forma selezionata
        changeColor.setOnAction(e -> {
            Shape selectedShape = selectionHandler.getSelectedShape();

            // Colori selezionati nei pannelli colore
            Color newStroke = colorHandler.getPerimetralColor();
            Color newFill = colorHandler.getFillingColor();

            // Esegui il comando di cambio colore (supporta anche l'undo)
            Command changeColorCmd = new ChangeColorCommand(selectedShape, newStroke, newFill);
            changeColorCmd.execute();
            commandHistory.push(changeColorCmd);
        });

        // Assegna le azioni
        bringToFront.setOnAction(e -> {
            Shape s = selectionHandler.getSelectedShape();
            if (s != null){
                Command allForwardCmd = new BringToFrontCommand(s, drawShapes, drawingPane);
                allForwardCmd.execute();
                commandHistory.push(allForwardCmd);
            }
        });
        bringForward.setOnAction(e -> {
            Shape s = selectionHandler.getSelectedShape();
            if (s != null){
                Command oneForwardCmd = new BringForwardCommand(s, drawShapes, drawingPane);
                oneForwardCmd.execute();
                commandHistory.push(oneForwardCmd);
            }
        });
        sendBackward.setOnAction(e -> {
            Shape s = selectionHandler.getSelectedShape();
            if (s != null){
                Command oneBackCmd = new SendBackwardCommand(s, drawShapes, drawingPane);
                oneBackCmd.execute();
                commandHistory.push(oneBackCmd);
            }
        });
        sendToBack.setOnAction(e -> {
            Shape s = selectionHandler.getSelectedShape();
            if (s != null){
               Command allBackCmd = new SendToBackCommand(s, drawShapes, drawingPane);
               allBackCmd.execute();
               commandHistory.push(allBackCmd);
            }
        });
        
        shapeMenu.getItems().add(deletion);
        shapeMenu.getItems().add(copy);
        shapeMenu.getItems().add(cut);
        shapeMenu.getItems().add(changeColor);
        
        layerMenu.getItems().addAll(bringToFront, bringForward, sendBackward, sendToBack);
        shapeMenu.getItems().add(layerMenu);
    }
    
    
    
    private void refreshDrawingPane() {
        drawingPane.getChildren().clear();

        for (Shape shape : drawShapes) {
            javafx.scene.shape.Shape fxShape = shape.toFXShape();
            shape.setFXShape(fxShape);
            
            fxShape.setOnMouseClicked(e -> shapeSelectionHandler(e));
            
            drawingPane.getChildren().add(fxShape);
        }

        System.out.println("Interfaccia aggiornata. Numero forme: " + drawShapes.size());
    }
    /* TO DO   
    @FXML
    private void handleCut(ActionEvent event) {
        Shape selectedShape = selectionHandler.getSelectedShape();
        if (selectedShape != null) {
            new CutCommand(Collections.singletonList(selectedShape), clipboard, drawShapes, drawingPane).execute();
            selectionHandler.setSelectedShape(null);
            pasteMenuItem.setDisable(false);
        }
    }
    */
    @FXML
    private void salvataggio(javafx.scene.input.MouseEvent event) {
        shapeIOManager.saveShapes(drawShapes, drawingPane.getScene().getWindow());
    }

    @FXML
    private void caricamento(javafx.scene.input.MouseEvent event) {
        List<Shape> loadedShapes = shapeIOManager.loadShapes(drawingPane.getScene().getWindow());
        
        if (loadedShapes == null) {
            System.out.println("Caricamento annullato.");
            return; // non fare nulla
        }
        
        drawShapes = loadedShapes;
        // Ricostruisci la view
        for (Shape shape : drawShapes) {
            javafx.scene.shape.Shape fxShape = shape.toFXShape();
            shape.setFXShape(fxShape);
        }

        refreshDrawingPane();
    }

    @FXML
    private void undoLastCommand(MouseEvent event) {
        if(!commandHistory.isEmpty()){
            commandHistory.undo();
        }
    }
    
    private void updateLayerMenuItems() {
        Shape selectedShape = selectionHandler.getSelectedShape();

        int index = drawShapes.indexOf(selectedShape);
        int maxIndex = drawShapes.size() - 1;

        bringForward.setDisable(index == maxIndex);     // se è già in cima
        bringToFront.setDisable(index == maxIndex);

        sendBackward.setDisable(index == 0);            // se è già in fondo
        sendToBack.setDisable(index == 0);
    }
    
    /**
     * Questo metodo si occupa di ridimensionare dinamicamente il riquadro 
     * di disegno, quando vengono inserite forme che vanno oltre i confini spaziali
     * del riquadro stesso.
     * 
     * @author ciroc
     * 
     * @param drawingPane riquadro di disegno da ridimensionare
     */
    private void drawingPaneSizeDynamicUpdate(Pane drawingPane){
        double maximumX = 0;
        double maximumY = 0;
        
        for(Node node : drawingPane.getChildren()){
            Bounds bounds = node.getBoundsInParent();
            maximumX = Math.max(maximumX, bounds.getMaxX());
            maximumY = Math.max(maximumY, bounds.getMaxY());            
        }
        
        drawingPane.setPrefWidth(maximumX + 100);
        drawingPane.setPrefHeight(maximumY + 100);
    }

    @FXML
    private void onZoomChanged(ActionEvent event) {
        String label = zoomComboBox.getSelectionModel().getSelectedItem();
        if (label == null) return;
        double scale = parseLabel(label);   // "50 %" -> 0.5
        setZoom(scale);
    }

    // ---------- Helpers ----------
    private double parseLabel(String s) {
        try {
            return Double.parseDouble(s.replace("%", "").trim()) / 100.0;
        } catch (NumberFormatException ex) {
            return 1.0;
        }
    }

    private void setZoom(double scale) {
        zoomProperty.set(scale);
    }
}
