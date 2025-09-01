package lab07.exercise2.checking;

public class CheckerError extends RuntimeException {

	public CheckerError(String message, int lineNumber) {
		super(String.format("Type error at line %d, %s", lineNumber, message));
	}

	public static CheckerError undeclared(NamedElementInterface entity, int lineNumber) {
		throw new CheckerError(entity + " not declared", lineNumber);
	}
	
	public static CheckerError redeclared(NamedElementInterface entity, int lineNumber) {
		throw new CheckerError(entity + " already declared", lineNumber);
	}

}
