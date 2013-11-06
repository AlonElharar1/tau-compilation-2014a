@echo off

for /r %%i in (./examples/in/*.ic) do /tests/test.bat %%~ni