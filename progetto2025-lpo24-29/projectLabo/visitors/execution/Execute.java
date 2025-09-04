package projectLabo.visitors.execution;

import java.io.PrintWriter;

import projectLabo.environments.EnvironmentException;
import projectLabo.parser.ast.Block;
import projectLabo.parser.ast.Exp;
import projectLabo.parser.ast.SetLit;
import projectLabo.parser.ast.Stmt;
import projectLabo.parser.ast.StmtSeq;
import projectLabo.parser.ast.Variable;
import projectLabo.visitors.Visitor;
import projectLabo.visitors.typechecking.SetType;
import projectLabo.visitors.typechecking.TypecheckerException;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static projectLabo.parser.TokenType.BOOL;
import static projectLabo.visitors.typechecking.AtomicType.BOOL;
import static projectLabo.visitors.typechecking.AtomicType.INT;

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

	@Override
	public Value visitAssertStmt(Exp exp){
		if(exp.accept(this).toBool())
			return null;
		throw new InterpreterException(new AssertionError());
	}

	@Override 
	public Value visitAssignStmt(Variable var, Exp exp){
		env.update(var, exp.accept(this));
		return null;
	}

	@Override	// aggiunta la semantica dinamica di WhileStmt
	public Value visitWhileStmt(Exp exp, Block block){
		while(exp.accept(this).toBool()){ // controllo che exp sia sempre vero in questo ambiente
			env.enterLevel(); // crea un nuovo ambiente per ogni iterazione
			try{
				block.accept(this); 
			}finally{	// serve per evitare anche in caso di errori venga sempre eseguito env.exitLevel()
				env.exitLevel(); // esce dall'ambiente appena creato
			}
		}
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

	@Override	// aggiunta la semantica dinamica di Diff
	public Value visitDiff(Exp left, Exp right){
		SetValue leftSet = left.accept(this).toSet(); // salvataggio dei literal dei parametri (INT, BOOL, PAIR o SET) e salva come SetValue
		SetValue rightSet = right.accept(this).toSet();

		Set<Value> result = new HashSet<>(leftSet.values()); // creazione dell'insieme risultante
		result.removeAll(rightSet.values());
		return new SetValue(result); // ritorna un nuovo SetValue, il tipo degli elementi coincide con i due tipi degli insiemi dei parametri
	}

	@Override	// aggiunta la semantica dinamica di Union
	public Value visitUnion(Exp left, Exp right){
		SetValue leftSet = left.accept(this).toSet(); // salvataggio dei literal dei parametri (INT, BOOL, PAIR o SET) e salva come SetValue
		SetValue rightSet = right.accept(this).toSet();

		Set<Value> result = new HashSet<>(leftSet.values()); // creazione dell'insieme risultante
		result.addAll(rightSet.values());
		return new SetValue(result); // ritorna un nuovo SetValue, il tipo degli elementi coincide con i due tipi degli insiemi dei parametri
	}

	@Override	// aggiunta della dinamica statica di IsIN
	public Value visitIsIn(Exp elem, Exp set){
		Value givenElem = elem.accept(this); // salvataggio del literal di elem (INT, BOOL, PAIR o SET)
		SetValue givenSet = set.accept(this).toSet(); // salvataggio del literal di set (INT, BOOL, PAIR o SET) e salva come SetValue
		return new BoolValue(givenSet.values().contains(givenElem)); // ritorna true se è presente, false altrimenti
	}

	@Override	// aggiunta la dinamica statica di Size
	public Value visitSize(Exp exp){
		SetValue setValue = exp.accept(this).toSet(); // salvataggio del literal di exp (INT, BOOL, PAIR o SET)
		return new IntValue(setValue.values().size());
	}

	@Override	// aggiunta la semantica dinamica di SetLit (OPEN_BLOCK Exp CLOSE_BLOCK)
	public Value visitSetLit(Exp exp){
		Value elem = exp.accept(this); // salvataggio del literal di exp (INT, BOOL, PAIR o SET)
		return new SetValue(Set.of(elem)); // creazione di un insieme con solo elem
	}

	@Override	// aggiunta la semantica dinamica di SetEnum (OPEN_BLOCK (FOR IDENT IN Exp EXP_SEP) Exp CLOSE_BLOCK)
	public Value visitSetEnum(Variable var, Exp set, Exp elem){
		
		SetValue setValue = set.accept(this).toSet();
		
		DynamicEnv newEnv = env;
		newEnv.enterLevel();
		newEnv.dec(var, new IntValue(0));

		Set<Value> resultSet = new HashSet<>();
		for(Value setElem : setValue.values()){
			newEnv.update(var, setElem);
			Value elemValue = elem.accept(this);
			resultSet.add(elemValue);
		}

		return new SetValue(resultSet);
	}

	@Override
	public Value visitVariable(Variable var) {
		return env.lookup(var);
	}
}
