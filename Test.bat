echo Exp: Compilazione Corretta
call call Toy2C.bat factorial.toy
call call Toy2C.bat expressions.toy
call call Toy2C.bat hanoi.toy
call call Toy2C.bat mat.toy
call call Toy2C.bat multAddDiff.toy
call call Toy2C.bat shoot.toy
call call Toy2C.bat temperatures.toy
::Test di file con errori
echo ***************************************************************************************
echo Exp: Commento non chiuso
call Toy2C.bat lexical_error_1.toy
echo Exp: Stringa costante non chiusa
call Toy2C.bat lexical_error_2.toy
echo Exp: Mancata chiusura di uno statement if
call Toy2C.bat syntax_error_1.toy
echo Exp: Condizione del while mancante
call Toy2C.bat syntax_error_2.toy
echo Exp: utilizzo di procedure non dichiarate all'interno del codice
call Toy2C.bat semantic_error_1.toy
echo Exp: utilizzo di variabili non dichiarate all'interno del codice
call Toy2C.bat semantic_error_2.toy
echo Exp: type mismatch in un'assegnazione
call Toy2C.bat semantic_error_3.toy
echo Exp: numero di argomenti in un'assegnazione multipla
call Toy2C.bat semantic_error_4.toy
echo Exp: type mismatch in un'assegnazione multipla
call Toy2C.bat semantic_error_5.toy
echo Exp: condizione (non booleana) all'interno dell'"if"
call Toy2C.bat semantic_error_6.toy
echo Exp: condizione (non booleana) all'interno dell'elif
call Toy2C.bat semantic_error_7.toy
echo Exp: utilizzo di variabili non dichiarate all'interno di una readln
call Toy2C.bat semantic_error_8.toy
echo Exp: utilizzo di procedure con valore di ritorno void all'interno di una write
call Toy2C.bat semantic_error_9.toy
echo Exp: numero di argomenti dopo il return diverso da quello definiti nella firma della procedura stessa
call Toy2C.bat semantic_error_10.toy
echo Exp: definizione di una procedura in cui i tipi di ritorno restituiti non corrispondono a quelli nella firma
call Toy2C.bat semantic_error_11.toy
echo Exp: procedura in cui i tipi di ritorno sono stati definiti, ma non viene indicato il valore di ritorno alla fine del corpo procedura
call Toy2C.bat semantic_error_12.toy
echo Exp: definizione di due procedure main
call Toy2C.bat semantic_error_13.toy
echo Exp: utilizzo di tipi non validi all'interno di operatori logici.
call Toy2C.bat semantic_error_14.toy
echo Exp: divisione di un intero per 0 all'interno del programma.
call Toy2C.bat semantic_error_15.toy
echo Exp: il numero di parametri formali e attuali non coincide
call Toy2C.bat semantic_error_16.toy
echo Exp: i parametri attuali non hanno lo stesso tipo di quelli formali
call Toy2C.bat semantic_error_17.toy
echo Exp: non è possibile effettuare un'operazione con operando void
call Toy2C.bat semantic_error_18.toy
echo Exp: write non accetta valori null
call Toy2C.bat semantic_error_19.toy
echo Exp: impossibile eseguire operazione binaria con entrambi gli operandi null
call Toy2C.bat semantic_error_20.toy
echo Exp: tipo non valido per l'operando (null)
call Toy2C.bat semantic_error_21.toy
echo Exp: l'operazione unaria NON accetta come operando un valore null
call Toy2C.bat semantic_error_22.toy