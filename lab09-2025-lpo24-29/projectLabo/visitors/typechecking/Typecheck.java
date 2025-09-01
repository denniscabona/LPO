package projectLabo.visitors.typechecking;

import static projectLabo.parser.TokenType.PAIR_OP;
import static projectLabo.visitors.typechecking.AtomicType.*;

import projectLabo.environments.EnvironmentException;
import projectLabo.parser.ast.Block;
import projectLabo.parser.ast.Exp;
import projectLabo.parser.ast.Stmt;
import projectLabo.parser.ast.StmtSeq;
import projectLabo.parser.ast.Variable;
import projectLabo.visitors.Visitor;

public class Typecheck implements Visitor<Type> {
	// aggiungere i metodi mancanti

	private final StaticEnv env = new StaticEnv();

	// useful to typecheck binary operations where operands must have the same type
	private void checkBinOp(Exp left, Exp right, Type type) {
		type.checkEqual(left.accept(this));
		type.checkEqual(right.accept(this));
	}

	// static semantics for programs; no value returned by the visitor

	@Override
	public Type visitLangProg(StmtSeq stmtSeq) {
		try {
			stmtSeq.accept(this);
		} catch (EnvironmentException e) { // undeclared variable
			throw new TypecheckerException(e);
		}
		return null;
	}

	// static semantics for sequences of statements
	// no value returned by the visitor

	@Override
	public Type visitEmptyStmtSeq() {
		return null;
	}

	@Override
	public Type visitNonEmptyStmtSeq(Stmt first, StmtSeq rest) {
		first.accept(this);
		rest.accept(this);
		return null;
	}
	
	// static semantics for statements; no value returned by the visitor

	@Override
	public Type visitIfStmt(Exp exp, Block thenBlock, Block elseBlock) {
		BOOL.checkEqual(exp.accept(this));
		thenBlock.accept(this);
		if (elseBlock != null)
			elseBlock.accept(this);
		return null;
	}
	
	@Override
	public Type visitPrintStmt(Exp exp) {
		exp.accept(this);
		return null;
	}

	@Override
	public Type visitVarStmt(Variable var, Exp exp) {
		env.dec(var, exp.accept(this));
		return null;
	}

	@Override // aggiunta della semantica statica di AssertStmt
	public Type visitAssertStmt(Exp exp){
		BOOL.checkEqual(exp.accept(this));
		return null;
	}

	@Override // aggiunta della semantica statica di AssignStmt
	public Type visitAssignStmt(Variable var, Exp exp){
		var found = env.lookup(var); // controllo che la variabile sia dichiarata
		found.checkEqual(exp.accept(this));
		return null;
	}

	@Override
	public Type visitBlock(StmtSeq stmtSeq) {
		env.enterLevel();
		stmtSeq.accept(this);
		env.exitLevel();
		return null;
	}

	// static semantics of expressions; a type is returned by the visitor

	@Override
	public AtomicType visitAdd(Exp left, Exp right) {
		checkBinOp(left, right, INT);
		return INT;
	}

	@Override
	public AtomicType visitBoolLiteral(boolean value) {
		return BOOL;
	}

	@Override
	public AtomicType visitEq(Exp left, Exp right) {
		left.accept(this).checkEqual(right.accept(this));
		return BOOL;
	}

	@Override  // aggiunta della semantica statica di And
	public AtomicType visitAnd(Exp left, Exp right){
		checkBinOp(left, right, BOOL);
		return BOOL;
	}

	@Override
	public Type visitFst(Exp exp) {
		return exp.accept(this).toPairType().fstType();
	}

	@Override
	public AtomicType visitIntLiteral(int value) {
		return INT;
	}

	@Override
	public AtomicType visitMinus(Exp exp) {
		INT.checkEqual(exp.accept(this));
		return INT;
	}
	
	@Override
	public AtomicType visitMul(Exp left, Exp right) {
		checkBinOp(left, right, INT);
		return INT;
	}

	@Override
	public PairType visitPairLit(Exp left, Exp right) {
		return new PairType(left.accept(this), right.accept(this));
	}

	@Override
	public Type visitSnd(Exp exp) {
		return exp.accept(this).toPairType().sndType();
	}

	@Override // aggiunta della semantica statica di Not
	public Type visitNot(Exp exp){
		BOOL.checkEqual(exp.accept(this));
		return BOOL;
	}
	
	@Override
	public Type visitVariable(Variable var) {
		return env.lookup(var);
	}

}
