#!/bin/bash
name=${1/.toy}
#name=${name/test_files\/}
echo "Cancello i file precedenti"
rm compiler_output/$name
rm compiler_output/$name"_SymbolTable.txt"
rm generated_c_files/$name.c
rm xml_files/$1.xml
echo "" # stampo una riga vuota
echo "Traduco il sorgente "$1" in C"
# 2>&1 serve a reindirizzare stderr a stdout
java -jar out/artifacts/ToyCompiler/ToyCompiler.jar test_files/$1 2>&1
echo "" # stampo una riga vuota
echo "Compilo il file "$name.c
clang -pthread -lm -o compiler_output/$name generated_c_files/$name.c 2>&1
