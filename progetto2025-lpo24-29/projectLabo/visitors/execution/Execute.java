package projectLabo.visitors.execution;

import java.io.PrintWriter;

import projectLabo.environments.EnvironmentException;
import projectLabo.parser.ast.Block;
import projectLabo.parser.ast.Exp;
import projectLabo.parser.ast.Stmt;
import projectLabo.parser.ast.StmtSeq;
import projectLabo.parser.ast.Variable;
import projectLabo.visitors.Visitor;

import static java.util.Objects.requireNonNull;

public class Execute implements Visitor<Value> {

	private final DynamicEnv env = new DynamicEnv();
	private final PrintWriter printWriter; // output stream used to print values

	public Execute() {
		printWriter = new PrintWriter(System.out, true);
	}

	public Execute(PrintWriter printWriter) {
		this.printWriter = requireNonNull(printWriter);
	}

	// dynamic semantics for programs; no value returned by the visitor

	@Override
	public Value visitLangProg(StmtSeq stmtSeq) {
		try {
			stmtSeq.accept(this);
			// possible runtime errors
			// EnvironmentException: undefined variable
		} catch (EnvironmentException e) {
			throw new InterpreterException(e);
		}
		return null;
	}

	// dynamic semantics for sequences of statements
	// no value returned by the visitor

	@Override
	public Value visitEmptyStmtSeq() {
		return null;
	}

	@Override
	public Value visitNonEmptyStmtSeq(Stmt first, StmtSeq rest) {
		first.accept(this);
		rest.accept(this);
		return null;
	}

	// dynamic semantics for statements; no value returned by the visitor

	@Override
	public Value visitIfStmt(Exp exp, Block thenBlock, Block elseBlock) {
		if (exp.accept(this).toBool())
			thenBlock.accept(this);
		else if (elseBlock != null)
			elseBlock.accept(this);
		return null;
	}

	@Override
	public Value visitPrintStmt(Exp exp) {
		printWriter.println(exp.accept(this));
		return null;
	}

	@Override
	public Value visitVarStmt(Variable var, Exp exp) {
		env.dec(var, exp.accept(this));
		return null;
	}

	@Override // aggiunta la semantica dinanima di AssertStmt, se assert ritorna false lancia AssertionError()
	public Value visitAssertStmt(Exp exp){
		if(exp.accept(this).toBool())
			return null;
		throw new InterpreterException(new AssertionError());
	}

	@Override // aggiunta la semantica dinamica di AssignStmt, aggiorna l'environment con il nuovo valore
	public Value visitAssignStmt(Variable var, Exp exp){
		env.update(var, exp.accept(this));
		return null;
	}

	@Override
	public Value visitBlock(StmtSeq stmtSeq) {
		env.enterLevel();
		stmtSeq.accept(this);
		env.exitLevel();
		return null;
	}

	// dynamic semantics of expressions; a value is returned by the visitor

	@Override
	public IntValue visitAdd(Exp left, Exp right) {
		return new IntValue(left.accept(this).toInt() + right.accept(this).toInt());
	}

	@Override
	public BoolValue visitBoolLiteral(boolean value) {
		return new BoolValue(value);
	}

	@Override
	public BoolValue visitEq(Exp left, Exp right) {
		return new BoolValue(left.accept(this).equals(right.accept(this)));
	}

	@Override // aggiunta  della semantica dinamica di And, tutti e due devono essere true
	public BoolValue visitAnd(Exp left, Exp right){
		return new BoolValue(left.accept(this).toBool() && right.accept(this).toBool());
	}

	@Override
	public Value visitFst(Exp exp) {
		return exp.accept(this).toPair().fstVal();
	}

	@Override
	public IntValue visitIntLiteral(int value) {
		return new IntValue(value);
	}

	@Override
	public IntValue visitMinus(Exp exp) {
		return new IntValue(-exp.accept(this).toInt());
	}

	@Override
	public IntValue visitMul(Exp left, Exp right) {
		return new IntValue(left.accept(this).toInt() * right.accept(this).toInt());
	}

	@Override
	public PairValue visitPairLit(Exp left, Exp right) {
		return new PairValue(left.accept(this), right.accept(this));
	}

	@Override
	public Value visitSnd(Exp exp) {
		return exp.accept(this).toPair().sndVal();
	}

	@Override // aggiunta della semantica dinamica di Not, inverte il bool dell'Exp
	public Value visitNot(Exp exp){
		return new BoolValue(!exp.accept(this).toBool());
	}

	@Override
	public Value visitVariable(Variable var) {
		return env.lookup(var);
	}
}
