import model.*;
import repository.*;
import service.*;
import ui.ConsoleUI;
import util.*;
import validator.*;

public class Main {
    
    public static void main(String[] args) {
        try {
            // ═══════════════════════════════════════════════════════
            // COMPOSICIÓN DE OBJETOS (Dependency Injection Manual)
            // ═══════════════════════════════════════════════════════
            
            System.out.println("Inicializando sistema...");
            
            // 1. CAPA UTIL - Crear utilidades
            System.out.println("✓ Creando FileManager...");
            FileManager fileManager = new FileManager();
            
            // 2. CAPA REPOSITORY - Crear repositorio con inyección de dependencias
            System.out.println("✓ Creando Repository...");
            IVehiculoRepository repository = new VehiculoFileRepository(fileManager);
            
            // 3. CAPA VALIDATOR - Crear validadores
            System.out.println("✓ Creando Validadores...");
            IValidator<Auto> autoValidator = new AutoValidator();
            IValidator<Moto> motoValidator = new MotoValidator();
            IValidator<Camion> camionValidator = new CamionValidator();
            
            // 4. CAPA SERVICE - Crear servicio con inyección de dependencias
            System.out.println("✓ Creando Service...");
            IVehiculoService service = new VehiculoService(
                repository,
                autoValidator,
                motoValidator,
                camionValidator
            );
            
            // 5. CAPA UI - Crear interfaz de usuario con inyección de dependencias
            System.out.println("✓ Creando Interfaz de Usuario...");
            ConsoleUI ui = new ConsoleUI(service);
            
            System.out.println("✓ Sistema inicializado correctamente\n");
            
            // ═══════════════════════════════════════════════════════
            // EJECUTAR APLICACIÓN
            // ═══════════════════════════════════════════════════════
            
            ui.iniciar();
            
        } catch (Exception e) {
            System.err.println("╔════════════════════════════════════════╗");
            System.err.println("║   ERROR CRÍTICO AL INICIAR SISTEMA     ║");
            System.err.println("╚════════════════════════════════════════╝");
            System.err.println("Detalles: " + e.getMessage());
            e.printStackTrace();
        }
    }
}