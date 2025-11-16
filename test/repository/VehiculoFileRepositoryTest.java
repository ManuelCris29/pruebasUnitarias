package repository;

import model.*;
import exception.VehiculoException;
import util.FileManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para VehiculoFileRepository
 * 
 * Sigue principios SOLID:
 * - SRP: Cada método de prueba verifica una única responsabilidad
 * - DIP: Usa abstracciones (IVehiculoRepository) cuando es posible
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VehiculoFileRepositoryTest {

    private static IVehiculoRepository repository;
    private static FileManager fileManager;
    
    @TempDir
    static Path tempDir;
    
    @BeforeAll
    static void setUp() {
        // Configurar FileManager con directorio temporal para tests
        fileManager = new FileManager();
        repository = new VehiculoFileRepository(fileManager);
    }
    
    @AfterEach
    void tearDown() {
        // Limpiar archivos de prueba después de cada test
        try {
            fileManager.eliminarArchivo("autos");
            fileManager.eliminarArchivo("motos");
            fileManager.eliminarArchivo("camiones");
        } catch (Exception e) {
            // Ignorar errores de limpieza
        }
    }
    
    @Test
    @Order(1)
    @DisplayName("Test CREATE - Crear vehículo exitosamente")
    void testCreate_Success() throws VehiculoException {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            4, "Automática", true);
        
        // Act
        repository.create(auto);
        
        // Assert
        assertTrue(repository.existsById(1, "auto"));
        Vehiculo encontrado = repository.read(1, "auto");
        assertNotNull(encontrado);
        assertEquals("Toyota", encontrado.getMarca());
        assertEquals("Corolla", encontrado.getModelo());
    }
    
    @Test
    @Order(2)
    @DisplayName("Test CREATE - Error al crear con ID duplicado")
    void testCreate_DuplicateId_ThrowsException() throws VehiculoException {
        // Arrange
        Auto auto1 = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                             4, "Automática", true);
        Auto auto2 = new Auto(1, "Honda", "Civic", 2021, 28000.0, "Azul", 
                             4, "Manual", true);
        
        // Act & Assert
        repository.create(auto1);
        
        VehiculoException exception = assertThrows(VehiculoException.class, () -> {
            repository.create(auto2);
        });
        
        assertTrue(exception.getMessage().contains("Ya existe un vehículo con ID: 1"));
    }
    
    @Test
    @Order(3)
    @DisplayName("Test READ - Leer vehículo existente")
    void testRead_Success() throws VehiculoException {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            4, "Automática", true);
        repository.create(auto);
        
        // Act
        Vehiculo encontrado = repository.read(1, "auto");
        
        // Assert
        assertNotNull(encontrado);
        assertEquals(1, encontrado.getId());
        assertEquals("Toyota", encontrado.getMarca());
        assertEquals("Corolla", encontrado.getModelo());
        assertEquals(2020, encontrado.getAnio());
        assertEquals(25000.0, encontrado.getPrecio(), 0.01);
        assertEquals("Rojo", encontrado.getColor());
        
        // Verificar que es un Auto
        assertTrue(encontrado instanceof Auto);
        Auto autoEncontrado = (Auto) encontrado;
        assertEquals(4, autoEncontrado.getNumeroPuertas());
        assertEquals("Automática", autoEncontrado.getTipoTransmision());
        assertTrue(autoEncontrado.isEsSedan());
    }
    
    @Test
    @Order(4)
    @DisplayName("Test READ - Error al leer vehículo inexistente")
    void testRead_NotFound_ThrowsException() {
        // Act & Assert
        VehiculoException exception = assertThrows(VehiculoException.class, () -> {
            repository.read(999, "auto");
        });
        
        assertTrue(exception.getMessage().contains("No se encontró vehículo con ID: 999"));
    }
    
    @Test
    @Order(5)
    @DisplayName("Test UPDATE - Actualizar vehículo existente")
    void testUpdate_Success() throws VehiculoException {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            4, "Automática", true);
        repository.create(auto);
        
        // Modificar el auto
        auto.setPrecio(30000.0);
        auto.setColor("Azul");
        
        // Act
        repository.update(auto);
        
        // Assert
        Vehiculo actualizado = repository.read(1, "auto");
        assertEquals(30000.0, actualizado.getPrecio(), 0.01);
        assertEquals("Azul", actualizado.getColor());
        assertEquals("Toyota", actualizado.getMarca()); // No cambió
    }
    
    @Test
    @Order(6)
    @DisplayName("Test UPDATE - Error al actualizar vehículo inexistente")
    void testUpdate_NotFound_ThrowsException() {
        // Arrange
        Auto auto = new Auto(999, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            4, "Automática", true);
        
        // Act & Assert
        VehiculoException exception = assertThrows(VehiculoException.class, () -> {
            repository.update(auto);
        });
        
        assertTrue(exception.getMessage().contains("No se encontró vehículo con ID: 999"));
    }
    
    @Test
    @Order(7)
    @DisplayName("Test DELETE - Eliminar vehículo existente")
    void testDelete_Success() throws VehiculoException {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            4, "Automática", true);
        repository.create(auto);
        assertTrue(repository.existsById(1, "auto"));
        
        // Act
        repository.delete(1, "auto");
        
        // Assert
        assertFalse(repository.existsById(1, "auto"));
        
        VehiculoException exception = assertThrows(VehiculoException.class, () -> {
            repository.read(1, "auto");
        });
        assertTrue(exception.getMessage().contains("No se encontró vehículo con ID: 1"));
    }
    
    @Test
    @Order(8)
    @DisplayName("Test DELETE - Error al eliminar vehículo inexistente")
    void testDelete_NotFound_ThrowsException() {
        // Act & Assert
        VehiculoException exception = assertThrows(VehiculoException.class, () -> {
            repository.delete(999, "auto");
        });
        
        assertTrue(exception.getMessage().contains("No se encontró vehículo con ID: 999"));
    }
    
    @Test
    @Order(9)
    @DisplayName("Test FINDALL - Listar todos los vehículos")
    void testFindAll_Success() throws VehiculoException {
        // Arrange
        Auto auto1 = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                             4, "Automática", true);
        Auto auto2 = new Auto(2, "Honda", "Civic", 2021, 28000.0, "Azul", 
                             4, "Manual", true);
        repository.create(auto1);
        repository.create(auto2);
        
        // Act
        List<Vehiculo> vehiculos = repository.findAll("auto");
        
        // Assert
        assertNotNull(vehiculos);
        assertEquals(2, vehiculos.size());
        assertEquals(1, vehiculos.get(0).getId());
        assertEquals(2, vehiculos.get(1).getId());
    }
    
    @Test
    @Order(10)
    @DisplayName("Test FINDALL - Lista vacía cuando no hay vehículos")
    void testFindAll_EmptyList() throws VehiculoException {
        // Act
        List<Vehiculo> vehiculos = repository.findAll("auto");
        
        // Assert
        assertNotNull(vehiculos);
        assertTrue(vehiculos.isEmpty());
    }
    
    @Test
    @Order(11)
    @DisplayName("Test EXISTSBYID - Verificar existencia de vehículo")
    void testExistsById() throws VehiculoException {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            4, "Automática", true);
        
        // Act & Assert
        assertFalse(repository.existsById(1, "auto"));
        repository.create(auto);
        assertTrue(repository.existsById(1, "auto"));
    }
    
    @Test
    @Order(12)
    @DisplayName("Test GETNEXTID - Obtener próximo ID disponible")
    void testGetNextId() throws VehiculoException {
        // Arrange
        Auto auto1 = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                             4, "Automática", true);
        Auto auto2 = new Auto(5, "Honda", "Civic", 2021, 28000.0, "Azul", 
                             4, "Manual", true);
        repository.create(auto1);
        repository.create(auto2);
        
        // Act
        int nextId = repository.getNextId("auto");
        
        // Assert
        assertEquals(6, nextId); // Debe ser el máximo (5) + 1
    }
    
    @Test
    @Order(13)
    @DisplayName("Test GETNEXTID - Retornar 1 cuando no hay vehículos")
    void testGetNextId_EmptyRepository() {
        // Act
        int nextId = repository.getNextId("auto");
        
        // Assert
        assertEquals(1, nextId);
    }
    
    @Test
    @Order(14)
    @DisplayName("Test CREATE - Crear diferentes tipos de vehículos")
    void testCreate_DifferentVehicleTypes() throws VehiculoException {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            4, "Automática", true);
        Moto moto = new Moto(1, "Yamaha", "R1", 2021, 15000.0, "Azul", 
                           1000, true);
        Camion camion = new Camion(1, "Volvo", "FH16", 2020, 150000.0, "Blanco", 
                                  25.0, 4);
        
        // Act
        repository.create(auto);
        repository.create(moto);
        repository.create(camion);
        
        // Assert
        assertTrue(repository.existsById(1, "auto"));
        assertTrue(repository.existsById(1, "moto"));
        assertTrue(repository.existsById(1, "camion"));
        
        assertEquals(1, repository.findAll("auto").size());
        assertEquals(1, repository.findAll("moto").size());
        assertEquals(1, repository.findAll("camion").size());
    }
}

