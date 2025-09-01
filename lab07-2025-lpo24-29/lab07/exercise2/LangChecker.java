package lab07.exercise2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import lab07.exercise2.checking.CheckerError;
import lab07.exercise2.checking.Environment;
import lab07.exercise2.parsing.Parser;
import lab07.exercise2.parsing.SyntaxError;

public class LangChecker {
	private static Reader tryopen(String path) throws FileNotFoundException {
		return path == null ? new InputStreamReader(System.in) : new FileReader(path); // Se ho un path uso quello,
																					// altrimenti leggo daa riga di comando
	}

	public static void main(String[] args) {
	    try(var reader = tryopen(args.length > 0 ? args[0] : null)){ //tetntativo di leggere gli argomenti da riga di comando 
			var parser = new Parser(reader); // inizializzazione di un nuovo parser secondo reader 
			var environment = new Environment(); // inizializzazione di un nuovo Environment per il controllo semantico
			boolean boolHasNoErrors = true; // boolean per tenere conto se ci sono errori da Parser/Checker
			do{ // controllo di tutte le righe di un file per verificarne la correttezza
				try{
					var next = parser.readNext(); // salvataggio della riga da controllare
					if(next == null) // se non esiste allora termina il ciclo do-while
						break;
					next.check(environment); // controllo della semantica
				}catch(SyntaxError e){ // caso in cui avvengano errori SyntaxError
					boolHasNoErrors = false;
					System.err.println("Error from parser: " + e.getMessage());
				}catch(CheckerError e){ // caso in cui avvengano errori CheckError
					boolHasNoErrors = false;
					System.err.println("Error from checker: " + e.getMessage());
				}
			}while(true);
			if(boolHasNoErrors)
				System.out.println("Checker and Parser have no errors\n");
		}catch(IOException e){ // caso in cui avvengano errori IOException
			System.err.println("Error from input/output: " + e.getMessage());
		}catch(Throwable e){ // caso di errori non preveduti
			System.err.println("Error not expected: ");
			e.printStackTrace();
		}
	}

}
