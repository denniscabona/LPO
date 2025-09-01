# Laboratorio di LPO, 3 dicembre 2024: composizione di oggetti e interfacce in Java

## Istruzioni per compilare e eseguire un programma Java (senza uso di package) da linea di comando
Per le attività di laboratorio su Java di questo semestre si consiglia di non utilizzare un IDE (Integrated Developemt Environment),
a meno che non si sia già esperti. È sufficiente impiegare un qualsiasi editor,
installare [Java SE Development Kit 23](https://www.oracle.com/java/technologies/downloads/#JDK23)
e seguire le istruzioni su [AW](https://2024.aulaweb.unige.it/mod/page/view.php?id=57634) per compilare e eseguire 
un programma da linea di comando.
#### Nota bene
Il codice di questo laboratorio è stato strutturato definendo il package `lab04`, quindi se non si usa un IDE, seguire le istruzioni di [AW](https://2024.aulaweb.unige.it/mod/page/view.php?id=57634) per compilare codice in presenza di package. 

### Esercizio proposto

Utilizzando il codice presente nel repository, completare le seguenti classi.
La classe `Test` permette di eseguire e verificare il codice.
Ulteriori prove possono essere effettuate estendendo il suo metodo `main` con altre asserzioni.

   * Le classi `Circle` e `Rectangle` implementano le interfacce `Shape` e  `Movable`.

     Una figura geometrica di tipo `Movable` ha un proprio punto di "ancoraggio" al piano cartesiano che corrisponde con il centro geometrico della figura. La figura può essere traslata con il metodo `move(double dx,double dy)` lungo il vettore `(dx,dy)`, spostando il suo punto di "ancoraggio".

     I metodi `perimeter()` e `area()` di `Shape` calcolano perimetro e area, mentre `resize(double factor)` permette di modificare le dimensioni della figura geometrica di un fattore `factor`. La modifica di `resize(double factor)` viene apportata solo se l'invariante di classe viene preservato, altrimenti viene sollevata un'eccezione di tipo `IllegalArgumentException`.

     Entrambe le classi definiscono la lunghezza minima ammessa per le dimensioni della figura, che può essere diversa per le due classi, ma uguale per ogni oggetto della stessa classe.

     La classe `Circle` definisce i costruttori `Circle()` (raggio di dimensione minima ammessa e punto di ancoraggio sull'origine) `Circle(double radius, PointInterface anchor)` e `Circle(Circle other)` (deep copy).

     La classe `Rectangle` definisce i costruttori `Rectangle()` (base e altezza di dimensione minima ammessa e punto di ancoraggio sull'origine), `Rectangle(double width, double height, PointInterface anchor)` e `Rectangle(Rectangle other)` (deep copy), insieme al metodo factory `ofWidthHeightAnchor(double width, double height, PointInterface anchor)`.

   * La classe `AreaComparator` implementa l'interfaccia `ShapeComparator` degli oggetti che permettono di confrontare due figure geometriche con il metodo `int compare(Shape shape1, Shape shape2)`. Esso restituisce un numero positivo se `shape1` è maggiore di `shape2`, uno negativo se `shape1` è minore di `shape2`, 0 altrimenti. Gli oggetti della classe `AreaComparator` si basano sul calcolo dell'area per confrontare le due figure geometriche.

   * La classe `Shapes` contiene solo metodi di classe e non provede quindi la necessità di creare oggetti a partire da essa.

     Il metodo `Shape max(ShapeComparator comp, Shape[] shapes)` restituisce il primo elemento in `shapes` che è maggiore o uguale agli altri rispetto all'ordinamento definito da `comp`, assumendo che tale ordinamento sia totale. Se l'array ha lunghezza 0, allora il metodo restituisce `null`. Se `comp == null` o `shapes == null`, allora viene sollevata un'eccezione di tipo `NullPointerExcecption`.

     Il metodo `void move(Movable[] movables, double dx, double dy)` muove gli elementi di `movables` lungo il vettore `(dx,dy)`. Se `movables == null`, allora viene sollevata un'eccezione di tipo `NullPointerExcecption`.

     Il metodo `void resize(Shape[] shapes, double factor)` modifica le dimensioni degli elementi di `shapes` applicando il fattore di scala `factor`. Se `shapes == null`, allora viene sollevata un'eccezione di tipo `NullPointerExcecption`.

     Il metodo `double area(Shape[] shapes)` restituisce la somma delle aree degli elementi di `shapes`. Se `shapes == null`, allora viene sollevata un'eccezione di tipo `NullPointerExcecption`.

