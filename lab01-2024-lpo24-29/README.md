[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=16800210)
# Laboratorio di LPO, 28 ottobre 2024: F# parte 1 (fino a liste e pattern matching) 

Prima di iniziare, create un progetto F# e apritelo con Visual Studio Code come spiegato nella [pagina AW di supporto per F#](https://2024.aulaweb.unige.it/mod/page/view.php?id=57635).

Per verificare il codice usate la modalità di esecuzione debugging e le asserzioni contenute nel testo degli esercizi e, possibilmente, altre definite da voi.
 
Il  costrutto <code>assert bool-expr</code> viene considerato solo in modalità debugging: viene calcolata l'espressione booleana <code>bool-expr</code>, dopo di che l'esecuzione viene interrotta con un messaggio di errore se il valore calcolato è false, altrimenti prosegue normalmente.

Esempio:
```fsharp
assert (mulAll (2 :: 4 :: 3 :: []) = 24) (* se il risultato non è corretto, allora l'asserzione fallisce *) 
```

Definire in F# le seguenti funzioni, **senza uso** di parametri di accumulazione o funzioni di libreria.

<!--Per stampare sullo standard output il valore di un'espressione è possibile usare la funzione <code>printfn</code>
con il formato generico "%A".

Esempio:
```fsharp
insert 0 [2;4;3] |> printfn "%A"
```
stampa
```fsharp
[2;4;3;0] 
```-->

## Esercizi proposti

1.  `mulAll : int list -> int` 
    
    `mulAll ls` restituisce il prodotto di tutti i numeri interi contenuti nella lista `ls`. 
    
    Esempio:
    ```fsharp
    assert (mulAll (2 :: 4 :: 3 :: []) = 24)
    ```
1. `isIn : 'a -> 'a list -> bool` 
    
    `isIn el ls` restituisce `true` se e solo se `el` è un elemento della lista `ls`. 
    
    Esempio:
    ```fsharp
    assert isIn 3 (2 :: 4 :: 3 :: [])
    assert not (isIn 5 (2 :: 4 :: 3 :: []))
    ```
    Provare a risolvere l'esercizio usando l'operatore `||` invece di `if ... then ... else` e una funzione ricorsiva ausiliaria per evitare di passare a ogni chiamata ricorsiva l'elemento da cercare.

1.  `insert : 'a -> 'a list -> 'a list`
 
    `insert el ls` restituisce la lista ottenuta aggiungendo `el` in fondo alla lista `ls` se `el` non appartiene già a `ls`;
  restituisce `ls` altrimenti.

    Esempio:
    ```fsharp
    assert (insert 0 (2 :: 4 :: 3 :: []) = 2 :: 4 :: 3 :: 0 :: [])
    assert (insert 3 (2 :: 4 :: 3 :: []) = 2 :: 4 :: 3 :: [])
    ```

1. `insert2 : 'a -> 'a list -> 'a list`

    come l'esercizio precedente, ma provare a usare una funzione ricorsiva ausiliaria per evitare di passare a ogni chiamata ricorsiva l'elemento da inserire.
 

1.  `odd : 'a list -> 'a list` 
    
    `odd ls` restituisce la lista ottenuta da `ls` tenendo solo gli elementi di indice dispari, considerando che gli indici iniziano da 0.
    
    Esempio:
    ```fsharp
    assert (odd (7 :: 3 :: 4 :: 1 :: 2 :: 5 :: []) = 3 :: 1 :: 5 :: [])
    ```
    
1.  `ordInsert : 'a -> 'a list -> 'a list` 

    assumendo che `ls` sia già ordinata in modo crescente e senza ripetizioni, `ordInsert el ls` restituisce la lista ordinata in modo crescente e senza ripetizioni ottenuta aggiungendo `el` a `ls`.

    Esempio:
    ```fsharp
    assert (ordInsert 0 (1 :: 2 :: 4 :: 5 :: []) = 0 :: 1 :: 2 :: 4 :: 5 :: [])
    assert (ordInsert 3 (1 :: 2 :: 4 :: 5 :: []) = 1 :: 2 :: 3 :: 4 :: 5 :: [])
    assert (ordInsert 7 (1 :: 2 :: 4 :: 5 :: []) = 1 :: 2 :: 4 :: 5 :: 7 :: [])
    assert (ordInsert 2 (1 :: 2 :: 4 :: 5 :: []) = 1 :: 2 :: 4 :: 5 :: [])
    ```

1.  `ordInsert2 : 'a -> 'a list -> 'a list` 
    come l'esercizio precedente, ma provare a usare una funzione ricorsiva ausiliaria per evitare di passare a ogni chiamata ricorsiva l'elemento da inserire.
    
1.  `merge : 'a list * 'a list -> 'a list` 
    
    assumendo che `ls1` e `ls2` siano già ordinate in modo crescente e senza ripetizioni, `merge (ls1,ls2)` restituisce la lista ordinata in modo crescente e senza ripetizioni ottenuta fondendo assieme `ls1` e `ls2`. 
    
    *Nota*: F# permette di abbreviare le due keyword `else` `if` con la singola `elif`. 

    Esempio:
    ```fsharp
    assert (merge ((1 :: 3 :: 5 :: []), (2 :: 4 :: 6 :: [])) = 1 :: 2 :: 3 :: 4 :: 5 :: 6 :: [])
    assert (merge ((1 :: 2 :: 3 :: []), (4 :: 5 :: [])) = 1 :: 2 :: 3 :: 4 :: 5 :: [])
    assert (merge ((3 :: []), (1 :: 2 :: 4 :: 5 :: [])) = 1 :: 2 :: 3 :: 4 :: 5 :: [])
    ```
1.  `curriedMerge : 'a list -> 'a list -> 'a list` 
    
    definire `curriedMerge`, la versione curried di `merge`, direttamente senza
  usare `merge`.
    
    Esempio:
    ```fsharp
    assert (curriedMerge (1 :: 3 :: 5 :: []) (2 :: 4 :: 6 :: []) = 1 :: 2 :: 3 :: 4 :: 5 :: 6 :: [])
    assert (curriedMerge (1 :: 2 :: 3 :: []) (4 :: 5 :: []) = 1 :: 2 :: 3 :: 4 :: 5 :: [])
    assert (curriedMerge (3 :: []) (1 :: 2 :: 4 :: 5 :: []) = 1 :: 2 :: 3 :: 4 :: 5 :: [])
    ```
