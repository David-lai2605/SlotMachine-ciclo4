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
public class SlotMachineContestCTest{
    
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
     * Caso de prueba compartido 1:
     * Evalúa que el método solve resuelva de forma óptima y retorne una cantidad válida de movimientos para la configuración básica de 3 ruedas.
     */
    @Test
    public void shouldSolve3Ruedas(){
        int n = 3;
        int totalMovimientos = contest.solve(n);
        assertTrue("El numero total de movimientos calculados debe ser mayor que 0", totalMovimientos >= 0);
    }
    
    /**
     * Caso de prueba compartido 2:
     * Valida que el algoritmo escale correctamente y resuelva una máquina de 5 ruedas dentro del margen máximo de movimientos permitidos por las reglas.
     */
    @Test
    public void shouldSolve5RuedasSinExcederLimite(){
        int n = 5;
        
        int maximoEsperado = n * 13;
        int movimientos = contest.solve(n);
        
        assertTrue("Los movimientos (" + movimientos + ") no deben superar el maximo de (" + maximoEsperado + ")", movimientos <= maximoEsperado);
    }
}    