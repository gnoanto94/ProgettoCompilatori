@ECHO OFF
set str=%1
::set str=%str:test_files/=%
set str=%str:.toy=%

java -jar out/artifacts/ToyCompiler/ToyCompiler.jar test_files/%1
clang -pthread -lm -o compiler_output/%str%.exe generated_c_files/%str%.c
