package projectLabo.parser.ast;

import static java.util.Objects.requireNonNull;

import projectLabo.visitors.Visitor;

public class SetEnum implements Exp{
    private final Variable var;
    private final Exp set;
    private final Exp elem;

    public SetEnum(Variable var, Exp set, Exp elem){
        this.var = requireNonNull(var);
        this.set = requireNonNull(set);
        this.elem = requireNonNull(elem);
    }

	@Override
	public String toString() {
		return String.format("{(FOR %s IN %s |) %s}", var, set, elem);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitSetEnum(var, set, elem);
	}
}
