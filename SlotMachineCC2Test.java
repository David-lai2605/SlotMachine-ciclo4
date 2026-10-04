import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

/**
 * Pruebas de unidad internas del proyecto Ciclo 2 (CC2).
 * Actualizadas para soporte de excepciones.
 * 
 * @author Julián David Laiton Laiton
 * @version 4.0
 */
public class SlotMachineCC2Test {
    private SlotMachine machine;
    
    @Before
    public void setUp() {
        machine = new SlotMachine();
        machine.makeInvisible();
    }
    
    @After
    public void tearDown() {
        if (machine != null) {
            machine.makeInvisible();
            machine = null;
        }
    }
    
    @Test
    public void accordingToMiLlShouldSpinWheelMultipleSteps() throws SlotMachineException {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(1, "green");
        
        machine.spin(1, 2);
        
        String[] config = machine.configuration();
        assertEquals("green", config[0]);
        assertTrue(machine.ok());
    }
    
    @Test
    public void accordingToMiLlShouldSetGivenConfiguration() throws SlotMachineException {
        machine.addWheel(1);
        machine.addWheel(2);
        
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        
        machine.addSymbol(2, "yellow");
        machine.addSymbol(2, "green");
        
        String[] target = {"blue", "green"};
        
        // Si spin recibe un arreglo de String[] o String separado por comas
        for (int i = 0; i < target.length; i++) {
            machine.placeSymbol(i + 1, target[i]);
        }
        
        String[] current = machine.configuration();
        assertEquals("blue", current[0]);
        assertEquals("green", current[1]);
        assertTrue(machine.ok());
    }
}