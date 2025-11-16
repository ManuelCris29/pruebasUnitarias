package validator;

import java.util.List;

public interface  IValidator<T> {
     /**
     * Valida un objeto y retorna una lista de errores.
     * Si la lista está vacía, el objeto es válido.
     * 
     * @param objeto El objeto a validar
     * @return Lista de mensajes de error (vacía si es válido)
     */
    List<String> validate(T objeto);
    
    /**
     * Verifica si un objeto es válido.
     * 
     * @param objeto El objeto a validar
     * @return true si es válido, false si tiene errores
     */
    default boolean isValid(T objeto) {
        return validate(objeto).isEmpty();
    }
    
}
