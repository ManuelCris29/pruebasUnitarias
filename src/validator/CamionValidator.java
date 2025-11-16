package validator;

import model.Camion;
import java.util.ArrayList;
import java.util.List;

public class CamionValidator extends VehiculoValidator implements IValidator<Camion> {
    
    @Override
    public List<String> validate(Camion camion) {
        List<String> errores = new ArrayList<>();
        
        // Validar atributos comunes
        errores.addAll(validarAtributosComunes(camion));
        
        // Validar capacidad de carga
        if (camion.getCapacidadCarga() <= 0) {
            errores.add("La capacidad de carga debe ser mayor a 0 toneladas");
        } else if (camion.getCapacidadCarga() > 50) {
            errores.add("La capacidad de carga no puede ser mayor a 50 toneladas");
        }
        
        // Validar número de ejes
        if (camion.getNumeroEjes() < 2) {
            errores.add("El número de ejes debe ser al menos 2");
        } else if (camion.getNumeroEjes() > 9) {
            errores.add("El número de ejes no puede ser mayor a 9");
        }
        
        // Validación de negocio: Relación entre capacidad y ejes
        if (camion.getCapacidadCarga() > 20 && camion.getNumeroEjes() < 4) {
            errores.add("Un camión de más de 20 toneladas debe tener al menos 4 ejes");
        }
        
        if (camion.getCapacidadCarga() <= 5 && camion.getNumeroEjes() > 3) {
            errores.add("Advertencia: Un camión de carga ligera (<5 tons) generalmente tiene 2-3 ejes");
        }
        
        return errores;
    }
}