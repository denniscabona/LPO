package projectLabo;

import static java.lang.System.err;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import projectLabo.parser.Parser;
import projectLabo.parser.Tokenizer;
import projectLabo.parser.ParserException;

public class Main {

	private static BufferedReader tryOpenInput(String inputPath) throws FileNotFoundException {
		return new BufferedReader(inputPath == null ? new InputStreamReader(System.in) : new FileReader(inputPath));
	}

	public static void main(String[] args) {
		try (final var reader = tryOpenInput(args.length > 0 ? args[0] : null);
				final var tokenizer = new Tokenizer(reader);
				final var parser = new Parser(tokenizer)) {
			final var prog = parser.parseProg();
			System.out.println(prog);
		} catch (IOException e) {
			err.println("I/O error: " + e.getMessage());
		} catch (ParserException e) {
			err.println("Syntax error " + e.getMessage());
		} catch (Throwable e) {
			err.println("Unexpected error.");
			e.printStackTrace();
		}

	}

}
