@echo off

for /r %%i in (./examples/in/*.ic) do (
	
	echo|set /p=%%~ni : 
	
	java -cp bin Main ./examples/in/%%~ni.ic > ./tests/results/%%~ni.tok
	diff -b ./tests/results/%%~ni.tok ./examples/out/%%~ni.tok > ./tests/results/%%~ni.diff

	call ./tests/fsize.bat ./tests/results/%%~ni.diff
)

