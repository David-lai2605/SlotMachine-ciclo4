import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

/**
 * Pruebas de unidad internas del proyecto Ciclo 2.
 * Actualizadas para soporte de excepciones.
 * 
 * @author Julián David Laiton Laiton
 * @version 4.0
 */
public class SlotMachineC2Test {
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
    public void shouldSwapTwoWheelCorrectly() throws SlotMachineException {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        
        machine.swap(1, 2);
        
        String[] config = machine.configuration();
        assertEquals("blue", config[0]);
        assertEquals("red", config[1]);
        assertTrue(machine.ok());
    }
    
    @Test
    public void shouldNotSwapWhenLessThanTwoWheels() throws SlotMachineException {
        machine.addWheel(1);
        try {
            machine.swap(1, 2);
        } catch (SlotMachineException e) {
            // Se espera la excepción al intentar hacer swap en una posición inválida[cite: 3]
        }
        
        assertFalse(machine.ok());
    }
    
    @Test
    public void shouldNotSpinWhenWheelIsLocked() throws SlotMachineException {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        
        machine.lock(1);
        machine.spin(1);
        
        String[] config = machine.configuration();
        assertEquals("red", config[0]);
    }
    
    @Test
    public void shouldSpinAfterWheelIsUnlocked() throws SlotMachineException {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        
        machine.lock(1);
        machine.spin(1);
        machine.unlock(1);
        machine.spin(1);
        
        String[] config = machine.configuration();
        assertEquals("blue", config[0]);
        assertTrue(machine.ok());
    }
}