package exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends Exception {
    
    // Lista de errores de validación
    private List<String> errores;
    
    // Constructor con un solo error
    public ValidationException(String mensaje) {
        super(mensaje);
        this.errores = new ArrayList<>();
        this.errores.add(mensaje);
    }
    
    // Constructor con lista de errores
    public ValidationException(List<String> errores) {
        super(construirMensaje(errores));
        this.errores = errores;
    }
    
    // Constructor con mensaje y lista de errores
    public ValidationException(String mensaje, List<String> errores) {
        super(mensaje);
        this.errores = errores;
    }
    
    // Obtener lista de errores
    public List<String> getErrores() {
        return errores;
    }
    
    // Verificar si hay errores
    public boolean tieneErrores() {
        return errores != null && !errores.isEmpty();
    }
    
    // Obtener cantidad de errores
    public int getCantidadErrores() {
        return errores != null ? errores.size() : 0;
    }
    
    // Construir mensaje con todos los errores
    private static String construirMensaje(List<String> errores) {
        if (errores == null || errores.isEmpty()) {
            return "Errores de validación";
        }
        
        StringBuilder sb = new StringBuilder("Errores de validación:\n");
        for (int i = 0; i < errores.size(); i++) {
            sb.append(String.format("  %d. %s\n", i + 1, errores.get(i)));
        }
        return sb.toString();
    }
    
    // Método para agregar un error adicional
    public void agregarError(String error) {
        if (this.errores == null) {
            this.errores = new ArrayList<>();
        }
        this.errores.add(error);
    }
    
    @Override
    public String toString() {
        return getMessage();
    }
}