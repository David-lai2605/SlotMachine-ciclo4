/**
 * Rueda tipo Golden: Rueda especial propuesta para el Ciclo 4.
 * 
 * @author Julián David Laiton Laiton
 * @version 4.0
 */
public class GoldenWheel extends Wheel {

    /**
     * Constructor para la rueda Golden.
     * @param xPos Posición X en el Canvas
     * @param yPos Posición Y en el Canvas
     */
    public GoldenWheel(int xPos, int yPos) {
        super(xPos, yPos);
    }

    @Override
    public String getType() {
        return "golden";
    }
}