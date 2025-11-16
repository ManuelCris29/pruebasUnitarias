package service;

import model.Vehiculo;
import exception.VehiculoException;
import exception.ValidationException;
import java.util.List;

public interface IVehiculoService {
    
    /**
     * Crea un nuevo vehículo (con validación)
     * 
     * @param vehiculo El vehículo a crear
     * @throws ValidationException Si los datos no son válidos
     * @throws VehiculoException Si hay error al guardar
     */
    void crear(Vehiculo vehiculo) throws ValidationException, VehiculoException;
    
    /**
     * Obtiene un vehículo por ID
     * 
     * @param id ID del vehículo
     * @param tipo Tipo de vehículo
     * @return El vehículo encontrado
     * @throws VehiculoException Si no existe o hay error
     */
    Vehiculo obtener(int id, String tipo) throws VehiculoException;
    
    /**
     * Actualiza un vehículo existente (con validación)
     * 
     * @param vehiculo El vehículo con datos actualizados
     * @throws ValidationException Si los datos no son válidos
     * @throws VehiculoException Si no existe o hay error
     */
    void actualizar(Vehiculo vehiculo) throws ValidationException, VehiculoException;
    
    /**
     * Elimina un vehículo
     * 
     * @param id ID del vehículo
     * @param tipo Tipo de vehículo
     * @throws VehiculoException Si no existe o hay error
     */
    void eliminar(int id, String tipo) throws VehiculoException;
    
    /**
     * Lista todos los vehículos de un tipo
     * 
     * @param tipo Tipo de vehículo
     * @return Lista de vehículos
     * @throws VehiculoException Si hay error al listar
     */
    List<Vehiculo> listarTodos(String tipo) throws VehiculoException;
    
    /**
     * Busca vehículos por marca
     * 
     * @param marca Marca a buscar
     * @param tipo Tipo de vehículo
     * @return Lista de vehículos que coinciden
     * @throws VehiculoException Si hay error
     */
    List<Vehiculo> buscarPorMarca(String marca, String tipo) throws VehiculoException;
    
    /**
     * Obtiene el próximo ID disponible
     * 
     * @param tipo Tipo de vehículo
     * @return Próximo ID
     */
    int obtenerProximoId(String tipo);
}