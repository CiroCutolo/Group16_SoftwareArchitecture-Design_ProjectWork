package Handlers;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.util.List;

/**
 * La classe ColorSelectionHandler gestisce la selezione dei colori da applicare
 * al bordo (perimetro) o al riempimento delle forme, in base ai radio button attivi.
 * Include la gestione visiva dei bottoni colore (hover, click).
 * 
 */
public class ColorSelectionHandler {

    private RadioButton perimeterRadio;
    private RadioButton fillRadio;

    //Colori di bordo e rimepimento predefiniti
    private Color perimetralColor = Color.BLACK;
    private Color fillingColor = Color.TRANSPARENT;

    private Button selectedColorButton = null;

    /**
     * Inizializza i radio button da utilizzare per determinare se applicare il colore
     * selezionato al bordo o al riempimento delle forme.
     *
     * @param perimeterRadio radio button associato al colore del bordo
     * @param fillRadio radio button associato al colore di riempimento
     */
    public void init(RadioButton perimeterRadio, RadioButton fillRadio) {
        this.perimeterRadio = perimeterRadio;
        this.fillRadio = fillRadio;
    }

    /**
     * Registra i bottoni colore con i relativi eventi mouse:
     * - hover (ingrandimento e ombreggiatura)
     * - uscita hover (reset effetto)
     * - click (applica il colore e aggiorna lo stile dei radio button).
     *
     * @param colorButtons lista dei pulsanti che rappresentano i colori disponibili
     */
    public void registerColorButtons(List<Button> colorButtons) {
        for (Button button : colorButtons) {
            button.setOnMouseEntered(this::hoverEffect);
            button.setOnMouseExited(this::removeHoverEffect);
            button.setOnMouseClicked(e -> {
                Color color = getColorFromButton(button);
                handleColorSelection(color);
                clickEffect(e);
            });
        }
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
    private void removeHoverEffect(MouseEvent event) {
        
        Button button = (Button) event.getSource(); // Viene salvato il color button sottoposto all'evento scatenante

        // Alla presione di un colore già selezionato non succede nulla
        if (button == selectedColorButton) {
            return;
        }

        button.setEffect(null); // Vengono azzerati gli effetti

        //Viene reimpostato lo scaling ad una taglia normale
        ScaleTransition scaling = new ScaleTransition(Duration.millis(150), button);
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
    private void hoverEffect(MouseEvent event) {
        Button button = (Button) event.getSource(); // Viene salvato il color button sottoposto all'evento scatenante

        // Alla pressione di un colore già selezionato non succede nulla
        if (button == selectedColorButton) {
            return;
        }

        // Viene istanziato l'effetto di ombreggiatura
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.LIGHTGRAY);
        shadow.setRadius(10);
        button.setEffect(shadow);

        // Viene istanziato un effetto di scaling
        ScaleTransition scaling = new ScaleTransition(Duration.millis(150), button  );
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
    private void clickEffect(MouseEvent event) {
        // Viene salvato il bottone che è sorgente dell'evento
        Button colorButton = (Button) event.getSource();
        
        // Viene salvato il colore di background del bottone 
        BackgroundFill fill = colorButton.getBackground().getFills().get(0);
        Paint selectedPaint = fill.getFill();

        // Alla presenza di un colore nel bottone
        if (selectedPaint instanceof Color) {
            Color color = (Color) selectedPaint;
            String hexColor = colorToHexString(color); // Viene rilevato il colore in forma di stringa esadecimale
            String textColor = isColorLight(color) ? "black" : "white"; // Viene effettuato un controllo dedito a mantenere un contrasto sopportabile tra background color e colore del testo

            // Viene formattata la stringa di stile in modo da renderla applicabile
            String style = String.format(
                "-fx-background-color: %s; -fx-text-fill: %s; -fx-background-radius: 20;",
                hexColor, textColor
            );

            // Alla pressione dei radio viene applicato il colore al bordo o al riempimento
            if (perimeterRadio.isSelected()) {
                perimeterRadio.setStyle(style);
            } else if (fillRadio.isSelected()) {
                fillRadio.setStyle(style);
            }
        }
    }

    /**
     * Metodo dedito al confronto tra colori per definire il colore del testo rispetto 
     * al background per un background troppo luminescente viene utilizzato il testo nero,
     * il bianco altrimenti.
     * 
     * @param color colore di cui si deve determinare la luminescenza
     * 
     * @return boolean valore logico residuo dal confronto tra i colori
     * 
     * @author ciroc 
     */
    private boolean isColorLight(Color color) {
        // Calcolo della luminescenza emanta dal colore
        double luminance = 0.2126 * color.getRed()
                         + 0.7152 * color.getGreen()
                         + 0.0722 * color.getBlue();
        return luminance > 0.6;
    }

    /**
     * Metodo dedito alla traduzione dei colori in una stringa esadecimale
     * 
     * @param color colore che deve essere tradotto, in modo da renderlo interpretabile dagli attributi di stile
     * 
     * @return String stringa interpretabile dagli attributi di stile
     * 
     * @author ciroc
     */
    private String colorToHexString(Color color) {
        int red = (int) (color.getRed() * 255);
        int green = (int) (color.getGreen() * 255);
        int blue = (int) (color.getBlue() * 255);
        return String.format("#%02X%02X%02X", red, green, blue);
    }
    
    /**
     * Ottiene il colore di background dal bottone
     * @return il colore del pulsante
     */
    private Color getColorFromButton(Button button) {
        return (Color) button.getBackground().getFills().get(0).getFill();
    }
    
    /**
     * Ottiene il colore selezionato per il bordo
     * @return colore selezionato per il bordo (perimetro)
     */
    public Color getPerimetralColor() {
        return perimetralColor;
    }

    /**
     * Ottiene il colore selezionato per il riempimento
     * @return colore selezionato per il riempimento
     */
    public Color getFillingColor() {
        return fillingColor;
    }
}
