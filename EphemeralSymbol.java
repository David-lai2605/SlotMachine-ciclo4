/**
 * Símbolo Ephemeral: En cada giro decrementa su tamaño hasta quedar como un punto.
 */
public class EphemeralSymbol extends Symbol {
    private int currentSize = 40;

    public EphemeralSymbol(String color, int xPos, int yPos) {
        super(color, xPos, yPos);
    }

    @Override
    public String getType() {
        return "ephemeral";
    }

    @Override
    public void onSpin() {
        if (currentSize > 5) {
            currentSize -= 10;
            if (this.shape != null){
                this.shape.changeSize(currentSize);
            }
        }
    }
}