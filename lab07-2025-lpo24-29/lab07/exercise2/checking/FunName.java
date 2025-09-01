package lab07.exercise2.checking;

public class FunName extends NamedElement {

	public FunName(String name) {
		super(name);
	}

	@Override
	public String toString() {
		return String.format("function '%s'", name()); // ritorna una stringa con formato personalizzato
	}

	@Override
	public final boolean equals(Object obj) {
		if(!(obj instanceof FunName)) // controllo che obj non sia un istanza di FunName
			return false;
		return super.equals(obj); // essendo una ridichiarazione non serve riscriverla tutta

	}

	@Override
	public final int hashCode() {
	    return super.hashCode(); // essendo una ridichiarazione non serve riscriverla tutta
	}
}
