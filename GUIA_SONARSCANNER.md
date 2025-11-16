# 🔍 Guía Completa: Cómo Ejecutar SonarScanner

## 📋 ¿Qué es SonarScanner?

SonarScanner es la herramienta que analiza tu código y envía los resultados a SonarQube/SonarCloud para ver la calidad del código.

---

## 🚀 MÉTODO RÁPIDO (Con Script)

### Paso 1: Ejecutar el script

```bash
ejecutar-sonar.bat TU_TOKEN_AQUI
```

**Ejemplo:**
```bash
ejecutar-sonar.bat abc123def456ghi789
```

---

## 📝 MÉTODO MANUAL (Paso a Paso)

### Opción A: SonarCloud (Recomendado - Más Fácil)

#### 1. Crear cuenta en SonarCloud

1. Ve a: https://sonarcloud.io/
2. Click en "Log in" (arriba derecha)
3. Inicia sesión con tu cuenta de **GitHub**

#### 2. Crear proyecto en SonarCloud

1. Click en **"+"** (arriba derecha) → **"Create Project"**
2. Selecciona **"Manually"** (análisis manual)
3. Completa:
   - **Project Key**: `crud-vehiculos` (o el que prefieras)
   - **Display Name**: `CRUD Vehículos`
4. Click en **"Set Up"**

#### 3. Obtener Token

1. Click en tu avatar (arriba derecha) → **"My Account"**
2. Ve a la pestaña **"Security"**
3. En **"Generate Tokens"**, escribe un nombre (ej: "crud-vehiculos")
4. Click en **"Generate"**
5. **COPIA EL TOKEN** (solo se muestra una vez, guárdalo bien)

#### 4. Configurar sonar-project.properties

Abre `sonar-project.properties` y cambia:

```properties
# Si tu proyecto en SonarCloud tiene key: tu-usuario_crud-vehiculos
sonar.projectKey=tu-usuario_crud-vehiculos

# Si usas SonarCloud, agrega también:
sonar.organization=tu-usuario
```

**Ejemplo:**
```properties
sonar.projectKey=manuelp_crud-vehiculos
sonar.organization=manuelp
```

#### 5. Instalar SonarScanner

**Windows:**

1. Descargar: https://docs.sonarqube.org/latest/analysis/scan/sonarscanner/
   - Busca "SonarScanner for Windows"
   - Descarga el ZIP

2. Extraer:
   - Extrae el ZIP en `C:\sonar-scanner\` (o donde prefieras)

3. Agregar al PATH:
   - Click derecho en "Este equipo" → "Propiedades"
   - "Configuración avanzada del sistema"
   - "Variables de entorno"
   - En "Variables del sistema", busca "Path" → "Editar"
   - "Nuevo" → Agrega: `C:\sonar-scanner\bin`
   - "Aceptar" en todo

4. Verificar instalación:
   ```bash
   sonar-scanner -v
   ```
   Debe mostrar la versión.

#### 6. Ejecutar SonarScanner

Abre PowerShell o CMD en la carpeta del proyecto:

```bash
cd C:\Users\ManuelP\Desktop\crud
sonar-scanner -Dsonar.login=TU_TOKEN_AQUI
```

**Reemplaza `TU_TOKEN_AQUI` con el token que copiaste en el paso 3.**

---

### Opción B: SonarQube Local (Más Complejo)

#### 1. Descargar SonarQube

1. Ve a: https://www.sonarqube.org/downloads/
2. Descarga **"Community Edition"** (gratis)

#### 2. Instalar SonarQube

1. Extrae el ZIP
2. Ve a la carpeta `bin/windows-x86-64/`
3. Ejecuta `StartSonar.bat`
4. Espera a que inicie (verás "SonarQube is operational")

#### 3. Acceder a SonarQube

1. Abre navegador: http://localhost:9000
2. Usuario: `admin`
3. Contraseña: `admin` (te pedirá cambiarla)

#### 4. Crear proyecto

1. Click en **"Projects"** → **"Create Project"**
2. Selecciona **"Manually"**
3. Completa:
   - **Project Key**: `crud-vehiculos`
   - **Display Name**: `CRUD Vehículos`
4. Click en **"Set Up"**

#### 5. Generar Token

1. Click en tu avatar → **"My Account"**
2. Pestaña **"Security"**
3. Genera un token
4. **COPIA EL TOKEN**

#### 6. Instalar SonarScanner

(Sigue los mismos pasos que en Opción A, paso 5)

#### 7. Ejecutar SonarScanner

```bash
sonar-scanner -Dsonar.login=TU_TOKEN_AQUI
```

---

## 🎯 Ejecución Rápida (Resumen)

### Si ya tienes todo configurado:

```bash
# 1. Ir al directorio del proyecto
cd C:\Users\ManuelP\Desktop\crud

# 2. Ejecutar SonarScanner con tu token
sonar-scanner -Dsonar.login=TU_TOKEN_AQUI
```

### O usar el script:

```bash
ejecutar-sonar.bat TU_TOKEN_AQUI
```

---

## ✅ Verificar que Funciona

Después de ejecutar, deberías ver:

```
INFO: Analysis total time: X.XXX s
INFO: ANALYSIS SUCCESSFUL
```

Luego ve a:
- **SonarCloud**: https://sonarcloud.io/projects
- **SonarQube Local**: http://localhost:9000/projects

---

## 🔧 Solución de Problemas

### Error: "sonar-scanner: command not found"

**Solución:** SonarScanner no está en el PATH
- Agrega `C:\sonar-scanner\bin` al PATH del sistema
- O ejecuta desde la carpeta `bin/` directamente:
  ```bash
  C:\sonar-scanner\bin\sonar-scanner.bat -Dsonar.login=TU_TOKEN
  ```

### Error: "Authentication failed"

**Solución:** Token incorrecto o expirado
- Genera un nuevo token en SonarCloud/SonarQube
- Asegúrate de copiarlo completo

### Error: "Project key already exists"

**Solución:** El projectKey ya está en uso
- Cambia `sonar.projectKey` en `sonar-project.properties`
- O elimina el proyecto existente en SonarCloud/SonarQube

---

## 📊 Qué Verás Después del Análisis

En SonarCloud/SonarQube verás:

- ✅ **Bugs**: Errores encontrados
- ✅ **Vulnerabilities**: Problemas de seguridad
- ✅ **Code Smells**: Problemas de mantenibilidad
- ✅ **Coverage**: Cobertura de pruebas
- ✅ **Duplications**: Código duplicado
- ✅ **Issues**: Todos los problemas encontrados

---

## 💡 Consejos

1. **Ejecuta SonarScanner después de cada cambio importante**
2. **Revisa los "Issues" y corrígelos**
3. **Apunta a tener 0 Bugs y 0 Vulnerabilities**
4. **Intenta tener más del 70% de cobertura de código**

---

## 🎓 Para tu Proyecto Académico

Si solo necesitas cumplir el requisito:

1. **Crea cuenta en SonarCloud** (5 minutos)
2. **Crea proyecto** (2 minutos)
3. **Obtén token** (1 minuto)
4. **Ejecuta**: `sonar-scanner -Dsonar.login=TOKEN` (2 minutos)
5. **Toma captura de pantalla** del reporte
6. **Listo** ✅

---

¿Necesitas ayuda con algún paso específico?

