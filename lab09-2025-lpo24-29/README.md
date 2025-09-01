# Laboratorio di LPO, 10 aprile 2025: implementazione di un interprete con visitor pattern

### Scopo del laboratorio
Quest'attività di laboratorio è dedicata all'implementazione 
della semantica statica e dinamica del linguaggio di programmazione di cui è stato sviluppato il parser nel laboratorio precedente. 

Le visite dell'albero della sintassi astratta (AST) generato dal parser, necessarie per implementare l'interprete, sono realizzate utilizzando il
*visitor pattern* (vedere [la lezione](https://2024.aulaweb.unige.it/mod/resource/view.php?id=132573) e il [codice di esempio](https://2024.aulaweb.unige.it/mod/folder/view.php?id=132635)).

## Esercizio
**Importante**: prima di iniziare a sviluppare il codice è consigliabile consultare la [documentazione  del codice di partenza](#documentazione).

Il codice di partenza [`projectLabo`](https://github.com/LPOatDIBRIS/lab09-2025/tree/main/projectLabo) implementa il parser guidato dalla grammatica
```
Prog ::= StmtSeq EOF
StmtSeq ::= Stmt (STMT_SEP StmtSeq)?
Stmt ::= VAR? IDENT ASSIGN Exp | PRINT Exp | IF OPEN_PAR Exp CLOSE_PAR Block (ELSE Block)? | ASSERT Exp
Block ::= OPEN_BLOCK StmtSeq CLOSE_BLOCK
Exp ::= And (PAIR_OP And)*
And ::= Eq (AND Eq)*
Eq ::= Add (EQ Add)*
Add ::= Mul (PLUS Mul)*
Mul::= Atom (TIMES Atom)*
Atom ::= FST Atom | SND Atom | MINUS Atom | NOT Atom | BOOL | NUM | IDENT | OPEN_PAR Exp CLOSE_PAR
```
e la [semantica statica (typechecking) e dinamica (esecuzione)](https://github.com/LPOatDIBRIS/lab09-2025/tree/main/semantics/projectLabo) di tutti gli elementi del linguaggio, eccetto i seguenti statement e operatori:
* lo statement di assegnazione
* lo statement di asserzione
* l'operatore logico `AND`
* l'operatore logico `NOT` 

### Linee guida per il completamento dell'esercizio 
1. metodo `<T> T accept(Visitor<T>)` e interfaccia `Visitor<T>`
   
   Aggiungere in `Visitor<T>` i metodi di visita mancanti e completare la definizione del metodo `<T> T accept(Visitor<T>)` nelle classi `AssertStmt`, `AssignStmt`, `And` e `Not`. 

1. `Typecheck`

   Implementare nella classe `Typecheck` i metodi di visita che sono stati aggiunti in `Visitor<T>` al punto 1. e che implementano la semantica statica degli statement di assegnazione e asserzione, e degli operatori `AND` e `NOT`. 

1. `Execute`

   Implementare nella classe `Execute` i metodi di visita che sono stati aggiunti in `Visitor<T>` al punto 1. e che implementano la semantica dinamica degli statement di assegnazione e asserzione, e degli operatori `AND` e `NOT`.      

1. Testing

   Utilizzare gli esempi nei seguenti folder per testare il programma (i risultati attesi sono accessibili negli stessi file sotto forma di commenti):
   - `tests/success`: programmi corretti rispetto alla sintassi e ai tipi
   - `tests/failure/static-semantics/`: programmi corretti sintatticamente, ma con errori di tipo
   - `tests/failure/static-semantics-only/`: programmi corretti sintatticamente che sono eseguiti senza errori con l'opzione `-ntc` (no typechecking) nonostante contengano degli errori di tipo
   - `tests/failure/dynamic-semantics/`: programma corretto sintatticamente e staticamente la cui esecuzione solleva un'eccezione di tipo `IntrepreterException` 

   Il metodo `main` della classe `Main` accetta da linea di comando le seguenti opzioni
   - `-i <nome-file>` legge il programma da `<nome-file>` invece che dallo standard input
   - `-o <nome-file>` stampa su `<nome-file>` i risultati dell'esecuzione del programma invece che sullo standard output 
   - `-ntc` l'interprete viene eseguito senza la fase di typechecking.

## Documentazione 
Questa documentazione contiene spiegazioni relative al codice dell'[interprete Java](https://github.com/LPOatDIBRIS/lab09-2025/tree/main/projectLabo) e alla [semantica in F#](https://github.com/LPOatDIBRIS/lab09-2025/tree/main/semantics/projectLabo).
Sono anche consultabili il [diagramma delle classi](https://github.com/LPOatDIBRIS/lab09-2025/blob/main/diagrams) e la [documentazione generata da `javadoc`](https://lpoatdibris.github.io/lab09-2025-doc/).

### Definizione della semantica in F#
Come visto a [lezione](https://2024.aulaweb.unige.it/mod/resource/view.php?id=129435), la semantica statica e dinamica del linguaggio sono
definite ed eseguibili tramite un programma F# contenuto nel file `semantics/Semantics.fs`, con alcuni esempi di test eseguibili in `semantics/Program.fs`. 
Il programma definisce i seguenti elementi:
- la sintassi astratta
- i tipi della semantica statica (i tipi atomici `IntType` e `BoolType` e il tipo composto `PairType`)
- i valori della semantica dinamica (interi, booleani e coppie)
- l'ambiente (environment) per la gestione delle variabili
- le eccezioni che corrispondono ai vari errori della semantica statica e dinamica 
- le funzioni della semantica statica `typecheckProg`, `typecheckStmt`, `typecheckBlock`, `typecheckStmtSeq` e `typecheckExp` 
- le funzioni della semantica dinamica `executeProg`, `executeStmt`, `executeBlock`, `executeStmtSeq`, `evalExp` 
- altre funzioni ausiliarie di utilità.

### Implementazione in Java dell'ambiente delle variabili (package [`projectLabo.environments`](https://lpoatdibris.github.io/lab09-2025-doc/projectLabo/environments/package-summary.html))
La classe generica `Environment<T>` implementa sia l'ambiente statico che quello dinamico.
Un ambiente generico è un oggetto di tipo `java.util.HashMap` dove le chiavi hanno tipo che implementa un dizionario con chiavi di tipo `NamedElement` e valori di tipo generico `T`; il parametro di tipo `T` permette di usare lo stesso codice per gli ambienti statici e dinamici. Nel primo caso `T` corrisponde a `Type` (`StaticEnv` estende `Environment<Type>`), nel secondo caso a `Value` (`DynamicEnv` estende `Environment<Value>`). 

**Nota bene**: l'implementazione delle variabili con il record `Variable` assicura il corretto funzionamento dei metodi della classe `java.util.HashMap` che utilizzano il codice hash delle variabili per le varie operazioni sugli ambienti.
Infatti, nei classi record i metodi `equals` e `hashCode` di `Object ` sono automaticamente ridefiniti in modo che gli oggetti risultino uguali e abbiano lo stesso codice hash se hanno i valori dei campi uguali (nel caso di `Variable`
l'unico campo è `name` di tipo `String`). 

Un ambiente è una lista di tipo `java.util.LinkedList` che contiene i dizionari con le dichiarazioni di variabili nei vari livelli annidati. 
Il primo livello nella lista è quello più annidato, che ha maggiore precedenza, visto che le sue dichiarazioni coprono le eventuali dichiarazioni in livelli più esterni di variabili con lo stesso nome. 
Nella lista seguono nell'ordine livelli sempre più esterni, fino all'ultimo che rappresenta il livello più alto. 

I metodi dell'interfaccia `EnvironmentInterface` sono utilizzati nell'implementazione della semantica statica e dinamica, a parte il metodo `update()` che è richiesto solamente dall'implementazione della semantica dinamica dello statement di assegnazione.

### Implementazione dei tipi in Java (package [`projectLabo.visitors.typechecking`](https://lpoatdibris.github.io/lab09-2025-doc/projectLabo/visitors/typechecking/package-summary.html))
I tipi atomici (ossia che non sono costruiti a partire da altri tipi più semplici) `IntType` e `BoolType` sono rappresentati dalle costanti `enum`  `BOOL` e `INT`   di tipo `AtomicType`, mentre il tipo composto `PairType` è implementato dalla classe record `PairType`.

L'interfaccia `Type` rappresenta l'insieme di tutti i tipi e contiene metodi di default ausiliari che facilitano le operazioni di controllo dei tipi implementati nel typechecker.

### Implementazione dei valori in Java (package [`projectLabo.visitors.execution`](https://lpoatdibris.github.io/lab09-2025-doc/projectLabo/visitors/execution/package-summary.html))
I valori atomici (ossia quelli che non sono costruiti a partire da altri valori più semplici) degli interi e dei booleani sono implementati dalle classi `BoolValue` e `IntValue`, che estendono la classe astratta `AtomicValue<T>`. Il tipo generico `T` dovrebbe venire sostituito solo con classi Java che rappresentano valori atoimici predefiniti 
come `Integer` e `Boolean`. Le coppie di valori sono implementate dalla classe record `PairValue`.

**Importante**: i metodi `equals()` e `hashCode()` in `BoolValue` e `IntValue` sono  ridefiniti per gestire correttamente l'operatore di uguaglianza tra valori. Similmente a quanto commentato per la classe record `PairType`, anche nella classe record `PairValue` tale ridefinizione è gestita automaticamente da Java.
Il metodo `toString()` è stato ridefinito in tutte e tre le classi per implementare correttamente la semantica dello statement `print`.

L'interfaccia `Value` rappresenta l'insieme di tutti i valori e contiene metodi di default di conversione che sono necessari per eseguire le operazioni del linguaggio.

Per esempio, per l'operazione `fst`, il metodo `toPair()` viene usato per controllare
che il valore sia del giusto tipo `PairValue` in modo da poter utilizzare correttamente il metodo `fstVal()`. Oppure, per le operazioni aritmetiche, va prima controllato che i valori
siano di tipo `IntValue` per poter correttamente estrarre i corrispondenti valori (conversione unboxing), eseguire l'operazione e ri-impacchettare il risultato in un valore di tipo `IntValue` (conversione boxing). 

Per questo motivo, i metodi di conversione nell'interfaccia `Value` sollevano un'eccezione di tipo `InterpreterException`. Essi sono ridefiniti nelle classi che implementano `Value`, solo quando la conversione è corretta.
