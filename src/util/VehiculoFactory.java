package util;

import model.*;

public class VehiculoFactory {
    
    /**
     * Crea una instancia de vehículo según el tipo especificado
     * 
     * @param tipo Tipo de vehículo ("auto", "moto", "camion")
     * @return Nueva instancia del tipo de vehículo
     * @throws IllegalArgumentException Si el tipo no es válido
     */
    public static Vehiculo crearVehiculo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de vehículo no puede estar vacío");
        }
        
        switch (tipo.toLowerCase().trim()) {
            case "auto":
                return new Auto();
            case "moto":
                return new Moto();
            case "camion":
            case "camión":
                return new Camion();
            default:
                throw new IllegalArgumentException("Tipo de vehículo no válido: " + tipo);
        }
    }
    
    /**
     * Crea un vehículo desde una línea CSV
     * 
     * @param tipo Tipo de vehículo
     * @param lineaCSV Línea CSV con los datos
     * @return Instancia del vehículo con datos cargados
     * @throws IllegalArgumentException Si el tipo no es válido o la línea es inválida
     */
    public static Vehiculo crearDesdeCSV(String tipo, String lineaCSV) {
        if (lineaCSV == null || lineaCSV.trim().isEmpty()) {
            throw new IllegalArgumentException("La línea CSV no puede estar vacía");
        }
        
        try {
            switch (tipo.toLowerCase().trim()) {
                case "auto":
                    return Auto.fromCSV(lineaCSV);
                case "moto":
                    return Moto.fromCSV(lineaCSV);
                case "camion":
                case "camión":
                    return Camion.fromCSV(lineaCSV);
                default:
                    throw new IllegalArgumentException("Tipo de vehículo no válido: " + tipo);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al parsear línea CSV: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene el nombre del archivo CSV según el tipo de vehículo
     * 
     * @param tipo Tipo de vehículo
     * @return Nombre del archivo (sin extensión)
     */
    public static String obtenerNombreArchivo(String tipo) {
        switch (tipo.toLowerCase().trim()) {
            case "auto":
                return "autos";
            case "moto":
                return "motos";
            case "camion":
            case "camión":
                return "camiones";
            default:
                throw new IllegalArgumentException("Tipo de vehículo no válido: " + tipo);
        }
    }
    
    /**
     * Obtiene el nombre del archivo según una instancia de vehículo
     * 
     * @param vehiculo Instancia del vehículo
     * @return Nombre del archivo (sin extensión)
     */
    public static String obtenerNombreArchivo(Vehiculo vehiculo) {
        if (vehiculo instanceof Auto) {
            return "autos";
        } else if (vehiculo instanceof Moto) {
            return "motos";
        } else if (vehiculo instanceof Camion) {
            return "camiones";
        } else {
            throw new IllegalArgumentException("Tipo de vehículo desconocido");
        }
    }
    
    /**
     * Verifica si un tipo de vehículo es válido
     * 
     * @param tipo Tipo a verificar
     * @return true si es válido, false si no
     */
    public static boolean esTipoValido(String tipo) {
        if (tipo == null) {
            return false;
        }
        
        String tipoLower = tipo.toLowerCase().trim();
        return tipoLower.equals("auto") || 
               tipoLower.equals("moto") || 
               tipoLower.equals("camion") ||
               tipoLower.equals("camión");
    }
}