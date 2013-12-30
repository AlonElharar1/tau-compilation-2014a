@echo off

for /r %%i in (./examples/in/*.ic) do (
	
	echo|set /p=%%~ni : 
	
	java -cp gearley.jar;bin Main ./examples/in/%%~ni.ic -L./examples/in/libic.sig > ./tests/results/%%~ni.3ac
	java -jar ./tests/3ac-emu.jar -vv ./tests/results/%%~ni.3ac > ./tests/results/%%~ni.debug.out
	
	echo done
)

echo Done!