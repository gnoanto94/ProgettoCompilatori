@ECHO OFF
set str=%1
::set str=%str:test_files/=%
set str=%str:.toy=%
del compiler_output\%name%.exe
del compiler_output\%name%_SymbolTable.txt
del generated_c_files\%name%.c
del xml_files\%1%.xml
java -jar out/artifacts/ToyCompiler/ToyCompiler.jar test_files/%1
clang -pthread -lm -o compiler_output/%str%.exe generated_c_files/%str%.c
