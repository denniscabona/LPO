# Laboratorio di LPO, 18 marzo 2025: gestione input ed eccezioni, uso di hash-table, introduzione alle nozioni di controllo sintattico e semantico

## Scopo del laboratorio
Il laboratorio è articolato in due punti, in ordine crescente di
difficoltà.

Il primo è incentrato sulla gestione dell'input e delle eccezioni e sul controllo sintattico mediante uso di espressioni regolari.

Il secondo ha come scopo principale l'apprendimento dell'uso corretto di hash table mediante opportune ridefinizioni dei metodi `equals()` e `hashCode()`.


## Esercizi proposti

1. Il codice di riferimento si trova nei folder `lab07/exercise1` e `lab07/exercise1/parsing`. Completare le classi `Parser` e `LangChecher` che permettono di leggere da un file o dallo standard input un programma scritto in un semplice e incompleto linguaggio e controllarne la sintassi.

   Un programma è costituito da una sequenza anche vuota di istruzioni, ognuna su una linea diversa, non sono ammesse linee vuote.

   Ogni istruzione è costituita dal nome di un'operazione (`call`, `const`, `fun`, `read`, `var`, `write`) seguita da un identificatore, ossia una sequenza non vuota di caratteri alfa-numerici (`_` incluso) che deve iniziare con una lettera minuscola o maiuscola. Nome dell'istruzione e identificatore devono essere separati da almeno un carattere considerato equivalente a uno spazio.

   Opzionalmente, ogni istruzione può essere seguita, sulla stessa linea, da un commento che deve inziare con la stringa `//`.
   ### Classe `Parser`
   Il metodo `readNext()` legge la prossima istruzione del programma tramite un oggetto `reader` e controlla che sia sintatticamente corretta.

   In caso affermativo, stampa su standard output il numero di linea, il tipo dell'istruzione, codificato tramite un opportuno tipo `enum` e il nome dell'identificatore. In caso di errore, viene lanciata un'eccezione di tipo `SyntaxError` associata con il numero di linea.

   Le eccezioni controllate di tipo `IOException` che possono essere sollevate durante la lettura dal `reader` non vengono gestite dal metodo e vengono propagate.

   Il metodo restituisce `true` se un'istruzione è stata letta, `false` se invece è stata raggiunta la fine del file.

   Esempio: l'output per il programma in `tests/test03.txt` dovrebbe essere simile al seguente:
   ```text
   Parser error: Syntax error at line 1
   Parser error: Syntax error at line 2
   Parser error: Syntax error at line 3
   linea: 4 istruzione: CALL identificatore: z1
   linea: 5 istruzione: FUN_DEC identificatore: foo
   linea: 6 istruzione: READ identificatore: foo
   linea: 7 istruzione: WRITE identificatore: foo
   linea: 8 istruzione: VAR_DEC identificatore: foo
   linea: 9 istruzione: FUN_DEC identificatore: foo
   linea: 10 istruzione: CONST_DEC identificatore: x1
   ```
   ### Classe `LangChecker`
   È la classe principale, il metodo `main` permette di gestire l'argomento opzionale passato da linea di comando che corrisponde al nome del file del programma. Se non è fornito allora il programma viene letto dallo standard input.

   Tramite un oggetto parser e il suo metodo `readNext()` vengono lette tutte le istruzioni del programma, dopo di che il file viene chiuso e l'applicazione termina.

   Tutte le possibili eccezioni sollevate sono catturate e i corrispondenti messaggi vengono stampati sullo standard error. Nel caso di eccezioni di tipo `SyntaxError` la lettura continua, per tutte le altre l'esecuzione viene interrotta senza completare la lettura.

1. Completare le classi nel folder `lab07/exercise2` e nei suoi sotto-folder per implementare un semplice controllo sul corretto uso delle variabili in un programma. Le funzioni vengono dichiarate con l'istruzione `fun`, le variabili o costanti con le istruzioni `var` e `const`. Il linguaggio usa uno spazio dei nomi separato per funzioni e varibili (che possono essere anche costanti). È possibile dichiarare una funzione e una variabile (o costante) con lo stesso nome poiché non c'è conflitto dato che lo spazio dei nomi è separato. È un errore di tipo `CheckerError` dichiarare due funzioni con lo stesso nome o due variabili o costanti con lo stesso nome (una costante è semplicemente vista come una variabile di sola lettura). È un errore di tipo `CheckerError` usare in un'istruzione `call` un nome di funzione non ancora dichiarata oppure in una istruzione `read` o `write` un nome di variabile/costante non ancora dichiarata. 
 Esempio: l'output per il programma in `tests/test02.txt` dovrebbe essere simile al seguente:
   ```text
   Checker error: Type error at line 3, function 'c1' not declared
   Checker error: Type error at line 4, function 'z1' not declared
   Checker error: Type error at line 6, variable 'foo' not declared
   Checker error: Type error at line 7, variable 'foo' not declared
   Checker error: Type error at line 9, function 'foo' already declared
   Checker error: Type error at line 10, variable 'x1' already declared
   ```
   ### Classe `Parser`
   Rispetto all'esercizio 1, il metodo `readNext()` non stampa sull'output  le informazioni sulle istruzioni lette ma restituisce un oggetto
   di tipo `InstructionInterface` che rappresenta l'istruzione riconosciuta, creata con la classe record `InstructionRecord`. Se è stata raggiunta
   la fine dell'input allora viene restituito `null`.
   ### Classi `FunName` e `VarName`
   Estendono la classe astratta `NamedElement`, i metodi `equals()` e `hashCode()` vanno ridefiniti correttamente in modo che all'interno di un
   hash table funzioni o variabili con lo stesso nome vengano considerate uguali, mentre funzioni e varibili con lo stesso nome siano considerate
   diverse.
   ```java
   var fn1 = new FunName("foo");
   var fn2 = new FunName("foo");
   var vn1 = new VarName("foo");
   var vn2 = new VarName("foo");
   assert fn1.equals(fn2) && fn1.hashCode()==fn2.hashCode();
   assert vn1.equals(vn2) && vn1.hashCode()==vn2.hashCode();
   assert !fn1.equals(vn1);
   ```
   ### Classe `Environment`
   Viene usata per memorizzare i nomi delle funzioni e variabili/costanti dichiarati nel programma utilizzando un insieme
   che contiene elementi di tipo `NamedElementInterface` e che è implementato tramite una hash table. Vannno completati i due metodi
   della classe, la specifica si trova in `EnvironmentInterface`.
   ### Record `InstructionRecord`
   Rappresenta i vari tipi di istruzione, il significato dei campi è spiegato in `InstructionInterface`. Deve essere completato il metodo `check`
   che, preso l'ambiente corrente `env` delle dichiarazioni, controlla che l'uso di `namedElement` sia corretto, ossia, se l'istruzione è
   una dichiarazione allora non deve essere già stato dichiarato in `env`; invece, se l'istruzione non è una dichiarazione (istruzioni `call`,
   `read` e `write`), allora  `namedElement` deve essere stato dichiarato in `env`.

   In caso di errore, `check()` solleva un'eccezione di tipo `CheckerException` con informazioni su `namedElement`, la linea di codice e il tipo di
   errore.
   ### Classe `LangChecker`
   Va esteso il metodo `main` della versione dell'esercizio 1 in modo che ogni istruzione restituita da `readNext()` venga controllata con il metodo `check()`.
   Come nel caso di `SyntaxError`, dopo aver catturato le eccezioni di tipo `CheckerError` e aver stampato il messaggio di errore sullo standard error,
   la lettura del programma **non** deve essere interrotta.



   

    

   
