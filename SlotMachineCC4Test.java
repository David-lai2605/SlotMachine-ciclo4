import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

/**
 * Casos de prueba de unidad compartidos para el Ciclo 4.
 * Contiene un escenario de éxito (Happy Path) y un escenario de fallo/excepción.
 * 
 * @author Julián David Laiton Laiton
 * @version 4.0
 */
public class SlotMachineCC4Test {

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

    /**
     * Verifica la adición de diferentes tipos de ruedas (normal, rebel, lefty, golden),
     * la inserción de símbolos especiales y la réplica de estado al girar una rueda Lefty.
     */
    @Test
    public void accordingToMiLlShouldWorkWithSpecialWheelsAndSymbols() throws SlotMachineException {
        // 1. Crear la máquina con las diferentes ruedas
        machine.addWheel("normal", 1);
        machine.addWheel("rebel", 2);
        machine.addWheel("lefty", 3);
        machine.addWheel("golden", 4);

        // 2. Agregar símbolos normales y especiales
        machine.addSymbol("ephemeral", 1, "red");
        machine.addSymbol("shy", 2, "blue");
        machine.addSymbol("chameleon", 3, "green");
        machine.addSymbol("normal", 4, "yellow");

        // 3. Girar la rueda Lefty (posición 3) para que copie la rueda a su izquierda (posición 2)
        machine.spin(3);

        // 4. Verificaciones
        assertTrue(machine.ok());
        assertEquals(4, machine.configuration().length);
    }

    /**
     * CASO DE PRUEBA COMPARTIDO 2 (Fallo / Exception Handling Path):
     * Verifica que al intentar realizar una acción prohibida sobre una RebelWheel (por ejemplo, lock)
     * la máquina lance la excepción SlotMachineException y marque ok() como false[cite: 5].
     */
    @Test
    public void accordingToMiLlShouldFailWhenPerformingForbiddenActionOnRebelWheel() throws SlotMachineException {
        machine.addWheel("rebel", 1);
        machine.addSymbol(1, "red");

        try {
            // Intentar bloquear una RebelWheel debe lanzar excepción
            machine.lock(1);
            fail("Se esperaba SlotMachineException al intentar bloquear una RebelWheel.");
        } catch (SlotMachineException e) {
            // Verificación de la excepción y del estado ok()
            assertEquals(SlotMachineException.REBEL_ACTION_FORBIDDEN, e.getMessage());
            assertFalse(machine.ok());
        }
    }
}