import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

/**
 * Pruebas de unidad internas para las nuevas funcionalidades y excepciones del Ciclo 4.
 * 
 * @author Julián David Laiton Laiton
 * @version 4.0
 */
public class SlotMachineC4Test {

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

    // 1. PRUEBAS DE EXCEPCIONES Y POSICIONES

    @Test
    public void shouldThrowExceptionOnInvalidPosition() {
        try {
            machine.addWheel(5); // Posición fuera de rango
            fail("Debió lanzar SlotMachineException por posición inválida.");
        } catch (SlotMachineException e) {
            assertEquals(SlotMachineException.INVALID_POSITION, e.getMessage());
            assertFalse(machine.ok());
        }
    }

    // 2. PRUEBAS DE TIPOS DE RUEDAS (WHEELS)

    @Test
    public void shouldNotAllowLockSwapOrDeleteOnRebelWheel() throws SlotMachineException {
        machine.addWheel("rebel", 1);
        
        // Intento de Bloquear
        try {
            machine.lock(1);
            fail("RebelWheel no debería permitir ser bloqueada.");
        } catch (SlotMachineException e) {
            assertEquals(SlotMachineException.REBEL_ACTION_FORBIDDEN, e.getMessage());
        }

        // Intento de Eliminar
        try {
            machine.delWheel(1);
            fail("RebelWheel no debería permitir ser eliminada.");
        } catch (SlotMachineException e) {
            assertEquals(SlotMachineException.REBEL_ACTION_FORBIDDEN, e.getMessage());
        }
    }

    @Test
    public void shouldCopyLeftWheelStateOnLeftyWheelSpin() throws SlotMachineException {
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);

        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        
        machine.addSymbol(2, "yellow");
        machine.addSymbol(2, "green");

        // Al girar la rueda 2 (Lefty), debe copiar el símbolo superior de la rueda 1 (red)
        machine.spin(2);

        String[] config = machine.configuration();
        assertEquals(config[0], config[1]);
        assertTrue(machine.ok());
    }

    @Test
    public void shouldCreateGoldenWheelCorrectly() throws SlotMachineException {
        machine.addWheel("golden", 1);
        machine.addSymbol(1, "gold");

        String[] config = machine.configuration();
        assertEquals("gold", config[0]);
        assertTrue(machine.ok());
    }

    // 3. PRUEBAS DE TIPOS DE SÍMBOLOS (SYMBOLS)

    @Test
    public void shouldAddSpecialSymbolsToWheel() throws SlotMachineException {
        machine.addWheel("normal", 1);

        machine.addSymbol("ephemeral", 1, "cyan");
        machine.addSymbol("shy", 1, "magenta");
        machine.addSymbol("chameleon", 1, "orange");

        assertTrue(machine.ok());
        assertEquals(3, machine.distinctSymbols());
    }

    @Test
    public void shouldVerifyJackpotWithSpecialWheels() throws SlotMachineException {
        machine.addWheel("normal", 1);
        machine.addWheel("golden", 2);

        machine.addSymbol(1, "red");
        machine.addSymbol(2, "red");

        assertTrue(machine.isJackpot());
    }
}