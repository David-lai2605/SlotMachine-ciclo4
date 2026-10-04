/**
 * Rueda tipo Lefty: Cuando gira la rueda, copia el estado o símbolo de la rueda a su izquierda.
 * 
 * @author Julián David Laiton Laiton
 * @version 4.0
 */
public class LeftyWheel extends Wheel {

    /**
     * Constructor para la rueda Lefty.
     * @param xPos Posición X en el Canvas
     * @param yPos Posición Y en el Canvas
     */
    public LeftyWheel(int xPos, int yPos) {
        super(xPos, yPos);
    }

    @Override
    public String getType() {
        return "lefty";
    }
}