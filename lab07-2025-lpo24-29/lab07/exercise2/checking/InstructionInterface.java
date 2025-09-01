package lab07.exercise2.checking;

public interface InstructionInterface {
	NamedElementInterface namedElement(); // il nome di funzione/variabile usato nell'istruzione

	boolean isDeclaration(); // true se e solo se l'istruzione è una dichiarazione, ossia const/fun/var
	
	int lineNumber(); // il numero di linea dell'istruzione
 
	void check(EnvironmentInterface env); // controlla il corretto uso del nome usato rispetto all'ambiente env
}