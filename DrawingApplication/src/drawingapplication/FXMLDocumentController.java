/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package drawingapplication;

import Command.*;
import Handlers.ColorSelectionHandler;
import Handlers.DrawingStateHistory;
import Handlers.GridHandler;
import Handlers.PreviewHandler;
import Handlers.ShapeButtonSelectionHandler;
import Handlers.ShapeIOManager;
import Handlers.ShapeSelectionHandler;
import Shapes.IrregularPolygonShape;
import Shapes.Shape;
import Shapes.ShapeFactory;
import Shapes.ShapeType;
import Shapes.TextShape;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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
    private ToggleButton lineButton;
    @FXML
    private ToggleGroup radioColorButtonToggleGroup;
    @FXML
    private Button salvaButton;
    @FXML
    private Button CaricaButton;


     private List<Shape> drawShapes = new ArrayList<>();
    // Handler per la selezione colori
    private final ColorSelectionHandler colorHandler = new ColorSelectionHandler();

    //Handler per il salvataggio e caricamento da file
    private final ShapeIOManager shapeIOManager = new ShapeIOManager();

    //Handler per preview della forma
    private final PreviewHandler previewHandler = new PreviewHandler();

    private final DrawingStateHistory commandHistory = new DrawingStateHistory();

    //Clipboard per copiare le forme
    private final Clipboard clipboard = new Clipboard();
    
    private final ShapeFactory shapeFactory = new ShapeFactory();
    
    private ShapeButtonSelectionHandler shapeBtnSelHandler;
    private DrawingReceiver drawingReceiver;

    //Handler per la selezione della forma
    private ShapeSelectionHandler selectionHandler;
    
    private ContextMenu shapeMenu;
    private double lastContextX;
    private double lastContextY;
    private ContextMenu canvasMenu;
    private MenuItem pasteMenuItem;
    private IrregularPolygonShape currentPolygon;
    private List<javafx.scene.shape.Circle> polygonSidesPreviewDots = new ArrayList<>();
    private boolean isDrawingPolygon = false;
    private Tooltip polygonTooltip = new Tooltip("Clicca per aggiungere punti. Clicca vicino al punto iniziale per chiudere.");

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
    @FXML
    private ToggleButton toggleGridButton;

    private GridHandler gridHandler;
    @FXML
    private ComboBox<Double> gridSizeComboBox;
    private ToggleButton textButton;
    @FXML
    private ToggleButton lineButton1;
    @FXML
    private ToggleGroup shapeToggleGroup1;
    @FXML
    private ToggleButton rectangleButton1;
    @FXML
    private ToggleButton ellipseButton1;
    @FXML
    private ToggleButton textButton1;
    @FXML
    private ToggleButton polygonButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        drawingReceiver = new DrawingReceiver(drawShapes, drawingPane);
        selectionHandler = new ShapeSelectionHandler(commandHistory, drawingReceiver);
        
        createShapeMenu();
        createCanvasMenu();

        // Sezione di metodi per far funzionare bene lo scrolling
        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // Sezione di metodi per la dimensione del riquadro di disegno
        AnchorPane.setBottomAnchor(drawingPane, 0.0);
        AnchorPane.setLeftAnchor(drawingPane, 0.0);
        AnchorPane.setRightAnchor(drawingPane, 0.0);
        VBox.setVgrow(drawingPane, Priority.ALWAYS);

        // Initialize ShapeButtonSelectionHandler after drawingPane is fully set up
        shapeBtnSelHandler = new ShapeButtonSelectionHandler(drawingReceiver, colorHandler, previewHandler);

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
        setTooltipAndImage(rectangleButton1, "Rectangle", "rectangle_icon.png");
        setTooltipAndImage(ellipseButton1, "Ellipse", "ellipse_icon.png");
        setTooltipAndImage(lineButton1, "Line", "line_icon.png");
        setTooltipAndImage(textButton1,"Text","text_icon.png");
        setTooltipAndImage(polygonButton, "Polygon", "polygon_icon.png");
        
        //Pressione del mouse
        drawingPane.setOnMousePressed(e -> {
            shapeBtnSelHandler.handleMousePressed(e, commandHistory);
        });

        //Trascinamento mouse
        drawingPane.setOnMouseDragged(e -> {
            shapeBtnSelHandler.handleMouseDragged(e);
            drawingPaneSizeDynamicUpdate(drawingPane);
        });

        //Rilascio del mouse
        drawingPane.setOnMouseReleased(e -> {
            shapeBtnSelHandler.handleMouseReleased(e, commandHistory);
        });

        /**
         * Metodo per gestire il click del mouse sul riquadro di disegno
         *
         * @author ciroc
         */
        drawingPane.setOnMouseClicked(event -> {
            shapeMenu.hide();
            canvasMenu.hide();

            if (event.getButton() == MouseButton.PRIMARY) {
                // Solo se nessuna forma è selezionata = modalità selezione
                if (!shapeBtnSelHandler.isShapeSelected()) {
                    selectionHandler.handleSelection(event, drawShapes, drawingPane);
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
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

        zoomComboBox.getItems().addAll( Arrays.asList("100 %", "200 %", "300 %", "400 %"));
        zoomComboBox.getSelectionModel().select("100 %");

        zoomGroup.scaleXProperty().bind(zoomProperty);
        zoomGroup.scaleYProperty().bind(zoomProperty);
        zoomProperty.set(1.0);

        /* Facoltativo: permette panning con drag del mouse */
        scrollPane.setPannable(false);
        zoomGroup.scaleXProperty().bind(zoomProperty);
        zoomGroup.scaleYProperty().bind(zoomProperty);

        // Popola la ComboBox con dimensioni di griglia predefinite
        gridSizeComboBox.getItems().addAll(10.0, 20.0, 50.0, 100.0);
        gridSizeComboBox.setValue(20.0); // Valore iniziale
        gridSizeComboBox.setDisable(true); // Disabilitata finché la griglia non è attiva

        // Inizializza la griglia e la aggiunge al pane (inizialmente invisibile)
        gridHandler = new GridHandler(drawingPane.getPrefWidth(), drawingPane.getPrefHeight(), gridSizeComboBox.getValue());
        drawingPane.getChildren().add(0, gridHandler.getGridNode());

        // Viene ridimensionata anche la griglia quando viene espanso il riquadro di disegno
        drawingPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            gridHandler.resize(newVal.doubleValue(), drawingPane.getHeight());
        });
        drawingPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            gridHandler.resize(drawingPane.getWidth(), newVal.doubleValue());
        });
    }

    @FXML
    private void selectShapeButton(ActionEvent event) {
        if(rectangleButton1.isSelected()){
            shapeBtnSelHandler.selectShape(ShapeType.RECTANGLE);
        } else if (ellipseButton1.isSelected()) {
            shapeBtnSelHandler.selectShape(ShapeType.ELLIPSE);
        } else if (lineButton1.isSelected()) {
            shapeBtnSelHandler.selectShape(ShapeType.LINE);
        } else if (polygonButton.isSelected()) {
            shapeBtnSelHandler.selectShape(ShapeType.POLYGON);
        } else if (textButton1.isSelected()) {
            shapeBtnSelHandler.selectShape(ShapeType.TEXT);
        } else {
            shapeBtnSelHandler.clearSelection();
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
     * Metodo dedito alla creazione di un menù contestuale generico per il
     * riquadro di disegno
     *
     * @author ciroc
     */
    private void createCanvasMenu() {
        canvasMenu = new ContextMenu();
        pasteMenuItem = new MenuItem("Incolla");
        pasteMenuItem.setDisable(true);
        pasteMenuItem.setOnAction(e -> {
            Command paste = new PasteCommand(clipboard, drawingReceiver, lastContextX, lastContextY);
            commandHistory.executeCommand(paste);
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
        MenuItem resize = new MenuItem("Ridimensiona");

        //Menu per il cambio livello
        Menu layerMenu = new Menu("Cambia livello");
        bringToFront = new MenuItem("Porta in primo piano");
        bringForward = new MenuItem("Porta avanti");
        sendBackward = new MenuItem("Porta indietro");
        sendToBack = new MenuItem("Manda in ultimo piano");
        
        // 1) Creo il sottomenù Specchiature
        Menu mirrorMenu = new Menu("Specchiature");
        MenuItem mirrorH = new MenuItem("Orizzontale");
        MenuItem mirrorV = new MenuItem("Verticale");

        deletion.setOnAction(e -> {
            Shape selectedShape = selectionHandler.getSelectedShape();
            if (selectedShape != null) {
                Command delete = new DeleteCommand(drawingReceiver, selectedShape);
                commandHistory.executeCommand(delete);
                selectionHandler.setSelectedShape(null);
            }
        });

        copy.setOnAction(e -> {
            Shape selectedShape = selectionHandler.getSelectedShape();
            if (selectedShape != null) {
                drawingReceiver.copyShape(selectedShape, clipboard);
                pasteMenuItem.setDisable(false);
            }
        });

        cut.setOnAction(e -> {
            Shape selectedShape = selectionHandler.getSelectedShape();
            if (selectedShape != null) {
                Command cutCmd = new CutCommand(selectedShape, clipboard, drawingReceiver);
                commandHistory.executeCommand(cutCmd);
                selectionHandler.setSelectedShape(null);
                pasteMenuItem.setDisable(false);
                shapeMenu.hide();
            }
        });

        // Cambia colore alla forma selezionata
        changeColor.setOnAction(e -> {
            Shape selectedShape = selectionHandler.getSelectedShape();
            if (selectedShape != null) {
                // Colori selezionati nei pannelli colore
                Color newStroke = colorHandler.getPerimetralColor();
                Color newFill = colorHandler.getFillingColor();
                // Esegui il comando di cambio colore
                Command changeColorCmd = new ChangeColorCommand(selectedShape, newStroke, newFill, drawingReceiver);
                commandHistory.executeCommand(changeColorCmd);
            }
        });

        // Assegna le azioni
        bringToFront.setOnAction(e -> {
            Shape selectedShape = selectionHandler.getSelectedShape();
            if (selectedShape != null) {
                Command allForwardCmd = new BringToFrontCommand(selectedShape, drawingReceiver);
                commandHistory.executeCommand(allForwardCmd);
            }
        });
        
        bringForward.setOnAction(e -> {
            Shape selectedShape = selectionHandler.getSelectedShape();
            if (selectedShape != null) {
                Command oneForwardCmd = new BringForwardCommand(selectedShape, drawingReceiver);
                commandHistory.executeCommand(oneForwardCmd);
            }
        });
        
        sendBackward.setOnAction(e -> {
            Shape selectedShape = selectionHandler.getSelectedShape();
            if (selectedShape != null) {
                Command oneBackCmd = new SendBackwardCommand(selectedShape, drawingReceiver);
                commandHistory.executeCommand(oneBackCmd);
            }
        });
        
        sendToBack.setOnAction(e -> {
            Shape selectedShape = selectionHandler.getSelectedShape();
            if (selectedShape != null) {
                Command allBackCmd = new SendToBackCommand(selectedShape, drawingReceiver);
                commandHistory.executeCommand(allBackCmd);
            }
        });

        // Specchiatura orizzontale
        mirrorH.setOnAction(e -> {
            Shape selectedShape = selectionHandler.getSelectedShape();
            if (selectedShape != null) {
                Command mirrorHorizontalCmd = new MirrorCommand(selectedShape, false, drawingReceiver);
                commandHistory.executeCommand(mirrorHorizontalCmd);
            }
        });
        
        mirrorV.setOnAction(e -> {
            Shape selectedShape = selectionHandler.getSelectedShape();
            if (selectedShape != null) {
                Command mirrorVerticalCmd = new MirrorCommand(selectedShape, true, drawingReceiver);
                commandHistory.executeCommand(mirrorVerticalCmd);
                selectionHandler.clearSelection();
            }
        });

        // Imposta l'azione associata alla voce di menu "Ridimensiona"
        resize.setOnAction(e -> showResizeDialog(selectionHandler.getSelectedShape()));

        // Aggiunge tutte le voci di menu al menu contestuale
        shapeMenu.getItems().addAll(deletion, copy, cut, changeColor, resize);
        layerMenu.getItems().addAll(bringToFront, bringForward, sendBackward, sendToBack);
        shapeMenu.getItems().add(layerMenu);
        mirrorMenu.getItems().addAll(mirrorH, mirrorV);
        shapeMenu.getItems().add(mirrorMenu);
        
        // Creo il sottomenù Ruota
        Menu rotateMenu = new Menu("Ruota");
        MenuItem rotate45 = new MenuItem("45°");
        MenuItem rotate90 = new MenuItem("90°");
        MenuItem rotate180 = new MenuItem("180°");

        // Imposto le azioni di rotazione
        rotate45.setOnAction(e -> {
            Shape selectedShape = selectionHandler.getSelectedShape();
            if (selectedShape != null) {
                Command rotate45Cmd = new RotateCommand(selectedShape, 45, drawingReceiver);
                commandHistory.executeCommand(rotate45Cmd);
                updateWorkspace();
            }
        });

        rotate90.setOnAction(e -> {
            Shape selectedShape = selectionHandler.getSelectedShape();
            if (selectedShape != null) {
                Command rotate90Cmd = new RotateCommand(selectedShape, 90, drawingReceiver);
                commandHistory.executeCommand(rotate90Cmd);
                updateWorkspace();
            }
        });

        rotate180.setOnAction(e -> {
            Shape selectedShape = selectionHandler.getSelectedShape();
            if (selectedShape != null) {
                Command rotate180Cmd = new RotateCommand(selectedShape, 180, drawingReceiver);
                commandHistory.executeCommand(rotate180Cmd);
                updateWorkspace();
            }
        });

        // Aggiungo le voci al menu e poi il menu stesso al context menu
        rotateMenu.getItems().addAll(rotate45, rotate90, rotate180);
        shapeMenu.getItems().add(rotateMenu);
    }
    
    private void updateWorkspace() {
        refreshDrawingPane();                  // già ridisegna le forme
        drawingPaneSizeDynamicUpdate(drawingPane); // e ricalcola l'area di lavoro
    }

    private void refreshDrawingPane() {
        drawingPane.getChildren().removeIf(node -> !(node instanceof Group));

        for (Shape shape : drawShapes) {
            javafx.scene.shape.Shape fxShape = shape.toFXShape();
            shape.setFXShape(fxShape);
            
            // Non serve??
            //fxShape.setOnMouseClicked(e -> shapeSelectionHandler(e));

            drawingPane.getChildren().add(fxShape);
        }
        
        drawingPaneSizeDynamicUpdate(drawingPane);

        System.out.println("Interfaccia aggiornata. Numero forme: " + drawShapes.size());
    }

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

        // Clear existing shapes
        drawShapes.clear();
        drawingPane.getChildren().clear();

        // Add shapes in the correct order
        for (Shape shape : loadedShapes) {
            // Create the visual representation
            javafx.scene.shape.Shape fxShape = shape.toFXShape();
            shape.setFXShape(fxShape);
            
            // Add to both logical and visual lists in the same order
            drawShapes.add(shape);
            drawingPane.getChildren().add(fxShape);
        }

        drawingPaneSizeDynamicUpdate(drawingPane);
    }

    @FXML
    private void undoLastCommand(MouseEvent event) {
        Command undone = commandHistory.undo();
        if (undone == null) {
            System.out.println("[DEBUG] No command to undo");
            return;
        }
        System.out.println("[DEBUG] Undone command: " + undone.getClass().getSimpleName());
        refreshDrawingPane();
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
     * Questo metodo si occupa di ridimensionare dinamicamente il riquadro di
     * disegno, quando vengono inserite forme che vanno oltre i confini spaziali
     * del riquadro stesso.
     *
     * @author ciroc
     *
     * @param drawingPane riquadro di disegno da ridimensionare
     */
    private void drawingPaneSizeDynamicUpdate(Pane drawingPane) {
        
        double maxX = 0;
        double maxY = 0;
        double paddingNegativeBound = 20;
        double paddingPositiveBound = 100;
        double edgeTolerance = 0.5;

        for (Shapes.Shape s : drawShapes) {
            javafx.scene.shape.Shape fx = s.getFXShape();
            if (fx == null) continue;

            Bounds bounds = fx.getBoundsInParent();

            double dx = 0, dy = 0;

            if (bounds.getMinX() < -edgeTolerance) {
                dx = -bounds.getMinX() + paddingNegativeBound;
            }

            if (bounds.getMinY() < -edgeTolerance) {
                dy = -bounds.getMinY() + paddingNegativeBound;
            }

            if (dx != 0 || dy != 0) {
                Command moveCmd = new MoveShapeCommand(s, dx, dy, drawingReceiver);
                    moveCmd.execute();
            }
        }
        for (Shapes.Shape s : drawShapes) {
            javafx.scene.shape.Shape fx = s.getFXShape();
            if (fx == null) continue;

            Bounds bounds = fx.getBoundsInParent();

            maxX = Math.max(maxX, bounds.getMaxX());
            maxY = Math.max(maxY, bounds.getMaxY());
        }

        // Estendi il canvas se serve
        if (maxX + paddingPositiveBound > drawingPane.getWidth()) {
            drawingPane.setPrefWidth(maxX + paddingPositiveBound);
        }

        if (maxY + paddingPositiveBound > drawingPane.getHeight()) {
            drawingPane.setPrefHeight(maxY + paddingPositiveBound);
        }
    }


    @FXML
    private void onZoomChanged(ActionEvent event) {
        String label = zoomComboBox.getSelectionModel().getSelectedItem();
        if (label == null) {
            return;
        }
        double scale = parseLabel(label);   // "50 %" -> 0.5
        setZoom(scale);
    }

    /**
     * Evento collegato al ToggleButton (attiva/disattiva griglia). Rende
     * visibile o invisibile la griglia e abilita/disabilita la ComboBox.
     */
    @FXML
    private void toggleGrid(ActionEvent event) {
        // Listener sul toggleGrid
        gridHandler.toggleVisibility();
        gridSizeComboBox.setDisable(!gridHandler.isVisible());
    }

    /**
     * Evento collegato alla ComboBox (cambia la dimensione della griglia).
     * Applica la nuova spaziatura solo se la griglia è attualmente visibile.
     */
    @FXML
    private void changeGridSize(ActionEvent event) {
        if (gridHandler.isVisible()) {
            gridHandler.setSpacing(gridSizeComboBox.getValue());
        }
    }

    /**
     * Mostra una finestra di dialogo per il ridimensionamento della forma
     * selezionata.
     *
     * La finestra consente all'utente di visualizzare e modificare le
     * dimensioni attuali (larghezza e altezza in pixel) della forma. Dopo la
     * conferma tramite il pulsante "Applica", viene eseguito un
     * {@link ResizeCommand} per aggiornare le dimensioni della forma e salvare
     * l'operazione nello storico dei comandi per supportare l'undo. Inoltre,
     * viene aggiornata la vista per riflettere le nuove dimensioni.
     *
     *
     * @param shape la forma selezionata da ridimensionare; si presuppone che
     * non sia null
     */
    private void showResizeDialog(Shape shape) {
        // Recupera le dimensioni attuali della forma
        double oldWidth = shape.getWidth();
        double oldHeight = shape.getHeight();

        //Proporzione della forma
        double aspectRatio = oldWidth / oldHeight;

        // Campi di testo precompilati con le dimensioni correnti
        TextField widthField = new TextField(String.valueOf(oldWidth));
        TextField heightField = new TextField(String.valueOf(oldHeight));
        CheckBox lockRatioCheckBox = new CheckBox("Mantieni proporzioni");

        // Crea il dialogo di tipo JavaFX
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Ridimensiona");

        // Layout del contenuto del dialogo
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Inserisce righe con etichette, campi di input e unità "px"
        grid.addRow(0, new Label("Larghezza:"), widthField, new Label("px"));
        grid.addRow(1, new Label("Altezza:"), heightField, new Label("px"));

        grid.add(lockRatioCheckBox, 0, 2, 3, 1); //checkbox su riga nuovaAdd commentMore actions

        // Listener per mantenere proporzioni quando il checkbox è attivo
        widthField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused && lockRatioCheckBox.isSelected()) {
                try {
                    String normalizedNumber = widthField.getText().replace(",", ".");
                    double newWidth = Double.parseDouble(normalizedNumber);
                    double newHeight = newWidth / aspectRatio;
                    heightField.setText(String.format("%.2f", newHeight).replace(".", ","));
                } catch (NumberFormatException ignored) {}
            }
        });

        // Quando si modifica l'altezza e si perde il focus
        heightField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused && lockRatioCheckBox.isSelected()) {
                try {
                    String normalizedNumber = heightField.getText().replace(",", ".");
                    double newHeight = Double.parseDouble(normalizedNumber);
                    double newWidth = newHeight * aspectRatio;
                    widthField.setText(String.format("%.2f", newWidth).replace(".", ","));
                } catch (NumberFormatException ignored) {}
            }
        });

        // Aggiunge il layout al contenuto del dialogo
        dialog.getDialogPane().setContent(grid);

        // Aggiunge i pulsanti Applica e Annulla
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

        // Gestisce il risultato del dialogo al click su "Applica"
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.APPLY) {
                try {
                    String normalizedWidth = widthField.getText().replace(",", ".");
                    String normalizedHeight = heightField.getText().replace(",", ".");
                    double newWidth = Double.parseDouble(normalizedWidth);
                    double newHeight = Double.parseDouble(normalizedHeight);
                    // Esegui il comando di ridimensionamento
                    Command resizeCommand = new ResizeCommand(shape, newWidth, newHeight, drawingReceiver);
                    commandHistory.executeCommand(resizeCommand);

                    refreshDrawingPane();
                    selectionHandler.setSelectedShape(null);
                } catch (NumberFormatException e) {
                    shapeBtnSelHandler.showError("Inserire valori numerici validi per larghezza e altezza");
                }
            }
            return null;
        });

        // Mostra il dialogo all'utente e attende l'interazione
        dialog.showAndWait();
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
    

    private void cancelPolygonDrawing() {
        if (isDrawingPolygon) {
            drawingPane.getChildren().removeAll(polygonSidesPreviewDots);
            Tooltip.uninstall(drawingPane, polygonTooltip);
            drawingPane.setCursor(Cursor.DEFAULT);

            isDrawingPolygon = false;
            currentPolygon = null;
            polygonSidesPreviewDots.clear();
        }
    }

}
