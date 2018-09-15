@echo off & setlocal enabledelayedexpansion

set LIB_JARS=""
cd .\lib
for %%i in (*) do set LIB_JARS=!LIB_JARS!%%i
cd ..\

java -server -jar lib/%LIB_JARS% --spring.profiles.active=prod --spring.config.location=conf
goto end

:end
pause