@echo off
echo ========================================
echo   EJECUTANDO SONARSCANNER PARA SONARCLOUD
echo ========================================
echo.

REM Configuración de SonarCloud
set SONAR_ORG=manuelcris29
set SONAR_PROJECT_KEY=manuelcris29_crud

REM El token debe configurarse como variable de entorno o pasarse como parámetro
REM Para configurar: set SONAR_TOKEN=tu_token_aqui
REM O pasar como parámetro: ejecutar-sonarcloud.bat tu_token_aqui
if "%1"=="" (
    if "%SONAR_TOKEN%"=="" (
        echo [ERROR] No se proporcionó token de SonarCloud
        echo.
        echo Uso: ejecutar-sonarcloud.bat TU_TOKEN
        echo   O configura la variable de entorno: set SONAR_TOKEN=tu_token
        echo.
        pause
        exit /b 1
    )
) else (
    set SONAR_TOKEN=%1
)

echo [INFO] Organización: %SONAR_ORG%
echo [INFO] Project Key: %SONAR_PROJECT_KEY%
echo.

REM Verificar si SonarScanner está en la carpeta del proyecto
if exist "sonar-scanner-7.2.0.5079-windows-x64\bin\sonar-scanner.bat" (
    echo [OK] SonarScanner encontrado en el proyecto
    echo.
    echo Ejecutando análisis...
    echo.
    "sonar-scanner-7.2.0.5079-windows-x64\bin\sonar-scanner.bat" ^
        -D"sonar.login=%SONAR_TOKEN%" ^
        -D"sonar.organization=%SONAR_ORG%" ^
        -D"sonar.projectKey=%SONAR_PROJECT_KEY%"
) else (
    REM Intentar usar sonar-scanner del PATH
    where sonar-scanner.bat >nul 2>&1
    if %errorlevel% equ 0 (
        echo [OK] SonarScanner encontrado en el PATH
        echo.
        echo Ejecutando análisis...
        echo.
        sonar-scanner.bat ^
            -D"sonar.login=%SONAR_TOKEN%" ^
            -D"sonar.organization=%SONAR_ORG%" ^
            -D"sonar.projectKey=%SONAR_PROJECT_KEY%"
    ) else (
        echo [ERROR] SonarScanner no encontrado
        echo.
        echo Por favor:
        echo 1. Asegúrate de que SonarScanner esté instalado
        echo 2. O ejecuta desde la carpeta sonar-scanner-7.2.0.5079-windows-x64\bin\
        echo.
        pause
        exit /b 1
    )
)

echo.
echo ========================================
echo   ANÁLISIS COMPLETADO
echo ========================================
echo.
echo Revisa los resultados en:
echo https://sonarcloud.io/project/overview?id=%SONAR_PROJECT_KEY%
echo.
pause

