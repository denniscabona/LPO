package lab07.exercise2.checking;

public interface EnvironmentInterface {

	// aggiorna l'ambiente con 'namedElement'
	// restituisce true se e solo se 'namedElement' non era già presente
	boolean declare(NamedElementInterface namedElement);

	// restituisce true se e sole se 'namedElement' è già presente nell'ambiente
	boolean find(NamedElementInterface namedElement);

}