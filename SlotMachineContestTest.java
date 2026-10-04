import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

/**
 * Casos de prueba para la clase SlotMachineContestTest.
 *
 * @author  Julian David Laiton Laiton
 * @version 1.0
 */
public class SlotMachineContestTest{
    
    private SlotMachineContest contest;
    
    /**
     * Configuracion previa a cada metodo de prueba
     */
    @Before
    public void setUp(){
        contest = new SlotMachineContest();
    }

    /**
     * Limpieza posterior a cada metodo de prueba
     */
    @After
    public void tearDown(){
        contest = null;
    }
    
    /**
     * Prueba el funcionamiento del método solve para una máquina con 3 ruedas.
     * Verifica que el total de giros calculados sea mayor o igual a cero.
     */
    @Test
    public void testSolve3Ruedas(){
        int n = 3;
        int totalMovimientos = contest.solve(n);
        assertTrue("El numero total de movimientos calculados debe ser mayor que 0", totalMovimientos >= 0);
    }
    
    /**
     * Verifica que la solución encontrada con solve no exceda el límite máximo razonable de giros permitidos según las reglas de la maratón.
     */
    @Test
    public void testSolveNoExcedeLimite(){
        int n = 5;
        
        int maximoEsperado = n * 13;
        int movimientos = contest.solve(n);
        
        assertTrue("Los movimientos (" + movimientos + ") no deben superar el maximo de (" + maximoEsperado + ")", movimientos <= maximoEsperado);
    }
    
    /**
     * Verifica que el método simulate ejecute y reconstruya adecuadamente la simulación gráfica para 3 ruedas.
     */
    @Test
    public void testSimulateSinErrores(){
        contest.simulate(3);
    }
}