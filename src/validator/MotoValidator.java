package validator;

import model.Moto;
import java.util.ArrayList;
import java.util.List;

public class MotoValidator extends VehiculoValidator implements IValidator<Moto> {
    
    @Override
    public List<String> validate(Moto moto) {
        List<String> errores = new ArrayList<>();
        
        // Validar atributos comunes
        errores.addAll(validarAtributosComunes(moto));
        
        // Validar cilindrada
        if (moto.getCilindrada() < 50) {
            errores.add("La cilindrada debe ser mayor o igual a 50cc");
        } else if (moto.getCilindrada() > 2000) {
            errores.add("La cilindrada no puede ser mayor a 2000cc");
        }
        
        // Validación de negocio: Motos de alta cilindrada generalmente tienen carenado
        if (moto.getCilindrada() >= 600 && !moto.isTieneCarenado()) {
            errores.add("Advertencia: Las motos de más de 600cc generalmente tienen carenado");
        }
        
        // Validación adicional: Motos pequeñas no suelen tener carenado
        if (moto.getCilindrada() < 150 && moto.isTieneCarenado()) {
            errores.add("Advertencia: Las motos de menos de 150cc rara vez tienen carenado");
        }
        
        return errores;
    }
}