# Laboratorio di LPO, 3 aprile 2025: parser a discesa ricorsiva con un simbolo di lookahead 

## Scopo del laboratorio

Questo laboratorio è dedicato allo sviluppo di un parser a discesa ricorsiva con un token di lookahead.
I principali obiettivi sono
- la comprensione del codice di un parser di un semplice linguaggio;
- l'estensione del codice preesistente per implementare il parser di un linguaggio più ricco. 

Il materiale didattico di riferimento, oltre alla documentazione accessibile nel repository, consiste nelle slide della [lezione sui parser a discesa ricorsiva](https://2024.aulaweb.unige.it/mod/resource/view.php?id=126750) e sulla [generazione di un albero della sintassi astratta (AST)](https://2024.aulaweb.unige.it/mod/folder/view.php?id=129666).
### Grammatica EBNF del linguaggio
Il progetto Java che si trova nel folder [`projectLabo`](https://github.com/LPOatDIBRIS/lab08-2025/tree/main/projectLabo) implementa un parser che riconosce e genera l'AST del linguaggio definito dalla seguente grammatica EBNF, dove i nomi con tutte lettere maiuscole corrispondono ai tipi di token riconosciuti dal tokenizer usato dal parser:
```
Prog ::= StmtSeq EOF
StmtSeq ::= Stmt (STMT_SEP StmtSeq)?
Stmt ::= VAR IDENT ASSIGN Exp | PRINT Exp | IF OPEN_PAR Exp CLOSE_PAR Block (ELSE Block)? 
Block ::= OPEN_BLOCK StmtSeq CLOSE_BLOCK
Exp ::= Eq (PAIR_OP Eq)*
Eq ::= Add (EQ Add)*
Add ::= Mul (PLUS Mul)*
Mul::= Atom (TIMES Atom)*
Atom ::= FST Atom | SND Atom | MINUS Atom | BOOL | NUM | IDENT | OPEN_PAR Exp CLOSE_PAR
```
Il folder [`esempi`](https://github.com/LPOatDIBRIS/lab08-2025/tree/main/esempi) contiene alcuni programmi corretti sintatticamente.

## Esercizio
**Importante**: prima di iniziare a sviluppare il codice è consigliabile consultare la [documentazione  del codice di partenza](#documentazione).

A partire dal [codice disponibile sul repository](https://github.com/LPOatDIBRIS/lab08-2025/tree/main/projectLabo) sviluppare il parser del linguaggio definito dalla seguente grammatica EBNF:
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
La definizione di `Stmt` è stata estesa con due nuove produzioni:
```
Stmt ::= IDENT ASSIGN Exp // assegnazione
Stmt ::= ASSERT Exp       // asserzione 
```
Per convenienza le due produzioni `VAR IDENT ASSIGN Exp` e `IDENT ASSIGN Exp` sono state fuse nell'unica produzione `VAR? IDENT ASSIGN Exp`.

È stato inoltre aggiunto l'operatore logico binario `AND`, che associa da sinistra è ha precedenza solo sull'operatore `PAIR_OP`.
```
Exp ::= And (PAIR_OP And)*
And ::= Eq (AND Eq)* // operatore binario AND
```
Infine, la definizione di `Atom` è stata estesa con l'aggiunta dell'operatore logico `NOT` che, come tutti gli altri operatori unari, ha precedenza sugli operatori binari.
```
Atom ::= NOT Atom
```
### Linee guida per il completamento dell'esercizio 
1. `TokenType` e `Tokenizer`
   
   Aggiungere in `TokenType` i nuovi tipi di token che devono essere riconosciuti e in `Tokenizer` aggiornare l'inizializzazione di `symbols` e `keywords` con le corrispondenti stringhe: `ASSERT` è associato a `"assert"`, `AND` a `"&&"` e `NOT` a `"!"`.
1. Package `projectLabo.ast`

   Aggiungere le classi che implementano quattro nuovi tipi di nodi: lo statement di assegnazione, lo statement di asserzione, l'espressione con l'operatore `AND` e l'espressione con l'operatore `NOT`.
1. `Parser`

   Modificare la classe per riconoscere il linguaggio esteso e generare il corrispondente AST. Più nel dettaglio ciò comporta:
   - la modifica del metodo `parseStmt()` con la conseguente aggiunta di metodi ausiliari
   - l'aggiunta del metodo `parseAnd()` che implementa la produzione `And ::= Eq (AND Eq)*` e la conseguente modifica di `parseExp()` che deve riflettere il cambiamento nella produzione `Exp ::= And (PAIR_OP And)*`
   - la modifica del metodo `parseAtom()` con la conseguente aggiunta di un metodo ausiliario.

1. Testing
   
   Utilizzare gli esempi nei seguenti folder per testare il programma (i risultati attesi sono accessibili negli stessi file sotto forma di commenti):
   - `tests/success`: programmi corretti sintatticamente
   - `tests/failure`: programmi con errori di sintassi

   Il metodo `main` della classe `Main` legge da linea di comando un argomento opzionale corrispondente al nome del file che contiene il programma da analizzare. Se nessun argomento viene passato, allora l'applicazione legge il programma dallo standard input.

## Documentazione 
Questa documentazione contiene spiegazioni relative al codice nel folder [`projectLabo`](https://github.com/LPOatDIBRIS/lab08-2025/tree/main/projectLabo).
Sono anche consultabili il [diagramma delle classi](https://github.com/LPOatDIBRIS/lab08-2025/blob/main/classDiagram.png) che implementano l'AST e la [documentazione generata da `javadoc`](https://lpoatdibris.github.io/lab08-2025-doc/).
### Tokenizer
Il tokenizer è implementato dalla classe  `projectLabo.parser.Tokenizer` che usa la classe enum `projectLabo.parser.TokenType` che definisce i vari tipi di token. 
La nozione di token è un'astrazione che permette di passare dalla sintassi concreta a quella astratta. Per esempio, il tipo  `STMT_SEP` corrisponde al token che separa due istruzioni;
il parser si riferisce a esso indipendentemente dalla sua sintassi concreta (il simbolo `";"`). In questo modo il codice risulterà più leggibile e più facilmente modificabile: nel caso si volesse usare una rappresentazione diversa, basterà modificare il tokenizer lasciando invariato il codice del parser. 
In modo del tutto simile, `PRINT` corrisponde alla parola chiave usata per l'istruzione di stampa; il parser si riferisce a questo tipo di token indipendentemente dalla sua rappresentazione concreta (la stringa `"print"`).

I tipi `SYMBOL`, `KEYWORD`, `SKIP` sono solo di utilità al tokenizer e non vengono usati dal parser.

I campi di classe `keywords` e `symbols` definiscono i dizionari che associano
alle parole chiavi e ai simboli di operazione il loro corrispondente tipo di token; i simboli sono stringhe di caratteri non alfanumerici, mentre le keyword sono stringhe di lettere che non possono essere usate come identificatori.

Per implementare i dizionari viene usata la classe `HashMap` per `keywords`, mentre per `symbols` è necessario l'uso di `HashTree` per garantire che le chiavi in `symbols` siano in ordine alfabetico decrescente.
Questo evita problemi di ambiguità con simboli che sono il prefisso di altri,
come accade con `=` e `==`. Infatti, poiché l'operatore `|` delle espressioni regolari dà la precedenza al primo operando, l'espressione regolare `==|=` riconosce correttamente i due simboli, mentre `=|==` riconosce erroneamente due simboli consecutivi `=` nel caso della stringa `"=="`.

Il codice genera automaticamente l'espressione regolare usata dal tokenizer e contenuta nel campo di classe `regEx`.
L'espressione regolare definisce i seguenti gruppi:
- `SYMBOL`: i simboli di operazione
- `KEYWORD`: le parole chiave 
- `SKIP`: gli spazi e i commenti che vanno ignorati
- `IDENT`: gli identificatori di variabile 
- `NUM`: i literal di tipo intero 

Il tokenizer implementa i metodi dell'interfaccia `TokenizerInterface` che vengono usati nel parser:
```java
	TokenType next() throws TokenizerException;
	TokenType tokenType();
	String tokenString();
	int intValue();
	boolean boolValue();
	void close() throws IOException;
	int getLineNumber();
```
Il metodo  `next()` avanza nella lettura e può sollevare l'eccezione controllata (checked) `TokenizerException` se il prossimo token non è riconoscito come valido o se ci sono problemi di lettura dello stream di input. Il tipo `SKIP` non viene mai restituito dal metodo `next()` ma è usato internamente al tokenizer
per gestire spazi bianchi e commenti, che vengono scartati.

Il metodo `tokenType()` restituisce il tipo del token appena riconosciuto (coincide con l'ultimo valore restituito da `next()`);
solleva l'eccezione `IllegalStateException` se nessun token è stato riconosciuto.

Il metodo `tokenString()` restituisce la stringa corrispondente al token appena riconosciuto; solleva l'eccezione
`IllegalStateException` se nessun token è stato riconosciuto.

Il metodo `intValue()` restituisce il valore del token di tipo `NUM` che è stato appena riconosciuto; 
 solleva l'eccezione `IllegalStateException` se nessun token di tipo `NUM` è stato riconosciuto.

Il metodo `boolValue()` restituisce il valore del token di tipo `BOOL` che è stato appena riconosciuto; 
 solleva l'eccezione `IllegalStateException` se nessun token di tipo `BOOL` è stato riconosciuto.

Il metodo `close()` è necessario perché la classe `Tokenizer` implementa l'interfaccia `AutoCloseable` per poter utilizzare lo statement `try-with-resources` (vedere il metodo `main` della classe `Main`) e gestire in modo automatico l'apertura e la chiusura dello stream di input; un commento analogo si applica alla classe `Parser`.

Il metodo `getLineNumber()` restituisce la linea corrente analizzata dal tokenizer; tale informazione è utile per rendere più chiari i messaggi di errore di sintassi.

### Implementazione dell'albero della sintassi astratta (AST)
Il package [`projectLabo.parser.ast`](https://github.com/LPOatDIBRIS/lab08-2025/tree/main/tests) contiene l'implementazione dell'albero della sintassi astratta (AST).
L'interfaccia `AST` introduce il tipo più generale che corrisponde a qualsiasi tipo di nodo dell'AST; le sotto-interfacce `Prog`, `StmtSeq`, `Stmt`, `Exp` e `NamedElement`
rappresentano sottotipi di nodi, corrispondenti alle categorie sintattiche
principali. L'interfaccia `AST` contiene il metodo di default `accept()` che permette di visitare i vari nodi dell'albero. In questo laboratorio il metodo
solleva un'eccezione perché **non** deve essere implementato, la sua definizione è rimandata al prossimo laboratorio insieme a quella dell'interfaccia `Visitor`.
 
Alcune classi astratte permettono di fattorizzare codice riutilizzabile:
- `UnaryOp`: codice comune agli operatori unari;
- `BinaryOp`: codice comune agli operatori binari;
- `AtomicLiteral<T>`: codice comune alle foglie che corrispondono a literal di tipo atomico (come `int` e `boolean`);
- `AbstractAssignStmt`: codice riutilizzabile per implementare l'assegnazione.

Per le sequenze di elementi sintattici (per esempio `StmtSeq`) vengono utilizzati i seguenti tipi di nodi:
- il tipo `EmptyStmtSeq` corrisponde a una sequenza vuota di statement, ossia un nodo foglia senza figli.
- il tipo `NonEmptyStmtSeq` corrisponde a una sequenza non vuota di statement, ossia un nodo interno con due figli, uno di tipo `Stmt` e
l'altro di tipo `StmtSeq`.

Entrambe le classi riusano il codice delle classi generiche astratte `EmptySeq` e `NonEmptySeq<FT,RT>`.

#### Importante
Per facilitare il testing, tutte le classi che implementano i nodi dell'AST ridefiniscono il metodo `String toString()` ereditato da `Object` per visualizzare l'AST generato dal parser. A tale scopo vengono usati i metodi predefiniti `getClass()`
e `getSimpleName()` per accedere al nome della classe di un oggetto.
Per esempio, la stampa dell'AST generato dal parser a partire dal programma contenuto nel file [`esempi/prog01.txt`](https://github.com/LPOatDIBRIS/lab08-2025/blob/main/esempi/prog01.txt) produce il termine
```java
 LangProg(NonEmptyStmtSeq(PrintStmt(Add(Minus(IntLiteral(40)),Mul(IntLiteral(5),IntLiteral(3)))),NonEmptyStmtSeq(PrintStmt(Minus(Mul(Add(IntLiteral(40),IntLiteral(5)),IntLiteral(3)))),EmptyStmtSeq)))
```

### Parser
Il codice del parser è guidato dalla grammatica del linguaggio e usa un solo simbolo di lookahead come spiegato a lezione.

La classe definisce i metodi di parsing, ognuno associato con un simbolo non-terminale della grammatica, insieme ai seguenti metodi ausiliari:
```java
String lineErrMsg(String msg) // metodo di utilità per generare messaggi di errore con il numero di linea del codice
<T> T unexpectedTokenError() throws ParserException // metodo di utilità che lancia un'eccezione se nessun token corretto è stato letto
void match(TokenType expected) throws ParserException // controlla che il tipo del token corrente sia uguale a 'expected', solleva un'eccezione in caso negativo
void consume(TokenType expected) throws ParserException // come il metodo 'match', ma dopo il controllo avanza al prossimo token
```
