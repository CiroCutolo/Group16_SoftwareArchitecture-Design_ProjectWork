/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package drawingapplication;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    }  

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
    private void clickEffect(javafx.scene.input.MouseEvent event) {
        Circle circle = (Circle) event.getSource();
        ScaleTransition scaling = new ScaleTransition(Duration.millis(100), circle);
        scaling.setToX(1.4);
        scaling.setToY(1.4);
        scaling.setAutoReverse(true);
        scaling.setCycleCount(2);
        scaling.play();
    }
}
