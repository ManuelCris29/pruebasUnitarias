package model;

public class Moto extends Vehiculo{
    //atributos especificos de la moto
    private int cilindrada;
    private boolean tieneCarenado;
   
    //constructor vacio
    public Moto(){
        super();
    }
    //constructor completo
    public Moto(int id, String marca, String modelo, int anio, double precio, String color, int cilindrada, boolean tieneCarenado){
        super(id, marca, modelo, anio, precio, color);
        this.cilindrada = cilindrada;
        this.tieneCarenado = tieneCarenado;
    }
    //getters and setters   
    public int getCilindrada(){
        return cilindrada;
    }
    public void setCilindrada(int cilindrada){
        this.cilindrada = cilindrada;
    }
    public boolean isTieneCarenado(){
        return tieneCarenado;
    }
    public void setTieneCarenado(boolean tieneCarenado){
        this.tieneCarenado = tieneCarenado;
    }
    @Override
    public String getTipo(){
        return "Moto";
    }
    @Override
    public String toCSV(){
        return String.format("%d,%s,%s,%d,%.2f,%s,%d,%b",
            id, marca, modelo, anio, precio, color, cilindrada, tieneCarenado);
    }

    public static Moto fromCSV(String linea){
        // Limpiar la línea de espacios en blanco y saltos de línea
        linea = linea.trim();
        String[] datos = linea.split(",");
        
        // Verificar que tenemos suficientes datos
        if (datos.length < 8) {
            throw new IllegalArgumentException("Formato CSV inválido. Se esperaban 8 campos, se encontraron: " + datos.length);
        }
        
        return new Moto(
            Integer.parseInt(datos[0].trim()),  // id
            datos[1].trim(),                     // marca
            datos[2].trim(),                     // modelo
            Integer.parseInt(datos[3].trim()),  // año
            Double.parseDouble(datos[4].trim()), // precio
            datos[5].trim(),                     // color
            Integer.parseInt(datos[6].trim()),  // cilindrada
            Boolean.parseBoolean(datos[7].trim()) // tieneCarenado
        );
    }

    @Override
    public double calcularImpuesto(){
        return precio * 0.10;// 10% para motos
    }
    @Override
    public String toString(){
        return super.toString() + String.format(" | Cilindrada: %d | Carenado: %s",
            cilindrada, tieneCarenado ? "Sí" : "No");
    }
}
