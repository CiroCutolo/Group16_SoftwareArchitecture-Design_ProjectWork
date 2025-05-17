/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package drawingapplication;

import Shapes.Shape;
import Shapes.ShapeFactory;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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
    private ToggleButton lineButton,rectangleButton,ellipseButton;
    private double startX, startY;
    private String selectedShapeType = null; 
    private javafx.scene.shape.Shape previewShape = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
        setTooltipAndImage(rectangleButton,"Rectangle","rectangle_icon.png");
        setTooltipAndImage(ellipseButton,"Ellipse","ellipse_icon.png");
        setTooltipAndImage(lineButton,"Line","line_icon.png");
        
        //Quando premo nell'are di disegno prende la posizione iniziale del mouse
        drawingPane.setOnMousePressed(event -> {
        if (selectedShapeType == null) return;
        startX = event.getX();
        startY = event.getY();
    });

    //Trascinamento mouse
    drawingPane.setOnMouseDragged(event -> {
        if (selectedShapeType == null) return;
        
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
        if (selectedShapeType == null) return;
        
        //Non permette alla figura di uscire dall'area di disegno
        double endX = Math.max(0, Math.min(event.getX(), drawingPane.getWidth()));
        double endY = Math.max(0, Math.min(event.getY(), drawingPane.getHeight()));

        //Rimuove la preview
        if (previewShape != null) {
            drawingPane.getChildren().remove(previewShape);
            previewShape = null;
        }

        //Crea la forma definitiva
        Shape finalShape = ShapeFactory.createShape(selectedShapeType, startX, startY, endX, endY,perimetralColor,fillingColor);
        javafx.scene.shape.Shape fxFinalShape = finalShape.toFXShape();

        drawingPane.getChildren().add(fxFinalShape);
    });
        
    }  

    //Todo - L'UI permette di selezionare contemporaneamnte perimeter e fill,
    //       quindi vanno in conflitto i due. Le diverse opzioni sono:
    //       1) A livello UI inserire mutua esclusione tra fill e perimeter
    //       2) Togliere l'else if e gestirla in maniera diversa (magari se ho
    //          selezionato sia fill che perimeter allora cambio il colore di entrambi)
    
    //Todo - Magari implementare modi per resettare il colore a quello predefinito
    //       (cioè fill trasparente o bianco e perimetro nero). Perchè ora se disattivo
    //       i pulsanti di fill e border i colori rimangono gli ultimi selezionati.
    //       Opzioni:
    //       1) Inserire un pulsante che resetti ai colori predefiniti
    //       2) Quando il pulsante "fill" non è selezionato allora usa il colore predefinito
    //          (lo stesso per il "border" o "perimeter").
    private void handleColorSelection(Color color) {
        
        if (perimeterRadio.isSelected()) {
            perimetralColor = color;
        } else if (fillRadio.isSelected()) {
            fillingColor = color;
        }
    }

    @FXML
    private void removeHoverEffect(javafx.scene.input.MouseEvent event) {
        Circle circle = (Circle) event.getSource();
        circle.setEffect(hover);
        ScaleTransition scaling = new ScaleTransition(Duration.millis(150), circle);
        scaling.setToX(1.0);
        scaling.setToY(1.0);
        scaling.play();
    }

    @FXML
    private void hoverEffect(javafx.scene.input.MouseEvent event) {
        Circle circle = (Circle) event.getSource();
        circle.setEffect(null);
        ScaleTransition scaling = new ScaleTransition(Duration.millis(150), circle);
        scaling.setToX(1.0);
        scaling.setToY(1.0);
        scaling.play();
    }

    @FXML
    //Todo - L'effetto non si nota abbastanza. E' difficile capire quale colore è attivo (ultimo selezionato).
    private void clickEffect(javafx.scene.input.MouseEvent event) {
        Circle circle = (Circle) event.getSource();
        ScaleTransition scaling = new ScaleTransition(Duration.millis(100), circle);
        scaling.setToX(1.4);
        scaling.setToY(1.4);
        scaling.setAutoReverse(true);
        scaling.setCycleCount(2);
        scaling.play();
        
    }

    
    @FXML
    private void selectRectangle(ActionEvent event) {
        if(rectangleButton.isSelected()){
            selectedShapeType = "RECTANGLE";
        } else{
            selectedShapeType = null;
        }
    }

    @FXML
    private void selectEllipse(ActionEvent event) {
        if(ellipseButton.isSelected()){
            selectedShapeType = "ELLIPSE";
        } else{
            selectedShapeType = null;
        }
    }

    @FXML
    private void selectLine(ActionEvent event) {
        if(lineButton.isSelected()){
            selectedShapeType = "LINE";
        } else{
            selectedShapeType = null;
        }
    }
    
        private void setTooltipAndImage(ToggleButton button, String tooltip, String imageFile){
        button.setTooltip(new Tooltip(tooltip));
        setImage(button,imageFile,22);
    }
    
    private void setImage(Labeled node,String imageFile,int dimension){
        Image imageOk = new Image(getClass().getResourceAsStream("/icons/" + imageFile));
        //Image imageOk = new Image("file:src/icons/" + imageFile);
        ImageView img = new ImageView(imageOk);
        img.setPreserveRatio(true);
        img.setFitHeight(dimension);
        img.setFitWidth(dimension);
        node.setGraphic(img);
    }
}
