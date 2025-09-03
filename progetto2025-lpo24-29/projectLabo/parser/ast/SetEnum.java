package projectLabo.parser.ast;

import static java.util.Objects.requireNonNull;

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
		return String.format("{(%s %s %s %s) %s}", getClass().getSimpleName(), var, set, getClass().getSimpleName(), elem);
	}
}
