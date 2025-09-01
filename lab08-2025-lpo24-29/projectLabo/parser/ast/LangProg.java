package projectLabo.parser.ast;

import static java.util.Objects.requireNonNull;

public class LangProg implements Prog {
	private final StmtSeq stmtSeq;

	public LangProg(StmtSeq stmtSeq) {
		this.stmtSeq = requireNonNull(stmtSeq);
	}

	@Override
	public String toString() {
		return String.format("%s(%s)", getClass().getSimpleName(), stmtSeq);
	}

}
