package util;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    
    private static final String DATA_DIRECTORY = "data";
    private static final String FILE_EXTENSION = ".csv";
    
    // Constructor
    public FileManager() {
        inicializarDirectorio();
    }
    
    /**
     * Inicializa el directorio de datos si no existe
     */
    private void inicializarDirectorio() {
        try {
            Path path = Paths.get(DATA_DIRECTORY);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                System.out.println("Directorio 'data/' creado exitosamente");
            }
        } catch (IOException e) {
            System.err.println("Error al crear directorio: " + e.getMessage());
        }
    }
    
    /**
     * Lee todas las líneas de un archivo CSV
     * 
     * @param nombreArchivo Nombre del archivo sin extensión (ej: "autos")
     * @return Lista de líneas del archivo
     * @throws IOException Si hay error al leer el archivo
     */
    public List<String> leerArchivo(String nombreArchivo) throws IOException {
        String rutaCompleta = DATA_DIRECTORY + File.separator + nombreArchivo + FILE_EXTENSION;
        Path path = Paths.get(rutaCompleta);
        
        // Si el archivo no existe, retornar lista vacía
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        
        List<String> lineas = Files.readAllLines(path, java.nio.charset.StandardCharsets.UTF_8);
        // Filtrar líneas vacías
        List<String> lineasFiltradas = new ArrayList<>();
        for (String linea : lineas) {
            if (linea != null && !linea.trim().isEmpty()) {
                lineasFiltradas.add(linea.trim());
            }
        }
        return lineasFiltradas;
    }
    
    /**
     * Escribe líneas en un archivo CSV (sobrescribe el contenido)
     * 
     * @param nombreArchivo Nombre del archivo sin extensión
     * @param lineas Lista de líneas a escribir
     * @throws IOException Si hay error al escribir
     */
    public void escribirArchivo(String nombreArchivo, List<String> lineas) throws IOException {
        String rutaCompleta = DATA_DIRECTORY + File.separator + nombreArchivo + FILE_EXTENSION;
        Path path = Paths.get(rutaCompleta);
        
        Files.write(path, lineas, java.nio.charset.StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
    
    /**
     * Agrega una línea al final de un archivo CSV
     * 
     * @param nombreArchivo Nombre del archivo sin extensión
     * @param linea Línea a agregar
     * @throws IOException Si hay error al escribir
     */
    public void agregarLinea(String nombreArchivo, String linea) throws IOException {
        String rutaCompleta = DATA_DIRECTORY + File.separator + nombreArchivo + FILE_EXTENSION;
        Path path = Paths.get(rutaCompleta);
        
        // Si el archivo no existe, crearlo con la línea
        if (!Files.exists(path)) {
            List<String> lineas = new ArrayList<>();
            lineas.add(linea);
            Files.write(path, lineas, java.nio.charset.StandardCharsets.UTF_8, 
                       StandardOpenOption.CREATE);
        } else {
            // Si existe, agregar al final
            Files.write(path, (linea + System.lineSeparator()).getBytes(java.nio.charset.StandardCharsets.UTF_8), 
                        StandardOpenOption.APPEND);
        }
    }
    
    /**
     * Verifica si un archivo existe
     * 
     * @param nombreArchivo Nombre del archivo sin extensión
     * @return true si existe, false si no
     */
    public boolean existeArchivo(String nombreArchivo) {
        String rutaCompleta = DATA_DIRECTORY + File.separator + nombreArchivo + FILE_EXTENSION;
        return Files.exists(Paths.get(rutaCompleta));
    }
    
    /**
     * Elimina un archivo
     * 
     * @param nombreArchivo Nombre del archivo sin extensión
     * @return true si se eliminó, false si no existía
     * @throws IOException Si hay error al eliminar
     */
    public boolean eliminarArchivo(String nombreArchivo) throws IOException {
        String rutaCompleta = DATA_DIRECTORY + File.separator + nombreArchivo + FILE_EXTENSION;
        Path path = Paths.get(rutaCompleta);
        
        return Files.deleteIfExists(path);
    }
    
    /**
     * Obtiene la ruta completa de un archivo
     * 
     * @param nombreArchivo Nombre del archivo sin extensión
     * @return Ruta completa del archivo
     */
    public String obtenerRutaCompleta(String nombreArchivo) {
        return DATA_DIRECTORY + File.separator + nombreArchivo + FILE_EXTENSION;
    }
}