import java.util.ArrayList;

/**
 * Representa una rueda individual en el simulador SlotMachine.
 * Modificada para soporte de Refactoring y Extensión (Ciclo 4)
 * 
 * @author David Laiton
 * @version 4.0
 */
public class Wheel {

    protected ArrayList<Symbol> symbols;
    protected int visibleIndex;
    protected Rectangle frame;
    protected int xPosition;
    protected int yPosition;
    protected boolean isVisible;
    protected boolean isLocked;

    public Wheel(int x, int y) {
        symbols = new ArrayList<Symbol>();
        visibleIndex = 0;
        xPosition = x;
        yPosition = y;
        isVisible = false;
        isLocked = false;

        frame = new Rectangle();
        frame.changeSize(60, 50);
        frame.changeColor("black");
        frame.moveHorizontal(x - 60);
        frame.moveVertical(y - 50);
    }

    /**
     * Retorna el tipo de rueda ("normal" por defecto)
     */
    public String getType() {
        return "normal";
    }

    public void setLocked(boolean locked) throws SlotMachineException {
        this.isLocked = locked;
    }

    public boolean isLocked() {
        return isLocked;
    }

    /**
     * Agrega un símbolo de tipo normal por defecto.
     */
    public void addSymbol(String color) {
        try {
            addSymbol("normal", color);
        } catch (SlotMachineException e) {
            //
        }
    }

    /**
     * Requisito Ciclo 4: Sobrecarga para instanciar símbolos según su tipo
     */
    public void addSymbol(String type, String color) throws SlotMachineException {
        Symbol newSymbol;
        
        if ("ephemeral".equalsIgnoreCase(type)) {
            newSymbol = new EphemeralSymbol(color, xPosition + 5, yPosition + 5);
        } else if ("shy".equalsIgnoreCase(type)) {
            newSymbol = new ShySymbol(color, xPosition + 5, yPosition + 5);
        } else if ("chameleon".equalsIgnoreCase(type)) {
            newSymbol = new ChameleonSymbol(color, xPosition + 5, yPosition + 5);
        } else {
            newSymbol = new Symbol(color, xPosition + 5, yPosition + 5); // Normal
        }

        symbols.add(newSymbol);

        if (symbols.size() == 1 && isVisible) {
            newSymbol.makeVisible();
        }
    }

    public boolean removeSymbol(String color) {
        for (int i = 0; i < symbols.size(); i++) {
            Symbol s = symbols.get(i);
            if (s.getColor().equalsIgnoreCase(color)) {
                s.makeInvisible();
                symbols.remove(i);
                if (visibleIndex >= symbols.size()) {
                    visibleIndex = 0;
                }
                updateVisibleSymbol();
                return true;
            }
        }
        return false;
    }

    public boolean containsSymbol(String color) {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getColor().equalsIgnoreCase(color)) {
                return true;
            }
        }
        return false;
    }

    public void spin() {
        if (isLocked || symbols.size() == 0) return;

        symbols.get(visibleIndex).makeInvisible();
        visibleIndex = (visibleIndex + 1) % symbols.size();
        
        // Ejecuta el comportamiento específico de cada símbolo al girar (Ephemeral, Shy, Chameleon)[cite: 2]
        Symbol activeSymbol = symbols.get(visibleIndex);
        activeSymbol.onSpin();

        updateVisibleSymbol();
    }

    /**
     * Requisito Lefty: Copia la lista de símbolos y el índice activo de otra rueda
     */
    public void copyStateFrom(Wheel otherWheel) {
        if (otherWheel != null && !otherWheel.symbols.isEmpty()) {
            if (this.isVisible && !this.symbols.isEmpty()) {
                this.symbols.get(this.visibleIndex).makeInvisible();
            }
            
            this.symbols.clear();
            for (Symbol s : otherWheel.symbols) {
                try {
                    this.addSymbol(s.getType(), s.getColor());
                } catch (SlotMachineException e) {
                    // Ignorar
                }
            }
            this.visibleIndex = otherWheel.visibleIndex;
            updateVisibleSymbol();
        }
    }

    public void setTopSymbol(String color) {
        if (isLocked) return;

        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getColor().equalsIgnoreCase(color)) {
                symbols.get(visibleIndex).makeInvisible();
                visibleIndex = i;
                updateVisibleSymbol();
                break;
            }
        }
    }

    public String getVisibleSymbol() {
        if (symbols.size() == 0) return null;
        return symbols.get(visibleIndex).getColor();
    }

    public String[] getSymbolsArray() {
        String[] arr = new String[symbols.size()];
        for (int i = 0; i < symbols.size(); i++) {
            arr[i] = symbols.get(i).getColor();
        }
        return arr;
    }

    public ArrayList<String> getAllSymbols() {
        ArrayList<String> unique = new ArrayList<String>();
        for (int i = 0; i < symbols.size(); i++) {
            String c = symbols.get(i).getColor();
            if (!unique.contains(c)) {
                unique.add(c);
            }
        }
        return unique;
    }

    public void setPosition(int x, int y) {
        int deltaX = x - xPosition;
        int deltaY = y - yPosition;
        xPosition = x;
        yPosition = y;

        frame.moveHorizontal(deltaX);
        frame.moveVertical(deltaY);

        for (int i = 0; i < symbols.size(); i++) {
            symbols.get(i).setPosition(x + 5, y + 5);
        }
    }

    public void makeVisible() {
        isVisible = true;
        frame.makeVisible();
        updateVisibleSymbol();
    }

    public void makeInvisible() {
        isVisible = false;
        frame.makeInvisible();
        for (int i = 0; i < symbols.size(); i++) {
            symbols.get(i).makeInvisible();
        }
    }

    private void updateVisibleSymbol() {
        if (isVisible && symbols.size() > 0) {
            symbols.get(visibleIndex).makeVisible();
        }
    }
}