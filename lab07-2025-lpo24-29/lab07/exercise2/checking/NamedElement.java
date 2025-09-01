package lab07.exercise2.checking;

import static java.util.Objects.requireNonNull;

public abstract class NamedElement implements NamedElementInterface {

	private final String name;

	public NamedElement(String name) {
		this.name = requireNonNull(name);
	}

	@Override
	public final String name() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) // controllo che i due oggetti siano gli stessi
			return true;
		if (obj instanceof NamedElement obj_name) // controllo che obj sia un'istanza di NamedElement, obj_name serve per il confronto
			return name.equals(obj_name.name);   // e rappresenta il nome passato con obj
		return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode(); // creazione dell'hashCode di name
	}
}
