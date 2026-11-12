@echo off
cd /d "%~dp0"
chcp 65001 > nul
echo ===================================================
echo   KHOI DONG UNG DUNG THU VIEN LIBMAN7 (JAVA SWING)
echo ===================================================
call mvn exec:java -Dexec.mainClass="libman.main.MainApp7"
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [LOI] Khong the chay qua Maven. Vui long kiem tra JDK hoac chay truc tiep file MainApp7.java trong IDE!
)
pause

