package model;

public abstract class Vehiculo {

    // atributos comunes a todos los vehiculos
    protected int id;
    protected String marca;
    protected String modelo;
    protected int anio;
    protected double precio;
    protected String color;

    //constructor vacio
    public Vehiculo(){}

    //constructor de vehiculos con parametros
    public Vehiculo(int id, String marca, String modelo, int anio, double precio, String color){

        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
        this.color = color;
    }


    //getters and setters
    public int  getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getMarca(){
        return marca;
    }
    public void setMarca(String marca){
        this.marca = marca;
    }
    public String getModelo(){
        return modelo;
    }
    public void setModelo(String modelo){
        this.modelo = modelo;
    }
    public int getAnio(){
        return anio;
    }
    public void setAnio(int anio){
        this.anio = anio;
    }
    public double getPrecio(){
        return precio;
    }
    public void setPrecio(double precio){
        this.precio = precio;
    }
    public String getColor(){
        return color;
    }
    public void setColor(String color){
        this.color = color;
    }

  // Método abstracto que cada tipo debe implementar
    public abstract String getTipo();

    // Método abstracto para convertir a formato CSV
    public abstract String toCSV();

     // Método para calcular impuesto (puede ser sobreescrito)
    public  double  calcularImpuesto(){
        return precio * 0.15;
    }

    @Override
    public String toString(){

        return String.format("ID: %d | %s %s (%d) | Color: %s | Precio: $%.2f", 
        id, marca, modelo, anio, color, precio);

    }
}