echo ==============
echo %1
echo ==============
java -cp bin Main ./examples/in/%TESTFILE%.ic > ./tests/results/%1.tok
diff ./tests/results/%1.tok ./examples/out/%1.tok