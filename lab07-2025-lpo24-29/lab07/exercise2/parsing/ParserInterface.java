package lab07.exercise2.parsing;

import java.io.IOException;

import lab07.exercise2.checking.InstructionInterface;

public interface ParserInterface {

    // restituisce null se il programma è finito 
    // solleva l'eccezione non-controllata SyntaxError in caso di errori di sintassi
	InstructionInterface readNext() throws IOException;

}
