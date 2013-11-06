@echo off

echo ==============
echo %1
echo ==============
java -cp bin Main ./examples/in/%1.ic > ./tests/results/%1.tok
diff -b ./tests/results/%1.tok ./examples/out/%1.tok