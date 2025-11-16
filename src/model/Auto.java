package model;

public class Auto extends Vehiculo {
    private int numeroPuertas;
    private  String tipoTransmision;
    private  boolean  esSedan;

    //construcotr vacio

    public Auto(){
        super();
    }

    //constructor comleto
    
    public Auto(int id, String marca, String modelo, int anio, double precio, String color, int numeroPuertas, String tipoTransmision, boolean esSedan){
        super(id, marca, modelo, anio, precio, color);
        this.numeroPuertas = numeroPuertas;
        this.tipoTransmision = tipoTransmision;
        this.esSedan = esSedan;
    }

    //getters and setters
    public int getNumeroPuertas(){
        return numeroPuertas;
    }
    public void setNumeroPuertas(int numeroPuertas){
        this.numeroPuertas = numeroPuertas;
    }
    public String getTipoTransmision(){
        return tipoTransmision;
    }
    public void setTipoTransmision(String tipoTransmision){
        this.tipoTransmision = tipoTransmision;
    }
    public boolean isEsSedan(){
        return esSedan;
    }
    public void setEsSedan(boolean esSedan){
        this.esSedan = esSedan;
    }

    @Override
    public String  getTipo(){
        return "Auto";
    }

    @Override
    public String toCSV(){

        // Formato: id,marca,modelo,año,precio,color,numeroPuertas,tipoTransmision,esSedan
        return String.format("%d,%s,%s,%d,%.2f,%s,%d,%s,%b",
            id, marca, modelo, anio, precio, color, numeroPuertas, tipoTransmision, esSedan);
    }

    public static Auto fromCSV(String linea){
        // Limpiar la línea de espacios en blanco y saltos de línea
        linea = linea.trim();
        
        // Validar que la línea no esté vacía
        if (linea.isEmpty()) {
            throw new IllegalArgumentException("La línea CSV está vacía");
        }
        
        String[] datos = linea.split(",");
        
        // Si hay más de 9 campos, el precio probablemente tiene coma decimal
        // Reconstruir el precio correctamente
        if (datos.length > 9) {
            // El precio está dividido (ej: "2000000" y "00")
            // Reconstruir: datos[4] + "." + datos[5] = precio completo
            String precioCompleto = datos[4].trim() + "." + datos[5].trim();
            // Reconstruir el array con el precio correcto
            String[] datosCorregidos = new String[9];
            datosCorregidos[0] = datos[0].trim();  // id
            datosCorregidos[1] = datos[1].trim();  // marca
            datosCorregidos[2] = datos[2].trim();  // modelo
            datosCorregidos[3] = datos[3].trim();  // año
            datosCorregidos[4] = precioCompleto;   // precio (reconstruido)
            datosCorregidos[5] = datos[6].trim();  // color (desplazado)
            datosCorregidos[6] = datos[7].trim();  // numeroPuertas (desplazado)
            datosCorregidos[7] = datos[8].trim();  // tipoTransmision (desplazado)
            datosCorregidos[8] = datos[9].trim();  // esSedan (desplazado)
            datos = datosCorregidos;
        }
        
        // Verificar que tenemos suficientes datos
        if (datos.length < 9) {
            throw new IllegalArgumentException("Formato CSV inválido. Se esperaban 9 campos, se encontraron: " + datos.length + ". Línea: " + linea);
        }
        
        try {
            // Normalizar el precio (reemplazar coma por punto si es necesario)
            String precioStr = datos[4].trim().replace(",", ".");
            
            return new Auto(
                Integer.parseInt(datos[0].trim()),  // id
                datos[1].trim(),                     // marca
                datos[2].trim(),                     // modelo
                Integer.parseInt(datos[3].trim()),  // año
                Double.parseDouble(precioStr),       // precio (normalizado)
                datos[5].trim(),                     // color
                Integer.parseInt(datos[6].trim()),  // numeroPuertas
                datos[7].trim(),                     // tipoTransmision
                Boolean.parseBoolean(datos[8].trim()) // esSedan
            );
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error al parsear valores numéricos en línea CSV: " + linea + ". Error: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al crear Auto desde CSV: " + linea + ". Error: " + e.getMessage(), e);
        }
    }

    @Override
    public double calcularImpuesto(){
        return precio * 0.16; // 16% para autos
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Puertas: %d | Transmisión: %s | Sedán: %s",
                numeroPuertas, tipoTransmision, esSedan ? "Sí" : "No");
    }





}
