# ProgettoCompilatori
Progetto delle prime fasi di un compilatore (Fasi di Analisi) svolto con il collega Alessio Romano.

## Sviluppi Futuri
1. Gestire meglio i caratteri di escape nelle stringhe costanti. Se ad esempio si inserisce un ritorno a capo nel sorgente, questo viene aggiunto erroneamente nella stringa.
Stesso discorso vale anche per le tabulazioni e altri caratteri di escape.

2. Modificare la gestione della comparazione di stringhe. Nel codice C generato bisogna usare le funzione fornite il string.h come strcmp ecc.
Bisogna fare attenzione all'uso di scanf nel generato C.

3. Si potrebbero supportare le operazioni tra procedure con ritorno multiplo. 
Es. la procedura multAddDiff ha la seguente firma: **procedure multAddDiff() int, int, int:**
se nel sorgente abbiamo: **int a, b, c;** e poi **a, b, c := multAddDiff() + multAddDiff();** ciò deve essere permesso

4. Per i ritorni multipli successivi al primo, tenere conto che l'utente potrebbe dichiarare una variabile globale 
con lo stesso nome di quelle usate per i ritorni successivi al primo. Bisognerebbe inserire nella tabella dei simboli tali variabili(?)

5. Aggiungere nei messaggi di errore riga e colonna in cui si verifica l'errore nel file sorgente. Gestire gli errori tramite eccezioni specifiche.

6. Verificare meglio cosa accade quando si annidano istruzioni dello stesso tipo: ad es. un **if** in un **if** ed un **while** in un **while**

7. Revisione generale e utilizzo di ComplexSymbolFactory per i simboli.

Nota: Le librerie esterne utilizzate sono jdom 2.0.6 e java-cup-11b-runtime
