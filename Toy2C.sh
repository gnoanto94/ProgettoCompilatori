#!/bin/bash
name=${1/.toy}
#name=${name/test_files\/}
java -jar out/artifacts/ToyCompiler/ToyCompiler.jar test_files/$1
clang -pthread -lm -o compiler_output/$name generated_c_files/$name.c
