[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=18560608)
# Laboratorio di LPO, 5 marzo 2025: iterator pattern

## Esercizi proposti

1.
   Completare le classi `Range` di oggetti iterabili e
   `RangeIterator` dei corrispondenti iteratori.

   Gli oggetti di `Range` sono immutabili e rappresentano sequenze di numeri interi contigui, dove l'estremo inferiore è incluso, mentre quello superiore è escluso.

   Per esempio, `r1 = Range.withStartEnd(-1,3)` rappresenta la sequenza di interi `-1, 0, 1, 2` e
   `r2 = Range.withStartEnd(2,2)` o `r3 = Range.withStartEnd(3,-1)` la sequenza vuota.
   
   Il metodo di classe `Range.withEnd(int end)` crea sequenze con estremo inferiore uguale a 0. Per esempio, `r4 = Range.withEnd(2)` rappresenta la sequenza `0, 1`.

   Il metodo di oggetto `size()` restituisce il numero di elementi della sequenza, quindi `r1.size() == 4`, `r2.size() == r3.size() == 0` e `r4.size() == 2`.

   Poiché gli intervalli sono iterabili, il codice
   ```java
   RangeInterface r = Range.withStartEnd(start,end);
   for(int i : r){...}
   ```
   deve risultare equivalente a
   ```java
   for(int i = start; i < end; i++){...}
   ```
   
   #### Nota bene
   **Non vanno usate liste**, l'implementazione deve avere una complessità spaziale **costante**, indipendente dalla dimensione delle sequenze.
   Per esempio, la quantità di memoria utilizzata per rappresentare `Range.withEnd(1)` e `Range.withEnd(10000)` deve essere la stessa e la minima possibile.

1.
   Completare il metodo di classe `RangeUtility.scalar(RangeInterface r1, RangeInterface r2)` che calcola il prodotto scalare di `r1` e `r2` visti come vettori. Per esempio, `scalar(new Range(1, 4), new Range(2, 5)) == 20` perché i due argomenti rappresentano i vettori `(1,2,3)` e `(2,3,4)`. 
   Se le due sequenze hanno dimensione nulla, allora viene restituito 0, se non hanno la stessa dimensione, allora viene lanciata l'eccezione `IllegalArgumentException`.

   La classe `RangeUtility` contiene anche dei metodi di test; è consigliabile commentare nel main la chiamata `testStepRange()` se si vuole testare la classe `Range` prima di completare `StepRange`. 
1.
   Completare le classi `StepRange` e `StepRangeIterator`, analogamente a quanto fatto nel punto 1.
  
   `StepRange` estende l'espressività di `Range` in modo che la distanza costante `step` tra un elemento della sequenza e quello immediatamente precedente possa essere un qualsiasi numero intero diverso da 0.
   Quindi gli oggetti di tipo `Range` corrispondono a oggetti di tipo `StepRange` nel caso specifico in cui `step==1`. 
    
    Se `step > 0`, allora la sequenza è crescente, mentre è decrescente se `step < 0`.

    Per esempio, `StepRange.withStartEndStep(1, 5, 2)` rappresenta la sequenza `1, 3` e
    `StepRange.withStartEndStep(1,-10,-3)` la sequenza `1, -2, -5, -8`.

    Se `step > 0`, il codice
    ```java
    RangeInterface r = StepRange.withStartEndStep(start, end, step);
    for(int i : r){...}
    ```
    deve risultare equivalente a
    ```java
    for(int i = start; i < end; i+=step){...}
    ```
    e se `step < 0`
    ```java
    RangeInterface r = StepRange.withStartEndStep(start, end, step);
    for(int i : r){...}
    ```
    deve risultare equivalente a
    ```java
    for(int i = start; i > end; i+=step){...}
    ```
