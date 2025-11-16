@echo off
echo ========================================
echo   EJECUTANDO PRUEBAS UNITARIAS
echo ========================================
echo.

REM Verificar si existe JUnit
if not exist "lib\junit-platform-console-standalone-1.9.3.jar" (
    echo [ERROR] JUnit 5 no encontrado en lib/
    echo.
    echo Por favor descarga JUnit Platform Console Standalone:
    echo https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.3/junit-platform-console-standalone-1.9.3.jar
    echo.
    echo Colocalo en la carpeta lib/
    pause
    exit /b 1
)

echo [1/3] Compilando codigo fuente...
javac -d out -encoding UTF-8 src\Main.java src\model\*.java src\exception\*.java src\validator\*.java src\util\*.java src\repository\*.java src\service\*.java src\ui\*.java

if %errorlevel% neq 0 (
    echo [ERROR] Error al compilar codigo fuente
    pause
    exit /b 1
)

echo [2/3] Compilando pruebas unitarias...
javac -d out -cp "out;lib\junit-platform-console-standalone-1.9.3.jar" -encoding UTF-8 test\repository\*.java test\service\*.java test\validator\*.java

if %errorlevel% neq 0 (
    echo [ERROR] Error al compilar pruebas
    pause
    exit /b 1
)

echo [3/3] Ejecutando pruebas...
echo.
java -jar lib\junit-platform-console-standalone-1.9.3.jar --class-path out --scan-class-path

echo.
echo ========================================
echo   PRUEBAS COMPLETADAS
echo ========================================
pause

