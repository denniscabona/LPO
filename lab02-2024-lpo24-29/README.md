# Laboratorio di LPO, 11 novembre 2024: F# parte 2 (ricorsione di coda e parametro di accumulazione, List.fold, discriminated union)

Prima di iniziare, potete scegliere di aggiornare il repository GitHub remoto dove salvate il vostro codice 
   1. a mano, utilizzando l'interfaccia web di GitHub
   1. [clonando](https://2024.aulaweb.unige.it/mod/page/view.php?id=79893) il repository remoto in locale.
   
Dopo di che, dovrete creare un progetto F# (all'interno del repository locale se avete seguito la modalità 2. e aprirlo con l'IDE
che preferite (per esempio Visual Studio Code come spiegato nella [pagina AW di supporto per F#](https://2024.aulaweb.unige.it/mod/page/view.php?id=57635)).

**Importante**: ricordatevi di salvare le modifiche sul repository remoto ogni volta che avete concluso un esercizio.

Per verificare il codice usate la modalità di esecuzione debugging e le asserzioni contenute nel testo degli esercizi e, possibilmente, altre definite da voi.
 
L'asserzione <code>assert bool-expr</code> viene controllata **solo in modalità debugging**: viene calcolata l'espressione booleana <code>bool-expr</code>, dopo di che l'esecuzione viene interrotta con un messaggio di errore se il valore calcolato è false, altrimenti prosegue normalmente.

Esempio:
```fsharp
assert (mulAll (2 :: 4 :: 3 :: []) = 24) (* se il risultato non è corretto, allora l'asserzione fallisce *) 
```

## Esercizi proposti

1.  Definire `catAll : string list -> string` tale che `catAll l` restituisce la stringa ottenuta concatenando tutte le stringhe di `l` secondo l'ordine determinato dalla lista. Utilizzare un parametro di accumulazione per ottenere ricorsione di coda. 
	
    Esempio:	
    ```fsharp
    assert (catAll ("This" :: " is " :: "awesome!"::[]) = "This is awesome!")
    ```

1. Definire `catAllFold : string list -> string`, equivalente a `catAll`, ma ottenuta come specializzazione di [`List.fold`](https://fsharp.github.io/fsharp-core-docs/reference/fsharp-collections-listmodule.html#fold).

1. Definire `zip : 'a list -> 'b list -> ('a * 'b) list` tale che

	```fsharp
	zip (a_0::...::a_n::[]) (b_0::...::b_n::[]) = (a_0,b_0)::...::(a_n,b_n)::[]
	```
	La funzione solleva l'eccezione `Failure "different length"` se le due liste hanno lunghezza diversa. Utilizzare un parametro di accumulazione per ottenere ricorsione di coda.

   Esempi: 
   ```fsharp
	assert (zip [] [] = [])
	
	assert (zip (1 :: 2 :: 3 :: []) ("a" :: "b" :: "c" :: []) = (1, "a") :: (2, "b") :: (3, "c") :: [])
	
	assert
	    ((try
	        zip (1 :: 2 :: []) ("a" :: "b" :: "c" :: [])
	      with
	      | Failure "different length" -> []) = [])
   ```	
1.  Definire `minEl : 'a list -> 'a option` tale che `minEl` restituisce `None` se la lista è vuota, altrimenti `Some m`, con `m` il minimo della lista; usare la funzione predefinita `min : 'a -> 'a -> 'a` per semplificare il codice. Utilizzare un parametro di accumulazione per ottenere ricorsione di coda.

    Esempi: 
    ```fsharp
	assert (minEl [] = None)
	
	assert (minEl (3 :: 4 :: 6 :: -1 :: []) = Some -1)
	
	assert (minEl ("orange" :: "apple" :: "banana" :: []) = Some "apple")
    ```

1. Definire `minElFold : 'a list -> 'a option`, equivalente a `minEl`, ma ottenuta usando [`List.fold`](https://fsharp.github.io/fsharp-core-docs/reference/fsharp-collections-listmodule.html#fold).
   
1. Definire il tipo discriminated union `direction` costituito dai quattro punti cardinali `North`, `East`, `South` e `West`.

1. Definire `versor : direction -> int * int` che preso un punto cardinale restituisce il corrispondente versore (vettore unitario) sul piano cartesiano:
   ```fsharp
   assert (versor North = (0,1))
   assert (versor East = (1,0))
   assert (versor South = (0,-1))
   assert (versor West = (-1,0))
   ```
1. Definire il tipo discriminated union `action` costituito dalle seguenti due azioni:
   *  gira verso un punto cardinale.

      Esempi:
      ```fsharp
      Turn North
      Turn West
      ```
   *  fai `n` passi, con `n` numero intero; se `n` è negativo, i passi vanno fatti indietro, se è 0 nessuna azione viene compiuta.

      Esempi:

      ```fsharp
      Step 2
      Step 0
      Step (-1)
      ```
    
 1.  Definire `move : direction * (int * int) -> action -> direction * (int * int)` che, presa una coppia, che definisce la direzione e le coordinate cartesiane iniziali, e presa un'azione, restituisce la coppia costituita dalla direzione e coordinate cartesiane finali ottenute compiendo l'azione. 

     Esempi:
     ```fsharp
     assert (move (North, (0, 0)) (Turn South) = (South, (0, 0)))
     assert (move (North, (0, 0)) (Step 2) = (North, (0, 2)))
     assert (move (North, (0, 0)) (Step -1) = (North, (0, -1)))
     assert (move (North, (0, 0)) (Step 0) = (North, (0, 0)))
     ```	
     *Suggerimento*: per semplificare il codice usare `versor : direction -> int * int` e definire le funzioni ausiliarie `scalar : int -> int * int -> int * int` e `add : int * int -> int * int -> int * int` che calcolano la moltiplicazione per scalare e l'addizione di vettori di numeri interi.
 
1. Usando [`List.fold`](https://fsharp.github.io/fsharp-core-docs/reference/fsharp-collections-listmodule.html#fold), definire la funzione `doAll : direction * (int * int) -> action list -> direction * (int * int)` che, presa una coppia, che definisce la direzione e le coordinate cartesiane iniziali, e presa una lista di azioni, restituisce la coppia costituita dalla direzione e coordinate cartesiane finali ottenute compiendo tutte le azioni della lista nell'ordine.

    Esempio:
    ```fsharp
    assert
    (doAll
        (North, (0, 0))
        (Step 2
         :: Turn East
            :: Step 2
               :: Step -1 :: Turn South :: Step 3 :: Step 0 :: []) = (South, (1, -1)))
    ```
   
1.  (*Difficile*) Dichiarare il tipo discriminated union polimorfo `'a tree` degli alberi con nodi etichettati con valori di tipo `'a` e numero arbitrario di figli.

    Definire la funzione `postOrder : 'a tree -> 'a list` che restituisce la lista delle etichette dell'albero ottenuta tramite visita post-order (prima i nodi figlio da sinistra a destra, poi il nodo radice).
    
    Esempio:
      ```fsharp
      assert
         (Node(
            1,
            (Node(2, [])
               :: Node(3, [])
                  :: Node(4, (Node(5, []) :: Node(6, []) :: [])) :: [])
         ))
         |> postOrder = (2 :: 3 :: 5 :: 6 :: 4 :: 1 :: [])    
      ```
    *Suggerimento*: usare un parametro di accumulazione e [`List.fold`](https://fsharp.github.io/fsharp-core-docs/reference/fsharp-collections-listmodule.html#fold).       La definizione ricorsiva è di coda?  

