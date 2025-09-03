package projectLabo.parser.ast;

import projectLabo.visitors.Visitor;

import java.util.HashSet;
import java.util.Set;

public class SetLit implements Exp{
	private final Set<Exp> elements;

	public SetLit(Set<Exp> exp) {
		elements = new HashSet<>(exp);
	}

	public Set<Exp> Elements(){
		return elements;
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitSetLit(this);
	}    
}
