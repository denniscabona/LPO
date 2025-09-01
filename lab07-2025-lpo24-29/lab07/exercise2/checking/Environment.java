package lab07.exercise2.checking;

import java.util.HashSet;

import static java.util.Objects.requireNonNull;

public class Environment implements EnvironmentInterface {
	private final HashSet<NamedElementInterface> declarations = new HashSet<>();

	@Override
	public boolean declare(NamedElementInterface namedElement) {
	    return declarations.add(namedElement); // aggiunta di namedElement all'hashSet
	}

	@Override
	public boolean find(NamedElementInterface namedElement) {
	    return declarations.contains(namedElement); // ricerca di namedElement nell'hashSet
	}

}
