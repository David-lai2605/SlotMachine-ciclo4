import java.util.Random;

/**
 * Símbolo Chameleon (Propuesto): Cambia a un color aleatorio cada vez que la rueda gira.
 * 
 * @author David Laiton
 * @version 4.0
 */
public class ChameleonSymbol extends Symbol {
    private static final String[] PALETTE = {"red", "blue", "green", "yellow", "magenta", "orange", "cyan"};

    public ChameleonSymbol(String color, int xPos, int yPos) {
        super(color, xPos, yPos);
    }

    @Override
    public String getType() {
        return "chameleon";
    }

    @Override
    public void onSpin() {
        String newColor = PALETTE[new Random().nextInt(PALETTE.length)];
        this.color = newColor; // Asigna el nuevo color a la variable protegida
        if (this.shape != null) {
            this.shape.changeColor(newColor); // Cambia el color visual en el Circle
        }
    }
}