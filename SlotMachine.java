import javax.swing.JOptionPane;
import java.util.ArrayList;

/**
 * Clase principal del simulador SlotMachine (Ciclo 4 - Refactoring y Extensión)[cite: 2].
 * 
 * @author Julián David Laiton Laiton
 * @version 4.0
 */
public class SlotMachine {

    private ArrayList<Wheel> wheels;
    private boolean isVisible;
    private boolean lastOperationOk;

    public SlotMachine() {
        wheels = new ArrayList<Wheel>();
        isVisible = false;
        lastOperationOk = true;
    }

    public SlotMachine(int n) {
        this();
        String[] simbolosBase = {"red", "blue", "green", "yellow", "black", "white", "gray", "orange", "cyan", "magenta", "pink", "gold", "purple"};
        
        for (int i = 1; i <= n; i++) {
            try {
                this.addWheel("normal", i);
                for (String simbolo : simbolosBase) {
                    this.addSymbol("normal", i, simbolo);
                }
            } catch (SlotMachineException e) {
                lastOperationOk = false;
            }
        }
    }

    /**
     * Agrega una rueda normal por defecto.
     */
    public void addWheel(int pos) throws SlotMachineException {
        addWheel("normal", pos);
    }

    /**
     * Requisito Ciclo 4: Agrega una rueda especificando su tipo ("normal", "lefty", "rebel", "golden")[cite: 2, 3].
     */
    public void addWheel(String type, int pos) throws SlotMachineException {
        if (pos < 1 || pos > wheels.size() + 1) {
            lastOperationOk = false;
            throw new SlotMachineException(SlotMachineException.INVALID_POSITION);
        }

        int xOffset = (pos - 1) * 65 + 50;
        Wheel newWheel;

        if ("rebel".equalsIgnoreCase(type)) {
            newWheel = new RebelWheel(xOffset, 100);
        } else if ("lefty".equalsIgnoreCase(type)) {
            newWheel = new LeftyWheel(xOffset, 100);
        } else if ("golden".equalsIgnoreCase(type)) {
            newWheel = new GoldenWheel(xOffset, 100);
        } else {
            newWheel = new Wheel(xOffset, 100);
        }

        wheels.add(pos - 1, newWheel);

        for (int i = 0; i < wheels.size(); i++) {
            wheels.get(i).setPosition(i * 65 + 50, 100);
        }

        if (isVisible) {
            newWheel.makeVisible();
        }
        lastOperationOk = true;
    }

    /**
     * Elimina una rueda validando si es de tipo Rebel[cite: 2, 3].
     */
    public void delWheel(int pos) throws SlotMachineException {
        checkPosition(pos);
        Wheel target = wheels.get(pos - 1);

        if ("rebel".equalsIgnoreCase(target.getType())) {
            lastOperationOk = false;
            throw new SlotMachineException(SlotMachineException.REBEL_ACTION_FORBIDDEN);
        }

        Wheel removed = wheels.remove(pos - 1);
        removed.makeInvisible();

        for (int i = 0; i < wheels.size(); i++) {
            wheels.get(i).setPosition(i * 65 + 50, 100);
        }
        lastOperationOk = true;
    }

    /**
     * Intercambia dos ruedas validando que ninguna sea Rebel[cite: 2, 3].
     */
    public void swap(int wheel1, int wheel2) throws SlotMachineException {
        checkPosition(wheel1);
        checkPosition(wheel2);

        Wheel w1 = wheels.get(wheel1 - 1);
        Wheel w2 = wheels.get(wheel2 - 1);

        if ("rebel".equalsIgnoreCase(w1.getType()) || "rebel".equalsIgnoreCase(w2.getType())) {
            lastOperationOk = false;
            throw new SlotMachineException(SlotMachineException.REBEL_ACTION_FORBIDDEN);
        }

        wheels.set(wheel1 - 1, w2);
        wheels.set(wheel2 - 1, w1);

        for (int i = 0; i < wheels.size(); i++) {
            wheels.get(i).setPosition(i * 65 + 50, 100);
        }
        lastOperationOk = true;
    }

    /**
     * Bloquea una rueda retornando su posición si fue exitoso[cite: 3].
     */
    public int lock(int wheel) throws SlotMachineException {
        checkPosition(wheel);
        Wheel target = wheels.get(wheel - 1);

        if ("rebel".equalsIgnoreCase(target.getType())) {
            lastOperationOk = false;
            throw new SlotMachineException(SlotMachineException.REBEL_ACTION_FORBIDDEN);
        }

        target.setLocked(true);
        lastOperationOk = true;
        return wheel;
    }

    public void unlock(int wheel) throws SlotMachineException {
        checkPosition(wheel);
        wheels.get(wheel - 1).setLocked(false);
        lastOperationOk = true;
    }

    public void addSymbol(int pos, String color) throws SlotMachineException {
        addSymbol("normal", pos, color);
    }

    /**
     * Requisito Ciclo 4: Agrega un símbolo especificando su tipo ("normal", "ephemeral", "shy", "chameleon")[cite: 2, 3].
     */
    public void addSymbol(String type, int pos, String color) throws SlotMachineException {
        checkPosition(pos);
        Wheel targetWheel = wheels.get(pos - 1);
        targetWheel.addSymbol(type, color);
        lastOperationOk = true;
    }

    public void delSymbol(String symbol) {
        boolean removedAny = false;
        for (int i = 0; i < wheels.size(); i++) {
            if (wheels.get(i).removeSymbol(symbol)) {
                removedAny = true;
            }
        }
        lastOperationOk = removedAny;
    }

    public void placeSymbol(int wheel, String symbol) throws SlotMachineException {
        checkPosition(wheel);
        Wheel target = wheels.get(wheel - 1);

        if (target.containsSymbol(symbol)) {
            target.setTopSymbol(symbol);
            lastOperationOk = true;
        } else {
            lastOperationOk = false;
        }
    }

    public void spin(int wheel) throws SlotMachineException {
        spin(wheel, 1);
    }

    public void spin(int wheel, int steps) throws SlotMachineException {
        checkPosition(wheel);
        Wheel target = wheels.get(wheel - 1);

        // Lógica para Rueda Lefty: Copia el estado de la rueda situada a su izquierda[cite: 2]
        if ("lefty".equalsIgnoreCase(target.getType()) && wheel > 1) {
            Wheel leftWheel = wheels.get(wheel - 2);
            target.copyStateFrom(leftWheel);
        }

        for (int i = 0; i < steps; i++) {
            target.spin();
            if (isVisible) {
                Canvas.getCanvas().wait(200);
            }
        }
        lastOperationOk = true;
    }

    public void spin(String setSymbols) {
        lastOperationOk = true;
    }

    public void spin() {
        boolean allOk = true;
        for (int i = 0; i < wheels.size(); i++) {
            try {
                spin(i + 1, 1);
            } catch (SlotMachineException e) {
                allOk = false;
            }
        }
        lastOperationOk = allOk;
    }

    public String symbols() {
        if (wheels.isEmpty()) return "[]";
        return wheels.get(0).getSymbolsArray().toString();
    }

    public int distinctSymbols() {
        ArrayList<String> uniqueList = new ArrayList<String>();
        for (Wheel wheel : wheels) {
            for (String symbol : wheel.getAllSymbols()) {
                if (!uniqueList.contains(symbol)) {
                    uniqueList.add(symbol);
                }
            }
        }
        lastOperationOk = true;
        return uniqueList.size();
    }

    public String[] configuration() {
        String[] config = new String[wheels.size()];
        for (int i = 0; i < wheels.size(); i++) {
            config[i] = wheels.get(i).getVisibleSymbol();
        }
        lastOperationOk = true;
        return config;
    }

    public boolean isJackpot() {
        if (wheels.isEmpty()) return false;
        String[] currentConfig = configuration();
        String first = currentConfig[0];
        if (first == null) return false;

        for (String s : currentConfig) {
            if (s == null || !s.equalsIgnoreCase(first)) {
                return false;
            }
        }
        return true;
    }

    public void makeVisible() {
        isVisible = true;
        for (Wheel w : wheels) w.makeVisible();
        lastOperationOk = true;
    }

    public void makeInvisible() {
        isVisible = false;
        for (Wheel w : wheels) w.makeInvisible();
        lastOperationOk = true;
    }

    public void exit() {
        makeInvisible();
        System.exit(0);
    }

    public boolean ok() {
        return lastOperationOk;
    }

    private void checkPosition(int pos) throws SlotMachineException {
        if (pos < 1 || pos > wheels.size()) {
            lastOperationOk = false;
            throw new SlotMachineException(SlotMachineException.INVALID_POSITION);
        }
    }
}