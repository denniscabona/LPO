package lab07.exercise2.parsing;

public class SyntaxError extends RuntimeException {

	public SyntaxError(int lineNumber) {
		super("Syntax error at line " + lineNumber);
	}

}
