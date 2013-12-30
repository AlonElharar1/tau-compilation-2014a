@echo off

for /r %%i in (./examples/in/*.ic) do (
	
	echo|set /p=%%~ni : 
	
	java -cp gearley.jar;bin Main ./examples/in/%%~ni.ic -L./examples/in/libic.sig > ./tests/results/%%~ni.3ac
	java -jar ./tests/3ac-emu.jar -q ./tests/results/%%~ni.3ac > ./tests/results/%%~ni.my.out
	diff -b ./tests/results/%%~ni.my.out ./examples/out/%%~ni.out > ./tests/results/%%~ni.diff

	call ./tests/fsize.bat ./tests/results/%%~ni.diff
)

echo Done!