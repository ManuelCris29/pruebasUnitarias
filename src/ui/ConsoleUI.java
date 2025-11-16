package ui;

import model.*;
import service.IVehiculoService;
import exception.ValidationException;
import exception.VehiculoException;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    
    private IVehiculoService service;
    private Scanner scanner;
    
    /**
     * Constructor con inyección de dependencias
     */
    public ConsoleUI(IVehiculoService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Inicia la aplicación
     */
    public void iniciar() {
        boolean continuar = true;
        
        mostrarBienvenida();
        
        while (continuar) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    menuAutos();
                    break;
                case 2:
                    menuMotos();
                    break;
                case 3:
                    menuCamiones();
                    break;
                case 0:
                    continuar = false;
                    mostrarDespedida();
                    break;
                default:
                    System.out.println("❌ Opción inválida. Intente nuevamente.");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Muestra el mensaje de bienvenida
     */
    private void mostrarBienvenida() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GESTIÓN DE VEHÍCULOS      ║");
        System.out.println("║        CRUD - Principios SOLID         ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
    }
    
    /**
     * Muestra el menú principal
     */
    private void mostrarMenuPrincipal() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("         MENÚ PRINCIPAL");
        System.out.println("═══════════════════════════════════════");
        System.out.println("1. Gestionar Autos");
        System.out.println("2. Gestionar Motos");
        System.out.println("3. Gestionar Camiones");
        System.out.println("0. Salir");
        System.out.println("═══════════════════════════════════════");
        System.out.print("Seleccione una opción: ");
    }
    
    /**
     * Lee una opción numérica del usuario
     */
    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Lee un texto del usuario
     */
    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }
    
    /**
     * Lee un número entero del usuario
     */
    private int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Debe ingresar un número entero.");
            }
        }
    }
    
    /**
     * Lee un número decimal del usuario
     */
    private double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Debe ingresar un número válido.");
            }
        }
    }
    
    /**
     * Lee un valor booleano del usuario
     */
    private boolean leerBoolean(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (S/N): ");
            String respuesta = scanner.nextLine().trim().toUpperCase();
            if (respuesta.equals("S") || respuesta.equals("SI") || respuesta.equals("SÍ")) {
                return true;
            } else if (respuesta.equals("N") || respuesta.equals("NO")) {
                return false;
            } else {
                System.out.println("❌ Error: Responda S (Sí) o N (No).");
            }
        }
    }
    
    /**
     * Pausa para que el usuario vea el mensaje
     */
    private void pausar() {
        System.out.println("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Menú de gestión de Autos
     */
    private void menuAutos() {
        boolean volver = false;
        
        while (!volver) {
            System.out.println("\n═══════════════════════════════════════");
            System.out.println("         GESTIÓN DE AUTOS");
            System.out.println("═══════════════════════════════════════");
            System.out.println("1. Crear Auto");
            System.out.println("2. Ver Auto");
            System.out.println("3. Actualizar Auto");
            System.out.println("4. Eliminar Auto");
            System.out.println("5. Listar todos los Autos");
            System.out.println("6. Buscar por marca");
            System.out.println("0. Volver al menú principal");
            System.out.println("═══════════════════════════════════════");
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    crearAuto();
                    break;
                case 2:
                    verAuto();
                    break;
                case 3:
                    actualizarAuto();
                    break;
                case 4:
                    eliminarAuto();
                    break;
                case 5:
                    listarAutos();
                    break;
                case 6:
                    buscarAutosPorMarca();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("❌ Opción inválida.");
            }
        }
    }
    
    /**
     * Crea un nuevo Auto
     */
    private void crearAuto() {
        System.out.println("\n--- CREAR NUEVO AUTO ---");
        
        try {
            // Obtener próximo ID
            int id = service.obtenerProximoId("auto");
            System.out.println("ID asignado automáticamente: " + id);
            
            // Capturar datos
            String marca = leerTexto("Marca: ");
            String modelo = leerTexto("Modelo: ");
            int anio = leerEntero("Año: ");
            double precio = leerDouble("Precio: $");
            String color = leerTexto("Color: ");
            int numeroPuertas = leerEntero("Número de puertas (2-5): ");
            String tipoTransmision = leerTexto("Tipo de transmisión (Manual/Automática): ");
            boolean esSedan = leerBoolean("¿Es sedán?");
            
            // Crear objeto
            Auto auto = new Auto(id, marca, modelo, anio, precio, color,
                                numeroPuertas, tipoTransmision, esSedan);
            
            // Guardar
            service.crear(auto);
            
            System.out.println("\n✅ Auto creado exitosamente!");
            System.out.println(auto);
            
        } catch (ValidationException e) {
            System.out.println("\n❌ ERRORES DE VALIDACIÓN:");
            for (String error : e.getErrores()) {
                System.out.println("  • " + error);
            }
        } catch (VehiculoException e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        }
        
        pausar();
    }
    
    /**
     * Ve un Auto por ID
     */
    private void verAuto() {
        System.out.println("\n--- VER AUTO ---");
        
        try {
            int id = leerEntero("Ingrese el ID del auto: ");
            Vehiculo vehiculo = service.obtener(id, "auto");
            
            System.out.println("\n✅ Auto encontrado:");
            System.out.println(vehiculo);
            
        } catch (VehiculoException e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        }
        
        pausar();
    }
    
    /**
     * Actualiza un Auto existente
     */
    private void actualizarAuto() {
        System.out.println("\n--- ACTUALIZAR AUTO ---");
        
        try {
            int id = leerEntero("Ingrese el ID del auto a actualizar: ");
            Vehiculo vehiculo = service.obtener(id, "auto");
            Auto auto = (Auto) vehiculo;
            
            System.out.println("\nAuto actual:");
            System.out.println(auto);
            System.out.println("\nIngrese los nuevos datos (presione ENTER para mantener el valor actual):");
            
            // Actualizar campos
            String marca = leerTexto("Marca [" + auto.getMarca() + "]: ");
            if (!marca.trim().isEmpty()) auto.setMarca(marca);
            
            String modelo = leerTexto("Modelo [" + auto.getModelo() + "]: ");
            if (!modelo.trim().isEmpty()) auto.setModelo(modelo);
            
            String anioStr = leerTexto("Año [" + auto.getAnio() + "]: ");
            if (!anioStr.trim().isEmpty()) auto.setAnio(Integer.parseInt(anioStr));
            
            String precioStr = leerTexto("Precio [" + auto.getPrecio() + "]: ");
            if (!precioStr.trim().isEmpty()) auto.setPrecio(Double.parseDouble(precioStr));
            
            String color = leerTexto("Color [" + auto.getColor() + "]: ");
            if (!color.trim().isEmpty()) auto.setColor(color);
            
            String puertasStr = leerTexto("Número de puertas [" + auto.getNumeroPuertas() + "]: ");
            if (!puertasStr.trim().isEmpty()) auto.setNumeroPuertas(Integer.parseInt(puertasStr));
            
            String transmision = leerTexto("Transmisión [" + auto.getTipoTransmision() + "]: ");
            if (!transmision.trim().isEmpty()) auto.setTipoTransmision(transmision);
            
            // Actualizar
            service.actualizar(auto);
            
            System.out.println("\n✅ Auto actualizado exitosamente!");
            System.out.println(auto);
            
        } catch (ValidationException e) {
            System.out.println("\n❌ ERRORES DE VALIDACIÓN:");
            for (String error : e.getErrores()) {
                System.out.println("  • " + error);
            }
        } catch (VehiculoException e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("\n❌ Error: Formato de número inválido.");
        }
        
        pausar();
    }
    
    /**
     * Elimina un Auto
     */
    private void eliminarAuto() {
        System.out.println("\n--- ELIMINAR AUTO ---");
        
        try {
            int id = leerEntero("Ingrese el ID del auto a eliminar: ");
            Vehiculo vehiculo = service.obtener(id, "auto");
            
            System.out.println("\nAuto a eliminar:");
            System.out.println(vehiculo);
            
            boolean confirmar = leerBoolean("\n¿Está seguro de eliminar este auto?");
            
            if (confirmar) {
                service.eliminar(id, "auto");
                System.out.println("\n✅ Auto eliminado exitosamente!");
            } else {
                System.out.println("\n⚠️ Operación cancelada.");
            }
            
        } catch (VehiculoException e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        }
        
        pausar();
    }
    
    /**
     * Lista todos los Autos
     */
    private void listarAutos() {
        System.out.println("\n--- LISTA DE AUTOS ---");
        
        try {
            List<Vehiculo> autos = service.listarTodos("auto");
            
            if (autos.isEmpty()) {
                System.out.println("\n⚠️ No hay autos registrados.");
            } else {
                System.out.println("\nTotal de autos: " + autos.size());
                System.out.println("═══════════════════════════════════════════════════════════════");
                for (Vehiculo v : autos) {
                    System.out.println(v);
                    System.out.println("───────────────────────────────────────────────────────────────");
                }
            }
            
        } catch (VehiculoException e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        }
        
        pausar();
    }
    
    /**
     * Busca Autos por marca
     */
    private void buscarAutosPorMarca() {
        System.out.println("\n--- BUSCAR AUTOS POR MARCA ---");
        
        try {
            String marca = leerTexto("Ingrese la marca a buscar: ");
            List<Vehiculo> autos = service.buscarPorMarca(marca, "auto");
            
            if (autos.isEmpty()) {
                System.out.println("\n⚠️ No se encontraron autos de la marca: " + marca);
            } else {
                System.out.println("\nAutos encontrados: " + autos.size());
                System.out.println("═══════════════════════════════════════════════════════════════");
                for (Vehiculo v : autos) {
                    System.out.println(v);
                    System.out.println("───────────────────────────────────────────────────────────────");
                }
            }
            
        } catch (VehiculoException e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        }
        
        pausar();
    }
    
    /**
     * Menú de gestión de Motos
     */
    private void menuMotos() {
        System.out.println("\n⚠️ Menú de Motos - Implementación similar a Autos");
        System.out.println("📝 Ejercicio: Implementa los métodos para Motos siguiendo el patrón de Autos");
        pausar();
    }
    
    /**
     * Menú de gestión de Camiones
     */
    private void menuCamiones() {
        System.out.println("\n⚠️ Menú de Camiones - Implementación similar a Autos");
        System.out.println("📝 Ejercicio: Implementa los métodos para Camiones siguiendo el patrón de Autos");
        pausar();
    }
    
    /**
     * Muestra mensaje de despedida
     */
    private void mostrarDespedida() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   ¡Gracias por usar el sistema!       ║");
        System.out.println("║   Hasta pronto                         ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
}