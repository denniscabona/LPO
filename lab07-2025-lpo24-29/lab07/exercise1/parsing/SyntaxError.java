package lab07.exercise1.parsing;

public class SyntaxError extends RuntimeException {

	public SyntaxError(int lineNumber) {
		super("Syntax error at line " + lineNumber);
	}

}
