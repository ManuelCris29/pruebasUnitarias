package validator;

import model.Moto;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para MotoValidator
 */
@DisplayName("Pruebas de MotoValidator")
class MotoValidatorTest {

    private MotoValidator validator;
    
    @BeforeEach
    void setUp() {
        validator = new MotoValidator();
    }
    
    @Test
    @DisplayName("Validar Moto con datos válidos - Sin errores")
    void testValidate_ValidMoto_NoErrors() {
        // Arrange
        Moto moto = new Moto(1, "Yamaha", "R1", 2021, 15000.0, "Azul", 
                           1000, true);
        
        // Act
        List<String> errores = validator.validate(moto);
        
        // Assert
        assertTrue(errores.isEmpty());
    }
    
    @Test
    @DisplayName("Validar Moto - Error: cilindrada menor a 50cc")
    void testValidate_CilindradaLessThan50() {
        // Arrange
        Moto moto = new Moto(1, "Yamaha", "R1", 2021, 15000.0, "Azul", 
                           30, true);
        
        // Act
        List<String> errores = validator.validate(moto);
        
        // Assert
        assertFalse(errores.isEmpty());
        assertTrue(errores.stream().anyMatch(e -> e.contains("cilindrada")));
    }
    
    @Test
    @DisplayName("Validar Moto - Error: cilindrada mayor a 2000cc")
    void testValidate_CilindradaGreaterThan2000() {
        // Arrange
        Moto moto = new Moto(1, "Yamaha", "R1", 2021, 15000.0, "Azul", 
                           2500, true);
        
        // Act
        List<String> errores = validator.validate(moto);
        
        // Assert
        assertFalse(errores.isEmpty());
        assertTrue(errores.stream().anyMatch(e -> e.contains("cilindrada")));
    }
    
    @Test
    @DisplayName("Validar Moto - Advertencia: alta cilindrada sin carenado")
    void testValidate_HighCilindradaWithoutCarenado() {
        // Arrange
        Moto moto = new Moto(1, "Yamaha", "R1", 2021, 15000.0, "Azul", 
                           1000, false);
        
        // Act
        List<String> errores = validator.validate(moto);
        
        // Assert
        assertFalse(errores.isEmpty());
        assertTrue(errores.stream().anyMatch(e -> e.contains("Advertencia") || 
                                             e.contains("carenado")));
    }
}

