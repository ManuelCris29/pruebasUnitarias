# 📊 Guía de Configuración de SonarQube

## Opción 1: SonarCloud (Recomendado - Gratis)

SonarCloud es la versión en la nube de SonarQube, ideal para proyectos académicos.

### Pasos:

1. **Crear cuenta en SonarCloud:**
   - Ir a: https://sonarcloud.io/
   - Iniciar sesión con GitHub

2. **Crear nuevo proyecto:**
   - Click en "Create Project"
   - Seleccionar "From GitHub" (si tu proyecto está en GitHub)
   - O seleccionar "Manually" para análisis manual

3. **Obtener token:**
   - Ir a: My Account > Security
   - Generar un nuevo token
   - Copiar el token (solo se muestra una vez)

4. **Configurar sonar-project.properties:**
   - El archivo ya está creado en la raíz del proyecto
   - Ajustar `sonar.projectKey` con el key de tu proyecto en SonarCloud

5. **Instalar SonarScanner:**
   - Descargar desde: https://docs.sonarqube.org/latest/analysis/scan/sonarscanner/
   - Agregar al PATH del sistema

6. **Ejecutar análisis:**
```bash
sonar-scanner -Dsonar.login=TU_TOKEN_AQUI
```

---

## Opción 2: SonarQube Local

### Requisitos:
- Java 21 (LTS) o superior
- Mínimo 2GB RAM
- PostgreSQL (opcional, puede usar H2 embebida)

### Pasos:

1. **Descargar SonarQube:**
   - Ir a: https://www.sonarqube.org/downloads/
   - Descargar Community Edition (gratis)

2. **Instalar:**
   - Extraer el archivo ZIP
   - En Windows: Ejecutar `StartSonar.bat` desde `bin/windows-x86-64/`
   - En Linux/Mac: Ejecutar `sonar.sh start` desde `bin/[sistema]`

3. **Acceder a la interfaz:**
   - Abrir navegador: http://localhost:9000
   - Usuario por defecto: `admin`
   - Contraseña por defecto: `admin` (cambiar en primer inicio)

4. **Crear proyecto:**
   - Ir a: Projects > Create Project
   - Seleccionar "Manually"
   - Ingresar Project Key y Display Name

5. **Generar token:**
   - My Account > Security
   - Generar token para el proyecto

6. **Ejecutar análisis:**
```bash
sonar-scanner -Dsonar.login=TU_TOKEN_AQUI
```

---

## Configuración del Proyecto

El archivo `sonar-project.properties` ya está configurado con:

```properties
sonar.projectKey=crud-vehiculos
sonar.projectName=CRUD Vehículos - Principios SOLID
sonar.sources=src
sonar.tests=test
sonar.sourceEncoding=UTF-8
```

### Ajustes necesarios:

1. **Cambiar projectKey:**
   - Debe coincidir con el key en SonarCloud/SonarQube

2. **Si usas SonarCloud, agregar:**
```properties
sonar.organization=tu-organizacion
```

---

## Interpretación de Resultados

### Métricas principales:

- **Coverage**: Porcentaje de código cubierto por pruebas
- **Duplications**: Código duplicado
- **Code Smells**: Problemas de mantenibilidad
- **Bugs**: Errores potenciales
- **Vulnerabilities**: Problemas de seguridad
- **Security Hotspots**: Puntos de atención de seguridad

### Objetivos de calidad:

- ✅ Coverage > 70%
- ✅ Code Smells < 50
- ✅ Bugs = 0
- ✅ Vulnerabilities = 0
- ✅ Duplications < 3%

---

## Troubleshooting

### Error: "sonar-scanner: command not found"
- Verificar que SonarScanner esté en el PATH
- O usar la ruta completa al ejecutable

### Error: "Authentication failed"
- Verificar que el token sea correcto
- Regenerar token si es necesario

### Error: "Project key already exists"
- Cambiar `sonar.projectKey` en `sonar-project.properties`
- O eliminar el proyecto existente en SonarCloud/SonarQube

---

## Recursos Adicionales

- Documentación oficial: https://docs.sonarqube.org/
- SonarCloud docs: https://docs.sonarcloud.io/
- Comunidad: https://community.sonarsource.com/

