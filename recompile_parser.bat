del /p C:\Users\Alessio\IdeaProjects\Romano-Antonucci_es5_scg\src\syntaxanalysis\parser.java
del /p C:\Users\Alessio\IdeaProjects\Romano-Antonucci_es5_scg\src\syntaxanalysis\sym.java
del /p C:\Users\Alessio\IdeaProjects\Romano-Antonucci_es5_scg\dump.txt

java -jar C:\CUP\java-cup-11b.jar -dump -destdir src\syntaxanalysis srcjflexcup\parser.cup 2>dump.txt