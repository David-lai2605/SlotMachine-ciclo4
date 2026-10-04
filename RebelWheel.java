/**
 * Rueda tipo Rebel: Bloquea las acciones de lock, swap y delWheel.
 * 
 * @author Julián David Laiton Laiton
 * @version 4.0
 */
public class RebelWheel extends Wheel {

    /**
     * Constructor para la rueda Rebel.
     * @param xPos Posición X en el Canvas
     * @param yPos Posición Y en el Canvas
     */
    public RebelWheel(int xPos, int yPos) {
        super(xPos, yPos);
    }

    @Override
    public String getType() {
        return "rebel";
    }
}