@echo off

for /r %%i in (./examples/in/*.ic) do (
	
	echo|set /p=%%~ni : 
	
	java -cp gearley.jar;bin Main ./examples/in/%%~ni.ic > ./tests/results/%%~ni.sym
	diff -b ./tests/results/%%~ni.sym ./examples/out/%%~ni.sym > ./tests/results/%%~ni.diff

	call ./tests/fsize.bat ./tests/results/%%~ni.diff
)

echo Done!