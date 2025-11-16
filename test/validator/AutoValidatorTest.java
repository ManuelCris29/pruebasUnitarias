package validator;

import model.Auto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para AutoValidator
 * 
 * Verifica todas las reglas de validación específicas para autos
 */
@DisplayName("Pruebas de AutoValidator")
class AutoValidatorTest {

    private AutoValidator validator;
    
    @BeforeEach
    void setUp() {
        validator = new AutoValidator();
    }
    
    @Test
    @DisplayName("Validar Auto con datos válidos - Sin errores")
    void testValidate_ValidAuto_NoErrors() {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            4, "Automática", true);
        
        // Act
        List<String> errores = validator.validate(auto);
        
        // Assert
        assertTrue(errores.isEmpty(), "No debería haber errores de validación");
    }
    
    @Test
    @DisplayName("Validar Auto - Error: número de puertas inválido (menor a 2)")
    void testValidate_InvalidDoors_LessThan2() {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            1, "Automática", true);
        
        // Act
        List<String> errores = validator.validate(auto);
        
        // Assert
        assertFalse(errores.isEmpty());
        assertTrue(errores.stream().anyMatch(e -> e.contains("número de puertas")));
    }
    
    @Test
    @DisplayName("Validar Auto - Error: número de puertas inválido (mayor a 5)")
    void testValidate_InvalidDoors_GreaterThan5() {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            6, "Automática", true);
        
        // Act
        List<String> errores = validator.validate(auto);
        
        // Assert
        assertFalse(errores.isEmpty());
        assertTrue(errores.stream().anyMatch(e -> e.contains("número de puertas")));
    }
    
    @Test
    @DisplayName("Validar Auto - Error: tipo de transmisión vacío")
    void testValidate_EmptyTransmission() {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            4, "", true);
        
        // Act
        List<String> errores = validator.validate(auto);
        
        // Assert
        assertFalse(errores.isEmpty());
        assertTrue(errores.stream().anyMatch(e -> e.contains("transmisión")));
    }
    
    @Test
    @DisplayName("Validar Auto - Error: tipo de transmisión inválido")
    void testValidate_InvalidTransmission() {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            4, "Turbo", true);
        
        // Act
        List<String> errores = validator.validate(auto);
        
        // Assert
        assertFalse(errores.isEmpty());
        assertTrue(errores.stream().anyMatch(e -> e.contains("transmisión")));
    }
    
    @Test
    @DisplayName("Validar Auto - Advertencia: 2 puertas y es sedán")
    void testValidate_TwoDoorsAndSedan_Warning() {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            2, "Automática", true);
        
        // Act
        List<String> errores = validator.validate(auto);
        
        // Assert
        assertFalse(errores.isEmpty());
        assertTrue(errores.stream().anyMatch(e -> e.contains("Advertencia") || 
                                             e.contains("2 puertas")));
    }
    
    @Test
    @DisplayName("Validar Auto - Acepta transmisión 'Manual'")
    void testValidate_ManualTransmission_Valid() {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            4, "Manual", true);
        
        // Act
        List<String> errores = validator.validate(auto);
        
        // Assert
        assertTrue(errores.isEmpty() || 
                  !errores.stream().anyMatch(e -> e.contains("transmisión")));
    }
    
    @Test
    @DisplayName("Validar Auto - Acepta transmisión 'Automática'")
    void testValidate_AutomaticTransmission_Valid() {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            4, "Automática", true);
        
        // Act
        List<String> errores = validator.validate(auto);
        
        // Assert
        assertTrue(errores.isEmpty() || 
                  !errores.stream().anyMatch(e -> e.contains("transmisión")));
    }
    
    @Test
    @DisplayName("Validar Auto - Múltiples errores de validación")
    void testValidate_MultipleErrors() {
        // Arrange - Auto con múltiples errores
        Auto auto = new Auto(-1, "", "Corolla", 1800, -1000.0, "Rojo123", 
                            10, "Turbo", true);
        
        // Act
        List<String> errores = validator.validate(auto);
        
        // Assert
        assertFalse(errores.isEmpty());
        assertTrue(errores.size() > 1, "Debería haber múltiples errores");
    }
    
    @Test
    @DisplayName("Validar Auto - isValid retorna true para datos válidos")
    void testIsValid_ValidAuto_ReturnsTrue() {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            4, "Automática", true);
        
        // Act
        boolean isValid = validator.isValid(auto);
        
        // Assert
        assertTrue(isValid);
    }
    
    @Test
    @DisplayName("Validar Auto - isValid retorna false para datos inválidos")
    void testIsValid_InvalidAuto_ReturnsFalse() {
        // Arrange
        Auto auto = new Auto(1, "", "Corolla", 1800, -1000.0, "Rojo", 
                            10, "Turbo", true);
        
        // Act
        boolean isValid = validator.isValid(auto);
        
        // Assert
        assertFalse(isValid);
    }
}

