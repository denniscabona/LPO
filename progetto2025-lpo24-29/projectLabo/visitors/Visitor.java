package projectLabo.visitors;

import projectLabo.parser.ast.*;

public interface Visitor<T> {

	T visitLangProg(StmtSeq stmtSeq);

	T visitEmptyStmtSeq();

	T visitNonEmptyStmtSeq(Stmt first, StmtSeq rest);

	T visitIfStmt(Exp exp, Block thenBlock, Block elseBlock);

	T visitPrintStmt(Exp exp);

	T visitVarStmt(Variable var, Exp exp);

	T visitAssignStmt(Variable var, Exp exp);

	T visitAssertStmt(Exp exp);

	T visitWhileStmt(Exp exp, Block block);				// aggiunta la definizione di visitWhileStmt

	T visitBlock(StmtSeq stmtSeq);

	T visitAdd(Exp left, Exp right);

	T visitBoolLiteral(boolean value);

	T visitEq(Exp left, Exp right);

	T visitAnd(Exp left, Exp right);

	T visitDiff(Exp left, Exp right);					// aggiunta	la definizione di visitDiff	

	T visitUnion(Exp left, Exp right);					// aggiunta la definizione di visitUnion

	T visitIsIn(Exp left, Exp right);					// aggiunta la definizione di visitIsIn

	T visitSize(Exp exp);								// aggiunta la definizione di visitSize

	T visitSetLit(SetLit set);								// aggiunta la definizione di visitSetLit (OPEN_BLOCK Exp CLOSE_BLOCK)

	T visitSetEnum(Variable var, Exp set, Exp elem);	// aggiunta la definizione di visitSetEnum (OPEN_BLOCK (FOR IDENT IN Exp EXP_SEP) Exp CLOSE_BLOCK)

	T visitFst(Exp exp);

	T visitIntLiteral(int value);

	T visitMinus(Exp exp);

	T visitMul(Exp left, Exp right);

	T visitPairLit(Exp left, Exp right);

	T visitSnd(Exp exp);

	T visitNot(Exp exp);

	T visitVariable(Variable var); // only in this case more efficient then T visitVariable(String name)

}
