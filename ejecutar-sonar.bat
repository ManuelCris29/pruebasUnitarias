@echo off
echo ========================================
echo   EJECUTANDO SONARSCANNER
echo ========================================
echo.

REM Verificar si SonarScanner está instalado
where sonar-scanner >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] SonarScanner no está instalado o no está en el PATH
    echo.
    echo Por favor:
    echo 1. Descarga SonarScanner desde:
    echo    https://docs.sonarqube.org/latest/analysis/scan/sonarscanner/
    echo.
    echo 2. Extrae el archivo ZIP
    echo.
    echo 3. Agrega la carpeta bin/ al PATH del sistema
    echo    O ejecuta desde la carpeta bin/ directamente
    echo.
    pause
    exit /b 1
)

echo [OK] SonarScanner encontrado
echo.

REM Verificar si existe sonar-project.properties
if not exist "sonar-project.properties" (
    echo [ERROR] No se encontró sonar-project.properties
    echo.
    pause
    exit /b 1
)

echo [OK] Archivo sonar-project.properties encontrado
echo.

REM Verificar si se proporcionó el token
if "%1"=="" (
    echo [ADVERTENCIA] No se proporcionó token de autenticación
    echo.
    echo Uso: ejecutar-sonar.bat TU_TOKEN_AQUI
    echo.
    echo O si usas SonarCloud, ejecuta:
    echo   ejecutar-sonar.bat TU_TOKEN_SONARCLOUD
    echo.
    echo Para obtener el token:
    echo - SonarCloud: My Account ^> Security ^> Generate Token
    echo - SonarQube Local: My Account ^> Security ^> Generate Token
    echo.
    set /p TOKEN="Ingresa tu token (o presiona Enter para continuar sin token): "
    if "!TOKEN!"=="" (
        echo.
        echo Ejecutando sin token (solo funciona si SonarQube está configurado localmente)...
        echo.
        sonar-scanner
    ) else (
        echo.
        echo Ejecutando con token...
        echo.
        sonar-scanner -Dsonar.login=%TOKEN%
    )
) else (
    echo Ejecutando SonarScanner con token proporcionado...
    echo.
    sonar-scanner -Dsonar.login=%1
)

echo.
echo ========================================
echo   ANÁLISIS COMPLETADO
echo ========================================
echo.
echo Revisa los resultados en:
echo - SonarCloud: https://sonarcloud.io/
echo - SonarQube Local: http://localhost:9000
echo.
pause

