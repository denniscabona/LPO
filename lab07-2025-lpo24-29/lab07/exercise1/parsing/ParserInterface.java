package lab07.exercise1.parsing;

import java.io.IOException;

public interface ParserInterface {

    // restituisce true se e solo se è stata letta un'istruzione, ossia il programma non è finito
    // solleva l'eccezione non-controllata SyntaxError in caso di errori di sintassi
	boolean readNext() throws IOException;

}
