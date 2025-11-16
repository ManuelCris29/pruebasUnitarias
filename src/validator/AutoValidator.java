package validator;

import model.Auto;
import java.util.ArrayList;
import java.util.List;

public class AutoValidator extends VehiculoValidator implements IValidator<Auto> {
    
    @Override
    public List<String> validate(Auto auto) {
        List<String> errores = new ArrayList<>();
        
        // Validar atributos comunes (heredados de Vehiculo)
        errores.addAll(validarAtributosComunes(auto));
        
        // Validar número de puertas
        if (auto.getNumeroPuertas() < 2 || auto.getNumeroPuertas() > 5) {
            errores.add("El número de puertas debe estar entre 2 y 5");
        }
        
        // Validar tipo de transmisión
        if (auto.getTipoTransmision() == null || auto.getTipoTransmision().trim().isEmpty()) {
            errores.add("El tipo de transmisión no puede estar vacío");
        } else {
            String transmision = auto.getTipoTransmision().trim().toLowerCase();
            if (!transmision.equals("manual") && !transmision.equals("automática") && 
                !transmision.equals("automatica")) {
                errores.add("El tipo de transmisión debe ser 'Manual' o 'Automática'");
            }
        }
        
        // Validación de negocio: Si tiene 2 puertas, probablemente no es sedán
        if (auto.getNumeroPuertas() == 2 && auto.isEsSedan()) {
            errores.add("Advertencia: Un auto de 2 puertas generalmente no es sedán");
        }
        
        return errores;
    }
}