#!/bin/bash
name=$1 + ".exe"
java -jar ToyCompiler.jar $1
clang -pthread -lm -o $name $1
