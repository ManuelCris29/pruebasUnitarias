# 📋 PLANTILLA PARA PROYECTOS JAVA EMPRESARIALES

Este documento es una guía completa para iniciar proyectos Java con arquitectura limpia, principios SOLID y buenas prácticas profesionales.

---

## 🎯 PROMPT INICIAL PARA NUEVOS PROYECTOS

```
Quiero crear un proyecto Java [DESCRIPCIÓN DEL PROYECTO] orientado a objetos siguiendo estos principios:

1. **Arquitectura en capas** (Model, Repository, Service, UI)
2. **Principios SOLID** aplicados en toda la arquitectura
3. **Patrones de diseño** apropiados (Factory, Strategy, Repository)
4. **Validaciones robustas** con validadores específicos
5. **Manejo de excepciones** personalizado
6. **Persistencia en** [archivos CSV / base de datos / memoria]
7. **Interfaz de usuario** [consola / GUI / API REST]

Estructura las carpetas según la arquitectura limpia y explica cómo aplicar cada principio SOLID.
```

---

## 📁 ESTRUCTURA DE CARPETAS ESTÁNDAR

```
proyecto/
│
├── src/
│   ├── model/              # Modelos de dominio (POJOs, entidades)
│   │   ├── [Entidad].java
│   │   └── [EntidadHija].java (si aplica herencia)
│   │
│   ├── repository/         # Capa de acceso a datos
│   │   ├── I[Entidad]Repository.java (interfaz)
│   │   └── [Entidad][Tipo]Repository.java (implementación)
│   │
│   ├── service/            # Lógica de negocio
│   │   ├── I[Entidad]Service.java (interfaz)
│   │   └── [Entidad]Service.java (implementación)
│   │
│   ├── validator/          # Validaciones de datos
│   │   ├── IValidator.java (interfaz genérica)
│   │   └── [Entidad]Validator.java (implementaciones)
│   │
│   ├── exception/          # Excepciones personalizadas
│   │   ├── [Dominio]Exception.java
│   │   └── ValidationException.java
│   │
│   ├── util/               # Utilidades y helpers
│   │   ├── [Nombre]Factory.java
│   │   ├── FileManager.java
│   │   └── DateUtil.java
│   │
│   ├── ui/                 # Interfaz de usuario
│   │   ├── ConsoleUI.java (para consola)
│   │   └── [Ventana]Frame.java (para GUI)
│   │
│   └── Main.java           # Punto de entrada
│
├── data/                   # Archivos de datos (si aplica)
├── lib/                    # Librerías externas (JARs)
├── test/                   # Tests unitarios (estructura paralela a src)
├── docs/                   # Documentación
└── README.md               # Descripción del proyecto
```

---

## 🏗️ PRINCIPIOS SOLID EXPLICADOS

### 1️⃣ **S - Single Responsibility Principle (Responsabilidad Única)**

**Definición**: Una clase debe tener una sola razón para cambiar.

**Aplicación**:
- **Model**: Solo representa datos del dominio
- **Repository**: Solo maneja persistencia
- **Service**: Solo contiene lógica de negocio
- **Validator**: Solo valida datos
- **UI**: Solo maneja interfaz y entrada/salida

**Ejemplo**:
```java
// ✅ CORRECTO - Responsabilidad única
public class VehiculoFileRepository {
    // Solo se encarga de guardar/leer archivos
}

// ❌ INCORRECTO - Múltiples responsabilidades
public class Vehiculo {
    private String marca;
    
    public void guardarEnArchivo() { } // NO: mezcla modelo con persistencia
    public void validar() { }          // NO: mezcla modelo con validación
    public void mostrarEnPantalla() { } // NO: mezcla modelo con UI
}
```

---

### 2️⃣ **O - Open/Closed Principle (Abierto/Cerrado)**

**Definición**: Abierto para extensión, cerrado para modificación.

**Aplicación**:
- Usa clases abstractas e interfaces
- Permite agregar nuevas funcionalidades sin modificar código existente
- Implementa herencia y polimorfismo

**Ejemplo**:
```java
// ✅ CORRECTO - Extensible sin modificar
public abstract class Vehiculo {
    protected String marca;
    public abstract double calcularImpuesto();
}

public class Auto extends Vehiculo {
    @Override
    public double calcularImpuesto() {
        return precio * 0.15; // Impuesto para autos
    }
}

public class Moto extends Vehiculo {
    @Override
    public double calcularImpuesto() {
        return precio * 0.10; // Impuesto para motos
    }
}

// Agregar Camion no requiere modificar Auto ni Moto
```

---

### 3️⃣ **L - Liskov Substitution Principle (Sustitución de Liskov)**

**Definición**: Los objetos de una clase derivada deben poder sustituir a objetos de la clase base sin alterar el funcionamiento.

**Aplicación**:
- Las clases hijas respetan el contrato de la clase padre
- No rompen precondiciones ni postcondiciones
- Mantienen el comportamiento esperado

**Ejemplo**:
```java
// ✅ CORRECTO - Sustituible
public void procesarVehiculo(Vehiculo v) {
    v.calcularImpuesto(); // Funciona con Auto, Moto, Camion
}

// Auto, Moto, Camion pueden sustituir a Vehiculo sin problemas
```

---

### 4️⃣ **I - Interface Segregation Principle (Segregación de Interfaces)**

**Definición**: Los clientes no deberían depender de interfaces que no usan.

**Aplicación**:
- Interfaces pequeñas y específicas
- No forzar métodos innecesarios
- Dividir interfaces grandes en específicas

**Ejemplo**:
```java
// ❌ INCORRECTO - Interfaz muy grande
public interface IVehiculoOperations {
    void create();
    void read();
    void update();
    void delete();
    void exportarPDF();
    void enviarEmail();
    void generarReporte();
}

// ✅ CORRECTO - Interfaces segregadas
public interface IRepository {
    void create();
    void read();
    void update();
    void delete();
}

public interface IExportable {
    void exportarPDF();
}

public interface INotificable {
    void enviarEmail();
}
```

---

### 5️⃣ **D - Dependency Inversion Principle (Inversión de Dependencias)**

**Definición**: Depender de abstracciones, no de implementaciones concretas.

**Aplicación**:
- Usa interfaces en lugar de clases concretas
- Inyección de dependencias por constructor
- Las capas superiores no conocen detalles de implementación

**Ejemplo**:
```java
// ✅ CORRECTO - Depende de abstracción
public class VehiculoService {
    private IVehiculoRepository repository; // Interfaz, no clase concreta
    
    public VehiculoService(IVehiculoRepository repository) {
        this.repository = repository; // Inyección de dependencias
    }
}

// ❌ INCORRECTO - Depende de implementación
public class VehiculoService {
    private VehiculoFileRepository repository = new VehiculoFileRepository();
    // Acoplado a la implementación específica
}
```

---

## 🎨 PATRONES DE DISEÑO RECOMENDADOS

### 1. **Repository Pattern**
```java
public interface IVehiculoRepository {
    void create(Vehiculo v);
    Vehiculo read(int id);
    void update(Vehiculo v);
    void delete(int id);
    List<Vehiculo> findAll();
}
```

**Ventajas**: Abstrae la persistencia, fácil cambiar de archivos a BD.

---

### 2. **Factory Pattern**
```java
public class VehiculoFactory {
    public static Vehiculo crearVehiculo(String tipo, String marca, String modelo) {
        switch(tipo.toLowerCase()) {
            case "auto": return new Auto(marca, modelo);
            case "moto": return new Moto(marca, modelo);
            case "camion": return new Camion(marca, modelo);
            default: throw new IllegalArgumentException("Tipo inválido");
        }
    }
}
```

**Ventajas**: Centraliza la creación de objetos.

---

### 3. **Strategy Pattern** (para validadores)
```java
public interface IValidator<T> {
    List<String> validate(T objeto);
}

public class AutoValidator implements IValidator<Auto> {
    @Override
    public List<String> validate(Auto auto) {
        // Lógica de validación específica
    }
}
```

**Ventajas**: Intercambiar algoritmos de validación fácilmente.

---

### 4. **Dependency Injection**
```java
// En Main.java
IVehiculoRepository repository = new VehiculoFileRepository();
IVehiculoService service = new VehiculoService(repository);
ConsoleUI ui = new ConsoleUI(service);
```

**Ventajas**: Bajo acoplamiento, fácil testing.

---

## 📐 ARQUITECTURA EN CAPAS

```
┌─────────────────────────────────┐
│    CAPA DE PRESENTACIÓN (UI)    │  ← Interacción con usuario
├─────────────────────────────────┤
│    CAPA DE NEGOCIO (Service)    │  ← Lógica y reglas
├─────────────────────────────────┤
│ CAPA DE PERSISTENCIA (Repository)│ ← Acceso a datos
├─────────────────────────────────┤
│     CAPA DE DATOS (Model)       │  ← Entidades del dominio
└─────────────────────────────────┘
```

**Flujo de comunicación**:
```
Usuario → UI → Service → Validator
                  ↓
              Repository → FileManager/DB → Datos
```

**Reglas**:
1. UI solo habla con Service
2. Service coordina Validators y Repository
3. Repository maneja persistencia
4. Nunca saltar capas (UI no debe hablar directamente con Repository)

---

## 🔤 CONVENCIONES DE NOMBRES

### Clases
- **Modelos**: `Vehiculo`, `Auto`, `Usuario` (sustantivos)
- **Servicios**: `VehiculoService`, `UsuarioService`
- **Repositorios**: `VehiculoRepository`, `VehiculoFileRepository`
- **Validadores**: `VehiculoValidator`, `AutoValidator`
- **Excepciones**: `VehiculoException`, `ValidationException`
- **Interfaces**: `IVehiculoRepository`, `IValidator<T>`

### Métodos
- **CRUD**: `create()`, `read()`, `update()`, `delete()`
- **Consultas**: `findById()`, `findAll()`, `findByMarca()`
- **Validación**: `validate()`, `isValid()`
- **Conversión**: `toCSV()`, `fromCSV()`, `toString()`

### Variables
- **camelCase**: `numeroEjes`, `tipoTransmision`
- **Constantes**: `MAX_CAPACIDAD`, `ARCHIVO_DATOS`
- **Booleanos**: `esSedan`, `tieneCarenado`, `isValid`

---

## ✅ CHECKLIST PARA NUEVOS PROYECTOS

### Fase 1: Planificación
- [ ] Definir entidades del dominio
- [ ] Identificar relaciones (herencia, composición)
- [ ] Elegir tipo de persistencia
- [ ] Elegir tipo de interfaz

### Fase 2: Estructura
- [ ] Crear carpetas según arquitectura en capas
- [ ] Crear archivo README.md
- [ ] Definir convenciones del equipo

### Fase 3: Implementación (orden recomendado)
1. [ ] **Model**: Clases de dominio (abstractas y concretas)
2. [ ] **Exception**: Excepciones personalizadas
3. [ ] **Validator**: Interfaces y validadores específicos
4. [ ] **Util**: Factories, FileManager, utilidades
5. [ ] **Repository**: Interfaces e implementaciones
6. [ ] **Service**: Interfaces e implementaciones
7. [ ] **UI**: Interfaz de usuario
8. [ ] **Main**: Ensamblar todo con DI

### Fase 4: Validación
- [ ] Probar cada capa independientemente
- [ ] Verificar principios SOLID aplicados
- [ ] Revisar manejo de excepciones
- [ ] Validar flujo completo CRUD

---

## 🛠️ VALIDACIONES AVANZADAS

### Tipos de validaciones a implementar:

1. **Validaciones de formato**
   - Expresiones regulares para placas, VIN, emails
   - Formatos de fecha válidos

2. **Validaciones de rango**
   - Año: entre 1900 y año actual + 1
   - Precio: mayor a 0
   - Cilindrada: entre 50cc y 2000cc

3. **Validaciones de negocio**
   - Unicidad de IDs
   - Capacidad de carga según tipo de camión
   - Número de puertas válido para autos

4. **Validaciones de integridad**
   - Referencias válidas
   - Datos requeridos no nulos
   - Longitudes mínimas/máximas

---

## 📦 MANEJO DE PERSISTENCIA

### Opción 1: Archivos CSV
```
data/
├── autos.csv
├── motos.csv
└── camiones.csv
```

**Ventajas**: Simple, no requiere BD
**Desventajas**: No escalable, sin transacciones

### Opción 2: Base de Datos
```java
// JDBC con interface Repository
public class VehiculoDBRepository implements IVehiculoRepository {
    private Connection connection;
    // Implementación con SQL
}
```

**Ventajas**: Escalable, transacciones, integridad
**Desventajas**: Requiere configuración de BD

### Opción 3: En Memoria
```java
public class VehiculoMemoryRepository implements IVehiculoRepository {
    private List<Vehiculo> vehiculos = new ArrayList<>();
    // Implementación con colecciones
}
```

**Ventajas**: Rápido para testing
**Desventajas**: No persiste datos

---

## 🎯 EJEMPLO DE MAIN.JAVA (Composición Manual)

```java
public class Main {
    public static void main(String[] args) {
        try {
            // 1. Crear utilidades
            FileManager fileManager = new FileManager();
            
            // 2. Crear repositorio
            IVehiculoRepository repository = new VehiculoFileRepository(fileManager);
            
            // 3. Crear validadores
            IValidator<Auto> autoValidator = new AutoValidator();
            IValidator<Moto> motoValidator = new MotoValidator();
            IValidator<Camion> camionValidator = new CamionValidator();
            
            // 4. Crear servicio (inyectar dependencias)
            IVehiculoService service = new VehiculoService(
                repository, 
                autoValidator, 
                motoValidator, 
                camionValidator
            );
            
            // 5. Crear UI (inyectar servicio)
            ConsoleUI ui = new ConsoleUI(service);
            
            // 6. Ejecutar aplicación
            ui.iniciar();
            
        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

---

## 🧪 TESTING (Opcional pero recomendado)

```
test/
├── model/
│   └── VehiculoTest.java
├── service/
│   └── VehiculoServiceTest.java
├── validator/
│   └── VehiculoValidatorTest.java
└── repository/
    └── VehiculoRepositoryTest.java
```

**Frameworks**: JUnit 5, Mockito

---

## 📚 RECURSOS ADICIONALES

### Libros recomendados:
- "Clean Code" - Robert C. Martin
- "Design Patterns" - Gang of Four
- "Effective Java" - Joshua Bloch

### Principios adicionales:
- **DRY**: Don't Repeat Yourself
- **KISS**: Keep It Simple, Stupid
- **YAGNI**: You Aren't Gonna Need It

---

## 🚀 COMANDO RÁPIDO PARA CREAR ESTRUCTURA

### Windows (PowerShell):
```powershell
mkdir src\model, src\repository, src\service, src\validator, src\exception, src\util, src\ui, data, docs, test
```

### Linux/Mac:
```bash
mkdir -p src/{model,repository,service,validator,exception,util,ui} data docs test
```

---

## 💡 CONSEJOS FINALES

1. **Empieza simple**: No sobre-ingenierices al principio
2. **Refactoriza**: Mejora el código conforme crece
3. **Comenta**: Documenta decisiones importantes
4. **Versiona**: Usa Git desde el inicio
5. **Prueba**: Testea cada capa independientemente
6. **Revisa**: Aplica code review periódicamente

---

## 📝 EJEMPLO DE README.MD

```markdown
# Nombre del Proyecto

## Descripción
[Breve descripción del proyecto]

## Arquitectura
- Arquitectura en capas (Model, Repository, Service, UI)
- Principios SOLID aplicados
- Patrones: Repository, Factory, Strategy

## Estructura
[Árbol de carpetas]

## Requisitos
- Java 11 o superior
- [Otras dependencias]

## Instalación
1. Clonar repositorio
2. Compilar: `javac -d out src/**/*.java`
3. Ejecutar: `java -cp out Main`

## Uso
[Instrucciones de uso]

## Autor
[Tu nombre]
```

---

## ✨ FIN DE LA PLANTILLA

**Usa esta plantilla como referencia para todos tus proyectos Java.**

**Personaliza según las necesidades específicas de cada proyecto.**

**Mantén los principios SOLID como guía en cada decisión de diseño.**

