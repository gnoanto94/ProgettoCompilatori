#!/bin/bash
rm src/syntaxanalysis/parser.java
rm src/syntaxanalysis/sym.java
rm dump.txt
java -jar ../CUP/java-cup-11b.jar -dump -destdir src/syntaxanalysis srcjflexcup/parser.cup 2>dump.txt
