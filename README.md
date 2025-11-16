# 🚗 Sistema CRUD de Vehículos - Principios SOLID

Sistema de gestión de vehículos implementado en Java siguiendo los principios SOLID y buenas prácticas de ingeniería de software.

## 📋 Descripción del Proyecto

Este proyecto implementa un sistema CRUD (Create, Read, Update, Delete) completo para la gestión de vehículos, con soporte para tres tipos: **Autos**, **Motos** y **Camiones**. El sistema utiliza una arquitectura en capas con principios SOLID aplicados en cada componente.

## 🏗️ Arquitectura del Proyecto

El proyecto sigue una **arquitectura en capas** con separación clara de responsabilidades:

```
┌─────────────────────────────────────┐
│          CAPA UI                    │
│      (Interfaz de Usuario)          │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│        CAPA SERVICE                 │
│    (Lógica de Negocio)              │
└──────┬──────────────────┬───────────┘
       │                  │
┌──────▼──────┐  ┌────────▼──────────┐
│ VALIDATORS  │  │   REPOSITORY      │
│ (Validación)│  │  (Persistencia)   │
└─────────────┘  └────────┬──────────┘
                          │
                 ┌────────▼──────────┐
                 │   UTIL            │
                 │ (FileManager)     │
                 └────────┬──────────┘
                          │
                 ┌────────▼──────────┐
                 │   MODEL           │
                 │ (Entidades)       │
                 └───────────────────┘
```

## 📁 Estructura del Proyecto

```
crud/
├── src/
│   ├── model/              # Modelos de dominio
│   │   ├── Vehiculo.java   # Clase abstracta base
│   │   ├── Auto.java       # Clase concreta: Auto
│   │   ├── Moto.java       # Clase concreta: Moto
│   │   └── Camion.java     # Clase concreta: Camión
│   │
│   ├── repository/         # Capa de persistencia
│   │   ├── IVehiculoRepository.java
│   │   └── VehiculoFileRepository.java
│   │
│   ├── service/            # Lógica de negocio
│   │   ├── IVehiculoService.java
│   │   └── VehiculoService.java
│   │
│   ├── validator/          # Validaciones
│   │   ├── IValidator.java
│   │   ├── VehiculoValidator.java
│   │   ├── AutoValidator.java
│   │   ├── MotoValidator.java
│   │   └── CamionValidator.java
│   │
│   ├── exception/          # Excepciones personalizadas
│   │   ├── VehiculoException.java
│   │   └── ValidationException.java
│   │
│   ├── util/               # Utilidades
│   │   ├── FileManager.java
│   │   └── VehiculoFactory.java
│   │
│   ├── ui/                 # Interfaz de usuario
│   │   └── ConsoleUI.java
│   │
│   └── Main.java           # Punto de entrada
│
├── test/                   # Pruebas unitarias
│   ├── repository/
│   ├── service/
│   └── validator/
│
├── data/                   # Archivos CSV de datos
│   ├── autos.csv
│   ├── motos.csv
│   └── camiones.csv
│
├── out/                    # Archivos compilados
│
├── sonar-project.properties # Configuración SonarQube
├── .gitignore
└── README.md
```

## 🎯 Principios SOLID Aplicados

### ✅ Single Responsibility Principle (SRP)
- Cada clase tiene una única responsabilidad:
  - `VehiculoFileRepository`: Solo persistencia
  - `VehiculoService`: Solo lógica de negocio
  - `AutoValidator`: Solo validación de autos
  - `ConsoleUI`: Solo interfaz de usuario

### ✅ Open/Closed Principle (OCP)
- Abierto para extensión, cerrado para modificación
- Puedes agregar nuevos tipos de vehículos sin modificar código existente
- Ejemplo: Agregar `Furgon` extendiendo `Vehiculo`

### ✅ Liskov Substitution Principle (LSP)
- Las clases hijas (`Auto`, `Moto`, `Camion`) pueden sustituir a `Vehiculo` sin alterar el comportamiento

### ✅ Interface Segregation Principle (ISP)
- Interfaces pequeñas y específicas:
  - `IValidator<T>`: Solo validación
  - `IVehiculoRepository`: Solo operaciones CRUD
  - `IVehiculoService`: Solo operaciones de negocio

### ✅ Dependency Inversion Principle (DIP)
- Las capas superiores dependen de abstracciones (interfaces)
- Inyección de dependencias por constructor
- Ejemplo: `VehiculoService` depende de `IVehiculoRepository`, no de `VehiculoFileRepository`

## 🚀 Requisitos del Sistema

- **Java**: JDK 11 o superior
- **Sistema Operativo**: Windows, Linux o macOS

## 📦 Instalación y Configuración

### 1. Clonar el repositorio

```bash
git clone <url-del-repositorio>
cd crud
```

### 2. Compilar el proyecto

```bash
# Windows (PowerShell)
javac -d out -encoding UTF-8 src\Main.java src\model\*.java src\exception\*.java src\validator\*.java src\util\*.java src\repository\*.java src\service\*.java src\ui\*.java

# Linux/Mac
javac -d out -encoding UTF-8 src/Main.java src/model/*.java src/exception/*.java src/validator/*.java src/util/*.java src/repository/*.java src/service/*.java src/ui/*.java
```

### 3. Ejecutar la aplicación

```bash
java -cp out Main
```

## 🧪 Ejecutar Pruebas Unitarias

### Opción 1: Con JUnit 5 (Recomendado)

1. **Descargar JUnit 5:**
   - JUnit Platform: https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.3/junit-platform-console-standalone-1.9.3.jar

2. **Compilar tests:**
```bash
javac -d out -cp "out;lib/junit-platform-console-standalone-1.9.3.jar" src\**\*.java test\**\*.java
```

3. **Ejecutar tests:**
```bash
java -jar lib/junit-platform-console-standalone-1.9.3.jar --class-path out --scan-class-path
```

### Opción 2: Script automatizado

Ejecutar `ejecutar-tests.bat` (Windows) o `ejecutar-tests.sh` (Linux/Mac)

## 📊 Análisis de Calidad con SonarQube

### Configuración

1. **Instalar SonarQube:**
   - Descargar desde: https://www.sonarqube.org/downloads/
   - O usar SonarCloud (gratis): https://sonarcloud.io/

2. **Configurar proyecto:**
   - El archivo `sonar-project.properties` ya está configurado
   - Ajustar `sonar.projectKey` si es necesario

3. **Ejecutar análisis:**
```bash
# Con SonarQube local
sonar-scanner

# Con SonarCloud
sonar-scanner -Dsonar.login=TU_TOKEN
```

### Métricas de Calidad

El análisis de SonarQube verificará:
- ✅ Cobertura de código
- ✅ Duplicación de código
- ✅ Complejidad ciclomática
- ✅ Code smells
- ✅ Vulnerabilidades de seguridad
- ✅ Bugs potenciales

## 📝 Funcionalidades Implementadas

### Operaciones CRUD

- ✅ **Create**: Crear nuevos vehículos con validación
- ✅ **Read**: Leer vehículos por ID o listar todos
- ✅ **Update**: Actualizar vehículos existentes
- ✅ **Delete**: Eliminar vehículos

### Validaciones

- ✅ Validación de datos comunes (marca, modelo, año, precio, color)
- ✅ Validaciones específicas por tipo de vehículo
- ✅ Reglas de negocio (ej: autos de 2 puertas generalmente no son sedán)
- ✅ Mensajes de error descriptivos

### Búsquedas

- ✅ Buscar por marca (case-insensitive)
- ✅ Listar todos los vehículos de un tipo
- ✅ Obtener próximo ID disponible

## 🧪 Cobertura de Pruebas

Las pruebas unitarias cubren:

- ✅ **Repository**: Todas las operaciones CRUD (casos exitosos y de error)
- ✅ **Service**: Lógica de negocio y validaciones
- ✅ **Validators**: Reglas de validación para cada tipo de vehículo

### Casos de Prueba Implementados

**Repository (14 pruebas):**
- Crear vehículo exitosamente
- Error al crear con ID duplicado
- Leer vehículo existente
- Error al leer vehículo inexistente
- Actualizar vehículo
- Error al actualizar inexistente
- Eliminar vehículo
- Error al eliminar inexistente
- Listar todos los vehículos
- Lista vacía cuando no hay vehículos
- Verificar existencia por ID
- Obtener próximo ID
- Crear diferentes tipos de vehículos

**Service (12 pruebas):**
- Crear con validación exitosa
- Error de validación
- Error con ID duplicado
- Obtener vehículo
- Actualizar con validación
- Eliminar vehículo
- Listar todos
- Buscar por marca
- Búsqueda case-insensitive

**Validators (múltiples pruebas):**
- Validaciones de Auto (10+ casos)
- Validaciones de Moto
- Validaciones de Camión

## 📚 Documentación Adicional

- Ver `JAVA_PROJECT_TEMPLATE.md` para guía completa de arquitectura y principios SOLID

## 👨‍💻 Autor

**Manuel Cristobal Morneo Lizcano.**  

## 📧 Contacto

Para consultas o colaboración 
**manuel199729@gmail.com**





**Versión**: 1.0  
**Última actualización**: Noviembre 2025

