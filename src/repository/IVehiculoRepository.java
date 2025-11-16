package repository;

import model.Vehiculo;
import exception.VehiculoException;
import java.util.List;

public interface IVehiculoRepository {
    
    /**
     * Crea (guarda) un nuevo vehículo
     * 
     * @param vehiculo El vehículo a guardar
     * @throws VehiculoException Si hay error al guardar
     */
    void create(Vehiculo vehiculo) throws VehiculoException;
    
    /**
     * Lee (busca) un vehículo por su ID
     * 
     * @param id ID del vehículo a buscar
     * @param tipo Tipo de vehículo ("auto", "moto", "camion")
     * @return El vehículo encontrado
     * @throws VehiculoException Si no se encuentra o hay error
     */
    Vehiculo read(int id, String tipo) throws VehiculoException;
    
    /**
     * Actualiza un vehículo existente
     * 
     * @param vehiculo El vehículo con datos actualizados
     * @throws VehiculoException Si no existe o hay error
     */
    void update(Vehiculo vehiculo) throws VehiculoException;
    
    /**
     * Elimina un vehículo por su ID
     * 
     * @param id ID del vehículo a eliminar
     * @param tipo Tipo de vehículo
     * @throws VehiculoException Si no existe o hay error
     */
    void delete(int id, String tipo) throws VehiculoException;
    
    /**
     * Obtiene todos los vehículos de un tipo específico
     * 
     * @param tipo Tipo de vehículo
     * @return Lista de vehículos
     * @throws VehiculoException Si hay error al leer
     */
    List<Vehiculo> findAll(String tipo) throws VehiculoException;
    
    /**
     * Verifica si existe un vehículo con el ID dado
     * 
     * @param id ID a verificar
     * @param tipo Tipo de vehículo
     * @return true si existe, false si no
     */
    boolean existsById(int id, String tipo);
    
    /**
     * Obtiene el próximo ID disponible para un tipo de vehículo
     * 
     * @param tipo Tipo de vehículo
     * @return Próximo ID disponible
     */
    int getNextId(String tipo);
}