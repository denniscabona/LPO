package projectLabo.parser.ast;

import static java.util.Objects.requireNonNull;

public class AssignStmt extends AbstractAssignStmt{
    public AssignStmt(Variable var, Exp exp){
        super(var, exp);
    }    
}
