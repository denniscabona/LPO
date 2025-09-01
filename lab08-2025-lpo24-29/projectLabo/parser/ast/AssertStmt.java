package projectLabo.parser.ast;

import static java.util.Objects.requireNonNull;

public class AssertStmt implements Stmt{
    private final Exp exp;

    public AssertStmt(Exp exp){
        this.exp = requireNonNull(exp); // l'argomento dell'assert non deve essere nullo
    }

    @Override
    public String toString(){
        return String.format("%s(%s)", getClass().getSimpleName(), exp); // definizione di come viene stampato assert
    }
}
