package model;

public class Camion extends Vehiculo {
    // Atributos específicos de Camión
    private double capacidadCarga; // En toneladas
    private int numeroEjes;
    
    // Constructor vacío
    public Camion() {
        super();
    }
    
    // Constructor completo
    public Camion(int id, String marca, String modelo, int anio, double precio, String color,
                  double capacidadCarga, int numeroEjes) {
        super(id, marca, modelo, anio, precio, color);
        this.capacidadCarga = capacidadCarga;
        this.numeroEjes = numeroEjes;
    }
    
    // Getters y Setters
    public double getCapacidadCarga() {
        return capacidadCarga;
    }
    
    public void setCapacidadCarga(double capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }
    
    public int getNumeroEjes() {
        return numeroEjes;
    }
    
    public void setNumeroEjes(int numeroEjes) {
        this.numeroEjes = numeroEjes;
    }
    
    @Override
    public String getTipo() {
        return "Camion";
    }
    
    @Override
    public String toCSV() {
        // Formato: id,marca,modelo,año,precio,color,capacidadCarga,numeroEjes
        return String.format("%d,%s,%s,%d,%.2f,%s,%.2f,%d",
                id, marca, modelo, anio, precio, color, capacidadCarga, numeroEjes);
    }
    
    // Método para crear Camion desde línea CSV
    public static Camion fromCSV(String linea) {
        // Limpiar la línea de espacios en blanco y saltos de línea
        linea = linea.trim();
        String[] datos = linea.split(",");
        
        // Verificar que tenemos suficientes datos
        if (datos.length < 8) {
            throw new IllegalArgumentException("Formato CSV inválido. Se esperaban 8 campos, se encontraron: " + datos.length);
        }
        
        return new Camion(
                Integer.parseInt(datos[0].trim()),  // id
                datos[1].trim(),                     // marca
                datos[2].trim(),                     // modelo
                Integer.parseInt(datos[3].trim()),  // año
                Double.parseDouble(datos[4].trim()), // precio
                datos[5].trim(),                     // color
                Double.parseDouble(datos[6].trim()), // capacidadCarga
                Integer.parseInt(datos[7].trim())   // numeroEjes
        );
    }
    
    @Override
    public double calcularImpuesto() {
        return precio * 0.12; // 12% para camiones
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format(" | Capacidad: %.2f tons | Ejes: %d",
                capacidadCarga, numeroEjes);
    }
}