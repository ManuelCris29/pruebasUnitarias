package repository;

import model.*;
import exception.VehiculoException;
import util.FileManager;
import util.VehiculoFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VehiculoFileRepository implements IVehiculoRepository {
    
    private FileManager fileManager;
    
    // Constructor con inyección de dependencias
    public VehiculoFileRepository(FileManager fileManager) {
        this.fileManager = fileManager;
    }
    
    @Override
    public void create(Vehiculo vehiculo) throws VehiculoException {
        try {
            String tipo = vehiculo.getTipo().toLowerCase();
            String nombreArchivo = VehiculoFactory.obtenerNombreArchivo(tipo);
            
            // Verificar si el ID ya existe (incluyendo líneas mal formateadas)
            List<String> lineasExistentes = fileManager.leerArchivo(nombreArchivo);
            for (String linea : lineasExistentes) {
                if (linea != null && !linea.trim().isEmpty()) {
                    String[] partes = linea.split(",", 2);
                    if (partes.length > 0) {
                        try {
                            int idExistente = Integer.parseInt(partes[0].trim());
                            if (idExistente == vehiculo.getId()) {
                                throw new VehiculoException("Ya existe un vehículo con ID: " + vehiculo.getId());
                            }
                        } catch (NumberFormatException e) {
                            // Ignorar líneas que no empiezan con número
                            continue;
                        }
                    }
                }
            }
            
            // Agregar al archivo
            fileManager.agregarLinea(nombreArchivo, vehiculo.toCSV());
            
        } catch (IOException e) {
            throw new VehiculoException("Error al guardar vehículo", e);
        }
    }
    
    @Override
    public Vehiculo read(int id, String tipo) throws VehiculoException {
        try {
            String nombreArchivo = VehiculoFactory.obtenerNombreArchivo(tipo);
            List<String> lineas = fileManager.leerArchivo(nombreArchivo);
            
            for (String linea : lineas) {
                try {
                    Vehiculo v = VehiculoFactory.crearDesdeCSV(tipo, linea);
                    if (v.getId() == id) {
                        return v;
                    }
                } catch (IllegalArgumentException e) {
                    // Ignorar líneas mal formateadas y continuar
                    continue;
                }
            }
            
            throw new VehiculoException("No se encontró vehículo con ID: " + id);
            
        } catch (IOException e) {
            throw new VehiculoException("Error al leer vehículo", e);
        }
    }
    
    @Override
    public void update(Vehiculo vehiculo) throws VehiculoException {
        try {
            String tipo = vehiculo.getTipo().toLowerCase();
            String nombreArchivo = VehiculoFactory.obtenerNombreArchivo(tipo);
            
            List<String> lineas = fileManager.leerArchivo(nombreArchivo);
            List<String> lineasActualizadas = new ArrayList<>();
            boolean encontrado = false;
            
            for (String linea : lineas) {
                try {
                    Vehiculo v = VehiculoFactory.crearDesdeCSV(tipo, linea);
                    
                    if (v.getId() == vehiculo.getId()) {
                        // Reemplazar con la versión actualizada
                        lineasActualizadas.add(vehiculo.toCSV());
                        encontrado = true;
                    } else {
                        // Mantener la línea original
                        lineasActualizadas.add(linea);
                    }
                } catch (IllegalArgumentException e) {
                    // Mantener líneas mal formateadas como están
                    lineasActualizadas.add(linea);
                }
            }
            
            if (!encontrado) {
                throw new VehiculoException("No se encontró vehículo con ID: " + vehiculo.getId());
            }
            
            // Guardar todo el archivo actualizado
            fileManager.escribirArchivo(nombreArchivo, lineasActualizadas);
            
        } catch (IOException e) {
            throw new VehiculoException("Error al actualizar vehículo", e);
        }
    }
    
    @Override
    public void delete(int id, String tipo) throws VehiculoException {
        try {
            String nombreArchivo = VehiculoFactory.obtenerNombreArchivo(tipo);
            List<String> lineas = fileManager.leerArchivo(nombreArchivo);
            List<String> lineasFiltradas = new ArrayList<>();
            boolean encontrado = false;
            
            for (String linea : lineas) {
                if (linea == null || linea.trim().isEmpty()) {
                    continue; // Ignorar líneas vacías
                }
                
                try {
                    Vehiculo v = VehiculoFactory.crearDesdeCSV(tipo, linea);
                    
                    if (v.getId() == id) {
                        // No agregar esta línea (eliminar)
                        encontrado = true;
                    } else {
                        // Mantener esta línea
                        lineasFiltradas.add(linea);
                    }
                } catch (IllegalArgumentException e) {
                    // Si la línea no se puede parsear, verificar si el ID está al inicio
                    // Formato esperado: "id,resto..."
                    String[] partes = linea.split(",", 2);
                    if (partes.length > 0) {
                        try {
                            int idEnLinea = Integer.parseInt(partes[0].trim());
                            if (idEnLinea == id) {
                                // Eliminar esta línea mal formateada también
                                encontrado = true;
                            } else {
                                // Mantener líneas mal formateadas con otro ID
                                lineasFiltradas.add(linea);
                            }
                        } catch (NumberFormatException ex) {
                            // No se puede determinar el ID, mantener la línea
                            lineasFiltradas.add(linea);
                        }
                    } else {
                        // Línea completamente inválida, eliminarla
                        continue;
                    }
                }
            }
            
            if (!encontrado) {
                throw new VehiculoException("No se encontró vehículo con ID: " + id);
            }
            
            // Guardar archivo sin el vehículo eliminado
            fileManager.escribirArchivo(nombreArchivo, lineasFiltradas);
            
        } catch (IOException e) {
            throw new VehiculoException("Error al eliminar vehículo", e);
        }
    }
    
    @Override
    public List<Vehiculo> findAll(String tipo) throws VehiculoException {
        try {
            String nombreArchivo = VehiculoFactory.obtenerNombreArchivo(tipo);
            List<String> lineas = fileManager.leerArchivo(nombreArchivo);
            List<Vehiculo> vehiculos = new ArrayList<>();
            
            for (String linea : lineas) {
                try {
                    Vehiculo v = VehiculoFactory.crearDesdeCSV(tipo, linea);
                    vehiculos.add(v);
                } catch (IllegalArgumentException e) {
                    // Ignorar líneas mal formateadas
                    System.err.println("Advertencia: Línea CSV mal formateada ignorada: " + linea);
                }
            }
            
            return vehiculos;
            
        } catch (IOException e) {
            throw new VehiculoException("Error al listar vehículos", e);
        }
    }
    
    @Override
    public boolean existsById(int id, String tipo) {
        try {
            String nombreArchivo = VehiculoFactory.obtenerNombreArchivo(tipo);
            List<String> lineas = fileManager.leerArchivo(nombreArchivo);
            
            for (String linea : lineas) {
                try {
                    Vehiculo v = VehiculoFactory.crearDesdeCSV(tipo, linea);
                    if (v.getId() == id) {
                        return true;
                    }
                } catch (IllegalArgumentException e) {
                    // Ignorar líneas mal formateadas
                    continue;
                }
            }
            
            return false;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public int getNextId(String tipo) {
        try {
            String nombreArchivo = VehiculoFactory.obtenerNombreArchivo(tipo);
            List<String> lineas = fileManager.leerArchivo(nombreArchivo);
            
            int maxId = 0;
            
            for (String linea : lineas) {
                try {
                    Vehiculo v = VehiculoFactory.crearDesdeCSV(tipo, linea);
                    if (v.getId() > maxId) {
                        maxId = v.getId();
                    }
                } catch (IllegalArgumentException e) {
                    // Ignorar líneas mal formateadas
                    continue;
                }
            }
            
            return maxId + 1;
            
        } catch (Exception e) {
            return 1; // Si hay error o no hay vehículos, empezar en 1
        }
    }
}