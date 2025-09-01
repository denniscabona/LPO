# Laboratorio di LPO, 10 dicembre 2024: inheritance in Java

## Istruzioni per compilare e eseguire un programma Java (senza uso di package) da linea di comando
Per le attività di laboratorio su Java di questo semestre si consiglia di non utilizzare un IDE (Integrated Developemt Environment),
a meno che non si sia già esperti. È sufficiente impiegare un qualsiasi editor,
installare [Java SE Development Kit 23](https://www.oracle.com/java/technologies/downloads/#JDK23)
e seguire le istruzioni su [AW](https://2024.aulaweb.unige.it/mod/page/view.php?id=57634) per compilare e eseguire 
un programma da linea di comando.
#### Nota bene
Il codice di questo laboratorio è stato strutturato in un package, quindi se non si usa un IDE, seguire le istruzioni di [AW](https://2024.aulaweb.unige.it/mod/page/view.php?id=57634) per compilare codice in presenza di package. 

## Esercizio proposto
Utilizzando il codice presente nel repository, completare la classe `HistoryAccount` che estende `CreditAccount` con le seguenti funzionalità.
La classe `Test` permette di eseguire e verificare il codice.
Ulteriori prove possono essere effettuate estendendo il suo metodo `main` con altre asserzioni.

  * `public HistoryAccount(int limit, int balance, PersonInterface owner)` ha lo stesso comportamento del corrispondente costruttore di `CreditAccount`. 
  * `public HistoryAccount newOfLimitBalanceOwner(int limit, int balance, PersonInterface owner)` ha lo stesso comportamento del metodo corrispondente di `CreditAccount`, a eccezione del fatto che restituisce un nuovo oggetto della classe `HistoryAccount`.
  * `public HistoryAccount newOfBalanceOwner(int balance, PersonInterface owner)` ha lo stesso comportamento del metodo corrispondente di `CreditAccount`, a eccezione del fatto che restituisce un nuovo oggetto della classe `HistoryAccount`.  
  * `limitLowerBound` e `limitUpperBound` sono accessibili dall'esterno solo in lettura tramite corrispondenti metodi read-only (metodi *getter*).    
  * `public int getBalance()`, `public int getLimit()`, `public PersonInterface getOwner()` e `public void setLimit(int limit)` hanno lo stesso comportamento dei metodi di `CreditAccount`.
  * `public int deposit(int amount)` ha lo stesso comportamento del metodo di `CreditAccout`. Inoltre memorizza quest'ultima transazione nel caso debba essere revocata con `undo()` o ripetuta con `redo()`.
  * `public int withdraw(int amount)` ha lo stesso comportamento del metodo di `CreditAccout`. Inoltre memorizza quest'ultima transazione nel caso debba essere revocata con `undo()` o ripetuta con `redo()`.
  * `public int undo()` revoca l'ultima transazione effettuata sul conto e restituisce il corrispondente nuovo bilancio. Solleva un'eccezione della classe `IllegalStateException` se nessuna transazione è stata eseguita prima o se l'ultima transazione è già stata revocata tramite `undo()`.
 * `public int redo()` ripete l'ultima transazione eseguita sul conto e restituisce il corrispondente nuovo bilancio. Solleva un'eccezione della classe `IllegalStateException` se nessuna transazione è stata effettuata prima. 
