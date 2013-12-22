@echo off

for /r %%i in (./examples/in/*.icl) do (
	
	echo|set /p=%%~ni : 
	
	java -cp gearley.jar;bin Main ./examples/in/%%~ni.icl -L./examples/in/libic.sig > ./tests/results/%%~ni.my.sym
	diff -b ./tests/results/%%~ni.my.sym ./examples/out/%%~ni.sym > ./tests/results/%%~ni.diff

	call ./tests/fsize.bat ./tests/results/%%~ni.diff
)

for /r %%i in (./examples/in/*.ic) do (
	
	echo|set /p=%%~ni : 
	
	java -cp gearley.jar;bin Main ./examples/in/%%~ni.ic > ./tests/results/%%~ni.my.sym
	diff -b ./tests/results/%%~ni.my.sym ./examples/out/%%~ni.sym > ./tests/results/%%~ni.diff

	call ./tests/fsize.bat ./tests/results/%%~ni.diff
)

echo|set /p=banner.ice: 
java -cp gearley.jar;bin Main ./examples/in/banner.ice Banner.triangle cool 7 > ./tests/results/banner_Banner.triangle_cool_7.my.out
diff -b ./tests/results/banner_Banner.triangle_cool_7.my.out ./examples/out/banner_Banner.triangle_cool_7.out > ./tests/results/banner.diff
call ./tests/fsize.bat ./tests/results/banner.diff

echo|set /p=fib-array.ice: 
java -cp gearley.jar;bin Main ./examples/in/fib-array.ice Fibonacci.fib 9 > ./tests/results/fib-array_Fibonacci.fib_9.my.out
diff -b ./tests/results/fib-array_Fibonacci.fib_9.my.out ./examples/out/fib-array_Fibonacci.fib_9.out > ./tests/results/fib-array.diff
call ./tests/fsize.bat ./tests/results/fib-array.diff

echo|set /p=gcd.ice: 
java -cp gearley.jar;bin Main ./examples/in/gcd.ice Math.gcd 143 39 > ./tests/results/gcd_Math.gcd_143_39.my.out
diff -b ./tests/results/gcd_Math.gcd_143_39.my.out ./examples/out/gcd_Math.gcd_143_39.out > ./tests/results/gcd.diff
call ./tests/fsize.bat ./tests/results/gcd.diff

echo Done!