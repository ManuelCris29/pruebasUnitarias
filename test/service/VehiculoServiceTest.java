package service;

import model.*;
import repository.*;
import validator.*;
import exception.ValidationException;
import exception.VehiculoException;
import util.FileManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para VehiculoService
 * 
 * Verifica la lógica de negocio y coordinación entre validadores y repository
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VehiculoServiceTest {

    private static IVehiculoService service;
    private static IVehiculoRepository repository;
    private static FileManager fileManager;
    
    @BeforeAll
    static void setUp() {
        fileManager = new FileManager();
        repository = new VehiculoFileRepository(fileManager);
        
        IValidator<Auto> autoValidator = new AutoValidator();
        IValidator<Moto> motoValidator = new MotoValidator();
        IValidator<Camion> camionValidator = new CamionValidator();
        
        service = new VehiculoService(repository, autoValidator, motoValidator, camionValidator);
    }
    
    @AfterEach
    void tearDown() {
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
    @DisplayName("Test CREAR - Crear vehículo con validación exitosa")
    void testCrear_WithValidData_Success() throws ValidationException, VehiculoException {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            4, "Automática", true);
        
        // Act
        service.crear(auto);
        
        // Assert
        Vehiculo encontrado = service.obtener(1, "auto");
        assertNotNull(encontrado);
        assertEquals("Toyota", encontrado.getMarca());
    }
    
    @Test
    @Order(2)
    @DisplayName("Test CREAR - Error de validación con datos inválidos")
    void testCrear_WithInvalidData_ThrowsValidationException() {
        // Arrange - Auto con datos inválidos
        Auto auto = new Auto(1, "", "Corolla", 1800, -1000.0, "Rojo123", 
                            10, "Turbo", true);
        
        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            service.crear(auto);
        });
        
        assertTrue(exception.tieneErrores());
        assertTrue(exception.getCantidadErrores() > 0);
    }
    
    @Test
    @Order(3)
    @DisplayName("Test CREAR - Error al crear con ID duplicado")
    void testCrear_DuplicateId_ThrowsVehiculoException() throws ValidationException, VehiculoException {
        // Arrange
        Auto auto1 = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                             4, "Automática", true);
        Auto auto2 = new Auto(1, "Honda", "Civic", 2021, 28000.0, "Azul", 
                             4, "Manual", true);
        
        service.crear(auto1);
        
        // Act & Assert
        VehiculoException exception = assertThrows(VehiculoException.class, () -> {
            service.crear(auto2);
        });
        
        assertTrue(exception.getMessage().contains("Ya existe un vehículo con ID: 1"));
    }
    
    @Test
    @Order(4)
    @DisplayName("Test OBTENER - Obtener vehículo existente")
    void testObtener_Success() throws ValidationException, VehiculoException {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            4, "Automática", true);
        service.crear(auto);
        
        // Act
        Vehiculo encontrado = service.obtener(1, "auto");
        
        // Assert
        assertNotNull(encontrado);
        assertEquals(1, encontrado.getId());
        assertEquals("Toyota", encontrado.getMarca());
    }
    
    @Test
    @Order(5)
    @DisplayName("Test OBTENER - Error con ID inválido")
    void testObtener_InvalidId_ThrowsException() {
        // Act & Assert
        VehiculoException exception = assertThrows(VehiculoException.class, () -> {
            service.obtener(-1, "auto");
        });
        
        assertTrue(exception.getMessage().contains("El ID debe ser mayor a 0"));
    }
    
    @Test
    @Order(6)
    @DisplayName("Test ACTUALIZAR - Actualizar vehículo con validación")
    void testActualizar_WithValidData_Success() throws ValidationException, VehiculoException {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            4, "Automática", true);
        service.crear(auto);
        
        auto.setPrecio(30000.0);
        auto.setColor("Azul");
        
        // Act
        service.actualizar(auto);
        
        // Assert
        Vehiculo actualizado = service.obtener(1, "auto");
        assertEquals(30000.0, actualizado.getPrecio(), 0.01);
        assertEquals("Azul", actualizado.getColor());
    }
    
    @Test
    @Order(7)
    @DisplayName("Test ACTUALIZAR - Error de validación al actualizar")
    void testActualizar_WithInvalidData_ThrowsValidationException() throws ValidationException, VehiculoException {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            4, "Automática", true);
        service.crear(auto);
        
        auto.setPrecio(-1000.0); // Precio inválido
        auto.setMarca(""); // Marca vacía
        
        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            service.actualizar(auto);
        });
        
        assertTrue(exception.tieneErrores());
    }
    
    @Test
    @Order(8)
    @DisplayName("Test ELIMINAR - Eliminar vehículo existente")
    void testEliminar_Success() throws ValidationException, VehiculoException {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            4, "Automática", true);
        service.crear(auto);
        
        // Act
        service.eliminar(1, "auto");
        
        // Assert
        VehiculoException exception = assertThrows(VehiculoException.class, () -> {
            service.obtener(1, "auto");
        });
        assertTrue(exception.getMessage().contains("No se encontró vehículo con ID: 1"));
    }
    
    @Test
    @Order(9)
    @DisplayName("Test LISTAR TODOS - Listar todos los vehículos")
    void testListarTodos_Success() throws ValidationException, VehiculoException {
        // Arrange
        Auto auto1 = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                             4, "Automática", true);
        Auto auto2 = new Auto(2, "Honda", "Civic", 2021, 28000.0, "Azul", 
                             4, "Manual", true);
        service.crear(auto1);
        service.crear(auto2);
        
        // Act
        List<Vehiculo> vehiculos = service.listarTodos("auto");
        
        // Assert
        assertNotNull(vehiculos);
        assertEquals(2, vehiculos.size());
    }
    
    @Test
    @Order(10)
    @DisplayName("Test BUSCAR POR MARCA - Buscar vehículos por marca")
    void testBuscarPorMarca_Success() throws ValidationException, VehiculoException {
        // Arrange
        Auto auto1 = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                             4, "Automática", true);
        Auto auto2 = new Auto(2, "Toyota", "Camry", 2021, 35000.0, "Negro", 
                             4, "Automática", true);
        Auto auto3 = new Auto(3, "Honda", "Civic", 2021, 28000.0, "Azul", 
                             4, "Manual", true);
        service.crear(auto1);
        service.crear(auto2);
        service.crear(auto3);
        
        // Act
        List<Vehiculo> toyotas = service.buscarPorMarca("Toyota", "auto");
        
        // Assert
        assertNotNull(toyotas);
        assertEquals(2, toyotas.size());
        assertTrue(toyotas.stream().allMatch(v -> v.getMarca().equalsIgnoreCase("Toyota")));
    }
    
    @Test
    @Order(11)
    @DisplayName("Test BUSCAR POR MARCA - Búsqueda case-insensitive")
    void testBuscarPorMarca_CaseInsensitive() throws ValidationException, VehiculoException {
        // Arrange
        Auto auto = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                            4, "Automática", true);
        service.crear(auto);
        
        // Act
        List<Vehiculo> resultados1 = service.buscarPorMarca("toyota", "auto");
        List<Vehiculo> resultados2 = service.buscarPorMarca("TOYOTA", "auto");
        List<Vehiculo> resultados3 = service.buscarPorMarca("Toy", "auto");
        
        // Assert
        assertEquals(1, resultados1.size());
        assertEquals(1, resultados2.size());
        assertEquals(1, resultados3.size()); // Búsqueda parcial
    }
    
    @Test
    @Order(12)
    @DisplayName("Test OBTENER PROXIMO ID - Obtener próximo ID disponible")
    void testObtenerProximoId() throws ValidationException, VehiculoException {
        // Arrange
        Auto auto1 = new Auto(1, "Toyota", "Corolla", 2020, 25000.0, "Rojo", 
                             4, "Automática", true);
        Auto auto2 = new Auto(5, "Honda", "Civic", 2021, 28000.0, "Azul", 
                             4, "Manual", true);
        service.crear(auto1);
        service.crear(auto2);
        
        // Act
        int nextId = service.obtenerProximoId("auto");
        
        // Assert
        assertEquals(6, nextId);
    }
}

