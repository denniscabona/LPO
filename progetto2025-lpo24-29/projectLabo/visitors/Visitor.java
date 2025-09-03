package projectLabo.visitors;

import projectLabo.parser.ast.Block;
import projectLabo.parser.ast.Exp;
import projectLabo.parser.ast.Stmt;
import projectLabo.parser.ast.StmtSeq;
import projectLabo.parser.ast.Variable;

public interface Visitor<T> {

	T visitLangProg(StmtSeq stmtSeq);

	T visitEmptyStmtSeq();

	T visitNonEmptyStmtSeq(Stmt first, StmtSeq rest);

	T visitIfStmt(Exp exp, Block thenBlock, Block elseBlock);

	T visitPrintStmt(Exp exp);

	T visitVarStmt(Variable var, Exp exp);

	T visitAssignStmt(Variable var, Exp exp);

	T visitAssertStmt(Exp exp);

	T visitForStmt(Exp parenthesisExp, Exp outerExp);	// aggiunta

	T visitWhileStmt(Exp exp, Block block);				// aggiunta

	T visitBlock(StmtSeq stmtSeq);

	T visitAdd(Exp left, Exp right);

	T visitBoolLiteral(boolean value);

	T visitEq(Exp left, Exp right);

	T visitAnd(Exp left, Exp right);

	T visitDiff(Exp left, Exp right);					// aggiunta				

	T visitUnion(Exp left, Exp right);					// aggiunta

	T visitIsIn(Exp left, Exp right);					// aggiunta

	T visitSize(Exp exp);								// aggiunta

	T visitSetLiteral(Exp exp);							// aggiunta

	T visitSetEnum(Variable var, Exp set, Exp elem);	// aggiunta

	T visitFst(Exp exp);

	T visitIntLiteral(int value);

	T visitMinus(Exp exp);

	T visitMul(Exp left, Exp right);

	T visitPairLit(Exp left, Exp right);

	T visitSnd(Exp exp);

	T visitNot(Exp exp); // aggiunta del visitor di Not

	T visitVariable(Variable var); // only in this case more efficient then T visitVariable(String name)

}
