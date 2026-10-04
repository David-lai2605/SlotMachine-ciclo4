
/**
 * Excepcion personalizada para SlotMachine
 *
 * @author Julian David Laiton Laiton
 * @version 1.0
 */
public class SlotMachineException extends Exception{
    public static final String REBEL_ACTION_FORBIDDEN = "No se puede bloquear, intercambiar o eliminar una rueda tipo Rebel";
    public static final String INVALID_POSITION = "La posicion especificada no existe en la maquina";
    public static final String INVALID_TYPE = "El tipo de rueda o simbolo especificado no es valido";

    public SlotMachineException(String message){
        super(message);
    }
}