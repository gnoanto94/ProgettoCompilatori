#!/bin/bash
name=${1/.toy}
name=${name/test_files\/}
java -jar out/artifacts/ToyCompiler/ToyCompiler.jar $1
clang -pthread -lm -o compiler_output/$name generated_c_files/$name.c
