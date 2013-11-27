@echo off

echo|set /p=libc.sig : 
java -cp gearley.jar;bin Main -L ./examples/in/libic.sig > ./tests/results/libic.ast
diff -b ./tests/results/libic.ast ./examples/out/libic.ast > ./tests/results/libic.diff

call ./tests/fsize.bat ./tests/results/libic.diff

for /r %%i in (./examples/in/*.ic) do (
	
	echo|set /p=%%~ni : 
	
	java -cp gearley.jar;bin Main ./examples/in/%%~ni.ic > ./tests/results/%%~ni.ast
	diff -b ./tests/results/%%~ni.ast ./examples/out/%%~ni.ast > ./tests/results/%%~ni.diff

	call ./tests/fsize.bat ./tests/results/%%~ni.diff
)

echo Done!