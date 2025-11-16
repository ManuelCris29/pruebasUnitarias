@echo off
echo ========================================
echo   DESCARGANDO JUNIT 5
echo ========================================
echo.

REM Crear carpeta lib si no existe
if not exist "lib" mkdir lib

echo Descargando JUnit Platform Console Standalone...
echo.

REM Descargar JUnit 5
powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.3/junit-platform-console-standalone-1.9.3.jar' -OutFile 'lib\junit-platform-console-standalone-1.9.3.jar'"

if %errorlevel% == 0 (
    echo.
    echo [OK] JUnit 5 descargado exitosamente en lib/
    echo.
    echo Ahora puedes ejecutar las pruebas con: ejecutar-tests.bat
) else (
    echo.
    echo [ERROR] No se pudo descargar JUnit 5
    echo.
    echo Descarga manual desde:
    echo https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.3/junit-platform-console-standalone-1.9.3.jar
    echo.
    echo Y colocalo en la carpeta lib/
)

echo.
pause

