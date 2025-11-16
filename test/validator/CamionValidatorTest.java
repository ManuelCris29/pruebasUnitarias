package validator;

import model.Camion;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para CamionValidator
 */
@DisplayName("Pruebas de CamionValidator")
class CamionValidatorTest {

    private CamionValidator validator;
    
    @BeforeEach
    void setUp() {
        validator = new CamionValidator();
    }
    
    @Test
    @DisplayName("Validar Camión con datos válidos - Sin errores")
    void testValidate_ValidCamion_NoErrors() {
        // Arrange
        Camion camion = new Camion(1, "Volvo", "FH16", 2020, 150000.0, "Blanco", 
                                  25.0, 4);
        
        // Act
        List<String> errores = validator.validate(camion);
        
        // Assert
        assertTrue(errores.isEmpty());
    }
    
    @Test
    @DisplayName("Validar Camión - Error: capacidad de carga inválida")
    void testValidate_InvalidCapacidadCarga() {
        // Arrange
        Camion camion = new Camion(1, "Volvo", "FH16", 2020, 150000.0, "Blanco", 
                                  -5.0, 4);
        
        // Act
        List<String> errores = validator.validate(camion);
        
        // Assert
        assertFalse(errores.isEmpty());
        assertTrue(errores.stream().anyMatch(e -> e.contains("capacidad de carga")));
    }
    
    @Test
    @DisplayName("Validar Camión - Error: número de ejes inválido")
    void testValidate_InvalidNumeroEjes() {
        // Arrange
        Camion camion = new Camion(1, "Volvo", "FH16", 2020, 150000.0, "Blanco", 
                                  25.0, 1);
        
        // Act
        List<String> errores = validator.validate(camion);
        
        // Assert
        assertFalse(errores.isEmpty());
        assertTrue(errores.stream().anyMatch(e -> e.contains("ejes")));
    }
    
    @Test
    @DisplayName("Validar Camión - Regla de negocio: alta capacidad requiere más ejes")
    void testValidate_HighCapacityRequiresMoreAxes() {
        // Arrange
        Camion camion = new Camion(1, "Volvo", "FH16", 2020, 150000.0, "Blanco", 
                                  25.0, 2); // Alta capacidad pero pocos ejes
        
        // Act
        List<String> errores = validator.validate(camion);
        
        // Assert
        assertFalse(errores.isEmpty());
        assertTrue(errores.stream().anyMatch(e -> e.contains("ejes") || 
                                             e.contains("toneladas")));
    }
}

