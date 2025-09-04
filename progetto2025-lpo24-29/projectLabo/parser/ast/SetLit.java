package projectLabo.parser.ast;

import projectLabo.visitors.Visitor;

public class SetLit implements Exp{
	private final Exp exp;

	public SetLit(Exp exp) {
		this.exp = exp;
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitSetLit(this);
	}    
}
