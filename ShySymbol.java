/**
 * Símbolo Shy: Alterna su estado de visible a invisible cada vez que es seleccionado en la rueda.
 * 
 * @author David Laiton
 * @version 4.0
 */
public class ShySymbol extends Symbol {
    private boolean isHiddenState = false;

    public ShySymbol(String color, int xPos, int yPos) {
        super(color, xPos, yPos);
    }

    @Override
    public String getType() {
        return "shy";
    }

    @Override
    public void onSpin() {
        isHiddenState = !isHiddenState;
        if (this.shape != null) {
            if (isHiddenState) {
                this.shape.makeInvisible();
            } else {
                this.shape.makeVisible();
            }
        }
    }
}