# 📤 Guía para Publicar en GitHub

## Pasos para Subir el Proyecto a GitHub

### 1. Crear Repositorio en GitHub

1. **Iniciar sesión en GitHub:**
   - Ir a: https://github.com
   - Iniciar sesión o crear cuenta

2. **Crear nuevo repositorio:**
   - Click en el botón "+" (arriba derecha)
   - Seleccionar "New repository"
   - **Nombre del repositorio**: `crud-vehiculos` (o el que prefieras)
   - **Descripción**: "Sistema CRUD de Vehículos con Principios SOLID"
   - **Visibilidad**: Public (para compartir con el docente) o Private
   - **NO marcar** "Initialize with README" (ya tenemos uno)
   - Click en "Create repository"

### 2. Inicializar Git en el Proyecto Local

Abre PowerShell o Terminal en la carpeta del proyecto:

```bash
# Ir al directorio del proyecto
cd C:\Users\ManuelP\Desktop\crud

# Inicializar repositorio Git
git init

# Agregar todos los archivos
git add .

# Hacer commit inicial
git commit -m "Initial commit: CRUD de Vehículos con Principios SOLID"
```

### 3. Conectar con GitHub

```bash
# Agregar el repositorio remoto (reemplaza TU_USUARIO con tu usuario de GitHub)
git remote add origin https://github.com/TU_USUARIO/crud-vehiculos.git

# Verificar que se agregó correctamente
git remote -v
```

### 4. Subir el Código

```bash
# Cambiar a la rama main (si es necesario)
git branch -M main

# Subir el código
git push -u origin main
```

Si te pide autenticación:
- **Usuario**: Tu usuario de GitHub
- **Contraseña**: Usa un **Personal Access Token** (no tu contraseña)
  - Crear token: GitHub > Settings > Developer settings > Personal access tokens > Generate new token
  - Seleccionar scope: `repo`
  - Copiar el token y usarlo como contraseña

### 5. Verificar en GitHub

- Ir a tu repositorio en GitHub
- Verificar que todos los archivos estén presentes
- Verificar que el README.md se muestre correctamente

---

## Compartir con el Docente

### Opción 1: Compartir URL del Repositorio

1. Ir a tu repositorio en GitHub
2. Click en "Settings" (Configuración)
3. Scroll down hasta "Collaborators"
4. Click en "Add people"
5. Ingresar el email: `jonathansanchez2948@correo.itm.edu.co`
6. Seleccionar permiso: "Read" (solo lectura)
7. Enviar invitación

### Opción 2: Enviar URL por Email

Enviar un email al docente con:
- **Asunto**: "Proyecto CRUD - [Tu Nombre]"
- **Contenido**:
```
Estimado Profesor Jonathan Sánchez,

Adjunto el enlace a mi repositorio de GitHub con el proyecto CRUD de Vehículos:

URL: https://github.com/TU_USUARIO/crud-vehiculos

El proyecto incluye:
- Implementación completa del CRUD
- Pruebas unitarias con JUnit 5
- Análisis de calidad con SonarQube
- Documentación completa

Quedo atento a sus comentarios.

Saludos,
[Tu Nombre]
```

---

## Estructura de Commits Recomendada

Para mantener un historial limpio:

```bash
# Commit inicial
git commit -m "Initial commit: CRUD de Vehículos con Principios SOLID"

# Si haces cambios, usa mensajes descriptivos:
git commit -m "feat: Agregar pruebas unitarias para Repository"
git commit -m "test: Agregar pruebas para validadores"
git commit -m "docs: Actualizar README con instrucciones de SonarQube"
git commit -m "fix: Corregir validación de cilindrada en MotoValidator"
```

### Convenciones de commits:
- `feat:` Nueva funcionalidad
- `fix:` Corrección de bug
- `test:` Agregar o modificar pruebas
- `docs:` Cambios en documentación
- `refactor:` Refactorización de código
- `style:` Cambios de formato (espacios, etc.)

---

## Actualizar el Repositorio

Si haces cambios después de subir:

```bash
# Ver cambios
git status

# Agregar archivos modificados
git add .

# Hacer commit
git commit -m "Descripción de los cambios"

# Subir cambios
git push
```

---

## Verificar Archivos Subidos

Asegúrate de que estos archivos estén en GitHub:

✅ `src/` - Todo el código fuente
✅ `test/` - Pruebas unitarias
✅ `README.md` - Documentación
✅ `sonar-project.properties` - Configuración SonarQube
✅ `.gitignore` - Archivos ignorados
✅ `INSTRUCCIONES_SONARQUBE.md` - Guía de SonarQube
✅ `INSTRUCCIONES_GITHUB.md` - Esta guía

❌ `out/` - NO debe subirse (está en .gitignore)
❌ `data/*.csv` - NO debe subirse (está en .gitignore)
❌ `*.class` - NO debe subirse (está en .gitignore)

---

## Troubleshooting

### Error: "remote origin already exists"
```bash
# Eliminar el remoto existente
git remote remove origin

# Agregar nuevamente
git remote add origin https://github.com/TU_USUARIO/crud-vehiculos.git
```

### Error: "failed to push some refs"
```bash
# Primero hacer pull
git pull origin main --allow-unrelated-histories

# Luego push
git push -u origin main
```

### Error: "authentication failed"
- Verificar que estés usando un Personal Access Token, no tu contraseña
- Crear nuevo token si es necesario

---

## Recursos Adicionales

- Guía oficial de Git: https://git-scm.com/doc
- Guía de GitHub: https://guides.github.com/
- Markdown para README: https://www.markdownguide.org/

