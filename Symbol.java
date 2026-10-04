/**
 * Representa un símbolo visual dentro de las ruedas.
 */
public class Symbol {

    protected String color;
    protected Circle shape;
    protected int xPosition;
    protected int yPosition;

    public Symbol(String color, int xPos, int yPos) {
        this.color = color;
        this.xPosition = xPos;
        this.yPosition = yPos;

        this.shape = new Circle();
        this.shape.changeColor(color);
        this.shape.changeSize(40);
        this.shape.moveHorizontal(xPos - 20);
        this.shape.moveVertical(yPos - 60);
    }
    
    /**
     * Retorna al simbolo tipo normal
     */
    public String getType() {
        return "normal";
    }
    
    /**
     * Se invoca este metodo a la clase Wheel al girar para detonar comportamientos especiales en subclases (Ephemeral, Shy, Chamaleon)
     */
    public void onSpin(){
        // En simbolo normal no se adiciona una accion
    }

    public String getColor() {
        return color;
    }

    public void setPosition(int newX, int newY) {
        int deltaX = newX - xPosition;
        int deltaY = newY - yPosition;

        xPosition = newX;
        yPosition = newY;

        shape.moveHorizontal(deltaX);
        shape.moveVertical(deltaY);
    }

    public void makeVisible() {
        shape.makeVisible();
    }

    public void makeInvisible() {
        shape.makeInvisible();
    }
}