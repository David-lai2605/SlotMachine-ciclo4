import java.util.ArrayList;
import java.util.List;

/**
 * Solucionador y simulador para la maratón de Slot Machine.
 * Actualizado para el Ciclo 4 con soporte de excepciones
 * 
 * @author Julián David Laiton Laiton
 * @version 4.0
 */
public class SlotMachineContest {

    private List<int[]> pasosSolucion;

    public SlotMachineContest() {
        this.pasosSolucion = new ArrayList<>();
    }

    /**
     * Calcula la cantidad mínima de movimientos para lograr el Jackpot de forma invisible
     * @param n Número de ruedas de la máquina
     * @return Cantidad total de giros/movimientos realizados
     */
    public int solve(int n) {
        pasosSolucion.clear();
        SlotMachine machine = new SlotMachine(n);
        machine.makeInvisible();

        int totalMovimientos = 0;

        for (int i = 2; i <= n; i++) {
            if (machine.isJackpot()) {
                break;
            }

            for (int paso = 1; paso <= n; paso++) {
                try {
                    machine.spin(i, 1);
                    totalMovimientos++;
                    pasosSolucion.add(new int[]{i, 1});

                    if (machine.isJackpot()) {
                        return totalMovimientos;
                    }

                    String[] config = machine.configuration();
                    if (config[i - 1] != null && config[i - 1].equalsIgnoreCase(config[0])) {
                        break;
                    }
                } catch (SlotMachineException e) {
                    // Si ocurre una excepción durante el giro en el solucionador, detiene el intento en esta rueda
                    break;
                }
            }
        }

        return totalMovimientos;
    }

    /**
     * Reproduce gráficamente en el Canvas la secuencia de solución encontrada
     * @param n Número de ruedas a simular
     */
    public void simulate(int n) {
        solve(n);

        SlotMachine machine = new SlotMachine(n);
        machine.makeVisible();

        for (int[] paso : pasosSolucion) {
            int rueda = paso[0];
            int pasos = paso[1];
            try {
                machine.spin(rueda, pasos);
            } catch (SlotMachineException e) {
                // Manejo de excepción visual si un giro falla
            }
        }
    }
}