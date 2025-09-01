package lab07.exercise1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import lab07.exercise1.parsing.Parser;
import lab07.exercise1.parsing.SyntaxError;

public class LangChecker {
	private static Reader tryOpen(String path) throws FileNotFoundException {
		return path == null ? new InputStreamReader(System.in) : new FileReader(path); // Se ho un path uso quello, altrimenti leggo la riga di comando
	}
	public static void main(String[] args) {
		try(var reader = tryOpen(args.length > 0 ? args[0] : null)){ // provo a leggere la riga di comando
			var parser = new Parser(reader); // inizializzo il parser
			var boolHasNoErrors = true; // bool per tenere conto se ci sono errori con il parser
			do{ // controllo tutte le righe di un file per verificarne la correttezza
				try{
					var next = parser.readNext(); // ottengo la riga da controllare
					if(!next) // se non ce ne sono concludo lo scorrimento
						break;
				}catch(SyntaxError e){  // catch per errori di sintassi 
					boolHasNoErrors = false;
					System.err.println("Error from parser: " + e.getMessage());
				}
			}while(true);
			if(boolHasNoErrors)
				System.out.print("Parser has no errors\n");
			
		}catch(IOException e){ // catch per errori di input/output
			System.err.println("Error from input/output" + e.getMessage());
		}catch(Throwable e){ // catch per errori inaspettati
			System.err.println("Error not expected: ");
			e.printStackTrace();
		}
	}
}

