[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=17331437)
# Laboratorio di LPO, 26 novembre 2024: classi e oggetti in Java

## Istruzioni per compilare e eseguire un programma Java (senza uso di package) da linea di comando
Per le attività di laboratorio su Java di questo semestre si consiglia di non utilizzare un IDE (Integrated Developemt Environment),
a meno che non si sia già esperti. È sufficiente impiegare un qualsiasi editor,
installare [Java SE Development Kit 23](https://www.oracle.com/java/technologies/downloads/#JDK23)
e seguire le istruzioni su [AW](https://2024.aulaweb.unige.it/mod/page/view.php?id=57634) per compilare e eseguire 
un programma da linea di comando.

### Esercizio proposto

Utilizzando il codice presente nel repository, completare la classe `CreditAccount` (conto corrente), con le seguenti funzionalità. La classe `TestCreditAccount` permette di eseguire e verificare il codice.
Ulteriori prove possono essere effettuate estendendo il suo metodo `main` con altre asserzioni.

  * ogni oggetto di `CreditAccount` è dotato dei seguenti campi:
    * limite  di giacenza del conto (`limit`) di tipo `int`, modificabile, espresso in centesimi di € e contenuto nell'intervallo compreso tra `limitLowerBound` e `limitUpperBound`;
    * saldo del conto (`balance`) di tipo `int`, modificabile, espresso in centesimi di €, non può essere mai inferiore al limite  di giacenza;
    * intestatario del conto (`owner`), di tipo `PersonInterface`, diverso da `null` e non modificabile.
  * `limitLowerBound` e `limitUpperBound` sono due costanti che definiscono il valore minimo e massimo del limite di giacenza per tutti gli oggetti di `CreditAccount`.
  * la classe definisce i seguenti metodi `private` ausiliari per la validazione dell'invariante di classe e del parametro dei metodi `deposit` e `withdraw`:
    * `PersonInterface requireNonNull(PersonInterface p)`
    * `int requirePositive(int amount)`
    * `int requireBalanceGeqLimit(int balance, int limit)`
    * `int requireLimitInBounds(int limit)`  
  * i seguenti dati sono accessibili dall'esterno solo in lettura tramite corrispondenti metodi read-only (metodi *getter*): `limitLowerBound`, `limitUpperBound`, limite di giacenza, saldo e intestatario.
  * `int deposit(int amount)` deposita sul conto una somma positiva `amount` (espressa in centesimi di €) e restituisce il saldo totale dopo il versamento di tale somma. Solleva un'eccezione e il deposito non viene eseguito se `amount` non è positivo.
  * `int withdraw(int amount)` preleva dal conto una somma positiva `amount` (espressa in centesimi di €)  e restituisce il saldo totale dopo il prelevamento di tale somma. Solleva un'eccezione e il prelievo non viene eseguito se `amount` non è positivo o se il prelievo non è compatibile con il limite di giacenza.
  * `void setLimit(int limit)` modifica il limite  di giacenza del conto. Solleva un'eccezione e la modifica non viene eseguita se `limit` non è contenuto nell'intervallo tra `limitLowerBound` e `limitUpperBound` o il saldo non è compatibile con il nuovo limite.
  * `CrediAccount(int limit, int balance, PersonInterface owner)` inizializza l'oggetto creato con limite di giacenza `limit` (in centesimi di €), saldo iniziale `balance` (in centesimi di €) e intestatario `owner`. Solleva un'eccezione se l'invariante di classe non è verificato.
  * `CreditAccount newOfLimitBalanceOwner(int limit, int balance, PersonInterface owner)` restituisce un nuovo oggetto di `CreditAccount` con limite di giacenza `limit` (in centesimi di €), saldo iniziale `balance` (in centesimi di €) e intestatario `owner`.
  Solleva un'eccezione se l'invariante di classe non è verificato.
  * `CreditAccount newOfBalanceOwner(int balance, PersonInterface owner)` restituisce un nuovo oggetto di tipo `CreditAccount`  con limite di giacenza pari a `limitUpperBound`, saldo iniziale `balance` (in centesimi di €) e intestatario `owner`. Solleva un'eccezione se l'invariante di classe non è verificato.      
