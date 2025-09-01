package lab07.exercise2.checking;

public class VarName extends NamedElement {

	public VarName(String name) {
		super(name);
	}

	@Override
	public String toString() {
	    // completare se si vuole restituire una stringa con più informazioni
		return String.format("variable '%s'", name());
	}
	
	@Override
	public final boolean equals(Object obj) {
	    if(!(obj instanceof VarName))
			return false;
		return super.equals(obj);
	}

	@Override
	public final int hashCode() {
	    return super.hashCode();
	}
}
