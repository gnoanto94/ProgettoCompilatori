@ECHO OFF
set str=%1
::set str=%str:test_files/=%
set str=%str:.toy=%

echo Cancello i file precedenti
del compiler_output\%str%.exe
del compiler_output\%str%_SymbolTable.txt
del generated_c_files\%str%.c
del xml_files\%1%.xml
echo Traduco il sorgente %str% in C
java -jar out/artifacts/ToyCompiler/ToyCompiler.jar test_files/%1
echo Compilo il file %str%.c
clang -pthread -lm -o compiler_output/%str%.exe generated_c_files/%str%.c
