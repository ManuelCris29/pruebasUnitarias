package validator;

import model.Vehiculo;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

public class VehiculoValidator {
    
    /**
     * Valida los atributos comunes de todos los vehículos.
     * Este método será llamado por los validadores específicos.
     */
    protected List<String> validarAtributosComunes(Vehiculo vehiculo) {
        List<String> errores = new ArrayList<>();
        
        // Validar ID
        if (vehiculo.getId() <= 0) {
            errores.add("El ID debe ser mayor a 0");
        }
        
        // Validar marca
        if (vehiculo.getMarca() == null || vehiculo.getMarca().trim().isEmpty()) {
            errores.add("La marca no puede estar vacía");
        } else if (vehiculo.getMarca().trim().length() < 2) {
            errores.add("La marca debe tener al menos 2 caracteres");
        } else if (vehiculo.getMarca().length() > 50) {
            errores.add("La marca no puede tener más de 50 caracteres");
        }
        
        // Validar modelo
        if (vehiculo.getModelo() == null || vehiculo.getModelo().trim().isEmpty()) {
            errores.add("El modelo no puede estar vacío");
        } else if (vehiculo.getModelo().trim().length() < 1) {
            errores.add("El modelo debe tener al menos 1 carácter");
        } else if (vehiculo.getModelo().length() > 50) {
            errores.add("El modelo no puede tener más de 50 caracteres");
        }
        
        // Validar año
        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        if (vehiculo.getAnio() < 1900) {
            errores.add("El año no puede ser menor a 1900");
        } else if (vehiculo.getAnio() > anioActual + 1) {
            errores.add("El año no puede ser mayor a " + (anioActual + 1));
        }
        
        // Validar precio
        if (vehiculo.getPrecio() <= 0) {
            errores.add("El precio debe ser mayor a 0");
        } else if (vehiculo.getPrecio() > 10000000) {
            errores.add("El precio no puede ser mayor a $10,000,000");
        }
        
        // Validar color
        if (vehiculo.getColor() == null || vehiculo.getColor().trim().isEmpty()) {
            errores.add("El color no puede estar vacío");
        } else if (vehiculo.getColor().trim().length() < 3) {
            errores.add("El color debe tener al menos 3 caracteres");
        } else if (!vehiculo.getColor().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            errores.add("El color solo puede contener letras y espacios");
        }
        
        return errores;
    }
}