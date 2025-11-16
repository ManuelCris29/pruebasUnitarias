package service;

import model.*;
import repository.IVehiculoRepository;
import validator.*;
import exception.VehiculoException;
import exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class VehiculoService implements IVehiculoService {
    
    private IVehiculoRepository repository;
    private IValidator<Auto> autoValidator;
    private IValidator<Moto> motoValidator;
    private IValidator<Camion> camionValidator;
    
    /**
     * Constructor con inyección de dependencias
     */
    public VehiculoService(IVehiculoRepository repository,
                          IValidator<Auto> autoValidator,
                          IValidator<Moto> motoValidator,
                          IValidator<Camion> camionValidator) {
        this.repository = repository;
        this.autoValidator = autoValidator;
        this.motoValidator = motoValidator;
        this.camionValidator = camionValidator;
    }
    
    @Override
    public void crear(Vehiculo vehiculo) throws ValidationException, VehiculoException {
        // 1. Validar el vehículo
        validarVehiculo(vehiculo);
        
        // 2. Verificar que el ID no exista (regla de negocio)
        String tipo = vehiculo.getTipo().toLowerCase();
        if (repository.existsById(vehiculo.getId(), tipo)) {
            throw new VehiculoException("Ya existe un vehículo con ID: " + vehiculo.getId());
        }
        
        // 3. Guardar en el repository
        repository.create(vehiculo);
    }
    
    @Override
    public Vehiculo obtener(int id, String tipo) throws VehiculoException {
        // Validar parámetros
        if (id <= 0) {
            throw new VehiculoException("El ID debe ser mayor a 0");
        }
        
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new VehiculoException("El tipo no puede estar vacío");
        }
        
        return repository.read(id, tipo);
    }
    
    @Override
    public void actualizar(Vehiculo vehiculo) throws ValidationException, VehiculoException {
        // 1. Validar el vehículo
        validarVehiculo(vehiculo);
        
        // 2. Verificar que exista (regla de negocio)
        String tipo = vehiculo.getTipo().toLowerCase();
        if (!repository.existsById(vehiculo.getId(), tipo)) {
            throw new VehiculoException("No existe vehículo con ID: " + vehiculo.getId());
        }
        
        // 3. Actualizar en el repository
        repository.update(vehiculo);
    }
    
    @Override
    public void eliminar(int id, String tipo) throws VehiculoException {
        // Validar parámetros
        if (id <= 0) {
            throw new VehiculoException("El ID debe ser mayor a 0");
        }
        
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new VehiculoException("El tipo no puede estar vacío");
        }
        
        // Verificar que exista antes de eliminar
        if (!repository.existsById(id, tipo)) {
            throw new VehiculoException("No existe vehículo con ID: " + id);
        }
        
        repository.delete(id, tipo);
    }
    
    @Override
    public List<Vehiculo> listarTodos(String tipo) throws VehiculoException {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new VehiculoException("El tipo no puede estar vacío");
        }
        
        return repository.findAll(tipo);
    }
    
    @Override
    public List<Vehiculo> buscarPorMarca(String marca, String tipo) throws VehiculoException {
        if (marca == null || marca.trim().isEmpty()) {
            throw new VehiculoException("La marca no puede estar vacía");
        }
        
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new VehiculoException("El tipo no puede estar vacío");
        }
        
        // Obtener todos y filtrar por marca
        List<Vehiculo> todos = repository.findAll(tipo);
        List<Vehiculo> filtrados = new ArrayList<>();
        
        for (Vehiculo v : todos) {
            if (v.getMarca().toLowerCase().contains(marca.toLowerCase())) {
                filtrados.add(v);
            }
        }
        
        return filtrados;
    }
    
    @Override
    public int obtenerProximoId(String tipo) {
        return repository.getNextId(tipo);
    }
    
    /**
     * Método privado para validar un vehículo según su tipo
     */
    private void validarVehiculo(Vehiculo vehiculo) throws ValidationException {
        List<String> errores;
        
        if (vehiculo instanceof Auto) {
            errores = autoValidator.validate((Auto) vehiculo);
        } else if (vehiculo instanceof Moto) {
            errores = motoValidator.validate((Moto) vehiculo);
        } else if (vehiculo instanceof Camion) {
            errores = camionValidator.validate((Camion) vehiculo);
        } else {
            throw new ValidationException("Tipo de vehículo desconocido");
        }
        
        // Si hay errores, lanzar excepción
        if (!errores.isEmpty()) {
            throw new ValidationException(errores);
        }
    }
}