/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package drawingapplication;

import Command.Clipboard;
import Command.CutCommand;
import Command.PasteCommand;
import Shapes.Shape;
import Shapes.ShapeFactory;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 *
 * @author genna
 */
public class FXMLDocumentController implements Initializable {

    private Label label;
    private Color perimetralColor = Color.BLACK;
    private Color fillingColor = Color.TRANSPARENT;
    private final DropShadow hover = new DropShadow(10, Color.GRAY);
    private List<Shape> drawShapes = new ArrayList<>();
    private Shape selectedShape = null;
    private ContextMenu shapeMenu;
    private Circle selectedColorButton = null;
    private Clipboard clipboard = new Clipboard();
    private double lastContextX;
    private double lastContextY;
    private ContextMenu canvasMenu;
    private MenuItem pasteMenuItem;

    @FXML
    private Pane drawingPane;
    @FXML
    private AnchorPane ShapeTastic;
    @FXML
    private HBox toolbar;
    @FXML
    private RadioButton perimeterRadio, fillRadio;
    @FXML
    private Circle purpleButton, blackButton, pinkButton, yellowButton, greenButton, blueButton, redButton, whiteButton, cyanButton;
    @FXML
    private HBox colorToolbarSection;
    @FXML
    private ToggleGroup shapeToggleGroup;
    @FXML
    private ToggleButton lineButton, rectangleButton, ellipseButton;
    private double startX, startY;
    private String selectedShapeType = null;
    private javafx.scene.shape.Shape previewShape = null;
    @FXML
    private ToggleGroup radioColorButtonToggleGroup;
    @FXML
    private Button salvaButton;
    @FXML
    private Button CaricaButton;
    @FXML
    private Button cutButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        createShapeMenu();
        createCanvasMenu();

        purpleButton.setFill(Color.PURPLE);
        blackButton.setFill(Color.BLACK);
        pinkButton.setFill(Color.PINK);
        yellowButton.setFill(Color.YELLOW);
        greenButton.setFill(Color.GREEN);
        blueButton.setFill(Color.BLUE);
        redButton.setFill(Color.RED);
        whiteButton.setFill(Color.WHITE);
        cyanButton.setFill(Color.CYAN);

        purpleButton.setOnMouseClicked(event -> handleColorSelection(Color.PURPLE));
        blackButton.setOnMouseClicked(event -> handleColorSelection(Color.BLACK));
        pinkButton.setOnMouseClicked(event -> handleColorSelection(Color.PINK));
        yellowButton.setOnMouseClicked(event -> handleColorSelection(Color.YELLOW));
        greenButton.setOnMouseClicked(event -> handleColorSelection(Color.GREEN));
        blueButton.setOnMouseClicked(event -> handleColorSelection(Color.BLUE));
        redButton.setOnMouseClicked(event -> handleColorSelection(Color.RED));
        whiteButton.setOnMouseClicked(event -> handleColorSelection(Color.WHITE));
        cyanButton.setOnMouseClicked(event -> handleColorSelection(Color.CYAN));

        //Evita che il disegno vada oltre l'area di disegno
        drawingPane.setClip(new Rectangle(drawingPane.getPrefWidth(), drawingPane.getPrefHeight()));

        //Imposta tooltip e immagine dei pulsanti delle forme
        setTooltipAndImage(rectangleButton, "Rectangle", "rectangle_icon.png");
        setTooltipAndImage(ellipseButton, "Ellipse", "ellipse_icon.png");
        setTooltipAndImage(lineButton, "Line", "line_icon.png");

        //Quando premo nell'are di disegno prende la posizione iniziale del mouse
        drawingPane.setOnMousePressed(event -> {
            if (selectedShapeType == null) {
                return;
            }
            startX = event.getX();
            startY = event.getY();

        });

        //Trascinamento mouse
        drawingPane.setOnMouseDragged(event -> {
            if (selectedShapeType == null) {
                return;
            }

            //Evita che la preview possa andare oltre l'area di disegno
            double endX = Math.max(0, Math.min(event.getX(), drawingPane.getWidth()));
            double endY = Math.max(0, Math.min(event.getY(), drawingPane.getHeight()));

            //Crea la forma provvisoria (preview)
            Shape temp = ShapeFactory.createShape(selectedShapeType, startX, startY, endX, endY);
            javafx.scene.shape.Shape fxTempShape = temp.toFXShape();
            //Stile del preview
            fxTempShape.getStrokeDashArray().addAll(5.0, 5.0); // tratteggiata
            fxTempShape.setStroke(Color.GRAY);
            fxTempShape.setFill(Color.TRANSPARENT);

            //Rimuove la forma precedente di preview se presente
            if (previewShape != null) {
                drawingPane.getChildren().remove(previewShape);
            }

            previewShape = fxTempShape;
            drawingPane.getChildren().add(previewShape);
        });

        // Rilascio del mouse
        drawingPane.setOnMouseReleased(event -> {
            if (selectedShapeType == null) {
                return;
            }

            //Non permette alla figura di uscire dall'area di disegno
            double endX = Math.max(0, Math.min(event.getX(), drawingPane.getWidth()));
            double endY = Math.max(0, Math.min(event.getY(), drawingPane.getHeight()));

            //Rimuove la preview
            if (previewShape != null) {
                drawingPane.getChildren().remove(previewShape);
                previewShape = null;
            }

            //Crea la forma definitiva
            Shape finalShape = ShapeFactory.createShape(selectedShapeType, startX, startY, endX, endY, perimetralColor, fillingColor);
            javafx.scene.shape.Shape fxShape = finalShape.toFXShape();
            finalShape.setFXShape(fxShape);
            drawingPane.getChildren().add(fxShape);
            drawShapes.add(finalShape);
            event.consume();
        });

        /**
         * Metodo per gestire il click del mouse sul riquadro di disegno
         * 
         * @author ciroc
         */
        drawingPane.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY && selectedShape != null) {
                // Alla pressione del tasto destro entro una forma selezionata
                shapeMenu.show(drawingPane, event.getScreenX(), event.getScreenY()); // Viene mostrato il menu contestuale delle figure
            } else if (event.getButton() == MouseButton.SECONDARY && selectedShape == null) {
                // Alla pressione del tasto destro al di fuori di una qualsiasi forma
                lastContextX = event.getX();
                lastContextY = event.getY();
                canvasMenu.show(drawingPane, event.getScreenX(), event.getScreenY()); // Viene mostrato il menu contestuale generico del riquadro di disegno
            } else {
                // All'occorrenza di ogni altro click vengono nascosti i menu contestuali
                shapeMenu.hide();
                canvasMenu.hide();
                shapeSelectionHandler(event); // Viene invocata la selezione
            }
        });

    }

    /**
     * Metodo dedito alla gestione della selezione dei colori di bordo e riempimento
     * 
     * @param color colore slezionato per il bordo o per il riempimento
     * 
     * @author ciroc
     */
    private void handleColorSelection(Color color) {

        if (perimeterRadio.isSelected()) {
            // Alla selezione del radio button dedicato al colore di bordo
            perimetralColor = color; // Viene aggiornato il colore di bordo
        } else if (fillRadio.isSelected()) {
            // Alla selezione del radio button dedicato al colore di riempimento
            fillingColor = color; // Viene aggiornato il colore di riempimento
        }
    }

    /**
     * Metodo dedito alla corretta rimozione dell'effetto `hover`, dopo che è stato applicato
     * 
     * @param event evento che indica la necessità di rimuovere l'effetto di `hover`
     * 
     * @author ciroc
     */
    @FXML
    private void removeHoverEffect(javafx.scene.input.MouseEvent event) {
        
        Circle circle = (Circle) event.getSource(); // Viene salvato il color button sottoposto all'evento scatenante

        // Alla presione di un colore già selezionato non succede nulla
        if (circle == selectedColorButton) {
            return;
        }

        circle.setEffect(null); // Vengono azzerati gli effetti

        //Viene reimpostato lo scaling ad una taglia normale
        ScaleTransition scaling = new ScaleTransition(Duration.millis(150), circle);
        scaling.setToX(1.0);
        scaling.setToY(1.0);
        scaling.play();
    }

    /**
     * Metodo dedito all'attuazione di un effetto visivo `hover`
     * 
     * @param event evento che indica la necessità di azionare l'effetto di `hover`
     * 
     * @author ciroc
     */
    @FXML
    private void hoverEffect(javafx.scene.input.MouseEvent event) {
        Circle circle = (Circle) event.getSource(); // Viene salvato il color button sottoposto all'evento scatenante

        // Alla pressione di un colore già selezionato non succede nulla
        if (circle == selectedColorButton) {
            return;
        }

        // Viene istanziato l'effetto di ombreggiatura
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.LIGHTGRAY);
        shadow.setRadius(10);
        circle.setEffect(shadow);

        // Viene istanziato un effetto di scaling
        ScaleTransition scaling = new ScaleTransition(Duration.millis(150), circle);
        scaling.setToX(1.1);
        scaling.setToY(1.1);
        scaling.play();
    }

    /**
     * Metodo dedito all'attuazione dell'effetto visivo di click
     * 
     * @param event evento che indica la necessità di azionare l'effetto di click
     * 
     * @author ciroc
     */
    @FXML
    private void clickEffect(javafx.scene.input.MouseEvent event) {
        Circle circle = (Circle) event.getSource();

        /*ScaleTransition scaling = new ScaleTransition(Duration.millis(100), circle);
        scaling.setToX(1.4);
        scaling.setToY(1.4);
        scaling.setAutoReverse(true);
        scaling.setCycleCount(2);
        scaling.play();
        
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        
        DropShadow selection = new DropShadow();
        selection.setColor(Color.BLACK);
        selection.setRadius(10);
        circle.setEffect(selection);
         */
        circle.setFill(Color.BLACK);

        selectedColorButton = circle;

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

        visualShapeSelectionHandler(newSelectedShape); // Viene richiamato il metodo che gestisce la componente visiva della selezione
    }

    /**
     * Metodo dedito all'attivazione e disattivazione degli effetti visivi legati alla selezione
     * 
     * @param shape forma selezionata a cui applicare o da cui rimuovere l'effetto visivo di selezione
     * 
     * @author ciroc
     */
    public void visualShapeSelectionHandler(Shape shape) {

        if (selectedShape != null) {
            deselectShape(selectedShape.getFXShape());
        }

        selectedShape = shape;

        if (selectedShape != null) {
            selectShape(selectedShape.getFXShape());
        }
    }

    /**
     * Metodo dedito all'applicazione degli effetti visivi legati alla selezione
     * 
     * @param shape forma su cui azionare effetti visivi per la selezione
     * 
     * @author ciroc
     */
    private void selectShape(javafx.scene.shape.Shape shape) {
        DropShadow selection = new DropShadow();
        selection.setColor(Color.BLACK);
        selection.setRadius(15);
        shape.setEffect(selection);
    }

    /**
     * Metodo dedito alla rimozione degli effetti visivi legati alla selezione
     * 
     * @param shape forma da cui devono essere rimossi gli effetti visivi di selezione
     * 
     * @author ciroc
     */
    private void deselectShape(javafx.scene.shape.Shape shape) {
        shape.setEffect(null);
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
            new PasteCommand(clipboard, drawingPane, drawShapes, lastContextX, lastContextY).execute();
        });
        canvasMenu.getItems().add(pasteMenuItem);
    }

    private void createShapeMenu() {
        shapeMenu = new ContextMenu();

        MenuItem deletion = new MenuItem("Elimina");
        MenuItem copy = new MenuItem("Copia");
        MenuItem cut = new MenuItem("Taglia");

        deletion.setOnAction(e -> {
            if (selectedShape != null) {
                drawingPane.getChildren().remove(selectedShape.getFXShape());
                drawShapes.remove(selectedShape);
                selectedShape = null;
            }
        });

        copy.setOnAction(e -> {
            if (selectedShape != null) {
                clipboard.setContents(Collections.singletonList(selectedShape));
                pasteMenuItem.setDisable(false);
            }
        });

        cut.setOnAction(e -> {
            if (selectedShape != null) {
                new CutCommand(Collections.singletonList(selectedShape), clipboard, drawShapes, drawingPane).execute();
                selectedShape = null;
                pasteMenuItem.setDisable(false);
                shapeMenu.hide();
            }
        });

        shapeMenu.getItems().add(deletion);
        shapeMenu.getItems().add(copy);
        shapeMenu.getItems().add(cut);
    }

    @FXML
    private void salvataggio(javafx.scene.input.MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salva Disegno");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Disegni serializzati", "*.ser")
        );
        File file = fileChooser.showSaveDialog(drawingPane.getScene().getWindow());

        if (file != null) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                out.writeObject(drawShapes);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        System.out.println("Forme salvate: " + drawShapes.size());
    }

    @FXML
    private void caricamento(javafx.scene.input.MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Carica Disegno");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Disegni serializzati", "*.ser")
        );
        File file = fileChooser.showOpenDialog(drawingPane.getScene().getWindow());

        if (file != null) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            @SuppressWarnings("unchecked")
            List<Shape> loadedShapes = (List<Shape>) in.readObject();
            drawShapes = loadedShapes;
            for (Shape shape : drawShapes) {
                javafx.scene.shape.Shape fxShape = shape.toFXShape();
                shape.setFXShape(fxShape);
            }

                refreshDrawingPane();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
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
    
    @FXML
    private void handleCut(ActionEvent event) {
        if (selectedShape != null) {
            new CutCommand(Collections.singletonList(selectedShape), clipboard, drawShapes, drawingPane).execute();
            selectedShape = null;
            pasteMenuItem.setDisable(false);
        }
    }
}
