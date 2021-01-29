#!/bin/bash
echo "Exp: Compilazione Corretta"
./Toy2C.sh factorial.toy
./Toy2C.sh expressions.toy
./Toy2C.sh hanoi.toy
./Toy2C.sh mat.toy
./Toy2C.sh multAddDiff.toy
./Toy2C.sh shoot.toy
./Toy2C.sh temperatures.toy
#Test di file con errori
echo "***************************************************************************************"
echo "Exp: Commento non chiuso"
./Toy2C.sh lexical_error_1.toy
echo "Exp: Stringa costante non chiusa"
./Toy2C.sh lexical_error_2.toy
echo "Exp: Mancata chiusura di uno statement if"
./Toy2C.sh syntax_error_1.toy
echo "Exp: Condizione del while mancante"
./Toy2C.sh syntax_error_2.toy
echo "Exp: utilizzo di procedure non dichiarate all'interno del codice"
./Toy2C.sh semantic_error_1.toy
echo "Exp: utilizzo di variabili non dichiarate all'interno del codice"
./Toy2C.sh semantic_error_2.toy
echo "Exp: type mismatch in un'assegnazione"
./Toy2C.sh semantic_error_3.toy
echo "Exp: numero di argomenti in un'assegnazione multipla non coincide"
./Toy2C.sh semantic_error_4.toy
echo "Exp: type mismatch in un'assegnazione multipla"
./Toy2C.sh semantic_error_5.toy
echo "Exp: condizione (non booleana) all'interno dell'if"
./Toy2C.sh semantic_error_6.toy
echo "Exp: condizione (non booleana) all'interno dell'elif"
./Toy2C.sh semantic_error_7.toy
echo "Exp: utilizzo di variabili non dichiarate all'interno di una readln"
./Toy2C.sh semantic_error_8.toy
echo "Exp: utilizzo di procedure con valore di ritorno void all'interno di una write"
./Toy2C.sh semantic_error_9.toy
echo "Exp: numero di argomenti dopo il return diverso da quello definiti nella firma della procedura stessa"
./Toy2C.sh semantic_error_10.toy
echo "Exp: definizione di una procedura in cui i tipi di ritorno restituiti non corrispondono a quelli nella firma"
./Toy2C.sh semantic_error_11.toy
echo "Exp: procedura in cui i tipi di ritorno sono stati definiti, ma non viene indicato il valore di ritorno alla fine del corpo procedura"
./Toy2C.sh semantic_error_12.toy
echo "Exp: definizione di due procedure main"
./Toy2C.sh semantic_error_13.toy
echo "Exp: utilizzo di tipi non validi all'interno di operatori logici."
./Toy2C.sh semantic_error_14.toy
echo "Exp: divisione di un intero per 0 all'interno del programma."
./Toy2C.sh semantic_error_15.toy
echo "Exp: il numero di parametri formali e attuali non coincide"
./Toy2C.sh semantic_error_16.toy
echo "Exp: i parametri attuali non hanno lo stesso tipo di quelli formali"
./Toy2C.sh semantic_error_17.toy
echo "Exp: non è possibile effettuare un'operazione con operando void"
./Toy2C.sh semantic_error_18.toy
echo "Exp: write non accetta valori null"
./Toy2C.sh semantic_error_19.toy
echo "Exp: impossibile eseguire operazione binaria con entrambi gli operandi null"
./Toy2C.sh semantic_error_20.toy
echo "Exp: tipo non valido per l'operando (null)"
./Toy2C.sh semantic_error_21.toy
echo "Exp: l'operazione unaria NON accetta come operando un valore null"
./Toy2C.sh semantic_error_22.toy
echo "Exp: Procedura già dichiarata"
./Toy2C.sh semantic_error_23.toy