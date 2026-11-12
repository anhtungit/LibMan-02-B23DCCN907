@echo off
cd /d "%~dp0"
chcp 65001 > nul

echo ===================================================
echo   KHOI DONG UNG DUNG THU VIEN LIBMAN7 (JAVA SWING)
echo ===================================================

:: 1. Kiem tra va uu tien chay qua Maven Wrapper (khong can cai dat Maven)
if exist "%~dp0mvnw.cmd" (
    echo [THONG TIN] Dang khoi dong qua Maven Wrapper...
    call "%~dp0mvnw.cmd" exec:java -Dexec.mainClass="libman.main.MainApp7"
    goto end
)

:: 2. Kiem tra Maven cai san tren he thong
where mvn >nul 2>nul
if %ERRORLEVEL% EQU 0 (
    echo [THONG TIN] Dang khoi dong qua Maven he thong...
    call mvn exec:java -Dexec.mainClass="libman.main.MainApp7"
    goto end
)

:: 3. Kiem tra chay truc tiep file JAR tat-ca-trong-mot neu co
if exist "%~dp0target\LibMan7_Desktop-1.0-SNAPSHOT-jar-with-dependencies.jar" (
    echo [THONG TIN] Dang khoi dong qua file JAR dong goi san...
    java -jar "%~dp0target\LibMan7_Desktop-1.0-SNAPSHOT-jar-with-dependencies.jar"
    goto end
)

echo [LOI] Khong tim thay Maven hoac file JAR de khoi dong.
echo Vui long mo project trong IDE (NetBeans/IntelliJ/Eclipse) va chay file MainApp7.java!

:end
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [CHU Y] Chuong trinh da ket thuc voi ma loi %ERRORLEVEL%.
)
pause
